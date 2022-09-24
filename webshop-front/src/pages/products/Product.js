import { Modal, Toast } from "bootstrap";
import React, { useEffect, useRef, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import useFetch from "../../utils/useFetch";
import { currencyFormatter, productTypeFormatter } from "../../utils/utils";

export default function Product({
  id,
  name,
  description,
  price,
  productType,
  picturePath,
  seller,
  getProducts,
  cart,
  setCart,
}) {
  const [toastMessage, setToastMessage] = useState();
  const [inCart, setInCart] = useState(false);
  const [blocked, setBlocked] = useState(false);
  const [sale, setSale] = useState({});
  const [selectedFile, setSelectedFile] = useState(null);
  const { user, getRole, authTokens } = useAuth();
  const { username } = useParams();
  const nameRef = useRef();
  const descriptionRef = useRef();
  const priceRef = useRef();
  const productTypeRef = useRef();
  const picturePathRef = useRef();
  const editModal = useRef();
  const removalModal = useRef();
  const toast = useRef();
  const api = useFetch();

  useEffect(() => {
    cart.forEach((item) => {
      if (item.product.id === id) {
        setInCart(true);
        return;
      }
    });

    getSale();
  }, []);

  useEffect(() => {
    if (cart.length === 0) {
      return setBlocked(false);
    }

    if (cart[0].product.seller.id !== seller.id) {
      setBlocked(true);
    }
  }, [cart]);

  const getSale = async () => {
    const { response, data } = await api(`/shop/sales/single/product/${id}`);

    if (response.status === 200) {
      setSale(data);
    }
  };

  const changeProduct = async () => {
    const changedProduct = getChangedProduct();

    if (selectedFile) {
      const file = await uploadFile();
      changedProduct.detailedDescription = file;
      await pushProduct(changedProduct);
    } else {
      await pushProduct(changedProduct);
    }

    getProducts();
    setToastMessage("Product successfully updated.");
    showToast();
  };

  const pushProduct = async (changedProduct) => {
    await api("/shop/products", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(changedProduct),
    });
  };

  const uploadFile = async () => {
    const formData = new FormData();
    formData.append("file", selectedFile);

    const response = await fetch(`http://localhost:8080/shop/files`, {
      method: "POST",
      headers: {
        Authorization: `Bearer ${authTokens.access_token}`,
      },
      body: formData,
    });

    return await response.json();
  };

  const removeProduct = async () => {
    await api(`/shop/products/${id}`, {
      method: "DELETE",
    });

    getProducts();
    setToastMessage("Product successfully removed.");
    showToast();
  };

  const getChangedProduct = () => {
    const changedProduct = {
      id: id,
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
      product: {
        id,
        name,
        description,
        price,
        productType,
        picturePath,
        seller,
      },
      quantity: 1,
    };

    setCart((cart) => [...cart, item]);
    setInCart(true);
  };

  const removeFromCart = () => {
    setCart(cart.filter((item) => item.product.id !== id));
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

  return (
    <>
      <div className="card products__item">
        <div className="products__item-img-wrapper">
          <img
            src={picturePath}
            className="card-img-top products__item-img"
            alt={name}
          />
        </div>
        <div className="card-body products__item-body">
          <div className="products__item-wrapper">
            <span className="badge bg-warning text-dark">
              {productTypeFormatter(productType)}
            </span>
            <h4 className="card-title products__item-title">
              <Link to={`/products/${id}`}>{name}</Link>
            </h4>
            <p className="card-text products__item-text">{description}</p>
            <span
              className={`products__item-price ${
                sale.id && "text-decoration-line-through fs-6 fw-normal"
              }`}
            >
              {currencyFormatter.format(price)}
            </span>
            {sale.id && (
              <span className="products__item-price ps-2">
                {currencyFormatter.format(
                  price - (price * sale.percentage) / 100
                )}
              </span>
            )}
          </div>
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
                className="btn btn-outline-primary products__item-button"
                onClick={addToCart}
              >
                <i className="bi bi-cart-plus"></i> Add to cart
              </button>
            </span>
          )}

          {getRole() === "buyer" && inCart && (
            <span>
              <button
                className="btn btn-outline-danger products__item-button"
                onClick={removeFromCart}
              >
                <i className="bi bi-cart-dash"></i> Remove from cart
              </button>
            </span>
          )}

          {getRole() === "seller" && (!username || username == user.sub) && (
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
                      defaultValue={name}
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
                      defaultValue={description}
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
                      defaultValue={price}
                    />
                    <label htmlFor="priceInput">Price (€)</label>
                  </div>
                  <div className="form-floating mb-3">
                    <select
                      className="form-select"
                      aria-label="Default select example"
                      id="productTypeInput"
                      ref={productTypeRef}
                      defaultValue={productType}
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
                      <option value="pets">
                        {productTypeFormatter("pets")}
                      </option>
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
                      defaultValue={picturePath}
                    />
                    <label htmlFor="picturePathInput">Picture path</label>
                  </div>
                  <div className="form-floating mb-3">
                    <input
                      type="file"
                      className="form-control form-control-sm form-file"
                      id="fileInput"
                      placeholder="Detailed description (PDF)"
                      onChange={(e) => setSelectedFile(e.target.files[0])}
                    />
                    <label htmlFor="fileInput">
                      Detailed description (PDF)
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
                <p>Are you sure you want to remove {name}?</p>
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
    </>
  );
}
