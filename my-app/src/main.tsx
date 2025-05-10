import {createRoot} from 'react-dom/client'
import {BrowserRouter} from "react-router-dom";
import App from './App.tsx'
import './assets/global.css'
import {GameProvider} from "./contexts/GameContext.tsx";

createRoot(document.getElementById('root')!).render(
    <BrowserRouter basename={import.meta.env.BASE_URL}>
        <GameProvider>
            <App/>
        </GameProvider>
    </BrowserRouter>
)
