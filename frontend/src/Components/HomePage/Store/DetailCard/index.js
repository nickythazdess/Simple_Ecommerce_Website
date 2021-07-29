import React, { useState } from 'react';
import {Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import "react-star-rating/dist/css/react-star-rating.min.css";

import { Link } from "react-router-dom";

toast.configure()
export default function Detail({product}) {
    return(
        <Link to={`/product/${product.id}`}>
            {/*<Card>
                <CardImg
                    top
                    width="100%"
                    src={`data:image/jpeg;base64,${product.img}`}
                    alt="Card image cap"
                />
                <CardBody>
                    <CardTitle tag="h5" className="card-name">
                    {prod.name}
                    </CardTitle>
                    <div className="card-info">
                    <CardSubtitle tag="h4" className="mb-2 card-price">
                        {numberFormat(product.price)}
                    </CardSubtitle>
                    <Link to={`/prodDetail/${product.id}`} className="card-btn">
                        Buy Now
                    </Link>
                    </div>
                </CardBody>
            </Card>*/}
        </Link>
    );
}