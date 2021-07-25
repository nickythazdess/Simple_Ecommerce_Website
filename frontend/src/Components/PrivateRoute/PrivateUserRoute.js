import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import { isUser } from '@/service';

const PrivateUserRoute = ({ component: Component, ...rest }) => (
    <Route {...rest} render={props => {
        isUser() ? <Component {...props} /> : <Redirect to={{ pathname: '/login', state: { from: props.location } }} />;
    }}/>
)

export default PrivateUserRoute;