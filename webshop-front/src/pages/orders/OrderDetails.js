import React, { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import useFetch from "../../utils/useFetch";
import { dateTimeFormatter } from "../../utils/utils";

export default function OrderDetails() {
  const [order, setOrder] = useState({});
  const [items, setItems] = useState([]);
  const { id } = useParams();
  const api = useFetch();

  useEffect(() => {
    getOrder(id);
    getItems(id);
  }, [id]);

  const getOrder = async (id) => {
    const { response, data } = await api(`/shop/orders/${id}`);

    if (response.status === 200) {
      setOrder(data);
    }
  };

  const getItems = async (id) => {
    const { response, data } = await api(`/shop/items/order/${id}`);

    if (response.status === 200) {
      setItems(data);
    }
  };

  return (
    <div className="container">
      <div className="order row">
        <h2 className="order__title">Order number: {order.id}</h2>
        <div className="row pt-2">
          <div className="order__info col-12 col-md-6">
            <p className="fs-5">
              Date and time: {dateTimeFormatter(order.time)}
            </p>
            <p className="fs-5">
              Delivered:{" "}
              {order.delivered ? (
                <i class="bi bi-check-lg order__check"></i>
              ) : (
                <i class="bi bi-x-lg order__x"></i>
              )}
            </p>
            <p className="fs-5">
              Seller:{" "}
              {items[0] && (
                <Link
                  className="text-decoration-none"
                  to={`/sellers/${items[0].product.seller.username}`}
                >
                  {items[0].product.seller.username}
                </Link>
              )}
            </p>
            <p className="fs-5 mb-1">Items:</p>
            {items.length === 0 ? (
              <p>There was an error while loading items...</p>
            ) : (
              items.map((item) => {
                return (
                  <p className="ps-2">
                    -{" "}
                    <Link
                      className="text-decoration-none"
                      to={`/products/${item.product.id}`}
                    >
                      {item.product.name}
                    </Link>{" "}
                    <span className="ps-2">x{item.quantity}</span>
                  </p>
                );
              })
            )}
          </div>
          {order.delivered && (
            <div className="order__response col-12 col-md-6">
              <p className="fs-5">
                Rate: {order.rate}{" "}
                <i className="bi bi-star-fill order__star"></i>
              </p>
              <p className="fs-5 mb-0">
                Comment
                {order.anonymousComment && (
                  <span className="text-muted">(anonymous)</span>
                )}
                :
              </p>
              <p className="fs-6">
                {order.comment !== "" ? (
                  order.comment
                ) : (
                  <span className="fst-italic">No comment</span>
                )}
              </p>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
