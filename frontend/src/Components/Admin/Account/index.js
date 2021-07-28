import React, { useState, useEffect } from "react";
import {Table, Form, FormGroup, Label, Input, Button} from "reactstrap";
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { get, post } from "../../../service/httpHelper";
import Detail from "./Detail";
import CustomModal from "../../Utils/CustomModal";
import Paging from "../../Utils/Pagination";
import CustomDropdownButton from "../../Utils/CustomDropDownButton";
import './admin_account.css'

toast.configure()
export default function Account() {
    const endpoint = "/account";
    const [accList, setAccList] = useState([]);
    const optionChoices = ["ID", "Email", "Username", "Name", "Role"];
    const [sortOption, setSortOption] = useState(["ID", true]);
    const [currentPage, setPage] = useState(0);

    const [showPassword, setShowPassword] = useState(false);
    const toggleShowPassword = () => setShowPassword(!showPassword);

    const handleSortOptionChange = (item, order) => {
      setSortOption([item, order]);
    }

    function getAccountList(){
      get(endpoint).then(response => {
          if (response.status === 200) {
            setAccList(response.data);
          }
      });
    }

    useEffect(() => {
      getAccountList();
    }, []);

    function compare (a, b) {
      switch (sortOption[0]) {
        case "ID": return a.id-b.id;
        case "Email": return a.email.localeCompare(b.email);
        case "Name": return a.name.localeCompare(b.name);
        case "Username": return a.username.localeCompare(b.username);
        case "Role": return a.role[0].localeCompare(b.role[0]);
        default: return a.id-b.id;
      }
    }

    useEffect(() => {}, [sortOption]);

    function sortList() {
      let sortedList = accList.sort(compare);
      if (!sortOption[1]) sortedList.reverse();
      return sortedList;
    }

    const confirmAdd = (e) => {
      e.preventDefault();
      if (e.target.password.value !== e.target.re_password.value) {
        toast.error("Password and Re-type password does not match!", {
          position: toast.POSITION.TOP_RIGHT,
          autoClose: 3000,
        });
      }
      const body = JSON.stringify({
        id: 0,
        name: e.target.name.value,
        username: e.target.username.value,
        email: e.target.email.value,
        password: e.target.password.value,
        role: [e.target.role.value]
      });
      console.log(body);
      post(`/auth/signup`, body)
      .then((response) => {
        if (response.status === 200) {
          toast.success("Add account succeeded!", {
              position: "top-right",
              autoClose: 3000,
          });
          getAccountList();
        }
      }).catch((error) => {
        let message = "Add account failed!";
        if (!error.response) message = "Connection error! Please try again later";
        else {
          console.log(error.response.status);
          switch (error.response.status) {
              case 400: message = "The username has been taken! Please use another username"; break;
              case 409: message = "The email has been used for an existing account!"; break;
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
            <Label for="name">Display Name</Label>
            <Input
            type="text"
            name="name"
            required
            placeholder="Contains 2-100 characters"
            title="Name must contains 2-100 characters"
            pattern=".{2,100}"/>
        </FormGroup>
        <FormGroup>
            <Label for="username">Username</Label>
            <Input
            type="text"
            name="username"
            required
            placeholder="Begins with alphabet character"
            title="Username must contains 2-100 alphanumeric (or _) characters and begins with an alphabetical character."
            pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{2,100}$"/>
        </FormGroup>
        <FormGroup>
            <Label for="email">Email</Label>
            <Input
            type="email"
            name="email"
            required
            title="Email ... well, must be an email"
            placeholder="abcde@example.com"/>
        </FormGroup>
        <FormGroup>
            <Label for="password">Password</Label>
            <Input
            type={showPassword ? "text" : "password"}
            name="password"
            required
            placeholder="Contains 6-100 characters"
            title="Password must contains 6-100 characters"
            pattern=".{6,100}"/>
        </FormGroup>
        <FormGroup>
            <Label for="re_password">Re-type password</Label>
            <Input
            type={showPassword ? "text" : "password"}
            name="re_password"
            required
            placeholder="Contains 6-100 characters"
            title="Password must contains 6-100 characters"
            pattern=".{6,100}"/>
        </FormGroup>
        <FormGroup>
          <Input
            type="checkbox"
            name="showpassword"
            onClick={toggleShowPassword}/> {' '}
          <Label for="showpassword">Show Password?</Label>
        </FormGroup>
        <FormGroup>
          <Label for="role">Role: </Label>
          <select id="role-select" name="role" form="add-form">
            <option value="user">User</option>
            <option value="admin">Admin</option>
            <option value={["admin", "user"]}>Both</option>
          </select>
        </FormGroup>
    </Form>

    return (
      <div className = "account-background">
        <h2>ACCOUNT MANAGEMENT</h2>
        <CustomModal
            buttonLabel = "Add"
            btnColor = "success"
            modalClassName = "cusmodal-add"
            title = {`Create new account`}
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
              <th>USERNAME</th>
              <th>EMAIL</th>
              <th>PASSWORD</th>
              <th>ROLE(S)</th>
              <th>ACTION</th>
            </tr>
          </thead>
          <tbody>
            {!accList ? <h2>Loading...</h2> :
                sortList().map((acc) => (
                  <tr key={acc.id}>
                      <Detail account={acc} update={(e) => getAccountList()}/>
                  </tr>
                ))
            }
          </tbody>
        </Table>
        <Paging list = {accList}
            total = {accList.length}
            size = {5}
            callback = {null}/>
      </div>
    );
}