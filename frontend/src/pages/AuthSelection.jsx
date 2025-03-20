// import React from "react";
// import { useNavigate } from "react-router-dom";
// import Button from "../components/Button";
//
// const AuthSelection = () => {
//     const navigate = useNavigate();
//
//     return (
//         <div className="auth-selection centered">
//             <h1>Welcome to D.com</h1>
//             <Button text="Login" onClick={() => navigate("/login")} className="auth-button" />
//             <Button text="Sign Up" onClick={() => navigate("/signup")} className="auth-button" />
//         </div>
//     );
// };
//
// export default AuthSelection;
import React from "react";
import { useNavigate } from "react-router-dom";
import "../styles/auth-selection.css";
import "../styles/global.css";

const AuthPage = () => {
    const navigate = useNavigate();

    return (
        <div className="auth-container">
            {/* 왼쪽 섹션 */}
            <div className="auth-left">
                <h1>Welcome to D.com</h1>
                <button
                    className="auth-button login"
                    onClick={() => navigate("/login")}
                >
                    Login
                </button>
                <button
                    className="auth-button signup"
                    onClick={() => navigate("/signup")}
                >
                    Sign Up
                </button>
                <p>Forgot your ID or Password?</p>
            </div>

            {/* 오른쪽 섹션 */}
            <div className="auth-right">
                {/* 실제 사진 배경 */}
                <div className="content">
                    <h1>Discover a New Way to Connect</h1>
                    <p>
                        Join our community and start sharing your journey with others.
                    </p>
                </div>
            </div>
        </div>
    );
};

export default AuthPage;
