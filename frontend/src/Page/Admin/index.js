import React, {useState, useEffect} from "react";
import SideBar from "../../components/SideBar"
//import Product from "../../components/Admin/Product"
import Category from "../../components/Admin/Category"
//import Account from "../../components/Admin/Account"
import * as http from "../../service/httpHelper"

export default function AdminPage() {
    const [choice, setChoice] = useState("PRODUCT");
    const choices = ["PRODUCT", "CATEGORY", "ACCOUNT"]

    /*function showRoute(){
        switch(choice){
          case "PRODUCT": return (<Product/>);
          case "CATEGORY": return (<Category/>);
          case "ACCOUNT": return (<Account/>);
          default: return;
        }
    }*/

    const handleChoiceChange = (e) => {
        console.log(e);
        setChoice(e);
    }
    useEffect(()=>{},[choice]);

    return (
        //http.get("/product")
        <SideBar title="ADMIN WORKSPACE" onChoice={(e) => handleChoiceChange(e)} choices={choices}/>
        //<Category/>
        //showRoute()
    );
}