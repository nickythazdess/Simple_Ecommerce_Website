import React, {useState, useEffect} from 'react';
import { useHistory, Link } from "react-router-dom";
import {Form, FormGroup, FormText, Label, Input, Button} from 'reactstrap';
import { toast } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";

import { post } from "../../../service/httpHelper";
import CustomModal from '../../Utils/CustomModal';

toast.configure()
const SignInModal = (props) => {
  const {
      onLogIn,
  } = props;

  const [showPassword, setShowPassword] = useState(false);
  const toggleShowPassword = () => setShowPassword(!showPassword);

  const [signOption, setSignOption] = useState(true);
  const toggleSignOption = () => {
    setSignOption(!signOption);
    setShowPassword(false);
  }

  const signUpHandle = (e) => {
      e.preventDefault();
      if (e.target.password.value !== e.target.re_password.value) {
        toast.error("Password and Re-type password does not match!", {
          position: toast.POSITION.TOP_RIGHT,
          autoClose: 3000,
        });
        return;
      }
      const body = JSON.stringify({
        id: 0,
        name: e.target.name.value ? e.target.name.value : e.target.username.value,
        username: e.target.username.value,
        email: e.target.email.value,
        password: e.target.password.value,
        role: ["user"]
      });
      console.log(body);
      post(`/auth/signup`, body)
      .then((response) => {
        if (response.status === 200) {
          toast.success("Sign up succeeded! Sign in now", {
              position: "top-right",
              autoClose: 3000,
          });
          toggleSignOption();
        }
      }).catch((error) => {
        let message = "Sign up failed!";
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

  const signInHandle = (e) => {
    e.preventDefault();
    const body = JSON.stringify({
      username: e.target.username.value,
      password: e.target.password.value
    });
    post(`/auth/signin`, body)
      .then((response) => {
        if (response.status === 200) {
          toast.success(`Welcome back my friend, ${response.data.name}`, {
              position: "top-right",
              autoClose: 3000,
          });
          document.cookie = `token=${response.data.token}; max-age=86400; path=/;`;
          document.cookie = `name=${response.data.name}; max-age=86400; path=/;`;
          document.cookie = `username=${response.data.username}; max-age=86400; path=/;`;
          document.cookie = `email=${response.data.email}; max-age=86400; path=/;`;
          document.cookie = `role=${response.data.roles}; max-age=86400; path=/;`; 
          document.cookie = `status=true; max-age=86400; path=/;`;
          onLogIn(response.data.name);
        }
      }).catch((error) => {
        let message = "Sign in failed!";
        if (!error.response) message = "Connection error! Please try again later";
        else {
          console.log(error.response.status);
          switch (error.response.status) {
              case 401: message = "Login Failed! Check your username and password again"; break;
              default: break;
          }
        }
        toast.error(message, {
            position: toast.POSITION.TOP_RIGHT,
            autoClose: 3000,
        });
      });
  }
      
  const signUpForm =
    <Form id="signup-form" onSubmit={(e) => signUpHandle(e)}>
          <FormGroup>
              <Label for="name">Display Name (not required)</Label>
              <Input
              type="text"
              name="name"
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
      </Form>

  const signInForm =
    <Form id="signin-form" onSubmit={(e) => signInHandle(e)}>
        <FormGroup>
            <Label for="username">Username</Label>
            <Input
            type="text"
            name="username"
            required
            placeholder="Input your username"
            title="Name must contains 2-100 characters"
            pattern=".{2,100}"/>
        </FormGroup>
        <FormGroup>
            <Label for="password">Password</Label>
            <Input
            type={showPassword ? "text" : "password"}
            name="password"
            required
            placeholder="Input your password"
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
    </Form>

  return (
      <CustomModal
        buttonLabel="Sign In"
        btnColor="#3D3D3D"
        modalClassName = {signOption ? "cusmodal-signin" : "cusmodal-signup"}
        title = {signOption ? "Welcome back, " : "Oh hello, come and join us!"}
        body = {signOption ? signInForm : signUpForm}
        confirmBtn = {signOption ? <Button color="dark" type="submit" form="signin-form">Sign In</Button>
                      : <Button color="dark" type="submit" form="signup-form">Sign Up</Button>}
        toggleSignOption={(e) => toggleSignOption()}>
      </CustomModal>
  );
}

export default SignInModal;