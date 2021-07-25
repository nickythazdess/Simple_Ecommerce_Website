import './App.css';
import React, {useState, useEffect} from 'react';
import {Route, BrowserRouter, Switch} from "react-router-dom"
import HomePage from "./page/HomePage"
import Help from "./page/Help"
import AdminPage from "./page/Admin"
import NavBar from "./components/NavBar"
import PrivateAdminRoute from "./components/PrivateRoute/PrivateAdminRoute"
import {getCookie} from "./service/Cookie"


export default function App() {
  const [loginStatus, setLoginStatus] = useState(getCookie("status"));

  useEffect(() => {
    console.log(`STATUS CHANGED: ${loginStatus}`);
  }, [loginStatus]);
  useEffect(() => {}, []);

  function handleStatusChange(e) {
    setLoginStatus(true);
  }
  function handleLogOut(e) {
    setLoginStatus(false);
  }

  /*<BrowserRouter>
      <div className="root">
         
        <PrivateAdminRoute path="/Admin" component={AdminPage} exact/>
        <NavBar/>
        <Switch>
          <Route exact path="/home">
            <HomePage/>
          </Route>
          <Route exact path="/help">
            <Help/>
          </Route>
        </Switch>
      </div>
    </BrowserRouter>*/

  return (
    <div>
      <NavBar/>
      <AdminPage/> 
    </div>
  );

}