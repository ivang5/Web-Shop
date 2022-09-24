import Login from "./pages/Login";
import Profile from "./pages/Profile";
import { AuthProvider } from "./contexts/AuthContext";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar";
import Products from "./pages/products/Products";
import PageNotFound from "./pages/PageNotFound";
import PrivateRoute from "./utils/PrivateRoute";
import ProductDetails from "./pages/products/ProductDetails";
import Users from "./pages/users/Users";
import User from "./pages/users/User";
import Sellers from "./pages/sellers/Sellers";
import SellerDetails from "./pages/sellers/SellerDetails";
import Orders from "./pages/orders/Orders";
import OrderDetails from "./pages/orders/OrderDetails";
import Cart from "./pages/cart/Cart";
import Footer from "./components/Footer";
import { useState } from "react";
import useLocalStorage from "./utils/useLocalStorage";

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [cart, setCart] = useLocalStorage("cart", []);

  return (
    <Router>
      <AuthProvider>
        <Navbar setLoggedIn={setLoggedIn} cart={cart} />
        <div className={`${loggedIn ? "l-content" : "l-content-100"}`}>
          <Routes>
            <Route exact path="/" element={<PrivateRoute />}>
              <Route
                exact
                path="/"
                element={<Products cart={cart} setCart={setCart} />}
              ></Route>
            </Route>
            <Route exact path="/login" element={<Login />}></Route>
            <Route exact path="/profile" element={<PrivateRoute />}>
              <Route exact path="/profile" element={<Profile />}></Route>
            </Route>
            <Route exact path="/products/:id" element={<PrivateRoute />}>
              <Route
                exact
                path="/products/:id"
                element={<ProductDetails cart={cart} setCart={setCart} />}
              ></Route>
            </Route>
            <Route
              exact
              path="/products/seller/:username"
              element={<PrivateRoute />}
            >
              <Route
                exact
                path="/products/seller/:username"
                element={<Products cart={cart} setCart={setCart} />}
              ></Route>
            </Route>
            <Route exact path="/users" element={<PrivateRoute />}>
              <Route exact path="/users" element={<Users />}></Route>
            </Route>
            <Route
              exact
              path="/users/:role/:username"
              element={<PrivateRoute />}
            >
              <Route
                exact
                path="/users/:role/:username"
                element={<User />}
              ></Route>
            </Route>
            <Route exact path="/sellers" element={<PrivateRoute />}>
              <Route exact path="/sellers" element={<Sellers />}></Route>
            </Route>
            <Route exact path="/sellers/:username" element={<PrivateRoute />}>
              <Route
                exact
                path="/sellers/:username"
                element={<SellerDetails />}
              ></Route>
            </Route>
            <Route exact path="/orders" element={<PrivateRoute />}>
              <Route exact path="/orders" element={<Orders />}></Route>
            </Route>
            <Route exact path="/orders/:id" element={<PrivateRoute />}>
              <Route
                exact
                path="/orders/:id"
                element={<OrderDetails />}
              ></Route>
            </Route>
            <Route exact path="/cart" element={<PrivateRoute />}>
              <Route
                exact
                path="/cart"
                element={<Cart cart={cart} setCart={setCart} />}
              ></Route>
            </Route>
            <Route path="*" element={<PrivateRoute />}></Route>
            <Route path="*" element={<PrivateRoute />}>
              <Route path="*" element={<PageNotFound />} />
            </Route>
          </Routes>
        </div>
        <Footer />
      </AuthProvider>
    </Router>
  );
}

export default App;
