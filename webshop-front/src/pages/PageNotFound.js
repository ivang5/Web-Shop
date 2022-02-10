import React, { useEffect } from "react";
import { Link } from "react-router-dom";

export default function PageNotFound() {
  return (
    <div className="not-found">
      <div className="not-found__content">
        <span className="not-found__number">404</span>
        <h1>Opps! Page Not Found</h1>
        <p>Sorry, the page you were looking for does not exist.</p>
        <Link className="btn btn-primary mt-3" to={"/"}>
          Home
        </Link>
      </div>
    </div>
  );
}
