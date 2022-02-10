import { Modal, Toast } from "bootstrap";
import React, { useEffect, useRef, useState } from "react";
import { Link, useNavigate, useParams } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import userImg from "../../img/default-user.png";
import useFetch from "../../utils/useFetch";
import {
  currencyFormatter,
  productTypeFormatter,
  dateFormatter,
} from "../../utils/utils";

export default function ProductDetails({ cart, setCart }) {
  const [product, setProduct] = useState({});
  const [seller, setSeller] = useState({});
  const [sale, setSale] = useState({});
  const [sellerRating, setSellerRating] = useState(0);
  const [ratesCount, setRatesCount] = useState(0);
  const [operatesFrom, setOperatesFrom] = useState("");
  const [inCart, setInCart] = useState(false);
  const [blocked, setBlocked] = useState(false);
  const [toastMessage, setToastMessage] = useState();
  const nameRef = useRef();
  const descriptionRef = useRef();
  const priceRef = useRef();
  const productTypeRef = useRef();
  const picturePathRef = useRef();
  const editModal = useRef();
  const removalModal = useRef();
  const toast = useRef();
  const { id } = useParams();
  const { user, getRole } = useAuth();
  const api = useFetch();
  const navigate = useNavigate();

  useEffect(() => {
    getProduct(id);
  }, [id]);

  useEffect(() => {
    cart.forEach((item) => {
      if (item.product.id === product.id) {
        setInCart(true);
        return;
      }
    });

    getSale();
  }, [product]);

  useEffect(() => {
    if (cart.length === 0) {
      return setBlocked(false);
    }

    if (cart[0].product.seller.id !== seller.id) {
      setBlocked(true);
    }
  }, [cart]);

  const getProduct = async (id) => {
    const { response, data } = await api(`/shop/products/${id}`);

    if (response.status === 200) {
      setProduct(data);
      setSeller(data.seller);
      getSellerRating(data.seller.id);
      getRatesCount(data.seller.id);
      setOperatesFrom(dateFormatter(data.seller.operatesFrom));
    }
  };

  const getSale = async () => {
    const { response, data } = await api(`/shop/sales/single/product/${id}`);

    if (response.status === 200) {
      setSale(data);
    }
  };

  const getSellerRating = async (id) => {
    const { response, data } = await api(`/shop/orders/rate/seller/${id}`);

    if (response.status === 200) {
      setSellerRating(data);
    }
  };

  const getRatesCount = async (id) => {
    const { response, data } = await api(`/shop/orders/ratecount/seller/${id}`);

    if (response.status === 200) {
      setRatesCount(data);
    }
  };

  const changeProduct = async () => {
    const changedProduct = getChangedProduct();

    await api("/shop/products", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(changedProduct),
    });

    getProduct(product.id);
    setToastMessage("Product successfully updated.");
    showToast();
  };

  const removeProduct = async () => {
    await api(`/shop/products/${product.id}`, {
      method: "DELETE",
    });

    navigate("/");
  };

  const getChangedProduct = () => {
    const changedProduct = {
      id: product.id,
      name: nameRef.current.value,
      description: descriptionRef.current.value,
      price: priceRef.current.value,
      productType: productTypeRef.current.value,
      picturePath: picturePathRef.current.value,
    };

    return changedProduct;
  };

  const addToCart = () => {
    const item = {
      product,
      quantity: 1,
    };

    setCart((cart) => [...cart, item]);
    setInCart(true);
  };

  const removeFromCart = () => {
    setCart(cart.filter((item) => item.product.id !== product.id));
    setInCart(false);
  };

  const showModal = (type) => {
    const modalEle = type.current;
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
      <div className="row product">
        <div className="col-12 col-md-8 col-lg-4 product__img-wrapper">
          <img
            src={product.picturePath}
            className="card-img-top product__img"
            alt={product.name}
          />
        </div>
        <div className="col-12 col-lg-5 col-xxl-6 product__info">
          <span className="badge bg-warning text-dark">
            {productTypeFormatter(product.productType)}
          </span>
          <h4 className="card-title">{product.name}</h4>
          <p className="card-text">{product.description}</p>
          <span
            className={`product__info-price ${
              sale.id && "text-decoration-line-through fs-6 fw-normal"
            }`}
          >
            {currencyFormatter.format(product.price)}
          </span>
          {sale.id && (
            <span className="products__item-price ps-2">
              {currencyFormatter.format(
                product.price - (product.price * sale.percentage) / 100
              )}
            </span>
          )}
          {getRole() === "buyer" && !inCart && (
            <span
              tabIndex="0"
              data-bs-toggle="tooltip"
              title={
                blocked
                  ? "You can only buy products from one seller at a time."
                  : ""
              }
            >
              <button
                disabled={blocked}
                className="btn btn-outline-primary product__info-button"
                onClick={addToCart}
              >
                <i className="bi bi-cart-plus"></i> Add to cart
              </button>
            </span>
          )}

          {getRole() === "buyer" && inCart && (
            <span>
              <button
                className="btn btn-outline-danger product__info-button"
                onClick={removeFromCart}
              >
                <i className="bi bi-cart-dash"></i> Remove from cart
              </button>
            </span>
          )}

          {getRole() === "seller" && seller.username == user.sub && (
            <div className="d-flex">
              <button
                className="btn btn-outline-primary mt-3 me-3"
                onClick={() => showModal(editModal)}
              >
                <i className="bi bi-pencil"></i> Edit
              </button>
              <button
                className="btn btn-outline-danger mt-3"
                onClick={() => showModal(removalModal)}
              >
                <i className="bi bi-eraser"></i> Remove
              </button>
            </div>
          )}
        </div>
        <div className="col-12 col-md-4 col-lg-3 col-xxl-2 product__seller">
          <img className="product__seller-img" src={userImg} alt="User image" />
          <div className="product__seller-wrapper">
            <h5 className="product__seller-name">
              <Link
                className="text-capitalize product__seller-link"
                to={`/sellers/${seller.username}`}
              >
                {seller.username}
              </Link>
            </h5>
            <div className="product__seller-rate pb-2">
              {renderStars()}
              <span className="product__seller-number">({ratesCount})</span>
            </div>
            <p className="product__seller-date">
              Operates from: <span className="fw-bold">{operatesFrom}</span>
            </p>
            <p className="product__seller-address">
              Address: <span className="fw-bold">{seller.address}</span>
            </p>
            <p className="product__seller-contact">
              Contact: <a href={`mailto:${seller.email}`}>{seller.email}</a>
            </p>
            <Link
              className="product__seller-products"
              to={`/products/seller/${seller.username}`}
            >
              All Products
            </Link>
          </div>
        </div>
      </div>
      <div className="modal fade" id="productModal" ref={editModal}>
        <div className="modal-dialog modal-dialog-centered">
          <div className="modal-content">
            <div className="modal-header">
              <h5 className="modal-title">Edit product</h5>
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
                    type="text"
                    className="form-control"
                    id="nameInput"
                    required
                    placeholder="Name"
                    ref={nameRef}
                    defaultValue={product.name}
                  />
                  <label htmlFor="nameInput">Name</label>
                </div>
                <div className="form-floating mb-3">
                  <textarea
                    style={{ height: "150px" }}
                    className="form-control"
                    id="descriptionInput"
                    required
                    placeholder="Description"
                    ref={descriptionRef}
                    defaultValue={product.description}
                  />
                  <label htmlFor="descriptionInput">Description</label>
                </div>
                <div className="form-floating mb-3">
                  <input
                    type="number"
                    className="form-control"
                    id="priceInput"
                    required
                    placeholder="Price"
                    ref={priceRef}
                    defaultValue={product.price}
                  />
                  <label htmlFor="priceInput">Price (€)</label>
                </div>
                <div className="form-floating mb-3">
                  <select
                    className="form-select"
                    aria-label="Default select example"
                    id="productTypeInput"
                    ref={productTypeRef}
                    defaultValue={product.productType}
                  >
                    <option value="books">
                      {productTypeFormatter("books")}
                    </option>
                    <option value="footwear">
                      {productTypeFormatter("footwear")}
                    </option>
                    <option value="clothes">
                      {productTypeFormatter("clothes")}
                    </option>
                    <option value="technology">
                      {productTypeFormatter("technology")}
                    </option>
                    <option value="pets">{productTypeFormatter("pets")}</option>
                    <option value="vehicles">
                      {productTypeFormatter("vehicles")}
                    </option>
                    <option value="audio">
                      {productTypeFormatter("audio")}
                    </option>
                    <option value="sport">
                      {productTypeFormatter("sport")}
                    </option>
                    <option value="furniture">
                      {productTypeFormatter("furniture")}
                    </option>
                    <option value="mobilePhones">
                      {productTypeFormatter("mobilePhones")}
                    </option>
                    <option value="realEstate">
                      {productTypeFormatter("realEstate")}
                    </option>
                    <option value="other">
                      {productTypeFormatter("other")}
                    </option>
                  </select>
                  <label htmlFor="priceInput">Product type</label>
                </div>
                <div className="form-floating mb-3">
                  <input
                    type="text"
                    className="form-control"
                    id="picturePathInput"
                    required
                    placeholder="Picture path"
                    ref={picturePathRef}
                    defaultValue={product.picturePath}
                  />
                  <label htmlFor="picturePathInput">Picture path</label>
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
                  onClick={changeProduct}
                >
                  <i className="bi bi-save pe-2"></i>
                  Save
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div className="modal fade" ref={removalModal}>
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
              <p>Are you sure you want to remove {product.name}?</p>
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
                className="btn btn-danger"
                data-bs-dismiss="modal"
                onClick={removeProduct}
              >
                <i className="bi bi-eraser"></i> Remove
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
          <div className="toast-body">{toastMessage}</div>
        </div>
      </div>
    </div>
  );
}
