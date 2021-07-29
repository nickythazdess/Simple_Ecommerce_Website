import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";

import ProductRatings from "../../components/ProductShowCase/ProductRatings"
import {get} from "../../service/httpHelper"
import {isUser} from "../../service/Authen"
import './showcase.css';

const Product = () => {
    const endpoint = "/product";
    const [user, setUser] = useState(isUser());  
    const [product, setProduct] = useState();
    const location = useLocation();
    const { id } = location.state;

    function getProduct(id) {
        get(endpoint + `${id}`).then(response => {
            if (response.status === 200) {
                setProduct(response.data);
            }
        });
    }

    useEffect(() => { getProduct(id); }, []);

    return (
        <div id = "product">
            <div className = "product-info">
                <img src={product.image} height="400px"/>
                <div className = "product-detail">
                    <div className = "product-name">{product.pname}</div>
                    <div className = "product-category">{product.categoryName}</div>
                    <div className = "product-rate-sold">
                        <img src = "/images/star.png" height="16px"/>
                        <p>{product.rating} | {product.sold} products sold</p>
                    </div>
                    <div className = "product-price">{product.price}</div>
                    <div className = "product-amount">
                        <button className = "circle-btn">-</button>
                        <input type="text" name="amount" placeholder="1" />
                        <button className = "circle-btn">+</button>
                    </div>
                    <button className = "btn-buy">ADD TO CART</button>
                </div>
            </div>
            <div className = "product-description">
                <p>description</p> 
                <div className = "description">{product.description}</div>
            </div>
            <div className = "product-review">
                <div className = "box-review">PRODUCT REVIEWS</div>
                <ProductRatings pid={id} />
                <UserRating user={user} productId={id}/>
            </div>
        </div>
    )
}

export default Product;
