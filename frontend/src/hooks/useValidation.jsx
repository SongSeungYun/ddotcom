const useValidation = () => {
    const validateEmail = (email) => /\S+@\S+\.\S+/.test(email);
    const validatePassword = (password) =>
        /^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#\$%^&*])[a-zA-Z0-9!@#\$%^&*]{8,20}$/.test(password);

    return { validateEmail, validatePassword };
};

export default useValidation;
