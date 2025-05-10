import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFacebook, faInstagram, faXTwitter} from "@fortawesome/free-brands-svg-icons";
import "./AppFooter.css";

function AppFooter() {
    const currentYear = new Date().getFullYear();

    return (
        <footer className="container">
            <section className="copyright">
                <p>&copy; {currentYear} H. L. Lyons Brewing. Trivia. All Rights Reserved.</p>
            </section>
            <section className="social-media-icons">
                <Link to={"/"} target="_blank"><FontAwesomeIcon icon={faFacebook} className="small-icon-button"/></Link>
                <Link to={"/"} target="_blank"><FontAwesomeIcon icon={faXTwitter} className="small-icon-button"/></Link>
                <Link to={"/"} target="_blank"><FontAwesomeIcon icon={faInstagram}
                                                                className="small-icon-button"/></Link>
            </section>
        </footer>
    );
}

export default AppFooter;