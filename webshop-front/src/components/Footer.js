import React from "react";
import { useAuth } from "../contexts/AuthContext";

export default function Footer() {
  const { user } = useAuth();
  const currentPath = window.location.pathname;

  return (
    <>
      {currentPath !== "/login" && user !== null && (
        <footer className="bg-light footer">
          Copyright © 2022, Web Shop, All Rights Reserved.
        </footer>
      )}
    </>
  );
}
