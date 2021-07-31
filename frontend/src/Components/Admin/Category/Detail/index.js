import React, { useState } from 'react';
import {Form, FormGroup, Label, Input, FormText, Button} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { del, put, get } from "../../../../service/httpHelper";
import CustomModal from "../../../Utils/CustomModal";

toast.configure()
export default function Detail({category, update}) {
    const endpoint = "/category";
    
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
            toast.error("Delete failed!", {
                position: toast.POSITION.TOP_RIGHT,
                autoClose: 3000,
            });
        })
    }

    const confirmEdit = (e, id) => {
        e.preventDefault();
        const body = JSON.stringify({
            id: id,
            name: e.target.cate_name.value,
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
                    case 404: message = "Category not found!"; break;
                    case 400: message = "Category is already exist!"; break;
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
        <Form id="edit-form" onSubmit={(e) => confirmEdit(e, category.id)}>
            <FormGroup>
                <Label for="cate_name">Category Name</Label>
                <Input
                type="text"
                name="cate_name"
                required
                placeholder={category.name}
                minLength={1}
                maxLength={255}/>
            </FormGroup>
        </Form>

    const deleteForm =
        <>
            <h5>Are you sure?</h5>
            <h5>You will also delete it's products!</h5>
        </> 


    return (
        <>
            <td>{category.id}</td>
            <td>{category.name}</td>
            <td>
                <CustomModal
                    buttonLabel = "Edit"
                    btnColor = "warning"
                    modalClassName = "cusmodal-edit"
                    title = {`Edit Category ${category.name}`}
                    body = {editForm}
                    confirmBtn = {<Button color="primary" type="submit" form="edit-form">Submit</Button>}>
                </CustomModal>
                <CustomModal
                    buttonLabel = "Delete"
                    btnColor = "danger"
                    modalClassName = "cusmodal-delete"
                    title = {`Delete Category ${category.name}`}
                    body = {deleteForm}
                    confirmBtn = {<Button color="danger" onClick={(e) => confirmDelete(category.id)}>Confirm</Button>}>
                </CustomModal>
            </td>
        </>
    );
}