import React, { useState, useEffect } from 'react';
import { ButtonDropdown, DropdownToggle, DropdownMenu, DropdownItem } from 'reactstrap';

const CustomDropdownButton = (props) => {
    const {
        choices,
        sendChoice,
    } = props;

    const [dropdownOpen, setOpen] = useState(false);
    const [order, setOrder] = useState(true);
    const [buttonLabel, setLabel] = useState(choices[0]);

    const toggleOrder = () => setOrder(!order);
    const toggle = () => setOpen(!dropdownOpen);

    useEffect(() => {
        sendChoice(buttonLabel, order);
    }, [buttonLabel, order]);

    const handleChoice = (item) => {
        if (item === buttonLabel) toggleOrder();
        else setLabel(item);
        toggle();
    }

    return (
        <>
        <p style={{display: "inline"}}>Sort {order ? "ASCENDING" : "DESCENDING"} by: </p>
        <ButtonDropdown isOpen={dropdownOpen} toggle={toggle}>
            <DropdownToggle caret> {buttonLabel} </DropdownToggle>
            <DropdownMenu>
                {choices.map((item) => {
                    return <DropdownItem key={item} onClick={(e) => handleChoice(item)}>{item}</DropdownItem>
                })}
            </DropdownMenu>
        </ButtonDropdown>
        </>
    );
}

export default CustomDropdownButton;