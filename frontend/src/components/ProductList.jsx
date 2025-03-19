import React from "react";
import ProductCard from "./ProductCard";

const ProductList = ({ products }) => {
    return (
        <section className="product-list">
            {products.map((product) => (
                <ProductCard key={product.id} product={product} />
            ))}
        </section>
    );
};

export default ProductList;
