import React, { useEffect, useState } from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../contexts/AuthContext";

export default function Navbar({ setLoggedIn, cart }) {
  const [isNavCollapsed, setIsNavCollapsed] = useState(true);
  const [cartLength, setCartLength] = useState(0);
  const handleNavCollapse = () => setIsNavCollapsed(!isNavCollapsed);
  const { user, logoutUser, getRole } = useAuth();
  const currentPath = window.location.pathname;

  useEffect(() => {
    if (currentPath != "/login") {
      setLoggedIn(true);
    } else setLoggedIn(false);
  }, [currentPath]);

  useEffect(() => {
    getCartLenght();
  }, [cart]);

  const getCartLenght = () => {
    let counter = 0;

    cart.forEach((item) => {
      counter += parseInt(item.quantity);
    });

    setCartLength(counter);
  };

  return (
    <>
      {currentPath !== "/login" && user !== null && (
        <nav className="navbar navbar-expand-lg sticky-top navbar-light bg-light nav">
          <div className="container">
            <Link className="navbar-brand text-primary" to={"/"}>
              <i className="bi bi-shop fs-3"></i>
            </Link>
            <button
              className="navbar-toggler"
              type="button"
              data-toggle="collapse"
              data-target="#navbarNavAltMarkup"
              aria-controls="navbarNavAltMarkup"
              aria-expanded={!isNavCollapsed ? true : false}
              aria-label="Toggle navigation"
              onClick={handleNavCollapse}
            >
              <span className="navbar-toggler-icon"></span>
            </button>
            <div
              className={`${isNavCollapsed ? "collapse" : ""} navbar-collapse`}
              id="navbarNavAltMarkup"
            >
              <ul className="navbar-nav me-auto">
                <li className="nav-item">
                  <Link
                    className="nav-link active"
                    aria-current="page"
                    to={"/"}
                  >
                    Home
                  </Link>
                </li>
                {getRole() === "buyer" && (
                  <li className="nav-item">
                    <Link className="nav-link" to={"/orders"}>
                      Orders
                    </Link>
                  </li>
                )}
                {(getRole() === "seller" || getRole() === "buyer") && (
                  <li className="nav-item">
                    <Link className="nav-link" to={"/sellers"}>
                      Sellers
                    </Link>
                  </li>
                )}
                {getRole() === "admin" && (
                  <li className="nav-item">
                    <Link className="nav-link" to={"/users"}>
                      Users
                    </Link>
                  </li>
                )}
              </ul>
              <ul className="navbar-nav nav__secondary">
                <li className="nav-item dropdown">
                  <a
                    className="nav-link dropdown-toggle text-capitalize"
                    href="#"
                    id="navbarLightDropdownMenuLink"
                    role="button"
                    data-bs-toggle="dropdown"
                    aria-expanded="false"
                  >
                    {user.sub}
                  </a>
                  <ul
                    className="dropdown-menu dropdown-menu-light"
                    aria-labelledby="navbarLightDropdownMenuLink"
                  >
                    <li>
                      <Link className="dropdown-item" to={"/profile"}>
                        <i className="bi bi-person pe-1 fs-5"></i> Profile
                      </Link>
                    </li>
                    <li>
                      <Link
                        className="dropdown-item l-align-center"
                        to={"/login"}
                        onClick={() => {
                          setLoggedIn(false);
                          logoutUser();
                        }}
                      >
                        <i className="bi bi-box-arrow-right pe-2 fs-5"></i>
                        Logout
                      </Link>
                    </li>
                  </ul>
                </li>
                {getRole() === "buyer" && (
                  <li className="nav-item nav__cart">
                    <Link
                      to={"/cart"}
                      className="btn btn-outline-primary position-relative nav__btn"
                    >
                      <i className="bi bi-cart nav__cart-icon" />
                      Cart
                      {cart.length !== 0 && (
                        <span className="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
                          {cartLength}
                          <span className="visually-hidden">
                            unread messages
                          </span>
                        </span>
                      )}
                    </Link>
                  </li>
                )}
              </ul>
            </div>
          </div>
        </nav>
      )}
    </>
  );
}
