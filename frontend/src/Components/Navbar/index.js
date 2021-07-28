import React, {useState, useEffect} from 'react';
import { Link } from "react-router-dom";
import {Button, ButtonDropdown, DropdownToggle, DropdownMenu} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { getCookie } from "../../service/Cookie";
import { isAdmin } from '../../service/Authen';
import CustomModal from '../Utils/CustomModal';
import SignInModal from './SignInModal'
import './navbar.css';


toast.configure()
export default function NavBar({isLogin, onLogin, onLogout}) {
    const [username, setUsername] = useState(getCookie("name"));
    const [dropdownOpen, setOpen] = useState(false);
    const toggle = () => setOpen(!dropdownOpen);

    useEffect(() => {}, [username])

    const handleLogin = () => {
        setUsername(getCookie("name"));
        onLogin();
    }

    const handleLogout = () => {
        console.log("at navbar");
        toast.success("Logout succeeded! See you again mate! ❤️", {
            position: toast.POSITION.TOP_RIGHT,
            autoClose: 3000,
        });
        document.cookie = `token=; max-age=86400; path=/;`;
        document.cookie = `username=; max-age=86400; path=/;`;
        document.cookie = `email=; max-age=86400; path=/;`;
        document.cookie = `role=; max-age=86400; path=/;`;
        document.cookie = `cart=; max-age=86400; path=/;`;
        document.cookie = `status=false; max-age=86400; path=/;`;
        setUsername(getCookie("name"));
        onLogout();
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
                {isAdmin() ?
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
                                btnColor = "#202020"
                                modalClassName = "cusmodal-logout"
                                title = {`LOG OUT`}
                                body = {<h5>Are you sure mate ???</h5>}
                                confirmBtn = {<Button color="danger" onClick={() => handleLogout()}>Log Out</Button>}>
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