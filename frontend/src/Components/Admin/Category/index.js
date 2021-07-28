import React, { useState, useEffect } from "react";
import {Table, Form, FormGroup, Label, Input, Button} from "reactstrap";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { get, postWithAuth } from "../../../service/httpHelper";
import Detail from "./Detail";
import CustomModal from "../../Utils/CustomModal";
import Paging from "../../Utils/Pagination";
import CustomDropdownButton from "../../Utils/CustomDropDownButton";
import './admin_category.css'

toast.configure()
export default function Category() {
    const endpoint = "/category";
    const [cateList, setCateList] = useState([]);
    const optionChoices = ["ID", "Name"];
    const [sortOption, setSortOption] = useState(["ID", true]);
    const [currentPage, setPage] = useState(0);

    const handleSortOptionChange = (item, order) => {
      setSortOption([item, order]);
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

    function compare (a, b) {
      if (sortOption[0] === "ID")
          return a.id-b.id;
      else if (sortOption[0] === "Name") {
          return a.name.localeCompare(b.name);
      }
    }

    useEffect(() => {}, [sortOption]);

    function sortList() {
      let sortedList = cateList.sort(compare);
      if (!sortOption[1]) sortedList.reverse();
      return sortedList;
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
        if (!error.response) message = "Connection error! Please try again later";
        else {
          console.log(error.response.status);
          switch (error.response.status) {
              case 404: message = "Category not found!"; break;
              case 400: message = "Category is already exist!"; break;
              default: break;
          }
        }
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
            placeholder="Must contain 2-255 alphabetic characters."
            pattern="[a-zA-z\-\_]{2,255}"/>
        </FormGroup>
    </Form>

    return (
      <div className = "category-background">
        <h2>CATEGORY MANAGEMENT</h2>
        <CustomModal
            buttonLabel = "Add"
            btnColor = "success"
            modalClassName = "cusmodal-add"
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
              <th>ID</th>
              <th>NAME</th>
              <th>ACTION</th>
            </tr>
          </thead>
          <tbody>
            {!cateList ? <h2>Loading...</h2> :
                sortList().map((cate) => (
                  <tr key={cate.id}>
                      <Detail category={cate} update={(e) => getListCate()}/>
                  </tr>
                ))
            }
          </tbody>
        </Table>
        <Paging list = {cateList}
            total = {cateList.length}
            size = {5}
            callback = {null}/>
      </div>
    );
}