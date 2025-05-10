import {Link} from "react-router-dom";
import {mainPage, siteImagePrefix} from "../utils.ts";
import "./AppHeader.css"
import HeaderDropdown from "./HeaderDropdown.tsx";

function AppHeader() {
    return (
        <header className="container">
            <section className="logo-and-title">
                <Link className="logo-and-text" to={mainPage}>
                    <img
                        src={`${siteImagePrefix}/logo.png`}
                        alt="H. L. Lyons Brewing Logo"
                    />
                </Link>
            </section>
            <section className="menu">
                <HeaderDropdown/>
            </section>
        </header>
);
}

export default AppHeader;