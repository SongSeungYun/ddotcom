import { useState } from "react";
import axios from "axios";

const useAuth = () => {
    const [isAuthenticated, setIsAuthenticated] = useState(false);

    const login = async (loginId, password) => {
        try {
            const response = await axios.post("http://localhost:8080/api/member/login", { loginId, password });
            setIsAuthenticated(response.data.success);
            return response.data;
        } catch (error) {
            console.error(error);
            return null;
        }
    };

    return { isAuthenticated, login };
};

export default useAuth;
