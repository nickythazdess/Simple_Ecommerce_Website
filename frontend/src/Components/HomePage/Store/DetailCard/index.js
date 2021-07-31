import React, { useState } from 'react';
import {Card, CardImg, CardText, CardBody, CardTitle, CardSubtitle} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import { Link } from "react-router-dom";

import Rating from '../../../Utils/Rating'
import './detail.css'

toast.configure()
export default function Detail({product}) {
    const RouteChange = (product) => {
        return `/product/` + product.name;
    }

    const currency = (price) => {
        return price + "$";
    }

    const color = {
        unfilled: "#353535",
        filled: "#DFDFDF",
    }

    return(
        <Link to={{ pathname: RouteChange(product), state: {name: product.name} }}>
            <Card className="product-card">
                <CardImg
                    top
                    width="100px"
                    src={`data:image/jpeg;base64,${product.img}`}
                    alt="Image"
                />
                <CardBody>
                    <CardTitle tag="h3" className="card-name">
                        {product.name}
                    </CardTitle>
                    <div className="card-info">
                    <CardSubtitle tag="h5" className="mb-2 card-dev">
                        {product.dev}
                    </CardSubtitle>
                    <CardSubtitle tag="h5" className="mb-2 card-price-and-rate">
                        {currency(product.price)} {' '} <Rating rating={product.rate} forShow={true} color={color}/>
                    </CardSubtitle>
                    </div>
                </CardBody>
            </Card>
        </Link>
    );
}