import React, { useState, useEffect } from 'react';
import {Form, FormGroup, Label, Input, FormText, Button} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { get, del, put } from "../../../../service/httpHelper";
import CustomModal from "../../../Utils/CustomModal";

toast.configure()
export default function Detail({product, update}) {
    const endpoint = "/product";
    const [cateList, setCateList] = useState([]);

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
        console.log(e);
        const body = JSON.stringify({
            id: id,
            name: e.target.name.value,
            dev: e.target.dev.value,
            price: e.target.price.value ? e.target.price.value : -1,
            description: e.target.description.value,
            createdDate: null,
            updatedDate: null,
            img: null
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
                    case 400: message = "The product name is already exist!"; break;
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

    function getListCate(){
        get(`/category`).then(response => {
            if (response.status === 200) {
              setCateList(response.data);
            }
        });
    }

    useEffect(() => {
        getListCate();
    }, []);

    const editForm = 
    <Form id="edit-form" onSubmit={(e) => confirmEdit(e, product.id)}>
        <FormGroup>
            <Label for="name">Product Name</Label> {' '}
            <Input
            type="text"
            name="name"
            placeholder={product.name}
            pattern=".{2,255}"/>
        </FormGroup>
        <FormGroup>
            <Label for="dev">Developer</Label> {' '}
            <Input
            type="text"
            name="dev"
            placeholder={product.dev}
            pattern=".{2,255}"/>
        </FormGroup>
        <FormGroup>
            <Label for="price">Price</Label> {' '}
            <Input
            type="number"
            name="price"
            placeholder={product.price + "$"}
            title="Must be a number"/>
        </FormGroup>
        <FormGroup>
            <Label for="category-select">Category</Label> {' '}
            <select id="category-select" name="category" form="edit-form">
                <option key={product.category_name} value={product.category_name}>{product.category_name}</option>
                {cateList.map((cate) => (
                    (cate.name !== product.category_name) ? <option key={cate.name} value={cate.name}>{cate.name}</option> : null
                ))}
          </select>
        </FormGroup>
        <FormGroup>
            <Label for="description">Description</Label> {' '}
            <Input
            type="text"
            name="description"
            placeholder={product.description}
            maxLength={255}/>
        </FormGroup>
    </Form>

    return (
        <>
            <td>{product.id}</td>
            <td>{product.name}</td>
            <td>{product.dev}</td>
            <td>{product.price} $</td>
            <td>{product.description}</td>
            <td>{product.img ? product.img.data : null}</td>
            <td>{product.createdDate}</td>
            <td>{product.updatedDate}</td>
            <td>
                <CustomModal
                    buttonLabel = "Edit"
                    btnColor = "warning"
                    modalClassName = "cusmodal-edit"
                    title = {`Edit Account ${product.name}`}
                    body = {editForm}
                    confirmBtn = {<Button color="primary" type="submit" form="edit-form">Submit</Button>}>
                </CustomModal>
                <CustomModal
                    buttonLabel = "Delete"
                    btnColor = "danger"
                    modalClassName = "cusmodal-delete"
                    title = {`Delete Category ${product.name}`}
                    body = {<h5>Are you sure?</h5>}
                    confirmBtn = {<Button color="danger" onClick={(e) => confirmDelete(product.id)}>Confirm</Button>}>
                </CustomModal>
            </td>
        </>
    );
}