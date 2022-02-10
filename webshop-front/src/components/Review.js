import { Modal, Toast } from "bootstrap";
import React, { useEffect, useRef, useState } from "react";
import profileImg from "../img/default-user.png";
import useFetch from "../utils/useFetch";
import { dateFormatter, listFormatter } from "../utils/utils";

export default function Review({
  id,
  comment,
  rate,
  time,
  anonymousComment,
  buyer,
  authorized,
  getReviews,
}) {
  const [productNames, setProductNames] = useState();
  const api = useFetch();
  const modal = useRef();
  const toast = useRef();

  useEffect(() => {
    getProductNames();
  }, []);

  const getProductNames = async () => {
    const { response, data } = await api(`/shop/products/name/order/${id}`);

    if (response.status === 200) {
      setProductNames(data);
    }
  };

  const archiveReview = async () => {
    let archivedOrder = {
      id,
      comment,
      rate,
      time,
      anonymousComment,
      buyer,
      delivered: true,
      archivedComment: true,
    };

    await api("/shop/orders", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(archivedOrder),
    });

    getReviews();
    showToast();
  };

  const renderStars = () => {
    let stars = [];

    if (rate === 0) {
      for (let i = 0; i < 5; i++) {
        stars.push(
          <i className="bi bi-star-fill product__seller-star--gray" key={i}></i>
        );
      }

      return <span className="me-1 product__seller-stars">{stars}</span>;
    }

    for (let i = 0; i < rate; i++) {
      stars.push(
        <i className="bi bi-star-fill product__seller-star" key={i}></i>
      );
    }

    if (rate < 5) {
      for (let i = 0; i < 5 - rate; i++) {
        stars.push(
          <i
            className="bi bi-star-fill product__seller-star--gray"
            key={i + 4}
          ></i>
        );
      }
    }

    return <span className="me-1 product__seller-stars">{stars}</span>;
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
    <div className="card review mt-4 mb-4">
      <div className="card-header review__header">
        <img className="review__img" src={profileImg} alt="profile" />
        <p className="review__author ps-2">
          {anonymousComment
            ? "Anonymous comment"
            : `${buyer.firstName} ${buyer.lastName}`}
        </p>
        <span className="float-end fw-bold">{dateFormatter(time)}</span>
      </div>
      <div className="card-body">
        <h5>{productNames && listFormatter(productNames)}</h5>
        <h6 className="card-title">
          {renderStars()} {rate} out of 5
        </h6>
        <p className="card-text review__text">{comment}</p>
      </div>
      {authorized && (
        <div className="card-footer">
          <button className="btn btn-outline-danger" onClick={showModal}>
            <i className="bi bi-archive"></i> Archive
          </button>
        </div>
      )}
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
                Are you sure you want to archive this review? You won't be able
                to unarchive it!
              </p>
            </div>
            <div className="modal-footer">
              <button
                type="button"
                className="btn btn-secondary"
                data-bs-dismiss="modal"
              >
                <i className="bi bi-x-circle pe-1"></i> Close
              </button>
              <button
                type="button"
                className="btn btn-danger"
                data-bs-dismiss="modal"
                onClick={archiveReview}
              >
                <i className="bi bi-archive"></i> Archive
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
          <div className="toast-body">Review successfully archived.</div>
        </div>
      </div>
    </div>
  );
}
