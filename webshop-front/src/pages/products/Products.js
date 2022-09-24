import { Modal, Toast, Collapse } from "bootstrap";
import React, { useEffect, useRef, useState } from "react";
import { Link, useParams } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import useFetch from "../../utils/useFetch";
import { currencyFormatter, productTypeFormatter } from "../../utils/utils";
import Product from "./Product";

export default function Products({ cart, setCart }) {
  const [products, setProducts] = useState([]);
  const [seller, setSeller] = useState({});
  const [searchResults, setSearchResults] = useState([]);
  const [esResults, setEsResults] = useState([]);
  const [ratingResults, setRatingResults] = useState([]);
  const [commentResults, setCommentResults] = useState([]);
  const [showResults, setShowResults] = useState(false);
  const [searching, setSearching] = useState(false);
  const nameRef = useRef();
  const descriptionRef = useRef();
  const priceRef = useRef();
  const productTypeRef = useRef();
  const picturePathRef = useRef();
  const searchNameRef = useRef();
  const searchPriceFromRef = useRef();
  const searchPriceToRef = useRef();
  const searchDescRef = useRef();
  const searchAllRef = useRef();
  const searchPhraseRef = useRef();
  const searchFuzzyRef = useRef();
  const searchRatingFromRef = useRef();
  const searchRatingToRef = useRef();
  const searchCommentsFromRef = useRef();
  const searchCommentsToRef = useRef();
  const [selectedFile, setSelectedFile] = useState(null);
  const modal = useRef();
  const toast = useRef();
  const collapse = useRef();
  const { user, getRole, authTokens } = useAuth();
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

  useEffect(() => {
    getSearchResults();
  }, [esResults, ratingResults, commentResults]);

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

    if (selectedFile) {
      const file = await uploadFile();
      product.detailedDescription = file;
      await pushProduct(product);
    } else {
      await pushProduct(product);
    }

    getProducts();
    showToast();
  };

  const pushProduct = async (product) => {
    await api("/shop/products", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(product),
    });
  };

  const getSeller = async () => {
    const { response, data } = await api(`/shop/users/seller/${user.sub}`);

    if (response.status === 200) {
      setSeller(data);
    }
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

  const search = async (e) => {
    e.preventDefault();

    setShowResults(true);
    setSearching(true);

    const rateFrom = getNumber(searchRatingFromRef.current.value);
    const rateTo = getNumber(searchRatingToRef.current.value);
    const commentsFrom = getNumber(searchCommentsFromRef.current.value);
    const commentsTo = getNumber(searchCommentsToRef.current.value);

    await getEsResults();

    if (rateFrom != 0 || rateTo != 0) {
      await getRatingResults(rateFrom, rateTo);
    } else {
      setRatingResults([]);
    }

    if (commentsFrom != 0 || commentsTo != 0) {
      await getCommentResults(commentsFrom, commentsTo);
    } else {
      setCommentResults([]);
    }
  };

  const getSearchResults = () => {
    const rateFrom = getNumber(searchRatingFromRef.current.value);
    const rateTo = getNumber(searchRatingToRef.current.value);
    const commentsFrom = getNumber(searchCommentsFromRef.current.value);
    const commentsTo = getNumber(searchCommentsToRef.current.value);
    let commonResults = [];

    if (esResults.length !== 0) {
      commonResults = esResults.filter(
        (value) => (value.id = value.associatedId)
      );

      if (rateFrom != 0 || rateTo != 0) {
        commonResults = commonResults.filter((product) =>
          ratingResults.some((item) => item.id === product.associatedId)
        );
      }

      if (commentsFrom != 0 || commentsTo != 0) {
        commonResults = commonResults.filter((product) =>
          commentResults.some((item) => item.id === product.associatedId)
        );
      }
    } else if (ratingResults.length !== 0) {
      if (areAllInputsEmpty() || !searchAllRef.current.checked) {
        commonResults = ratingResults;

        if (commentsFrom != 0 || commentsTo != 0) {
          commonResults = commonResults.filter((product) =>
            commentResults.some((item) => item.id === product.id)
          );
        }
      }
    } else if (commentResults.length !== 0) {
      if (areAllInputsEmpty() || !searchAllRef.current.checked) {
        commonResults = commentResults;
      }
    }

    setSearchResults(commonResults);
    setSearching(false);
  };

  const getSearchObjEs = () => {
    const nameToSearch =
      searchPhraseRef.current.checked && searchNameRef.current.value !== ""
        ? `"${searchNameRef.current.value}"`
        : searchNameRef.current.value;

    const descToSearch =
      searchPhraseRef.current.checked && searchDescRef.current.value !== ""
        ? `"${searchDescRef.current.value}"`
        : searchDescRef.current.value;

    const operation = searchAllRef.current.checked ? "AND" : "OR";

    const searchObj = {
      name: nameToSearch,
      description: descToSearch,
      priceFrom: getNumber(searchPriceFromRef.current.value),
      priceTo: getNumber(searchPriceToRef.current.value),
      matchAll: operation,
      isFuzzy: searchFuzzyRef.current.checked,
    };

    return searchObj;
  };

  const getEsResults = async () => {
    if (!areAllInputsEmpty()) {
      const searchObjEs = getSearchObjEs();

      const { response, data } = await api(
        `/shop/products/search?name=${searchObjEs.name}&text=${searchObjEs.description}&from=${searchObjEs.priceFrom}&to=${searchObjEs.priceTo}&operation=${searchObjEs.matchAll}&fuzzy=${searchObjEs.isFuzzy}`
      );

      if (response.status === 200) {
        setEsResults(data);
      }
    } else {
      setEsResults([]);
    }
  };

  const getRatingResults = async (rateFrom, rateTo) => {
    rateTo = rateTo == 0 ? 5 : rateTo;

    const { response, data } = await api(
      `/shop/products/rate?from=${rateFrom}&to=${rateTo}`
    );

    if (response.status === 200) {
      setRatingResults(data);
    }
  };

  const getCommentResults = async (commentsFrom, commentsTo) => {
    commentsTo = commentsTo == 0 ? 100000000 : commentsTo;

    const { response, data } = await api(
      `/shop/products/comments?from=${commentsFrom}&to=${commentsTo}`
    );

    if (response.status === 200) {
      setCommentResults(data);
    }
  };

  const areAllInputsEmpty = () => {
    if (
      searchNameRef.current.value === "" &&
      searchDescRef.current.value === "" &&
      getNumber(searchPriceFromRef.current.value) === 0 &&
      getNumber(searchPriceToRef.current.value) === 0
    ) {
      return true;
    }

    return false;
  };

  const getNumber = (value) => {
    const retVal = value === "" ? 0 : value;
    return retVal;
  };

  const handlePhraseCbChange = () => {
    if (searchPhraseRef.current.checked && searchFuzzyRef.current.checked) {
      searchFuzzyRef.current.checked = !searchFuzzyRef.current.checked;
    }
  };

  const handleFuzzyCbChange = () => {
    if (searchFuzzyRef.current.checked && searchPhraseRef.current.checked) {
      searchPhraseRef.current.checked = !searchPhraseRef.current.checked;
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

  const toggleCollapse = () => {
    const collapseEle = collapse.current;
    const bsCollapse = new Collapse(collapseEle, {
      backdrop: "static",
      keyboard: false,
    });
    bsCollapse.toggle();
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
      <div className="search-form mt-5 px-3 pt-2">
        <form>
          <h4
            className="search-form__title"
            aria-expanded="false"
            onClick={toggleCollapse}
          >
            <a href="#">
              <i className="bi bi-search"></i> Search for products
            </a>
          </h4>
          <div className="collapse" ref={collapse}>
            <div className="l-height-1"></div>
            <div className="row">
              <div className="col-md-7">
                <label className="form-label">Name:</label>
                <input
                  type="text"
                  className="form-control"
                  id="searchInput"
                  placeholder="Time machine..."
                  ref={searchNameRef}
                />
              </div>
              <div className="col-md-5">
                <label className="form-label">Price:</label>
                <div className="d-flex">
                  <input
                    type="number"
                    className="form-control"
                    placeholder="From"
                    min={0}
                    ref={searchPriceFromRef}
                  />
                  <span className="search-form__price-delimiter">-</span>
                  <input
                    type="number"
                    className="form-control"
                    placeholder="To"
                    min={0}
                    ref={searchPriceToRef}
                  />
                </div>
              </div>
              <div className="col-md-12 mt-3">
                <label className="form-label">Description:</label>
                <input
                  type="text"
                  className="form-control"
                  id="searchInput"
                  placeholder="With this machine, you can go back in time..."
                  ref={searchDescRef}
                />
              </div>
            </div>
            <div className="d-block">
              <div className="d-inline-block mt-3 mb-3 me-3 form-check">
                <input
                  type="checkbox"
                  className="form-check-input"
                  id="nameCheck"
                  defaultChecked="true"
                  ref={searchAllRef}
                />
                <label className="form-check-label" htmlFor="nameCheck">
                  Satisfy all search inputs
                </label>
              </div>
              <div className="d-inline-block mb-3 form-check">
                <input
                  type="checkbox"
                  className="form-check-input"
                  id="descCheck"
                  ref={searchPhraseRef}
                  onChange={handlePhraseCbChange}
                />
                <label className="form-check-label" htmlFor="descCheck">
                  Match whole text input
                </label>
              </div>
              <div className="d-inline-block mb-3 mx-3 form-check">
                <input
                  type="checkbox"
                  className="form-check-input"
                  id="priceCheck"
                  ref={searchFuzzyRef}
                  onChange={handleFuzzyCbChange}
                />
                <label className="form-check-label" htmlFor="priceCheck">
                  Allow small differences
                </label>
              </div>
            </div>
            <hr className="search-form__line" />
            <h5>Filter results:</h5>
            <div className="row mt-3">
              <div className="col-md-6">
                <label className="form-label">
                  Products with average rating:
                </label>
                <div className="d-flex">
                  <input
                    type="number"
                    className="form-control"
                    placeholder="From"
                    min={1}
                    max={5}
                    ref={searchRatingFromRef}
                  />
                  <span className="search-form__price-delimiter">-</span>
                  <input
                    type="number"
                    className="form-control"
                    placeholder="To"
                    min={1}
                    max={5}
                    ref={searchRatingToRef}
                  />
                </div>
              </div>
              <div className="col-md-6">
                <label className="form-label">
                  Products with number of comments:
                </label>
                <div className="d-flex">
                  <input
                    type="number"
                    className="form-control"
                    placeholder="From"
                    min={0}
                    ref={searchCommentsFromRef}
                  />
                  <span className="search-form__price-delimiter">-</span>
                  <input
                    type="number"
                    className="form-control"
                    placeholder="To"
                    min={0}
                    ref={searchCommentsToRef}
                  />
                </div>
              </div>
            </div>
            <button
              type="submit"
              className="btn btn-primary mt-3"
              onClick={search}
            >
              <i className="bi bi-search"></i> Search
            </button>
            <div className="l-height-1"></div>
            <div className={`search-form__results ${!showResults && "hidden"}`}>
              <hr className="search-form__line" />
              <h4>Results:</h4>
              <div
                className={`spinner-border text-primary ${
                  !searching && "hidden"
                }`}
                role="status"
              >
                <span className="visually-hidden">Loading...</span>
              </div>
              <div className={searching && "hidden"}>
                {searchResults.length === 0 ? (
                  <p>Sorry, we couldn't find any results</p>
                ) : (
                  searchResults.map((product) => {
                    return (
                      <div
                        key={product.id}
                        className="search-form__results-item pb-1"
                      >
                        <h5 className="d-inline-block">
                          <Link
                            className="text-decoration-none"
                            to={`/products/${product.id}`}
                          >
                            {product.name}
                          </Link>
                        </h5>
                        <span className="ps-2 fw-bold fs-5">
                          ({currencyFormatter.format(product.price)})
                        </span>
                      </div>
                    );
                  })
                )}
              </div>
            </div>
          </div>
        </form>
      </div>
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
