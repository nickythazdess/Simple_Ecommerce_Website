import './App.css';
import React, {useState, useEffect} from 'react';
import {Route, BrowserRouter, Switch} from "react-router-dom"
import HomePage from "./page/HomePage"
import Help from "./page/Help"
import AboutUs from "./page/About_Us"
import AdminPage from "./page/Admin"
import NavBar from "./components/NavBar"
import PrivateAdminRoute from "./components/PrivateRoute/PrivateAdminRoute"
import {getCookie} from "./service/Cookie"


export default function App() {
  const [loginStatus, setLoginStatus] = useState(getCookie("status"));

  useEffect(() => {
    console.log(`STATUS CHANGED: ${loginStatus}`);
  }, [loginStatus]);

  const handleLogIn = (e) => {
    console.log("hello logged in");
    setLoginStatus(true);
  }
  const handleLogOut = (e) => {
    console.log("Logging out at App.js");
    setLoginStatus(false);
  }

  return (
    <div className="root">
      <BrowserRouter>
        <div className="main-navbar">
          <NavBar isLogin={loginStatus} onLogin={(e) => handleLogIn(e)} onLogout={(e) => handleLogOut(e)}/>
        </div>
        <div className="main-content">
          <Switch>
            <Route path='/' exact component={HomePage}/>
            <Route path='/help' exact component={Help}/>
            <Route path='/about_us' exact component={AboutUs}/>
            <PrivateAdminRoute exact path="/admin" component={AdminPage}/>
          </Switch>
        </div>
      </BrowserRouter>
    </div>
  );

}