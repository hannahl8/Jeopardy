import GamesList from "../components/GamesList.tsx";
import "./MainPage.css";

function MainPage() {
    return (
        <div className="games-page main container">
            <h1>Games</h1>
            <GamesList/>
        </div>
    );
}

export default MainPage;