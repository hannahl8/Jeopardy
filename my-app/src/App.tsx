import {Route, Routes} from "react-router-dom";
import AppHeader from "./components/AppHeader.tsx";
import MainPage from "./pages/MainPage.tsx";
import AppFooter from "./components/AppFooter.tsx";
import {createGamePage, mainPage, gamePage} from "./utils.ts";
import CreateGamePage from "./pages/CreateGamePage.tsx";
import GamePage from "./pages/GamePage.tsx";

function App() {
    return (
        <div className="app">
            <AppHeader/>
            <Routes>
                <Route path={mainPage} element={<MainPage/>}/>
                <Route path={createGamePage} element={<CreateGamePage/>}/>
                <Route path={gamePage} element={<GamePage/>}/>
            </Routes>
            <AppFooter/>
        </div>
    )
}

export default App
