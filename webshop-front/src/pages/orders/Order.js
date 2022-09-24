import { Modal, Toast } from "bootstrap";
import React, { useEffect, useRef, useState } from "react";
import { Link } from "react-router-dom";
import useFetch from "../../utils/useFetch";
import { dateFormatter } from "../../utils/utils";

export default function Order({ order, getOrders }) {
  const [rateDisabled, setRateDisabled] = useState(true);
  const deliveredRef = useRef();
  const rateRef = useRef();
  const commentRef = useRef();
  const anonymousCommentRef = useRef();
  const modal = useRef();
  const toast = useRef();
  const api = useFetch();

  useEffect(() => {
    setRateDisabled(!deliveredRef.current.checked);
  }, []);

  const submitReview = async () => {
    if (rateRef.current.value === "" || commentRef.current.value === "") {
      return alert("You need to fill both fields, rate and comment!");
    }

    let changedOrder = order;
    changedOrder.rate = rateRef.current.value;
    changedOrder.comment = commentRef.current.value;
    changedOrder.anonymousComment = anonymousCommentRef.current.checked;

    await api("/shop/orders", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(changedOrder),
    });

    showToast();
    getOrders();
  };

  const handleDeliver = async () => {
    setRateDisabled(!deliveredRef.current.checked);

    let changedOrder = order;
    changedOrder.delivered = deliveredRef.current.checked;

    await api("/shop/orders", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(changedOrder),
    });
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
      <tr>
        <th scope="row">
          <Link className="text-decoration-none" to={`/orders/${order.id}`}>
            {order.id}
          </Link>
        </th>
        <td>{dateFormatter(order.time)}</td>
        <td>
          <div className="form-check" style={{ minHeight: "unset" }}>
            <input
              className="form-check-input"
              type="checkbox"
              id="flexCheckDisabled"
              ref={deliveredRef}
              defaultChecked={order.delivered}
              disabled={order.rate}
              onChange={handleDeliver}
            />
          </div>
        </td>
        <td>
          {order.rate ? (
            <>
              Rated - {order.rate}{" "}
              <i className="bi bi-star-fill product__seller-star"></i>
            </>
          ) : (
            <button
              className="btn btn-outline-primary"
              onClick={showModal}
              disabled={rateDisabled}
            >
              <i className="bi bi-star"></i> Rate
            </button>
          )}
        </td>
        <td>
          <div className="modal fade" ref={modal}>
            <div className="modal-dialog modal-dialog-centered">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">Order review</h5>
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
                        type="number"
                        min="1"
                        max="5"
                        className="form-control"
                        id="rateInput"
                        required
                        placeholder="Rate (1-5)"
                        ref={rateRef}
                      />
                      <label htmlFor="rateInput">Rate (1-5)</label>
                    </div>
                    <div className="form-floating mb-3">
                      <textarea
                        style={{ height: "150px" }}
                        className="form-control"
                        id="commentInput"
                        required
                        placeholder="Comment"
                        ref={commentRef}
                      />
                      <label htmlFor="commentInput">Comment</label>
                    </div>
                    <div className="form-check form-switch">
                      <input
                        className="form-check-input"
                        type="checkbox"
                        id="flexSwitchCheckDefault"
                        ref={anonymousCommentRef}
                      />
                      <label
                        className="form-check-label"
                        htmlFor="flexSwitchCheckDefault"
                      >
                        Anonymous comment
                      </label>
                    </div>
                  </form>
                </div>
                <div className="modal-footer">
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
                      onClick={submitReview}
                    >
                      <i className="bi bi-check-circle"></i> Submit
                    </button>
                  </div>
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
              <div className="toast-body">Review successfully submitted!</div>
            </div>
          </div>
        </td>
      </tr>
    </>
  );
}
