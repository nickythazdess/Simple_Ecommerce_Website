import React, { useState, useEffect } from "react";
import {Table} from "reactstrap";

import { get, postWithAuth } from "../../../service/httpHelper";
import Detail from "./Detail";
import './admin_account.css'

export default function Account() {
    const endpoint = "/category";
    const [cateList, setCateList] = useState([]);

    useEffect(() => {
      getListCate();
    }, []);

    function getListCate(){
      get(endpoint).then(response => {
          if (response.status === 200) {
            setCateList(response.data);
          }
        });
    }

    return (
      <div>
        <h2 className="title-user">CATEGORY MANAGEMENT</h2>
          <br/>
        <Table bordered striped>
          <thead>
            <tr>
              <th>ID</th>
              <th>NAME</th>
              <th>ACTION</th>
            </tr>
          </thead>
          <tbody>
            {(typeof cateList != "undefined") ? cateList.map((cate) => (
              <tr key={cate.id}>
                <Detail category={cate} update={() => getListCate()}/>
              </tr>
            )): null}
          </tbody>
        </Table>
      </div>
    );
  }