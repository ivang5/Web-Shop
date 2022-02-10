import React, { useEffect, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import Review from "../../components/Review";
import { useAuth } from "../../contexts/AuthContext";
import profileImg from "../../img/default-user.png";
import useFetch from "../../utils/useFetch";
import { dateFormatter } from "../../utils/utils";

export default function SellerDetails() {
  const [seller, setSeller] = useState({});
  const [sellerRating, setSellerRating] = useState(0);
  const [ratesCount, setRatesCount] = useState(0);
  const [reviews, setReviews] = useState([]);
  const { user } = useAuth();
  const { username } = useParams();
  const api = useFetch();
  const navigate = useNavigate();

  useEffect(() => {
    getSeller();
  }, []);

  useEffect(() => {
    if (seller.blocked) {
      navigate("/404");
    }

    getSellerRating();
    getRatesCount();
    getReviews();
  }, [seller]);

  const getSeller = async () => {
    const { response, data } = await api(`/shop/users/seller/${username}`);

    if (response.status === 200) {
      setSeller(data);
    }
  };

  const getSellerRating = async () => {
    const { response, data } = await api(
      `/shop/orders/rate/seller/${seller.id}`
    );

    if (response.status === 200) {
      setSellerRating(data);
    }
  };

  const getRatesCount = async () => {
    const { response, data } = await api(
      `/shop/orders/ratecount/seller/${seller.id}`
    );

    if (response.status === 200) {
      setRatesCount(data);
    }
  };

  const getReviews = async () => {
    const { response, data } = await api(`/shop/orders/seller/${seller.id}`);

    if (response.status === 200) {
      setReviews(data);
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
    <div className="container">
      <div className="row seller">
        <div className="col-12 col-md-6 col-lg-4 seller__img-wrapper">
          <img src={profileImg} className="seller__img" alt="profile" />
        </div>
        <div className="col-12 col-md-6 col-lg-8 seller__info">
          <h4 className="seller__title">
            {seller.firstName} {seller.lastName}
          </h4>
          <div className="mb-2">
            {renderStars()}
            <span>({ratesCount})</span>
          </div>
          <p className="seller__text">
            Username: <span className="fw-bold">{seller.username}</span>
          </p>
          <p className="seller__text">
            Company name: <span className="fw-bold">{seller.name}</span>
          </p>
          <p className="seller__text">
            Address: <span className="fw-bold">{seller.address}</span>
          </p>
          <p className="seller__text">
            <a href={`mailto:${seller.email}`}>{seller.email}</a>
          </p>
          <Link
            className="d-block pt-2 text-decoration-none seller__text"
            to={`/products/seller/${username}`}
          >
            View products
          </Link>
          <span className="seller__text">
            Operates from:{" "}
            <span className="fw-bold">
              {dateFormatter(seller.operatesFrom)}
            </span>
          </span>
        </div>
      </div>
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
                authorized={username === user.sub ? true : false}
                getReviews={getReviews}
              />
            );
          })
        )}
      </div>
    </div>
  );
}
