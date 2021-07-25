import React, { useState} from 'react';
import {Form, FormGroup, Label, Input} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { del, put } from "../../../../service/httpHelper";
import CustomModal from "../../../Utils/CustomModal";

export default function Detail({category, update}) {
    const endpoint = "/category";

const confirmDelete = (id) => {
    del(endpoint + `/admin/${id}`)
    .then((response) => {
      if (response.status === 200) {
        toast.success("Delete succeeded!", {
            position: "top-right",
            autoClose: 2000,
            closeOnClick: true,
        });
        update();
      }
    }).catch((error) => {
        console.log(error.message);
        toast.error("Delete failed!", {
            position: "top-right",
            autoClose: 2000,
            closeOnClick: true,
        });
    })
}

const confirmEdit = (e, id) => {
    e.preventDefault();
    put(endpoint + `/admin`, {"name" : e.target.name.value})
    .then((response) => {
      if (response.status === 200) {
        toast.success("Edit succeeded!", {
            position: "top-right",
            autoClose: 2000,
            closeOnClick: true,
        });
        update();
      }
    }).catch((error) => {
        console.log(error.message);
        toast.error("Edit failed!", {
            position: "top-right",
            autoClose: 2000,
            closeOnClick: true,
        });
    })
}

const editForm = 
    <Form>
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

    return (
        <>
            <th scope="row">{category.id}</th>
            <td>{category.name}</td>
            <td>
                <CustomModal
                    buttonLabel = "Edit"
                    className = "cusmodal-edit"
                    title = {`Edit Category ${category.name}`}
                    body = {editForm}
                    onConfirm = {(e) => confirmEdit(e, category.id)}
                    confirmButtonLabel = "Submit">
                </CustomModal>
                <CustomModal
                    buttonLabel = "Delete"
                    className = "cusmodal-delete"
                    title = {`Delete Category ${category.name}`}
                    body = {<h5>Are you sure?</h5>}
                    onConfirm = {() => confirmDelete(category.id)}
                    confirmButtonLabel = "Confirm">
                </CustomModal>
            </td>
        </>
    );
}