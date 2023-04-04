import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App";
import ServerContextProvider from "./contexts/ServerContext";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <ServerContextProvider>
      <App />
    </ServerContextProvider>
  </React.StrictMode>
);
