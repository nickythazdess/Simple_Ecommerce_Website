import './navbar.css';
import React from 'react';

export default class NavBar extends React.Component {
    /*state = {
        username: 'Tan',
    }*/

    render() {
        return (
            <nav id='navbar'>
                <ul>
                <a href="#"><li>Home</li></a>
                <a href="#"><li>Contact</li></a>
                <a href="#"><li>About</li></a>
                </ul>

                <div className="nav-details">
                <p className="nav-username"> {this.props.username} </p>
                </div>
            </nav>
        );
    }
}