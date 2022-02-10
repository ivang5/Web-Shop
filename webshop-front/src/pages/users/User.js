import React, { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import useFetch from "../../utils/useFetch";
import { dateFormatter } from "../../utils/utils";
import profileImg from "../../img/default-user.png";
import { Modal, Toast } from "bootstrap";
import { useAuth } from "../../contexts/AuthContext";

export default function User() {
  const [user, setUser] = useState({});
  const [blocked, setBlocked] = useState();
  const modal = useRef();
  const toast = useRef();
  const params = useParams();
  const { getRole } = useAuth();
  const api = useFetch();
  const navigate = useNavigate();

  useEffect(() => {
    if (getRole() !== "admin") {
      navigate("/404");
    }

    getUser();
  }, []);

  const getUser = async () => {
    const { response, data } = await api(
      `/shop/users/${params.role}/${params.username}`
    );

    if (response.status === 200) {
      setUser(data);
      setBlocked(data.blocked);
    }
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

  const handleBlock = async () => {
    const { response, data } = await api(
      `/shop/users/${params.role}/handle-block/${user.id}`,
      {
        method: "PUT",
      }
    );

    if (response.status === 200) {
      if (blocked) {
        setBlocked(false);
      } else {
        setBlocked(true);
      }
      showToast();
    }
  };

  return (
    <>
      <div className="container">
        <div className="row mt-5 profile">
          <div className="col-12 col-md-4 profile__image-wrapper">
            <img className="profile__image" src={profileImg} alt="profile" />
          </div>
          <div className="col-12 col-md-8 profile__info">
            <div className="card text-dark bg-light mb-3">
              <div className="card-header profile__info-header">
                Profile overview
              </div>
              <div className="card-body">
                <ul className="list-group list-group-flush">
                  <li className="list-group-item mt-2">
                    Username: {user.username}
                  </li>
                  <li className="list-group-item mt-2">Role: {params.role}</li>
                  <li className="list-group-item mt-2">
                    First name: {user.firstName}
                  </li>
                  <li className="list-group-item mt-2">
                    Last name: {user.lastName}
                  </li>
                  {(params.role === "seller" || params.role === "buyer") && (
                    <li className="list-group-item mt-2">
                      Address: {user.address}
                    </li>
                  )}
                  {params.role === "seller" && (
                    <>
                      <li className="list-group-item mt-2">
                        Company name: {user.name}
                      </li>
                      <li className="list-group-item mt-2">
                        Contact: {user.email}
                      </li>
                      <li className="list-group-item mt-2">
                        Operates from: {dateFormatter(user.operatesFrom)}
                      </li>
                    </>
                  )}
                  <li className="list-group-item mt-2">
                    Status: {blocked ? "blocked" : "active"}
                  </li>
                </ul>
                <button
                  className={`btn btn-outline-${
                    blocked ? "success" : "danger"
                  } mt-3`}
                  onClick={showModal}
                >
                  {blocked ? (
                    <>
                      <i className="bi bi-person-plus"></i> Unblock user
                    </>
                  ) : (
                    <>
                      <i className="bi bi-person-x"></i> Block user
                    </>
                  )}
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="modal fade" ref={modal}>
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">Confirmation</h5>
              <button
                type="button"
                className="btn-close"
                data-bs-dismiss="modal"
                aria-label="Close"
              ></button>
            </div>
            <div className="modal-body">
              <p>
                Are you sure you want to {blocked ? "unblock" : "block"} user{" "}
                {user.username}?
              </p>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                <i className="bi bi-x-circle pe-2"></i>Close
              </button>
              <button
                type="button"
                className={`btn btn-${blocked ? "success" : "danger"}`}
                data-bs-dismiss="modal"
                onClick={handleBlock}
              >
                {blocked ? (
                  <>
                    <i className="bi bi-person-plus"></i> Unblock
                  </>
                ) : (
                  <>
                    <i className="bi bi-person-x"></i> Block
                  </>
                )}
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
          <div className="toast-body">
            User {user.username} successfully{" "}
            {blocked ? "blocked" : "unblocked"}
          </div>
        </div>
      </div>
    </>
  );
}
