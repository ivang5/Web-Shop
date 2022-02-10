import React, { useEffect, useRef, useState } from "react";
import jwt_decode from "jwt-decode";
import profileImg from "../img/default-user.png";
import { useAuth } from "../contexts/AuthContext";
import useFetch from "../utils/useFetch";
import { Modal, Toast } from "bootstrap";
import { dateFormatter } from "../utils/utils";
import Review from "../components/Review";

export default function Profile() {
  const [userData, setUserData] = useState();
  const [error, setError] = useState();
  const [toastMessage, setToastMessage] = useState();
  const [reviews, setReviews] = useState([]);
  const firstName = useRef();
  const lastName = useRef();
  const address = useRef();
  const name = useRef();
  const email = useRef();
  const currentPassword = useRef();
  const password = useRef();
  const repeatedPassword = useRef();
  const modal = useRef();
  const modalPass = useRef();
  const modalError = useRef();
  const toast = useRef();
  const { user, setUser, setAuthTokens, getRole } = useAuth();
  const api = useFetch();

  useEffect(() => {
    getUser();
  }, []);

  useEffect(() => {
    if (!userData) return;
    if (getRole() === "seller") getReviews();
  }, [userData]);

  const getUser = async () => {
    const { response, data } = await api(
      `/shop/users/${getRole()}/${user.sub}`
    );

    if (response.status === 200) {
      setUserData(data);
    }
  };

  const getReviews = async () => {
    const { response, data } = await api(`/shop/orders/seller/${userData.id}`);

    if (response.status === 200) {
      setReviews(data);
    }
  };

  const changeProfile = async () => {
    let changedUser = getChangedUser();
    await api(`/shop/users/${getRole()}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(changedUser),
    });

    setToastMessage("Profile successfully updated.");
    getUser();
    showToast();
  };

  const changePassword = async () => {
    if (password.current.value !== repeatedPassword.current.value) {
      setError("Please make sure your new passwords match.");
      showErrorModal();
      return;
    }

    const credentials = {
      username: userData.username,
      password: currentPassword.current.value,
    };
    const isCurrentPwCorrect = await TryTologinUser(credentials);

    if (!isCurrentPwCorrect) {
      setError(
        "Please make sure you have entered the correct current password."
      );
      showErrorModal();
      return;
    }

    let changedUser = getChangedUser("password");
    await api(`/shop/users/${getRole()}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(changedUser),
    });

    setToastMessage("Password successfully updated.");
    showToast();
  };

  const getChangedUser = (type = "profile") => {
    let changedUser;

    if (getRole() === "admin") {
      changedUser = {
        id: userData.id,
        firstName:
          type === "profile" ? firstName.current.value : userData.firstName,
        lastName:
          type === "profile" ? lastName.current.value : userData.lastName,
        username: userData.username,
        password: type === "profile" ? "" : password.current.value,
        blocked: false,
      };
    } else if (getRole() === "buyer") {
      changedUser = {
        id: userData.id,
        firstName:
          type === "profile" ? firstName.current.value : userData.firstName,
        lastName:
          type === "profile" ? lastName.current.value : userData.lastName,
        username: userData.username,
        password: type === "profile" ? "" : password.current.value,
        blocked: false,
        address: type === "profile" ? address.current.value : userData.address,
      };
    } else {
      changedUser = {
        id: userData.id,
        firstName:
          type === "profile" ? firstName.current.value : userData.firstName,
        lastName:
          type === "profile" ? lastName.current.value : userData.lastName,
        username: userData.username,
        password: type === "profile" ? "" : password.current.value,
        blocked: false,
        address: type === "profile" ? address.current.value : userData.address,
        name: type === "profile" ? name.current.value : userData.name,
        email: type === "profile" ? email.current.value : userData.email,
        operatesFrom: userData.operatesFrom,
      };
    }

    return changedUser;
  };

  const TryTologinUser = async (credentials) => {
    try {
      const response = await fetch("http://localhost:8080/shop/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(credentials),
      });
      const data = await response.json();

      if (response.status === 200) {
        setAuthTokens(data);
        setUser(jwt_decode(data.access_token));
        localStorage.setItem("authTokens", JSON.stringify(data));
        return true;
      } else {
        return false;
      }
    } catch (error) {
      return false;
    }
  };

  const showModal = () => {
    handleModal(modal, "show");
  };

  const changeToProfileModal = () => {
    handleModal(modalPass, "hide");
    handleModal(modal, "show");
  };

  const changeToPasswordModal = () => {
    handleModal(modal, "hide");
    handleModal(modalPass, "show");
  };

  const showErrorModal = () => {
    handleModal(modalError, "show");
  };

  const handleModal = (modalType, visibilityOption) => {
    const modalEle = modalType.current;
    const bsModal = new Modal(modalEle, {
      backdrop: "static",
      keyboard: false,
    });

    if (visibilityOption === "show") {
      bsModal.show();
    } else {
      bsModal.hide();
    }
  };

  const showToast = () => {
    const toastEle = toast.current;
    const bsToast = new Toast(toastEle, {
      backdrop: "static",
      keyboard: false,
    });
    bsToast.show();
  };

  return (
    <>
      {userData && (
        <>
          <div className="container">
            <div className="row pt-5 profile">
              <div className="col-12 col-md-4 profile__image-wrapper">
                <img
                  className="profile__image"
                  src={profileImg}
                  alt="profile"
                />
              </div>
              <div className="col-12 col-md-8 profile__info">
                <div className="card text-dark bg-light mb-3">
                  <div className="card-header profile__info-header">
                    Profile overview
                  </div>
                  <div className="card-body">
                    <h5 className="card-title text-capitalize">
                      {userData.username}
                    </h5>
                    <ul className="list-group list-group-flush">
                      <li className="list-group-item mt-2">
                        First name: {userData.firstName}
                      </li>
                      <li className="list-group-item mt-2">
                        Last name: {userData.lastName}
                      </li>
                      {(getRole() === "seller" || getRole() === "buyer") && (
                        <li className="list-group-item mt-2">
                          Address: {userData.address}
                        </li>
                      )}
                      {getRole() === "seller" && (
                        <>
                          <li className="list-group-item mt-2">
                            Company name: {userData.name}
                          </li>
                          <li className="list-group-item mt-2">
                            Contact: {userData.email}
                          </li>
                          <li className="list-group-item mt-2">
                            Operates from:{" "}
                            {dateFormatter(userData.operatesFrom)}
                          </li>
                        </>
                      )}
                    </ul>
                    <button
                      className="btn btn-outline-primary mt-3"
                      onClick={showModal}
                    >
                      <i className="bi bi-pencil"></i> Edit
                    </button>
                  </div>
                </div>
              </div>
            </div>
            {getRole() === "seller" && (
              <div className="seller__reviews pt-5">
                <h3>Customer reviews</h3>
                {reviews.length === 0 ? (
                  <p>There aren't any reviews for this seller yet!</p>
                ) : (
                  reviews.map((review) => {
                    return (
                      <Review
                        key={review.id}
                        id={review.id}
                        comment={review.comment}
                        rate={review.rate}
                        time={review.time}
                        anonymousComment={review.anonymousComment}
                        buyer={review.buyer}
                        authorized={true}
                        getReviews={getReviews}
                      />
                    );
                  })
                )}
              </div>
            )}
          </div>
          <div className="modal fade" id="profileModal" ref={modal}>
            <div className="modal-dialog modal-dialog-centered">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">Edit profile</h5>
                  <button
                    type="button"
                    className="btn-close"
                    data-bs-dismiss="modal"
                    aria-label="Close"
                  ></button>
                </div>
                <div className="modal-body">
                  <form>
                    <div className="form-floating mb-3">
                      <input
                        type="text"
                        className="form-control"
                        id="firstNameInput"
                        required
                        placeholder="First name"
                        ref={firstName}
                        defaultValue={userData.firstName}
                      />
                      <label htmlFor="firstNameInput">First name</label>
                    </div>
                    <div className="form-floating mb-3">
                      <input
                        type="text"
                        className="form-control"
                        id="lastNameInput"
                        required
                        placeholder="Last name"
                        ref={lastName}
                        defaultValue={userData.lastName}
                      />
                      <label htmlFor="lastNameInput">Last name</label>
                    </div>
                    {(getRole() === "seller" || getRole() === "buyer") && (
                      <div className="form-floating mb-3">
                        <input
                          type="text"
                          className="form-control"
                          id="addressInput"
                          required
                          placeholder="Address"
                          ref={address}
                          defaultValue={userData.address}
                        />
                        <label htmlFor="addressInput">Address</label>
                      </div>
                    )}
                    {getRole() === "seller" && (
                      <>
                        <div className="form-floating mb-3">
                          <input
                            type="text"
                            className="form-control"
                            id="businessInput"
                            required
                            placeholder="Company"
                            ref={name}
                            defaultValue={userData.name}
                          />
                          <label htmlFor="businessInput">Company</label>
                        </div>
                        <div className="form-floating mb-3">
                          <input
                            type="email"
                            className="form-control"
                            id="emailInput"
                            required
                            placeholder="Email"
                            ref={email}
                            defaultValue={userData.email}
                          />
                          <label htmlFor="emailInput">Email</label>
                        </div>
                      </>
                    )}
                  </form>
                </div>
                <div className="modal-footer profile__modal-footer">
                  <button
                    className="btn btn-primary"
                    type="button"
                    data-bs-toggle="modal"
                    onClick={changeToPasswordModal}
                  >
                    <i className="bi bi-lock pe-2"></i>
                    Edit password
                  </button>
                  <div className="profile__modal-btn-group">
                    <button
                      type="button"
                      className="btn btn-secondary"
                      data-bs-dismiss="modal"
                    >
                      <i className="bi bi-x-circle pe-2"></i>
                      Close
                    </button>
                    <button
                      type="button"
                      className="btn btn-success"
                      data-bs-dismiss="modal"
                      onClick={changeProfile}
                    >
                      <i className="bi bi-save pe-2"></i>
                      Save
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="modal fade" id="passwordModal" ref={modalPass}>
            <div className="modal-dialog modal-dialog-centered">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">Edit password</h5>
                  <button
                    type="button"
                    className="btn-close"
                    data-bs-dismiss="modal"
                    aria-label="Close"
                  ></button>
                </div>
                <div className="modal-body">
                  <form>
                    <div className="form-floating mb-3">
                      <input
                        type="password"
                        className="form-control"
                        id="currentPasswordInput"
                        required
                        placeholder="Current password"
                        ref={currentPassword}
                      />
                      <label htmlFor="currentPasswordInput">
                        Current password
                      </label>
                    </div>
                    <div className="form-floating mb-3">
                      <input
                        type="password"
                        className="form-control"
                        id="passwordInput"
                        required
                        placeholder="Password"
                        ref={password}
                      />
                      <label htmlFor="passwordInput">New password</label>
                    </div>
                    <div className="form-floating mb-3">
                      <input
                        type="password"
                        className="form-control"
                        id="repeatPasswordInput"
                        required
                        placeholder="Repeat password"
                        ref={repeatedPassword}
                      />
                      <label htmlFor="repeatPasswordInput">
                        Repeat new password
                      </label>
                    </div>
                  </form>
                </div>
                <div className="modal-footer profile__modal-footer">
                  <button
                    className="btn btn-primary"
                    type="button"
                    data-bs-toggle="modal"
                    onClick={changeToProfileModal}
                  >
                    <i className="bi bi-person pe-2"></i>
                    Edit Profile
                  </button>
                  <div className="profile__modal-btn-group">
                    <button
                      type="button"
                      className="btn btn-secondary"
                      data-bs-dismiss="modal"
                    >
                      <i className="bi bi-x-circle pe-2"></i>
                      Close
                    </button>
                    <button
                      type="button"
                      className="btn btn-success"
                      data-bs-dismiss="modal"
                      onClick={changePassword}
                    >
                      <i className="bi bi-save pe-2"></i>
                      Save
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
          <div className="modal fade" ref={modalError}>
            <div className="modal-dialog">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">Validation error</h5>
                  <button
                    type="button"
                    className="btn-close"
                    data-bs-dismiss="modal"
                    aria-label="Close"
                  ></button>
                </div>
                <div className="modal-body">
                  <p>{error}</p>
                </div>
                <div className="modal-footer">
                  <button
                    type="button"
                    className="btn btn-secondary"
                    data-bs-dismiss="modal"
                    onClick={changeToPasswordModal}
                  >
                    Close
                  </button>
                </div>
              </div>
            </div>
          </div>
          <div
            className="position-fixed bottom-0 end-0 p-3"
            style={{ zIndex: "11" }}
          >
            <div
              id="liveToast"
              className="toast hide"
              role="alert"
              aria-live="assertive"
              aria-atomic="true"
              ref={toast}
            >
              <div className="toast-header">
                <strong className="me-auto">Web Shop</strong>
                <small>Just now</small>
                <button
                  type="button"
                  className="btn-close"
                  data-bs-dismiss="toast"
                  aria-label="Close"
                ></button>
              </div>
              <div className="toast-body">{toastMessage}</div>
            </div>
          </div>
        </>
      )}
    </>
  );
}
