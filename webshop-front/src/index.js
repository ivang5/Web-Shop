import React from "react";
import ReactDOM from "react-dom";
import "./scss/bundle.scss";
import "bootstrap/dist/js/bootstrap.bundle.min";
import App from "./App";

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById("root")
);
