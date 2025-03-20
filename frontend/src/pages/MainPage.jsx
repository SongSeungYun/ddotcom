import React from "react";
import Header from "../components/Header";
import Banner from "../components/Banner";
import ProductList from "../components/ProductList";
import Footer from "../components/Footer";
import "../styles/mainpage.css";
import "../styles/global.css";

const MainPage = () => {
    // 샘플 상품 데이터
    const products = [
        { id: 1, name: "상품1", price: "20,000원", participants: "3 / 5" },
        { id: 2, name: "상품2", price: "15,000원", participants: "2 / 4" },
        { id: 3, name: "상품3", price: "30,000원", participants: "5 / 10" },
        { id: 4, name: "상품4", price: "25,000원", participants: "1 / 3" },
        { id: 5, name: "상품5", price: "10,000원", participants: "4 / 6" },
        { id: 6, name: "상품6", price: "50,000원", participants: "8 / 10" },
    ];

    return (
        <div className="container">
            <Header />
            <Banner />
            <button className="register-btn">공구상품 등록하기 →</button>
            <ProductList products={products} />
            <Footer />
        </div>
    );
};

export default MainPage;
