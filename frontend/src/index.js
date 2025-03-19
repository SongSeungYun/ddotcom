import React from "react";
import ReactDOM from "react-dom/client";
import "./styles/global.css"; // 글로벌 CSS 파일
import App from "./App";      // App 컴포넌트

// ReactDOM 렌더링
const root = ReactDOM.createRoot(document.getElementById("root")); // HTML의 root 엘리먼트에 연결
root.render(
    <React.StrictMode>
        <App />
    </React.StrictMode>
);