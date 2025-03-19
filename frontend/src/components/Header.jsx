import React from "react";

const Header = () => {
    return (
        <header className="header">
            <h1>D.com</h1>
            <nav>
                <ul>
                    <li><a href="#">Home</a></li>
                    <li><a href="#">About</a></li>
                    <li><a href="#">Blog</a></li>
                    <li><a href="#">Contact</a></li>
                </ul>
            </nav>
            <button className="get-started">Get Started</button>
        </header>
    );
};

export default Header;
