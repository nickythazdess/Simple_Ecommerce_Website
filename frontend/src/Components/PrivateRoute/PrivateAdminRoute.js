import React from 'react';
import { Route, Redirect } from 'react-router-dom';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { isAdmin } from '../../service/Authen';

toast.configure();
const PrivateAdminRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => {
        isAdmin() ? <Component {...props} /> : 
            toast.error("This site is not allowed to you! Please login with an admin account!", {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
    }}/>
)

export default PrivateAdminRoute;