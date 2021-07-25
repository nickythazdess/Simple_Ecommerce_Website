import React, { useState, useEffect } from "react";
import {Table, Form, FormGroup, Label, Input} from "reactstrap";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { get, postWithAuth } from "../../../service/httpHelper";
import Detail from "./Detail";
import CustomModal from "../../Utils/CustomModal";
import './admin_category.css'

export default function Category() {
    const endpoint = "/category";
    const [cateList, setCateList] = useState([]);

    useEffect(() => {
      getListCate();
    }, []);

    function getListCate(){
      get(endpoint).then(response => {
          if (response.status === 200) {
            setCateList(response.data);
          }
        });
    }

    const confirmAdd = (e) => {
      e.preventDefault();
      postWithAuth(endpoint + `/admin`, {"name" : e.target.name.value})
      .then((response) => {
        if (response.status === 200) {
          toast.success("Add succeeded!", {
              position: "top-right",
              autoClose: 2000,
              closeOnClick: true,
          });
          getListCate();
        }
      }).catch((error) => {
          console.log(error.message);
          toast.error("Add failed!", {
              position: "top-right",
              autoClose: 2000,
              closeOnClick: true,
          });
      })
  }

  const addForm = 
    <Form>
        <FormGroup>
            <Label for="cate_name">Category Name</Label>
            <Input
            type="text"
            name="cate_name"
            required
            placeholder="Category Name"
            minLength={1}
            maxLength={255}/>
        </FormGroup>
    </Form>

  return (
    <div className = "category-container">
      <h2 className="title-user">CATEGORY MANAGEMENT</h2>
        <CustomModal
            buttonLabel = "Add"
            className = "cusmodal-add"
            title = {`Add Category`}
            body = {addForm}
            onConfirm = {(e) => confirmAdd(e)}
            confirmButtonLabel = "Submit">
        </CustomModal>
        <br/>
      <Table bordered striped>
        <thead>
          <tr>
            <th>ID</th> <th>NAME</th> <th>ACTION</th>
          </tr>
        </thead>
        <tbody>
          {(typeof cateList != "undefined") ? cateList.map((cate) => (
            <tr key={cate.id}>
              <Detail category={cate} update={() => getListCate()}/>
            </tr>
          )): null}
        </tbody>
      </Table>
    </div>
  );
}