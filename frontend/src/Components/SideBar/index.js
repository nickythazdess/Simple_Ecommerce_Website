import {React} from "react";
import { useHistory, Link } from "react-router-dom";

const SideBar = (props) => {
    const {
        title,
        choices,
    } = props;

    return (
        <nav className="sidebar">
            <h4>{title}</h4>
            <div className="horizontal-divider"/>
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

export const AnotherSideBar = (props) => {
    const {
        title,
        choices,
        onChoice
    } = props;

    return (
        <nav className="sidebar">
            <h3>{title}</h3>
            <div className="horizontal-divider"/>
            {choices.map((item) => { 
                return (
                    <li key={item.id} onClick={(e) => onChoice(item.name)}>
                        <a>{item.name}</a>
                    </li>
                );
            })}
        </nav>
    );
}