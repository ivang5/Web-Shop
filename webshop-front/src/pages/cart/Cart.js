import { Modal, Toast } from "bootstrap";
import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import useFetch from "../../utils/useFetch";
import { currencyFormatter } from "../../utils/utils";
import CartItem from "./CartItem";

export default function Cart({ cart, setCart }) {
  const [buyer, setBuyer] = useState({});
  const [readyForFetch, setReadyForFetch] = useState(false);
  const [order, setOrder] = useState({});
  const [totalPrice, setTotalPrice] = useState(0);
  const modal = useRef();
  const toast = useRef();
  const { user, getRole } = useAuth();
  const api = useFetch();
  const navigate = useNavigate();

  useEffect(() => {
    if (getRole() !== "buyer") navigate("/404");

    getBuyer();
  }, []);

  useEffect(() => {
    calculateTotalPrice();
  }, [cart]);

  useEffect(() => {
    if (readyForFetch) getLastOrder();
  }, [readyForFetch]);

  useEffect(() => {
    if (order.id) createItems();
  }, [order]);

  const purchase = async () => {
    const newOrder = {
      time: new Date(),
      delivered: false,
      buyer: buyer,
    };

    await api("/shop/orders", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(newOrder),
    });

    setReadyForFetch(true);
  };

  const getLastOrder = async () => {
    const { response, data } = await api(`/shop/orders/last/buyer/${buyer.id}`);

    if (response.status === 200) {
      setOrder(data);
      setReadyForFetch(false);
    }
  };

  const createItems = () => {
    cart.forEach((item) => {
      const newItem = {
        quantity: item.quantity,
        product: item.product,
        order: order,
      };

      createItem(newItem);
    });

    setOrder({});
    setCart([]);
    showToast();
  };

  const createItem = async (item) => {
    await api("/shop/items", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(item),
    });
  };

  const getBuyer = async () => {
    const { response, data } = await api(`/shop/users/buyer/${user.sub}`);

    if (response.status === 200) {
      setBuyer(data);
    }
  };

  const calculateTotalPrice = () => {
    let total = 0;

    cart.forEach((item) => {
      total += item.product.price * item.quantity;
    });

    setTotalPrice(total);
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
    <div className="container">
      {cart.length === 0 && (
        <div className="not-found">
          <div className="not-found__content">
            <span className="not-found__number">
              <i className="bi bi-cart"></i>
            </span>
            <h1>Your cart is currently empty</h1>
            <p>Take a look at the store, you might find something you need!</p>
            <Link className="btn btn-primary mt-3" to={"/"}>
              Home
            </Link>
          </div>
        </div>
      )}
      {cart.length !== 0 && (
        <>
          <h1 className="pt-5">Your cart</h1>
          <table className="table mt-4">
            <thead>
              <tr>
                <th scope="col">#</th>
                <th scope="col">Name</th>
                <th scope="col">Seller</th>
                <th scope="col">Price</th>
                <th scope="col">Quantity</th>
                <th scope="col">Total</th>
              </tr>
            </thead>
            <tbody>
              {cart.map((item) => {
                return (
                  <CartItem
                    key={item.product.id}
                    index={cart.indexOf(item) + 1}
                    item={item}
                    cart={cart}
                    setCart={setCart}
                  />
                );
              })}
            </tbody>
            <tfoot>
              <tr className="border-white">
                <td colSpan={5}></td>
                <td className="fw-bold">
                  {currencyFormatter.format(totalPrice)}
                </td>
                <td>
                  <button
                    className="btn btn-outline-success"
                    onClick={showModal}
                  >
                    <i className="bi bi-cart-check"></i>
                  </button>
                </td>
              </tr>
            </tfoot>
          </table>
        </>
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
              <p>Are you sure you want to make this purchase?</p>
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
                className="btn btn-success"
                data-bs-dismiss="modal"
                onClick={purchase}
              >
                <i className="bi bi-cart-check"></i> Purchase
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
          <div className="toast-body">Successfully purchased!</div>
        </div>
      </div>
    </div>
  );
}
