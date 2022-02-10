import React, { useRef, useState } from "react";
import { Modal, Toast } from "bootstrap";
import { useAuth } from "../contexts/AuthContext";

export default function Login() {
  const username = useRef();
  const password = useRef();
  const usernameReg = useRef();
  const passwordReg = useRef();
  const repeatedPassword = useRef();
  const firstName = useRef();
  const lastName = useRef();
  const address = useRef();
  const business = useRef();
  const email = useRef();
  const [userType, setUserType] = useState("buyer");
  const [visibility, setVisibility] = useState("hidden");
  const [classNames, setClassNames] = useState(
    "container-fluid login-container"
  );
  const modal = useRef();
  const toast = useRef();
  const { loginUser, registerUser } = useAuth();

  const addClass = () => {
    setClassNames("container-fluid login-container registration");
  };

  const removeClass = () => {
    setClassNames("container-fluid login-container");
  };

  const showAdditionalFields = () => {
    setUserType("seller");
    setVisibility("visible");
  };

  const hideAdditionalFields = () => {
    setUserType("buyer");
    setVisibility("hidden");
  };

  const login = (e) => {
    e.preventDefault();

    const credentials = {
      username: username.current.value,
      password: password.current.value,
    };

    loginUser(credentials);
  };

  const register = (e) => {
    e.preventDefault();

    if (passwordReg.current.value !== repeatedPassword.current.value) {
      showModal();
      return;
    }

    const user = getRegistrationUser();
    const startRegistration = async () => registerUser(user, userType);

    startRegistration().then((res) => res === "created" && showToast());
  };

  const getRegistrationUser = () => {
    let user;

    if (userType === "buyer") {
      user = {
        firstName: firstName.current.value,
        lastName: lastName.current.value,
        username: usernameReg.current.value,
        password: passwordReg.current.value,
        blocked: false,
        address: address.current.value,
      };
    } else {
      user = {
        firstName: firstName.current.value,
        lastName: lastName.current.value,
        username: usernameReg.current.value,
        password: passwordReg.current.value,
        blocked: false,
        address: address.current.value,
        name: business.current.value,
        email: email.current.value,
      };
    }

    return user;
  };

  const showModal = () => {
    const modalEle = modal.current;
    const bsModal = new Modal(modalEle, {
      backdrop: "static",
      keyboard: false,
    });
    bsModal.show();
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
      <div className={classNames}>
        <div className="login-bg-overlay"></div>
        <form
          className="col-md-6 offset-md-3 col-lg-4 offset-lg-4 login-form"
          onSubmit={login}
        >
          <div className="login-overlay shadow"></div>
          <h2 className="fs-1 fw-bold text-white mb-5">Login</h2>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="loginUsernameInput"
              required
              placeholder="Username"
              ref={username}
            />
            <label htmlFor="loginUsernameInput">Username</label>
          </div>
          <div className="form-floating mb-3">
            <input
              type="password"
              className="form-control"
              id="loginPassword"
              required
              placeholder="Password"
              ref={password}
            />
            <label htmlFor="loginPassword">Password</label>
          </div>
          <button type="submit" className="btn btn-primary btn-lg mt-2">
            Login
          </button>
          <p className="mt-3 text-white">
            Don't have an account?{" "}
            <span className="login-form-link text-primary" onClick={addClass}>
              Sign up here
            </span>
          </p>
        </form>

        <form
          className="col-md-8 offset-md-2 registration-form"
          onSubmit={register}
        >
          <div className="login-overlay shadow"></div>
          <h2 className="fs-1 fw-bold text-white mb-5">Registration</h2>
          <div className="form-floating mb-3">
            <input
              type="text"
              className="form-control"
              id="regUsernameInput"
              required
              placeholder="Username"
              ref={usernameReg}
            />
            <label htmlFor="regUsernameInput">Username</label>
          </div>
          <div className="form-floating col-md-5 d-md-inline-block mb-3">
            <input
              type="password"
              className="form-control"
              id="regPassword"
              required
              placeholder="Password"
              ref={passwordReg}
            />
            <label htmlFor="regPassword">Password</label>
          </div>
          <div className="form-floating col-md-5 offset-md-2 d-md-inline-block mb-3">
            <input
              type="password"
              className="form-control"
              id="regPasswordRepeat"
              required
              placeholder="Password"
              ref={repeatedPassword}
            />
            <label htmlFor="regPasswordRepeat">Repeat password</label>
          </div>
          <div className="form-floating col-md-5 d-md-inline-block mb-4">
            <input
              type="text"
              className="form-control"
              id="firstNameInput"
              required
              placeholder="First name"
              ref={firstName}
            />
            <label htmlFor="firstNameInput">First name</label>
          </div>
          <div className="form-floating col-md-5 offset-md-2 d-md-inline-block mb-4">
            <input
              type="text"
              className="form-control"
              id="lastNameInput"
              required
              placeholder="Last name"
              ref={lastName}
            />
            <label htmlFor="lastNameInput">Last name</label>
          </div>
          <div className="form-floating mb-4">
            <input
              type="text"
              className="form-control"
              id="addressInput"
              required
              placeholder="Address"
              ref={address}
            />
            <label htmlFor="addressInput">Address</label>
          </div>
          <div className="mb-3">
            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="buyerRadio"
                defaultChecked
                onChange={hideAdditionalFields}
              />
              <label
                className="form-check-label text-white"
                htmlFor="buyerRadio"
              >
                Buyer
              </label>
            </div>
            <div className="form-check">
              <input
                className="form-check-input"
                type="radio"
                name="flexRadioDefault"
                id="sellerRadio"
                onChange={showAdditionalFields}
              />
              <label
                className="form-check-label text-white"
                htmlFor="sellerRadio"
              >
                Seller
              </label>
            </div>
          </div>
          <div className={visibility}>
            <div className="form-floating col-md-5 d-md-inline-block mb-4">
              <input
                type="text"
                className="form-control"
                id="businessNameInput"
                required={userType === "seller" ? true : false}
                placeholder="Business name"
                ref={business}
              />
              <label htmlFor="businessNameInput">Business name</label>
            </div>
            <div className="form-floating col-md-5 offset-md-2 d-md-inline-block mb-4">
              <input
                type="email"
                className="form-control"
                id="emailInput"
                required={userType === "seller" ? true : false}
                placeholder="Email address"
                ref={email}
              />
              <label htmlFor="emailInput">Email address</label>
            </div>
          </div>
          <button type="submit" className="btn btn-primary btn-lg mt-2">
            Register
          </button>
          <p className="mt-3 text-white">
            Already have an account?{" "}
            <span
              className="login-form-link text-primary"
              onClick={removeClass}
            >
              Sign in here
            </span>
          </p>
        </form>
      </div>
      <div className="modal" ref={modal}>
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
              <p>Please make sure your passwords match.</p>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
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
          <div className="toast-body">Successfully registered.</div>
        </div>
      </div>
    </>
  );
}
