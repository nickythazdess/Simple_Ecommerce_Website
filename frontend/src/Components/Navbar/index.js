import './navbar.css';
import React from 'react';
import { getCookie } from "../../service/Cookie";

export default class NavBar extends React.Component {
    render() {
        return (
            <nav id='navbar'>
                <ul>
                    <a href="#"><img src={process.env.PUBLIC_URL + '/Logo.png'} /></a>
                    <a href="#"><li>Store</li></a>
                    <a href="#"><li>Help</li></a>
                    <a href="#"><li>About Us</li></a>
                </ul>

                <div className="nav-details">
                <p className="nav-username"> {this.props.username} </p>
                </div>
            </nav>
        );
    }
}