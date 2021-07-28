import React, { useState, useEffect} from 'react';
import { Pagination, PaginationItem, PaginationLink } from 'reactstrap';

import './pagination.css'

const Paging = (props) => {
    const {
        list,
        total,
        size,
        callback
    } = props;

    const [currentPage, setCurrentPage] = useState(0);
    const maxPage = Math.ceil(total / size);

    const listPage = [];
    for (let i = 1; i <= maxPage; i++) {
        listPage.push(i);
    }

    useEffect(() => {
    }, [currentPage]);

    return (
        <Pagination aria-label="Page navigation example" key="Page" className="pagination justify-content-center ">
            {currentPage === 0 ?
                <PaginationItem disabled key="first">
                    <PaginationLink first onClick={() => setCurrentPage(0)}/> 
                </PaginationItem>
                :
                <PaginationItem key="first">
                    <PaginationLink first onClick={() => setCurrentPage(0)}/> 
                </PaginationItem>
            }
            

            {listPage.map((number) => (
                (number === currentPage+1) ? 
                    <PaginationItem active key={`page${number}`}>
                        <PaginationLink onClick={() => setCurrentPage(number-1)}>
                            {number}
                        </PaginationLink>
                    </PaginationItem>
                    :
                    <PaginationItem key={`page${number}`}>
                        <PaginationLink onClick={() => setCurrentPage(number-1)}>
                            {number}
                        </PaginationLink>
                    </PaginationItem>   
            ))}

            {currentPage === maxPage - 1 ? 
                <PaginationItem disabled key="last">
                    <PaginationLink last onClick={() => setCurrentPage(maxPage-1)}/> 
                </PaginationItem>
                :
                <PaginationItem key="last">
                    <PaginationLink last onClick={() => setCurrentPage(maxPage-1)}/> 
                </PaginationItem>
            }
                
            
        </Pagination>
    );
}

export default Paging;