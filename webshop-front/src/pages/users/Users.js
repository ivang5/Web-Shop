import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { useAuth } from "../../contexts/AuthContext";
import useFetch from "../../utils/useFetch";
import UserRow from "./UserRow";

export default function Users() {
  const [users, setUsers] = useState([]);
  const { getRole } = useAuth();
  const api = useFetch();
  const navigate = useNavigate();

  useEffect(() => {
    if (getRole() !== "admin") {
      navigate("/404");
    }

    getUsers();
  }, []);

  const getUsers = async () => {
    var newList = [];
    var reply = { response: {}, data: {} };

    reply = await api(`/shop/users/admin`);
    reply.data.forEach((user) => {
      user.role = "admin";
    });
    newList = newList.concat(reply.data);

    reply = await api(`/shop/users/seller`);
    reply.data.forEach((user) => {
      user.role = "seller";
    });
    newList = newList.concat(reply.data);

    reply = await api(`/shop/users/buyer`);
    reply.data.forEach((user) => {
      user.role = "buyer";
    });
    newList = newList.concat(reply.data);

    setUsers(newList);
  };

  return (
    <div className="container">
      <h1 className="pt-5">Users</h1>
      <table className="table mt-4">
        <thead>
          <tr>
            <th scope="col">#</th>
            <th scope="col">Username</th>
            <th scope="col">Role</th>
            <th scope="col">First name</th>
            <th scope="col">Last name</th>
            <th scope="col">Blocked</th>
          </tr>
        </thead>
        <tbody>
          {users.map((user) => {
            return (
              <UserRow
                key={user.username}
                id={user.id}
                username={user.username}
                role={user.role}
                firstName={user.firstName}
                lastName={user.lastName}
                blocked={user.blocked}
              />
            );
          })}
        </tbody>
      </table>
    </div>
  );
}
