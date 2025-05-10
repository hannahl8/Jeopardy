import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBars} from "@fortawesome/free-solid-svg-icons";
import "./HeaderDropdown.css";
import { Link } from "react-router-dom";
import { createGamePage, mainPage } from "../utils";

function HeaderDropdown() {
    return (
        <div className="header-dropdown">
            <FontAwesomeIcon icon={faBars} className="icon-button"/>
            {/*<HeaderDropdownNavLinks/>*/}
            <ul>
                <li>
                    <Link to={createGamePage} className="create-new-game" title="Create New Game">Create New Game</Link>
                </li>
                <li>
                    <Link to={mainPage} className="random-new-game" title="Randomly Generated Game">Randomly Generated Game</Link>
                </li>
            </ul>
        </div>
    );
}

export default HeaderDropdown;