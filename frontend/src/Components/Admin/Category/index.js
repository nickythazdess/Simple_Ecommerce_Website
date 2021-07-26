import React, { useState, useEffect } from "react";
import {Table, Form, FormGroup, Label, Input, Button} from "reactstrap";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { get, postWithAuth } from "../../../service/httpHelper";
import Body from "./Body";
import CustomModal from "../../Utils/CustomModal";
import CustomDropdownButton from "../../Utils/CustomDropDownButton";
import './admin_category.css'

toast.configure()
export default function Category() {
    const endpoint = "/category";
    const [cateList, setCateList] = useState([]);
    const [sortOption, setSortOption] = useState(["ID", true]);

    useEffect(() => {
      getListCate();
    }, []);

    useEffect(() => {
      
    }, sortOption);

    function getListCate(){
      get(endpoint).then(response => {
          if (response.status === 200) {
            setCateList(response.data);
          }
        });
    }

    const confirmAdd = (e) => {
      e.preventDefault();
      const body = JSON.stringify({
        id: 0,
        name: e.target.cate_name.value,
      });
      console.log(body);
      postWithAuth(endpoint + `/admin`, body)
      .then((response) => {
        if (response.status === 200) {
          toast.success("Add succeeded!", {
              position: "top-right",
              autoClose: 3000,
          });
          getListCate();
        }
      }).catch((error) => {
        let message = "Add failed!";
        console.log(error.response.status);
        switch (error.response.status) {
            case 404: message = "Category not found!"; break;
            case 400: message = "Category already exists!"; break;
            default: break;
        }
        console.log(message);
        toast.error(message, {
            position: toast.POSITION.TOP_RIGHT,
            autoClose: 3000,
        });
      });
    }

    const addForm = 
    <Form id="add-form" onSubmit={(e) => confirmAdd(e)}>
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

    const handleSortOptionChange = (item, order) => {
      setSortOption([item, order]);
    }

    const optionChoices = ["ID", "Name"];

    return (
      <div className = "category-background">
        <h2 className="title-user">CATEGORY MANAGEMENT</h2>
        <CustomModal
            buttonLabel = "Add"
            btnColor = "success"
            className = "cusmodal-add"
            title = {`Add Category`}
            body = {addForm}
            confirmBtn = {<Button color="primary" type="submit" form="add-form">Submit</Button>}>
        </CustomModal>
        <br/>
        <CustomDropdownButton
          choices = {optionChoices}
          sendChoice = {(item, order) => handleSortOptionChange(item, order)}/>
        <Table bordered striped>
          <thead>
            <tr>
              <th>ID</th> <th>NAME</th> <th>ACTION</th>
            </tr>
          </thead>
          <tbody>
            {(typeof cateList === "undefined") ? null :
              <Body
                by = {sortOption[0]}
                order = {sortOption[1]}
                cateList={cateList}
                getListCate={(e) => getListCate()}/>
            }
          </tbody>
        </Table>
      </div>
    );
}