import React, {useState} from "react";

export default function HomePage(props) {
    return (
        <div>
            Welcome to {props.bootcamp}, {props.name}.
        </div>);
}

