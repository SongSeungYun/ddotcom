import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import Input from "../components/Input"; // 수정된 Input 컴포넌트 사용
import Button from "../components/Button";
import useValidation from "../hooks/useValidation";
import axios from "axios";
import "../styles/signup.css";
import "../styles/global.css";

const SignupPage = () => {
    const [formData, setFormData] = useState({
        loginId: "",
        password: "",
        name: "",
        phoneNumber: "",
        email: "",
        nickname: "",
        university: "",
        dormitory: "",
        code: "",
    });

    const [emailVerified, setEmailVerified] = useState(false);
    const [dormitories, setDormitories] = useState([]);
    const { validateEmail, validatePassword } = useValidation();

    const navigate = useNavigate();

    // 입력 필드 변경 핸들러
    const handleInputChange = (e) => {
        const { name, value } = e.target;

        setFormData((prevData) => {
            const updatedData = {
                ...prevData,
                [name]: value,
            };
            console.log("Updated formData:", updatedData);
            return updatedData;
        });
    };

    useEffect(() => {
        console.log("formData changed:", formData);
    }, [formData]);

    // 중복 체크 함수
    const checkLoginId = async () => {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/member/check-login-id?loginId=${formData.loginId}`
            );
            alert(response.data.message);
        } catch (error) {
            console.error(error);
            alert("Error checking login ID availability");
        }
    };

    // 닉네임 중복 체크 함수
    const checkNickname = async () => {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/member/check-nickname?nickname=${formData.nickname}`
            );
            alert(response.data.message);
        } catch (error) {
            console.error(error);
            alert("Error checking nickname availability");
        }
    };

    // 이메일 인증 코드 전송
    const sendEmailVerificationCode = async () => {
        try {
            await axios.post("http://localhost:8080/api/email/send", { email: formData.email });
            alert("Verification code sent to your email.");
        } catch (error) {
            console.error(error);
            alert("Failed to send verification code.");
        }
    };

    // 이메일 인증 및 university 업데이트
    const verifyEmailCodeAndFetchUniversity = async () => {
        try {
            const response = await axios.post(
                `http://localhost:8080/api/member/verify-email?email=${formData.email}&code=${formData.code}`
            );

            if (response.data.success) {
                const universityName = response.data.data; // university 이름
                setFormData((prevData) => ({
                    ...prevData,
                    university: universityName,
                }));
                setEmailVerified(true);
                alert("Email verified successfully.");
            } else {
                alert(response.data.message);
            }
        } catch (error) {
            console.error(error);
            alert("Failed to verify email.");
        }
    };

    // university를 기반으로 기숙사 목록 가져오기
    useEffect(() => {
        if (formData.university) {
            fetchDormitories(formData.university);
        }
    }, [formData.university]);

    const fetchDormitories = async (universityName) => {
        try {
            const response = await axios.get(
                `http://localhost:8080/api/university/dormitories?universityName=${universityName}`
            );
            setDormitories(response.data.data || []);
        } catch (error) {
            console.error(error);
            alert("Failed to fetch dormitories.");
        }
    };

    // 회원가입 처리
    const handleSignup = async () => {
        if (!validateEmail(formData.email)) return alert("Invalid email format.");
        if (!validatePassword(formData.password)) return alert("Invalid password format.");

        try {
            await axios.post("http://localhost:8080/api/member/signup", formData);
            alert("Signup successful!");

            // 회원가입 성공 후에만 폼 초기화
            setFormData({
                loginId: "",
                password: "",
                name: "",
                phoneNumber: "",
                email: "",
                nickname: "",
                dormitory: "",
                code: "",
                university: "",
            });
            navigate("/login");

        } catch (error) {
            console.error(error);
            alert("Signup failed.");
        }
    };

    return (
        <div className="signup-form">
            <h1>Sign Up</h1>

            {/* 로그인 ID */}
            <Input
                name="loginId"
                placeholder="Login ID"
                value={formData.loginId}
                onChange={handleInputChange}
            />

            <Button type="button" text="Check Login ID" onClick={checkLoginId} />

            {/* 비밀번호 */}
            <Input
                name="password"
                type="password"
                placeholder="Password"
                value={formData.password}
                onChange={handleInputChange}
            />

            {/* 이름 */}
            <Input
                name="name"
                placeholder="Name"
                value={formData.name}
                onChange={handleInputChange}
            />

            {/* 전화번호 */}
            <Input
                name="phoneNumber"
                placeholder="Phone Number"
                value={formData.phoneNumber}
                onChange={handleInputChange}
            />

            {/* 이메일 */}
            <Input
                name="email"
                placeholder="Email"
                value={formData.email}
                onChange={handleInputChange}
            />

            <Button type="button" text="Send Verification Code" onClick={sendEmailVerificationCode} />

            {/* 대학교 표시 */}
            {emailVerified && (
                <p>
                    University matched to your email:
                    <strong>{formData.university || "No university found"}</strong>
                </p>
            )}

            {/* 인증 코드 */}
            <Input
                name="code"
                placeholder="Verification Code"
                value={formData.code}
                onChange={handleInputChange}
            />

            <Button text="Verify Email" onClick={verifyEmailCodeAndFetchUniversity} />

            {/* 기숙사 선택 */}
            {emailVerified && (
                <select name="dormitory" value={formData.dormitory} onChange={handleInputChange}>
                    <option value="">Select Dormitory</option>
                    {dormitories.map((dormitory) => (
                        <option key={dormitory} value={dormitory}>
                            {dormitory}
                        </option>
                    ))}
                </select>
            )}

            {/* 닉네임 */}
            <Input
                name="nickname"
                placeholder="Nickname"
                value={formData.nickname}
                onChange={handleInputChange}
            />

            <Button type="button" text="Check Nickname" onClick={checkNickname} />

            {/* 회원가입 버튼 */}
            <Button type="button" text="Sign Up" onClick={handleSignup} />
        </div>
    );
};

export default SignupPage;
