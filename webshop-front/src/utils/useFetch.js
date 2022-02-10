import jwt_decode from "jwt-decode";
import dayjs from "dayjs";
import { useAuth } from "../contexts/AuthContext";

const useFetch = () => {
  let { authTokens, setAuthTokens, setUser, logoutUser } = useAuth();

  const baseURL = "http://localhost:8080";

  const originalRequest = async (url, config) => {
    url = `${baseURL}${url}`;
    const response = await fetch(url, config);
    let data = "";

    try {
      data = await response.json();
    } catch (error) {
      console.error(error);
    }

    return { response, data };
  };

  const refreshToken = async (authTokens) => {
    const response = await fetch(
      "http://localhost:8080/shop/users/token/refresh",
      {
        method: "GET",
        headers: { Authorization: `Bearer ${authTokens.refresh_token}` },
      }
    );
    const data = await response.json();

    if (response.status === 403) {
      logoutUser();
      return null;
    }

    localStorage.setItem("authTokens", JSON.stringify(data));
    setAuthTokens(data);
    setUser(jwt_decode(data.access_token));
    return data;
  };

  const callFetch = async (url, config = {}) => {
    const user = jwt_decode(authTokens.access_token);
    const isExpired = dayjs.unix(user.exp).diff(dayjs()) < 1;

    if (isExpired) {
      authTokens = await refreshToken(authTokens);
    }

    if (config["method"] === "POST" || config["method"] === "PUT") {
      config["headers"] = {
        "Content-Type": "application/json",
        Authorization: `Bearer ${authTokens?.access_token}`,
      };
    } else {
      config["headers"] = {
        Authorization: `Bearer ${authTokens?.access_token}`,
      };
    }

    const { response, data } = await originalRequest(url, config);
    return { response, data };
  };

  return callFetch;
};

export default useFetch;
