import React from "react";
import { useNavigate } from "react-router-dom";
import Button from "../components/Button";

const AuthSelection = () => {
    const navigate = useNavigate();

    return (
        <div className="auth-selection">
            <h1>Welcome to D.com</h1>
            <Button text="Login" onClick={() => navigate("/login")} className="auth-button" />
            <Button text="Sign Up" onClick={() => navigate("/signup")} className="auth-button" />
        </div>
    );
};

export default AuthSelection;
