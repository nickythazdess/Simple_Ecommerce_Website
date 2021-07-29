import React, { useState, useEffect } from 'react';
import {Form, FormGroup, Label, Input, Button} from "reactstrap";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import {get, post, put} from '../../../service/httpHelper'
import getCookie from '../../../service/Cookie'
import Rating from "../../Utils/Rating";
import CustomModal from "../../Utils/CustomModal";
import './user_rating.css'

toast.configure()
const UserRating = ({pid}) => {
    const endpoint = "/rating";
    
    const userDisplayName = getCookie("name");
    const [userRate, setUserRate] = useState();
    const [rating, setRating] = useState(0);
    const [ratingOption, setRatingOption] = useState(true);

    function getUserRating(id) {
        get(endpoint + `/user/${id}`).then(response => {
            if (response.status === 200) {
                response.data ? setUserRate(response.data) : setUserRate(null);
            }
        });
    }

    useEffect(() => { getUserRating(pid); }, []);

    const handleRatingSubmit = async (e, pid) => {
        e.preventDefault();
        const body = JSON.stringify({
            uid: 0,
            pid: pid,
            rate: rating,
            cmt: e.target.cmt.value,
            date: null,
          });
        console.log(body);
        ratingOption ?
        post(endpoint + `/admin`, body)
        .then((response) => {
        if (response.status === 200) {
            toast.success("Rating post successfully!", {
                position: "top-right",
                autoClose: 3000,
            });
            setUserRate(e);
            getUserRating(pid);
        }
        }).catch((error) => {
            let message = "Rating post failed!";
            if (!error.response) message = "Connection error! Please try again later";
            toast.error(message, {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
        }) :
        put(endpoint + `/admin`, body)
        .then((response) => {
        if (response.status === 200) {
            toast.success("Rating edit successfully!", {
                position: "top-right",
                autoClose: 3000,
            });
            setUserRate(e);
            getUserRating(pid);
        }
        }).catch((error) => {
            let message = "Rating edit failed!";
            if (!error.response) message = "Connection error! Please try again later";
            toast.error(message, {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
        });
    }

    const ratingForm =
    <Form id="rating-form" onSubmit={(e) => handleRatingSubmit(e, pid)}>
        <FormGroup>
            <Label for="cmt">Comment</Label> {' '}
            <Input
            type={ratingOption ? "text" : "read-only"}
            name="cmt"
            placeholder={ratingOption ? "Write some comments about the product" : userRate.cmt }
            title="Your comment"
            pattern=".{,255}"/>
        </FormGroup>
    </Form>

    return (
        <div className="rating">
            <div className = "rating-account">
                <p>{userDisplayName}</p>
            </div>
            <div className = "rating-detail">
                {ratingOption ? <Rating rating={rating} onRate={rate => setRating(rate)}/>
                              : <Rating rating={userRate.rate}/>}
                
                {ratingForm}
                <CustomModal
                    buttonLabel = "POST"
                    btnColor = "dark"
                    modalClassName = "cusmodal-rating-post"
                    title = {`Comfirm rating post`}
                    body = {<h5>Do you want to post your product's rating?</h5>}
                    confirmBtn = {<Button color="dark" type="submit" form="rating-form">Confirm</Button>}>
                </CustomModal>
            </div>
        </div>
    )
}

export default UserRating;