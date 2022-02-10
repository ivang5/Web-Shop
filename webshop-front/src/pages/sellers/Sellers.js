import React, { useEffect, useState } from "react";
import useFetch from "../../utils/useFetch";
import Seller from "./Seller";

export default function Sellers() {
  const [sellers, setSellers] = useState([]);
  const api = useFetch();

  useEffect(() => {
    getSellers();
  }, []);

  const getSellers = async () => {
    const { response, data } = await api("/shop/users/seller/active");

    if (response.status === 200) {
      setSellers(data);
    }
  };

  return (
    <div className="container">
      <h1 className="pt-3">Sellers</h1>
      <div className="products">
        {sellers.map((seller) => {
          return (
            <Seller
              key={seller.id}
              id={seller.id}
              username={seller.username}
              name={seller.name}
              operatesFrom={seller.operatesFrom}
            />
          );
        })}
      </div>
    </div>
  );
}
