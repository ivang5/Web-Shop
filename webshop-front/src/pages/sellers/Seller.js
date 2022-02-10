import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import profileImg from "../../img/default-user.png";
import useFetch from "../../utils/useFetch";
import { dateFormatter } from "../../utils/utils";

export default function Seller({ id, username, name, operatesFrom }) {
  const [sellerRating, setSellerRating] = useState(0);
  const [ratesCount, setRatesCount] = useState(0);
  const api = useFetch();

  useEffect(() => {
    getSellerRating();
    getRatesCount();
  }, []);

  const getSellerRating = async () => {
    const { response, data } = await api(`/shop/orders/rate/seller/${id}`);

    if (response.status === 200) {
      setSellerRating(data);
    }
  };

  const getRatesCount = async () => {
    const { response, data } = await api(`/shop/orders/ratecount/seller/${id}`);

    if (response.status === 200) {
      setRatesCount(data);
    }
  };

  const renderStars = () => {
    let stars = [];

    if (sellerRating === 0) {
      for (let i = 0; i < 5; i++) {
        stars.push(
          <i className="bi bi-star-fill product__seller-star--gray" key={i}></i>
        );
      }

      return <span className="me-1 product__seller-stars">{stars}</span>;
    }

    for (let i = 0; i < sellerRating; i++) {
      stars.push(
        <i className="bi bi-star-fill product__seller-star" key={i}></i>
      );
    }

    if (sellerRating < 5) {
      for (let i = 0; i < 5 - sellerRating; i++) {
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

  return (
    <div className="card products__item">
      <div className="products__item-img-wrapper products__item-img-wrapper--no-height">
        <img
          src={profileImg}
          className="card-img-top products__item-img"
          alt="profile"
        />
      </div>
      <div className="card-body products__item-body">
        <div className="products__item-wrapper">
          <h4 className="card-title products__item-title">
            <Link to={`/sellers/${username}`}>
              {username} ({name})
            </Link>
          </h4>
          <div className="mb-2 product__seller-rate">
            {renderStars()}
            <span className="product__seller-number">({ratesCount})</span>
          </div>
          <Link
            className="d-block pt-2 text-decoration-none"
            to={`/products/seller/${username}`}
          >
            View products
          </Link>
          <span className="products__item-text">
            Operates from: {dateFormatter(operatesFrom)}
          </span>
        </div>
      </div>
    </div>
  );
}
