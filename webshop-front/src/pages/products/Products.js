import { Modal, Toast } from "bootstrap";
import React, { useEffect, useRef, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import useFetch from "../../utils/useFetch";
import { productTypeFormatter } from "../../utils/utils";
import Product from "./Product";

export default function Products({ cart, setCart }) {
  const [products, setProducts] = useState([]);
  const [seller, setSeller] = useState({});
  const nameRef = useRef();
  const descriptionRef = useRef();
  const priceRef = useRef();
  const productTypeRef = useRef();
  const picturePathRef = useRef();
  const modal = useRef();
  const toast = useRef();
  const { user, getRole } = useAuth();
  const { username } = useParams();
  const api = useFetch();

  useEffect(() => {
    getProducts();
  }, [username]);

  useEffect(() => {
    if (getRole() === "seller") {
      getSeller();
    }
  }, []);

  const getProducts = async () => {
    let reply = { response: {}, data: {} };

    if (username) {
      reply = await api(`/shop/products/seller/${username}`);
    } else if (getRole() === "seller") {
      reply = await api(`/shop/products/seller/${user.sub}`);
    } else {
      reply = await api("/shop/products/active");
    }

    if (reply.response.status === 200) {
      setProducts(reply.data);
    }
  };

  const createProduct = async () => {
    const product = {
      name: nameRef.current.value,
      description: descriptionRef.current.value,
      price: priceRef.current.value,
      productType: productTypeRef.current.value,
      picturePath: picturePathRef.current.value,
      seller: seller,
    };

    await api("/shop/products", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(product),
    });

    getProducts();
    showToast();
  };

  const getSeller = async () => {
    const { response, data } = await api(`/shop/users/seller/${user.sub}`);

    if (response.status === 200) {
      setSeller(data);
    }
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
      {getRole() === "seller" && (!username || username === user.sub) && (
        <div className="mt-3">
          <h1>Your products:</h1>
          <button className="btn btn-outline-success mt-3" onClick={showModal}>
            <i className="bi bi-plus-circle"></i> Add product
          </button>
        </div>
      )}
      {username && username !== user.sub && (
        <h1 className="mt-3">
          Products from:{" "}
          <Link
            className="text-capitalize text-decoration-none"
            to={`/sellers/${username}`}
          >
            {username}
          </Link>
        </h1>
      )}
      <div className="products">
        {products.map((product) => {
          return (
            <Product
              key={product.id}
              id={product.id}
              name={product.name}
              description={product.description}
              price={product.price}
              productType={product.productType}
              picturePath={product.picturePath}
              seller={product.seller}
              getProducts={getProducts}
              cart={cart}
              setCart={setCart}
            />
          );
        })}
        <div className="modal fade" id="productModal" ref={modal}>
          <div className="modal-dialog modal-dialog-centered">
            <div className="modal-content">
              <div className="modal-header">
                <h5 className="modal-title">Add product</h5>
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
                    />
                    <label htmlFor="priceInput">Price (€)</label>
                  </div>
                  <div className="form-floating mb-3">
                    <select
                      className="form-select"
                      aria-label="Default select example"
                      id="productTypeInput"
                      required
                      ref={productTypeRef}
                      defaultValue="other"
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
                    onClick={createProduct}
                  >
                    <i className="bi bi-save pe-2"></i>
                    Add
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
            <div className="toast-body">"Product successfully added.</div>
          </div>
        </div>
      </div>
    </div>
  );
}
