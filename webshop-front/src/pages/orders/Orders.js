import React, { useEffect, useRef, useState } from "react";
import { Collapse } from "bootstrap";
import { Link, useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import { dateFormatter } from "../../utils/utils";
import useFetch from "../../utils/useFetch";
import Order from "./Order";

export default function Orders() {
  const [orders, setOrders] = useState([]);
  const [buyer, setBuyer] = useState({});
  const { user, getRole } = useAuth();
  const [searchResults, setSearchResults] = useState([]);
  const [esResults, setEsResults] = useState([]);
  const [showResults, setShowResults] = useState(false);
  const [searching, setSearching] = useState(false);
  const commentRef = useRef();
  const ratingFromRef = useRef();
  const ratingToRef = useRef();
  const priceFromRef = useRef();
  const priceToRef = useRef();
  const searchAllRef = useRef();
  const searchPhraseRef = useRef();
  const searchFuzzyRef = useRef();
  const collapse = useRef();
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

  useEffect(() => {
    let commonResults = esResults.filter((product) =>
      orders.some((item) => item.id === product.associatedId)
    );

    setSearchResults(commonResults);
    setSearching(false);
  }, [esResults]);

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

  const search = async (e) => {
    e.preventDefault();

    setShowResults(true);
    setSearching(true);

    await getEsResults();
  };

  const getEsResults = async () => {
    if (!areAllInputsEmpty()) {
      const searchObjEs = getSearchObjEs();

      const { response, data } = await api(
        `/shop/orders/search?comment=${searchObjEs.comment}&fromRate=${searchObjEs.rateFrom}&toRate=${searchObjEs.rateTo}&fromPrice=${searchObjEs.priceFrom}&toPrice=${searchObjEs.priceTo}&operation=${searchObjEs.matchAll}&fuzzy=${searchObjEs.isFuzzy}`
      );

      if (response.status === 200) {
        setEsResults(data);
      }
    } else {
      setEsResults([]);
    }
  };

  const getSearchObjEs = () => {
    const commentToSearch =
      searchPhraseRef.current.checked && commentRef.current.value !== ""
        ? `"${commentRef.current.value}"`
        : commentRef.current.value;

    const operation = searchAllRef.current.checked ? "AND" : "OR";

    const searchObj = {
      comment: commentToSearch,
      rateFrom: getNumber(ratingFromRef.current.value),
      rateTo: getNumber(ratingToRef.current.value),
      priceFrom: getNumber(priceFromRef.current.value),
      priceTo: getNumber(priceToRef.current.value),
      matchAll: operation,
      isFuzzy: searchFuzzyRef.current.checked,
    };

    return searchObj;
  };

  const getNumber = (value) => {
    const retVal = value === "" ? 0 : value;
    return retVal;
  };

  const areAllInputsEmpty = () => {
    if (
      commentRef.current.value === "" &&
      getNumber(ratingFromRef.current.value) === 0 &&
      getNumber(ratingToRef.current.value) === 0 &&
      getNumber(priceFromRef.current.value) === 0 &&
      getNumber(priceToRef.current.value) === 0
    ) {
      return true;
    }

    return false;
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

  const toggleCollapse = () => {
    const collapseEle = collapse.current;
    const bsCollapse = new Collapse(collapseEle, {
      backdrop: "static",
      keyboard: false,
    });
    bsCollapse.toggle();
  };

  return (
    <>
      {orders && (
        <div className="container">
          <h1 className="pt-5">
            {orders.length === 0
              ? "You don't have any orders yet..."
              : "Your orders:"}
          </h1>
          <div className="search-form mt-5 px-3 pt-2">
            <form>
              <h4
                className="search-form__title"
                aria-expanded="false"
                onClick={toggleCollapse}
              >
                <a href="#">
                  <i className="bi bi-search"></i> Search for orders
                </a>
              </h4>
              <div className="collapse" ref={collapse}>
                <div className="l-height-1"></div>
                <div className="row">
                  <div className="col-md-12 pb-3">
                    <label className="form-label">Comment:</label>
                    <input
                      type="text"
                      className="form-control"
                      id="searchInput"
                      placeholder="This was the best order of my life..."
                      ref={commentRef}
                    />
                  </div>
                  <div className="col-md-6">
                    <label className="form-label">Rating:</label>
                    <div className="d-flex">
                      <input
                        type="number"
                        className="form-control"
                        placeholder="From"
                        min={1}
                        max={5}
                        ref={ratingFromRef}
                      />
                      <span className="search-form__price-delimiter">-</span>
                      <input
                        type="number"
                        className="form-control"
                        placeholder="To"
                        min={1}
                        max={5}
                        ref={ratingToRef}
                      />
                    </div>
                  </div>
                  <div className="col-md-6">
                    <label className="form-label">Price:</label>
                    <div className="d-flex">
                      <input
                        type="number"
                        className="form-control"
                        placeholder="From"
                        min={0}
                        ref={priceFromRef}
                      />
                      <span className="search-form__price-delimiter">-</span>
                      <input
                        type="number"
                        className="form-control"
                        placeholder="To"
                        min={0}
                        ref={priceToRef}
                      />
                    </div>
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
                <button
                  type="submit"
                  className="btn btn-primary mt-3"
                  onClick={search}
                >
                  <i className="bi bi-search"></i> Search
                </button>
                <div className="l-height-1"></div>
                <div
                  className={`search-form__results ${!showResults && "hidden"}`}
                >
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
                      searchResults.map((order) => {
                        return (
                          <div
                            key={order.associatedId}
                            className="search-form__results-item pb-1"
                          >
                            <h5 className="d-inline-block">
                              <Link
                                className="text-decoration-none"
                                to={`/orders/${order.associatedId}`}
                              >
                                {dateFormatter(order.time)}
                              </Link>
                            </h5>
                            <span className="ps-3 fw-bold fs-5">
                              {order.anonymousComment
                                ? "Anonymous"
                                : "Non-anonymous"}{" "}
                              comment
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
