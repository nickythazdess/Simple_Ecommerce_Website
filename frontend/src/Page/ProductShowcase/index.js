import React, { useState, useEffect } from "react";
import { useLocation } from "react-router-dom";
import {Col, Row, CardImg} from 'reactstrap';

import ProductRatings from "../../components/ProductShowCase/ProductRatings"
import UserRating from "../../components/ProductShowCase/UserRating"
import {get} from "../../service/httpHelper"
import {isUser} from "../../service/Authen"
import './showcase.css';

const ProductShowcase = () => {
    const endpoint = "/product";
    const [user, setUser] = useState(isUser());  
    const [product, setProduct] = useState();

    const location = useLocation();
    const { name } = location.state;

    function getProduct(name) {
        get(endpoint + `/name/${name}`).then(response => {
            if (response.status === 200) {
                setProduct(response.data);
            }
        });
    }

    useEffect(() => { getProduct(name); }, []);

    return (
        <div id = "product">
        {!product ? <h2>Loading...</h2> :
            <>
                {console.log(product)}
                <div className = "product-info">
                    {/*<CardImg
                        top
                        height="200px"
                        width="100px"
                        src={`data:image/jpeg;base64,${product.img_id.data}`}
                    alt="Image"/>*/}
                    <div className = "product-detail">
                        <div className = "product-name">{product.name}</div>
                        <div className = "product-category">{product.category}</div>
                        <div className = "product-rate">
                            <p>Average rating: {product.avg_rate} | Total: {product.total}</p>
                        </div>
                        <div className = "product-price">{product.price}$</div>
                    </div>
                </div>
                <Row className="mb-2">
                    <Col className = "product-description col-6">
                        <p>Description</p> 
                        <div className = "description">{product.description}</div>
                    </Col>
                    <Col className = "product-review col-6 box-review">
                        PRODUCT REVIEWS
                        <div className = "user-review">
                            <UserRating user={user} pid={product.id}/>
                        </div>
                        <div className = "product-review">
                        <ProductRatings pid={product.id} />
                        </div>
                    </Col>
                </Row>
            </>
        }
        </div>
    )
}

export default ProductShowcase;
