import {React} from "react";
import { useHistory, Link } from "react-router-dom";
import {Button} from 'reactstrap';
import './sidebar.css'

const SideBar = (props) => {
    const {
        title,
        choices,
    } = props;

    return (
        <nav className="sidebar">
            <h4>{title}</h4>
            <div className="vertical-divider"/>
            {choices.map((item) => { 
                return (
                    <li key={item[0]}>
                        <Link to={item[1]}>
                            <span>{item[0]}</span>
                        </Link>
                    </li>
                );
            })}
        </nav>
    );
}

export default SideBar;