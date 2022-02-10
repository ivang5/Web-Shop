import React, { useRef } from "react";
import { Link } from "react-router-dom";
import { currencyFormatter } from "../../utils/utils";

export default function CartItem({ index, item, cart, setCart }) {
  const quantityInput = useRef();

  const changeQuantity = () => {
    let newCart = [...cart];
    let newItem = { ...newCart[index - 1] };

    newItem.quantity = quantityInput.current.value;
    newCart[index - 1] = newItem;

    setCart(newCart);
  };

  const removeFromCart = () => {
    setCart(cart.filter((cartItem) => cartItem.product.id !== item.product.id));
  };

  return (
    <tr>
      <th scope="row">{index}</th>
      <td>
        <Link
          className="text-decoration-none"
          to={`/products/${item.product.id}`}
        >
          {item.product.name}
        </Link>
      </td>
      <td>{item.product.seller.username}</td>
      <td>{currencyFormatter.format(item.product.price)}</td>
      <td>
        <input
          type="number"
          min="1"
          max="10"
          ref={quantityInput}
          defaultValue={item.quantity}
          onChange={changeQuantity}
        />
      </td>
      <td>{currencyFormatter.format(item.product.price * item.quantity)}</td>
      <td>
        <button className="btn btn-outline-danger" onClick={removeFromCart}>
          <i className="bi bi-cart-dash"></i>
        </button>
      </td>
    </tr>
  );
}
