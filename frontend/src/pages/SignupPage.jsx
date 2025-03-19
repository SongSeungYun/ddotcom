import React, { useState } from "react";
import Input from "../components/Input";
import Button from "../components/Button";
import useValidation from "../hooks/useValidation";
import axios from "axios";

const SignupPage = () => {
    const [formData, setFormData] = useState({
        loginId: "",
        password: "",
        name: "",
        phoneNumber: "",
        email: "",
        nickname: "",
        dormitory: "",
        code: "",
    });
    const [emailVerified, setEmailVerified] = useState(false);
    const [dormitories, setDormitories] = useState([]);
    const { validateEmail, validatePassword } = useValidation();

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setFormData({ ...formData, [name]: value });
    };

    const checkAvailability = async (field, value) => {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/member/check-${field}?${field}=${value}`
            );
            alert(response.data.message);
        } catch (error) {
            console.error(error);
            alert("Error checking availability");
        }
    };

    const sendEmailVerificationCode = async () => {
        try {
            await axios.post("http://localhost:8080/api/email/send", { email: formData.email });
            alert("Verification code sent to your email.");
        } catch (error) {
            console.error(error);
            alert("Failed to send verification code.");
        }
    };

    const verifyEmailCodeAndFetchDormitories = async () => {
        try {
            const response = await axios.post("http://localhost:8080/api/member/verify-email", {
                email: formData.email,
                code: formData.code,
            });
            if (response.data.success) {
                setEmailVerified(true);
                setDormitories(response.data.data.dormitories);
                alert("Email verified successfully.");
            } else {
                alert(response.data.message);
            }
        } catch (error) {
            console.error(error);
            alert("Failed to verify email.");
        }
    };

    const handleSignup = async () => {
        if (!validateEmail(formData.email)) return alert("Invalid email format.");
        if (!validatePassword(formData.password)) return alert("Invalid password format.");

        try {
            await axios.post("http://localhost:8080/api/member/signup", formData);
            alert("Signup successful!");
        } catch (error) {
            console.error(error);
            alert("Signup failed.");
        }
    };

    return (
        <form className="signup-form">
            <h1>Sign Up</h1>

            <Input name="loginId" placeholder="Login ID" onChange={handleInputChange} />
            <Button text="Check Login ID" onClick={() => checkAvailability("login-id", formData.loginId)} />

            <Input name="password" type="password" placeholder="Password" onChange={handleInputChange} />

            <Input name="name" placeholder="Name" onChange={handleInputChange} />

            <Input name="phoneNumber" placeholder="Phone Number" onChange={handleInputChange} />

            <Input name="email" placeholder="Email" onChange={handleInputChange} />
            <Button text="Send Verification Code" onClick={sendEmailVerificationCode} />

            {/* Verify Email and Fetch Dormitories */}
            <Button
                text="Verify Email and Fetch Dormitories"
                onClick={verifyEmailCodeAndFetchDormitories}
            />

            {emailVerified && (
                <>
                    <select name="dormitory" onChange={handleInputChange}>
                        {dormitories.map((dormitory) => (
                            <option key={dormitory} value={dormitory}>
                                {dormitory}
                            </option>
                        ))}
                    </select>
                </>
            )}

            <Input name="nickname" placeholder="Nickname" onChange={handleInputChange} />
            <Button text="Check Nickname" onClick={() => checkAvailability("nickname", formData.nickname)} />

            <Button text="Sign Up" onClick={handleSignup} />
        </form>
    );
};

export default SignupPage;
