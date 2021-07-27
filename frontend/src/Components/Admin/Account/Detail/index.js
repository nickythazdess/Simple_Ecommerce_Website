import React, { useState} from 'react';
import {Form, FormGroup, Label, Input, FormText, Button} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { del, put } from "../../../../service/httpHelper";
import CustomModal from "../../../Utils/CustomModal";

toast.configure()
export default function Detail({account, update}) {
    const endpoint = "/account";
    const [showPassword, setShowPassword] = useState(false);
    const toggleShowPassword = () => setShowPassword(!showPassword);

    const confirmDelete = (id) => {
        del(endpoint + `/admin/${id}`)
        .then((response) => {
        if (response.status === 200) {
            toast.success("Delete succeeded!", {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
            update();
        }
        }).catch((error) => {
            let message = "Delete failed!";
            if (!error.response) message = "Connection error! Please try again later";
            toast.error(message, {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
        })
    }

    const confirmEdit = (e, id) => {
        e.preventDefault();
        setShowPassword(false);
        const body = JSON.stringify({
            id: id,
            name: e.target.name.value,
            username: e.target.username.value,
            email: e.target.email.value,
            password: e.target.password.value,
        });
        put(endpoint + `/admin`, body)
        .then((response) => {
        if (response.status === 200) {
            toast.success("Edit succeeded!", {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
            update();
        }
        }).catch((error) => {
            let message = "Edit failed!";
            if (!error.response) message = "Connection error! Please try again later";
            else {
                console.log(error.response.status);
                switch (error.response.status) {
                    case 400: message = "The username has been taken! Please use another username"; break;
                    case 409: message = "The email has been used for an existing account!"; break;
                    default: break;
                }
            }
            console.log(message);
            toast.error(message, {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
        });
    }

    const editForm = 
    <Form id="edit-form" onSubmit={(e) => confirmEdit(e)}>
        <FormGroup>
            <Label for="name">Display Name</Label>
            <Input
            type="text"
            name="name"
            placeholder={account.name}
            title="Name must contains 2-100 characters"
            pattern=".{2,100}"/>
        </FormGroup>
        <FormGroup>
            <Label for="username">Username</Label>
            <Input
            type="text"
            name="username"
            placeholder={account.username}
            title="Username must contains 2-100 alphanumeric (or _) characters and begins with an alphabetical character."
            pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{2,100}$"/>
        </FormGroup>
        <FormGroup>
            <Label for="email">Email</Label>
            <Input
            type="email"
            name="email"
            placeholder={account.email}
            title="Email ... well, must be an email"/>
        </FormGroup>
        <FormGroup>
            <Label for="password">Password</Label>
            <Input
            type={showPassword ? "text" : "password"}
            name="password"
            placeholder="Contains 6-100 characters"
            title="Password must contains 6-100 characters"
            pattern=".{6,100}"/>

            <Input
            type="checkbox"
            name="showpassword"
            onClick={toggleShowPassword}/> {' '}
            <Label for="showpassword">Show Password?</Label>
        </FormGroup>
        
        <FormGroup>
            <p>Role: {account.roles.toString()}</p>
        </FormGroup>
    </Form>

    return (
        <>
            <td>{account.id}</td>
            <td>{account.name}</td>
            <td>{account.username}</td>
            <td>{account.email}</td>
            <td>{account.password}</td>
            <td>{account.roles.toString()}</td>
            <td>
                <CustomModal
                    buttonLabel = "Edit"
                    btnColor = "warning"
                    modalClassName = "cusmodal-edit"
                    title = {`Edit Account ${account.username}`}
                    body = {editForm}
                    confirmBtn = {<Button color="primary" type="submit" form="edit-form">Submit</Button>}>
                </CustomModal>
                <CustomModal
                    buttonLabel = "Delete"
                    btnColor = "danger"
                    modalClassName = "cusmodal-delete"
                    title = {`Delete Category ${account.username}`}
                    body = {<h5>Are you sure?</h5>}
                    confirmBtn = {<Button color="danger" onClick={(e) => confirmDelete(account.id)}>Confirm</Button>}>
                </CustomModal>
            </td>
        </>
    );
}