import React from "react";
import { Link } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";

export default function UserRow({
  id,
  username,
  role,
  firstName,
  lastName,
  blocked,
}) {
  const { user } = useAuth();

  return (
    <tr>
      <th scope="row">{id}</th>
      <td>
        {username === user.sub ? (
          <Link className="text-decoration-none" to={"/profile"}>
            {username}
          </Link>
        ) : (
          <Link
            className="text-decoration-none"
            to={`/users/${role}/${username}`}
          >
            {username}
          </Link>
        )}
      </td>
      <td>{role}</td>
      <td>{firstName}</td>
      <td>{lastName}</td>
      <td>{blocked ? "true" : "false"}</td>
    </tr>
  );
}
