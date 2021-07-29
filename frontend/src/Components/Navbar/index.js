import React, {useState, useEffect} from 'react';
import { Link } from "react-router-dom";
import {Button, ButtonDropdown, DropdownToggle, DropdownMenu} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { getCookie } from "../../service/Cookie";
import { isAdmin } from '../../service/Authen';
import { post } from "../../service/httpHelper";
import CustomModal from '../Utils/CustomModal';
import SignInModal from './SignInModal'
import './navbar.css';


toast.configure()
export default function NavBar({isLogin, onLogin, onLogout}) {
    const [username, setUsername] = useState(getCookie("name"));
    const [dropdownOpen, setOpen] = useState(false);
    const [Admin, setAdmin] = useState(isAdmin());
    const toggle = () => setOpen(!dropdownOpen);

    useEffect(() => {}, [username])

    const handleLogin = () => {
        setUsername(getCookie("name"));
        setAdmin(isAdmin())
        onLogin();
    }

    const handleLogout = () => {
        console.log("at navbar");
        post(`/auth/signin`, null).then((response) => {
        if (response.status === 200) {
            toast.success("Logout succeeded! See you again mate! ❤️", {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
            document.cookie = `token=; max-age=86400; path=/;`;
            document.cookie = `name=; max-age=86400; path=/;`;
            document.cookie = `username=; max-age=86400; path=/;`;
            document.cookie = `email=; max-age=86400; path=/;`;
            document.cookie = `role=; max-age=86400; path=/;`;
            document.cookie = `cart=; max-age=86400; path=/;`;
            document.cookie = `status=false; max-age=86400; path=/;`;
            setUsername(getCookie("name"));
            setAdmin(false);
            onLogout();
        }
        }).catch((error) => {
            let message = "Log out failed!";
            if (!error.response) message = "Connection error! Please try again later";
            else {
                console.log(error.response.status);
                switch (error.response.status) {
                    case 401: message = "Logout failed! ... Cause we don't want you to leave :("; break;
                    default: break;
                }
            }
            toast.error(message, {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
        });
    }
    
    return (
        <nav id='navbar'>
            <ul className='nav-items'>
                <Link to="/">
                    <span><img src={process.env.PUBLIC_URL + '/Logo.png'} /></span>
                </Link>
                <Link to="/">
                    <li><span>Store</span></li>
                </Link>
                <Link to="/help">
                    <li><span>Help</span></li>
                </Link>
                {Admin ?
                    <Link to="/admin">
                        <li><span>Admin Workspace</span></li>
                    </Link>
                    :
                    <Link to="/about_us">
                        <li><span>About Us</span></li>
                    </Link>
                }
            </ul>
            <div className="nav-details">
                {isLogin ? 
                    <ButtonDropdown direction="left" isOpen={dropdownOpen} toggle={toggle}>
                        <DropdownToggle caret> {username} </DropdownToggle>
                        <DropdownMenu>
                            <CustomModal
                                buttonLabel = "Log Out"
                                btnColor = "dark"
                                modalClassName = "cusmodal-logout"
                                title = {`LOG OUT`}
                                body = {<h5>Are you sure mate ???</h5>}
                                confirmBtn = {<Button color="danger" onClick={()=>handleLogout()}>Log Out</Button>}>
                            </CustomModal>
                        </DropdownMenu>
                    </ButtonDropdown>
                    :
                    <SignInModal signInButton="Sign In" onLogIn={() => handleLogin()}/>
                }
            </div>
        </nav>
    );
}