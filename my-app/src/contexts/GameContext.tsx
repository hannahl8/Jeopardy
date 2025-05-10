import React, {createContext, useState, useEffect, useContext} from 'react';
import {GameResponse} from '../types';
import {fetchGames} from '../services.ts'; // This will be created later

const DEFAULT_GAME_NAME = "AI Generated Game";

interface GameContextType {
    games: GameResponse[];
    lastSelectedGameName: string;
    setLastSelectedGameName: (name: string) => void;
}

const GameContext = createContext<GameContextType>({
    games: [],
    lastSelectedGameName: DEFAULT_GAME_NAME,
    setLastSelectedGameName: () => {},
});

export const GameProvider = ({children}: React.PropsWithChildren) => {
    const [lastSelectedGameName, setLastSelectedGameName] = useState<string>(DEFAULT_GAME_NAME);
    const [games, setGames] = useState<GameResponse[]>([]);

    useEffect(() => {
        fetchGames()
            .then((data) => setGames(data))
            .catch((error) => console.error(error));
    }, []);

    return (
        <GameContext.Provider value={{games, lastSelectedGameName, setLastSelectedGameName}}>
            {children}
        </GameContext.Provider>
    );
};

export const useGameContext = () => {
    const context = useContext(GameContext);
    if (!context) {
        throw new Error('useCategoryContext must be used within a CategoryProvider');
    }
    return context;
};