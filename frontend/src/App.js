import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import AuthSelection from "./pages/AuthSelection"; // 로그인/회원가입 선택 페이지
import LoginPage from "./pages/LoginPage";         // 로그인 페이지
import SignupPage from "./pages/SignupPage";       // 회원가입 페이지
import MainPage from "./pages/MainPage";           // 메인 페이지

const App = () => {
    return (
        <Router>
            <Routes>
                {/* 라우트 정의 */}
                <Route path="/" element={<AuthSelection />} />    {/* 로그인/회원가입 선택 */}
                <Route path="/login" element={<LoginPage />} />   {/* 로그인 */}
                <Route path="/signup" element={<SignupPage />} /> {/* 회원가입 */}
                <Route path="/main" element={<MainPage />} />     {/* 메인 페이지 */}
            </Routes>
        </Router>
    );
};

export default App;