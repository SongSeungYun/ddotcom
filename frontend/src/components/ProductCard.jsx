import React from "react";

const ProductCard = ({ product }) => {
    return (
        <div className="product">
            <div className="product-image">이미지</div>
            <div className="product-info">
                <p>{product.name}</p>
                <p>가격: {product.price}</p>
                <p>인원: {product.participants}</p>
            </div>
            <button className="group-buy-btn">공구하기</button>
        </div>
    );
};

export default ProductCard;
