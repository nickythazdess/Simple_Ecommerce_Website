import React, { useState, useEffect } from "react";
import {Table, Form, FormGroup, Label, Input, Button, InputGroupAddon} from "reactstrap";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { get, post } from "../../../service/httpHelper";
import Detail from "./Detail";
import CustomModal from "../../Utils/CustomModal";
import Paging from "../../Utils/Pagination";
import CustomDropdownButton from "../../Utils/CustomDropDownButton";
import './admin_product.css'

toast.configure()
export default function Product() {
    const endpoint = "/product";
    const [productList, setProductList] = useState([]);
    const optionChoices = ["ID", "Name", "Dev", "Price", "Updated"];
    const [sortOption, setSortOption] = useState(["ID", true]);
    const [currentPage, setPage] = useState(0);
    const [cateList, setCateList] = useState([]);

    const handleSortOptionChange = (item, order) => {
      setSortOption([item, order]);
    }

    function getProductList(){
      get(endpoint).then(response => {
          if (response.status === 200) {
            setProductList(response.data);
          }
      });
    }

    function getListCate(){
      get(endpoint).then(response => {
          if (response.status === 200) {
            setCateList(response.data);
          }
      });
    }

    useEffect(() => {
      getProductList();
      getListCate();
    }, []);

    function compare (a, b) {
      switch (sortOption[0]) {
        case "ID": return a.id-b.id;
        case "Price": return a.price-b.price;
        case "Name": return a.name.localeCompare(b.name);
        case "Dev": return a.dev.localeCompare(b.dev);
        case "Updated": return a.role[0].localeCompare(b.role[0]);
        default: return a.id-b.id;
      }
    }

    useEffect(() => {}, [sortOption]);

    function sortList() {
      let sortedList = productList.sort(compare);
      if (!sortOption[1]) sortedList.reverse();
      return sortedList;
    }

    const confirmAdd = (e) => {
      e.preventDefault();
      const body = JSON.stringify({
        id: 0,
        name: e.target.name.value,
        dev: e.target.dev.value,
        price: e.target.price.value,
        description: e.target.description.value,
        createdDate: null,
        updatedDate: null,
        img: e.target.img.value
      });
      console.log(body);
      post(endpoint + `/admin`, body)
      .then((response) => {
        if (response.status === 200) {
          toast.success("Add product succeeded!", {
              position: "top-right",
              autoClose: 3000,
          });
          getProductList();
        }
      }).catch((error) => {
        let message = "Add product failed!";
        if (!error.response) message = "Connection error! Please try again later";
        else {
          console.log(error.response.status);
          switch (error.response.status) {
              case 400: message = "The product name is already exist!"; break;
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
            <Label for="name">Product Name</Label> {' '}
            <Input
            type="text"
            name="name"
            placeholder="Name of the product"
            title="The product's name must contains 2 to 255 characters"
            pattern=".{2,255}"/>
        </FormGroup>
        <FormGroup>
            <Label for="dev">Developer</Label> {' '}
            <Input
            type="text"
            name="dev"
            title="The product's developer-name must contains 2 to 255 characters"
            pattern=".{2,255}"/>
        </FormGroup>
        <FormGroup>
            <Label for="price">Price</Label> {' '}
            <Input
            type="number"
            name="price"
            placeholder="Price of the product"
            title="The product price must be a number"/>

        </FormGroup>
        <FormGroup>
            <Label for="category-select">Category</Label> {' '}
            <select id="category-select" name="category" form="add-form">
              {cateList.map((cate) => (
                  <option value={cate.name}>{cate.name}</option>
              ))}
          </select>
        </FormGroup>
        <FormGroup>
            <Label for="description">Description</Label> {' '}
            <Input
            type="text"
            name="description"
            placeholder="Description of the product"
            maxLength={255}/>
        </FormGroup>
    </Form>

    return (
      <div className = "product-background">
        <h2>PRODUCT MANAGEMENT</h2>
        <CustomModal
            buttonLabel = "Add"
            btnColor = "success"
            className = "cusmodal-add"
            title = {`Create new product`}
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
              <th>DEV</th>
              <th>PRICE</th>
              <th>DESCRIPTION</th>
              <th>IMAGE</th>
              <th>CREATED DATE</th>
              <th>UPDATED DATE</th>
              <th>ACTION</th>
            </tr>
          </thead>
          <tbody>
            {!productList ? <h2>Loading...</h2> :
                sortList().map((product) => (
                  <tr key={product.id}>
                      <Detail product={product} update={(e) => getProductList()}/>
                  </tr>
                ))
            }
          </tbody>
        </Table>
        <Paging list = {productList}
            total = {productList.length}
            size = {5}
            callback = {null}/>
      </div>
    );
}