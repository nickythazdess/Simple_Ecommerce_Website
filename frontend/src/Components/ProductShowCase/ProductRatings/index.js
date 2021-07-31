import React, { useState, useEffect } from 'react';

import {get} from '../../../service/httpHelper'
import './product_rating.css'
import Rating from "../../Utils/Rating";

const ProductRatings = ({pid}) => {
    const endpoint = "/rating";
    const [ratingList, setRatingList] = useState([]);

    function getProductRatingsList(id) {
        get(endpoint + `/product/${id}`).then(response => {
            if (response.status === 200) {
              setRatingList(response.data);
            }
        });
    }

    useEffect(() => { getProductRatingsList(pid); }, []);

    return (
        <>
            {ratingList.length === 0 ? <h4>No review</h4> :
                ratingList.map(rating => (
                    <div className = "rating" key={rating.username}>
                        <div className = "rating-account">
                            <p>{rating.username}</p>
                        </div>
                        <div className = "rating-detail">
                            <div className = "rating-star">
                                <Rating rating={rating.rate} forShow={true}/>
                                <p style={{display:"inline-block"}}>({rating.rate})</p>
                            </div>
                            <div className = "rating-comment">
                                {rating.cmt}
                            </div>
                        </div>
                    </div>
            ))}
        </>
    )
}

export default ProductRatings;