import {GameResponse} from "../types.ts";
import GamesListItem from "./GamesListItem.tsx";
import {useGameContext} from "../contexts/GameContext.tsx";

function GamesList() {
    const {games} = useGameContext();

    const gamesList = games.map((myGame: GameResponse) => (
        <GamesListItem key={myGame.gameId} game={myGame}/>
    ));

    return <ul id="game-boxes">{gamesList}</ul>;
}

export default GamesList;