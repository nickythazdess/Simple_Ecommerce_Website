import React, { useState, useEffect } from 'react';
import {Form, FormGroup, Label, Input, Button} from "reactstrap";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import {get, post, put} from '../../../service/httpHelper'
import { isUser } from '../../../service/Authen'
import {getCookie} from '../../../service/Cookie'
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

    const toggleRatingOption = () => setRatingOption(!ratingOption);

    function getUserRating(pid) {
        get(endpoint + `/${pid}`).then(response => {
            if (response.status === 200) {
                if (response.data != null) {
                    setUserRate(response.data);
                    setRatingOption(false);
                }
                else setUserRate(null);
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

        userRate ?
        put(endpoint, body)
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
        }) : 
        post(endpoint, body)
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

    const color = {
        unfilled: "#353535",
        filled: "#DFDFDF",
    }

    const RatingStar = ({userRate, ratingOption}) => {
        if (!userRate) {
            return <Rating rating={rating} color={color} onRate={rate => setRating(rate)}/>
        } else {
            if (ratingOption) {
                return <Rating rating={rating} color={color} onRate={rate => setRating(rate)}/>
            }
            else {
                return <Rating rating={userRate.rate} forShow={true}/>
            }
        }
    }

    const RatingToggleButton = ({userRate, ratingOption}) => {
        if (userRate && ratingOption) {
            return <Button color="danger" onClick={toggleRatingOption}>X</Button>
        } else if (userRate && !ratingOption) {
            return <Button color="success" onClick={toggleRatingOption}>Edit</Button> 
        }
        return null;
    }

    return (
        <>
        {isUser() ?
            <div className="rating">
                <div className = "rating-account">
                    <p>{userDisplayName}</p>
                </div>
                <div className = "rating-detail">

                    <RatingStar userRate={userRate} ratingOption={ratingOption}/>
                    {ratingForm}
                    <RatingToggleButton userRate={userRate} ratingOption={ratingOption}/>
                    {ratingOption ? 
                    <CustomModal
                        buttonLabel = "POST"
                        btnColor = "light"
                        modalClassName = "cusmodal-rating-post"
                        title = {`Comfirm rating post`}
                        body = {<h5>Do you want to post your product's rating?</h5>}
                        confirmBtn = {<Button color="dark" type="submit" form="rating-form">Confirm</Button>}>
                    </CustomModal> : null}
                    
                </div>
            </div> : null
        }
        </>
    )
}

export default UserRating;