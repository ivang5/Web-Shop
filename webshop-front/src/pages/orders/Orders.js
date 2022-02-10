import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import useFetch from "../../utils/useFetch";
import Order from "./Order";

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const [buyer, setBuyer] = useState({});
  const { user, getRole } = useAuth();
  const api = useFetch();
  const navigate = useNavigate();

  useEffect(() => {
    if (getRole() !== "buyer") {
      navigate("/404");
    }

    getBuyer();
  }, []);

  useEffect(() => {
    getOrders();
  }, [buyer]);

  const getBuyer = async () => {
    const { response, data } = await api(`/shop/users/buyer/${user.sub}`);

    if (response.status === 200) {
      setBuyer(data);
    }
  };

  const getOrders = async () => {
    if (!buyer.id) return;
    const { response, data } = await api(`/shop/orders/buyer/${buyer.id}`);

    if (response.status === 200) {
      setOrders(data);
    }
  };

  return (
    <>
      {orders && (
        <div className="container">
          <h1 className="pt-5">
            {orders.length === 0
              ? "You don't have any orders yet..."
              : "Your orders"}
          </h1>
          {orders.length !== 0 && (
            <table className="table mt-4">
              <thead>
                <tr>
                  <th scope="col">Order No.</th>
                  <th scope="col">Date</th>
                  <th scope="col">Delivered</th>
                  <th scope="col">Rated</th>
                  <th style={{ width: "0" }}></th>
                </tr>
              </thead>
              <tbody>
                {orders.map((order) => {
                  return (
                    <Order key={order.id} order={order} getOrders={getOrders} />
                  );
                })}
              </tbody>
            </table>
          )}
        </div>
      )}
    </>
  );
}
