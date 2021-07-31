import React, {useState, useEffect} from 'react';
import { Link } from "react-router-dom";
import {Button, ButtonDropdown, DropdownToggle, DropdownMenu, 
    Modal, ModalHeader, ModalBody, ModalFooter} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { getCookie } from "../../service/Cookie";
import { isAdmin } from '../../service/Authen';
import { postWithAuth } from "../../service/httpHelper";
import SignInModal from './SignInModal'
import './navbar.css';


toast.configure()
export default function NavBar({isLogin, onLogin, onLogout}) {
    const [username, setUsername] = useState(getCookie("name"));
    const [Admin, setAdmin] = useState(isAdmin());

    const [dropdownOpen, setDropdownOpen] = useState(false);
    const toggleDrop = () => setDropdownOpen(!dropdownOpen);
    const [modalOpen, setModalOpen] = useState(false);
    const toggleModal = () => setModalOpen(!modalOpen);

    useEffect(() => {}, [username])

    const handleLogin = () => {
        setUsername(getCookie("name"));
        setAdmin(isAdmin())
        onLogin();
    }

    const handleLogout = () => {
        postWithAuth(`/auth/signout`).then((response) => {
        if (response.status === 200) {
            toast.success("Logout succeeded! See you again mate! ❤️", {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
            document.cookie = `token=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            document.cookie = `name=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            document.cookie = `username=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            document.cookie = `email=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            document.cookie = `role=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
            document.cookie = `status=false; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;`;
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
                    <>
                        <ButtonDropdown direction="left" isOpen={dropdownOpen} toggle={toggleDrop}>
                            <DropdownToggle caret> {username} </DropdownToggle>
                            <DropdownMenu>
                                <Button className="toggle-button text-center" color="dark" onClick={toggleModal}>Log out</Button>
                            </DropdownMenu>
                        </ButtonDropdown>
                        <Modal isOpen={modalOpen} backdrop={true} toggle={toggleModal} className={"cusmodal-logout" + " modal-dialog-centered"}>
                            <ModalHeader toggle={toggleModal}>LOG OUT</ModalHeader>
                            <ModalBody>Do you really want to log out?</ModalBody>
                            <ModalFooter>
                                <Button color="danger" onClick={() => handleLogout()}>Confirm</Button>
                                <Button color="secondary" onClick={() => toggleModal()}>Cancel</Button>
                            </ModalFooter>
                        </Modal>
                    </>
                    :
                    <SignInModal signInButton="Sign In" onLogIn={() => handleLogin()}/>
                }
            </div>
        </nav>
    );
}