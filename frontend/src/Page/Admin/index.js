import React, {useState, useEffect} from "react";
import SideBar from "../../components/SideBar"
//import Product from "../../components/Admin/Product"
import Category from "../../components/Admin/Category"
//import Account from "../../components/Admin/Account"
import {Route, BrowserRouter as Router, Switch} from "react-router-dom"
import { Row, Col } from "reactstrap";


export default function AdminPage() {
    const choices = [["PRODUCT", '/admin/product'], ["CATEGORY", '/admin/category'], ["ACCOUNT", '/admin/account']]

    return (
        <Row className="admin-background">
            <Router>
                <Col className="col-2 admin-sidebar">
                    <SideBar title="ADMIN WORKSPACE" choices={choices}/>
                </Col>
                <Col className="col admin-content">
                    <Switch>
                        <Route path='/admin/category' exact component={Category}/>
                        {/*<Route path='/admin/product' exact component={Product}/>
                        <Route path='/admin/account' exact component={Account}/>*/}
                    </Switch>
                </Col>
            </Router>
        </Row>
    );
}