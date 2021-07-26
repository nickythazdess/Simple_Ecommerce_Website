import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';

import Detail from './Detail';

const Body = (props) => {
    const {
      by,
      order,
      cateList,
      getListCate
    } = props;

    console.log("body" + by + ' ' + order);

    function compare (a, b){
        if (by === "ID")
            return a.id-b.id;
        else if (by === "Name") {
            return a.name.localeCompare(b.name);
        }
    }

    let sortedList = cateList.sort(compare);

    if (!order) {
        sortedList.reverse();
    }
  
    return (
      <>
        {sortedList.map((cate) => (
            <tr key={cate.id}>
                <Detail category={cate} update={(e) => getListCate()}/>
            </tr>
        ))}
      </>
    );
  }
  
  export default Body;