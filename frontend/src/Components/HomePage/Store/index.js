import React, { useState, useEffect } from 'react';
import { Row, Col, Table } from "reactstrap";

import {AnotherSideBar} from '../../SideBar'
import { get } from "../../../service/httpHelper";
import Paging from "../../Utils/Pagination";
import CustomDropdownButton from "../../Utils/CustomDropDownButton";

import './store.css';

export default function Store() {
    const [cateList, setCateList] = useState([]);
    const [productList, setProductList] = useState([]);
    const optionChoices = ["Name", "Price", "Dev", "Rating"];
    const [sortOption, setSortOption] = useState(["ID", true]);

    function getCateList(){
        get(`/category`).then(response => {
            if (response.status === 200) {
                setCateList(response.data.sort((a,b) => a.name.localeCompare(b.name)));
            }
        });
    }
  
    function getProductList(e){
        if (e === "All") {
            get(`/product`).then(response => {
                if (response.status === 200) {
                    setProductList(response.data);
                }
            });
        } else {
            get(`/product/category/${e}`).then(response => {
                if (response.status === 200) {
                    setProductList(response.data);
                }
            });
        }
    }
  
    useEffect(() => {
        getCateList();
        getProductList("All");
    }, []);

    function handleCategoryChoice(e) {
        getProductList(e);
    }

    function compare (a, b) {
        switch (sortOption[0]) {
            case "Name": return a.name.localeCompare(b.name);
            case "Price": return a.price-b.price;
            case "Dev": return a.dev.localeCompare(b.dev);
            case "Rating": return a.email.localeCompare(b.email);
            default: return a.name.localeCompare(b.name);
          }
    }
  
    useEffect(() => {}, [sortOption, productList]);

    function sortList() {
    let sortedList = cateList.sort(compare);
    if (!sortOption[1]) sortedList.reverse();
    return sortedList;
    }

    const handleSortOptionChange = (item, order) => {
        setSortOption([item, order]);
    }

    React.render(<Store/>, document.getElementById('star-rating'));
    return (
        <Row className="store-background">
            <Col className="col-xl-2 store-sidebar">
                {!cateList ? <h2>Loangding...</h2> :
                    <AnotherSideBar title="Game Genre" choices={[{id:0, name:"All"}, ...cateList]} onChoice={(e) => handleCategoryChoice(e)}/>
                }
            </Col>
            <Col className="col-xl-10 store-container">
                <CustomDropdownButton
                choices = {optionChoices}
                sendChoice = {(item, order) => handleSortOptionChange(item, order)}/>
                
                <div className="product-list-container">
                
                </div>

                <Paging list = {cateList}
                    total = {cateList.length}
                    size = {5}
                    callback = {null}/>
            </Col>
        </Row>
    );

}