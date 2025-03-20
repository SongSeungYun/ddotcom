import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Input from "../components/Input";
import Button from "../components/Button";
import useAuth from "../hooks/useAuth";
import "../styles/login.css";
import "../styles/global.css";

const LoginPage = () => {
    const [loginId, setLoginId] = useState("");
    const [password, setPassword] = useState("");
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        const response = await login(loginId, password);
        if (response && response.success) {
            navigate("/main");
        } else {
            alert(response?.message || "Login failed");
        }
    };

    return (
        <form onSubmit={handleSubmit} className="login-form">
            <h1>Login</h1>
            <Input
                type="text"
                placeholder="Login ID"
                value={loginId}
                onChange={(e) => setLoginId(e.target.value)}
            />
            <Input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
            />
            <Button text="Login" type="submit" className="login-button" />
        </form>
    );
};

export default LoginPage;
