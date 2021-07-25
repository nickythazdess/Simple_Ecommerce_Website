import {React} from "react";
import { useHistory, Link } from "react-router-dom";
import {Button} from 'reactstrap';
import './sidebar.css'

const SideBar = (props) => {
    const {
        title,
        choices,
        onChoice
      } = props;

    return (
        <nav className="sidebar">
            {choices.map((item) => { 
                return (
                    <li key={item} onClick={() => onChoice(item)}>
                        {item}
                    </li>
                );
            })}
        </nav>
    );
}

export default SideBar;