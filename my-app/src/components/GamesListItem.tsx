import {GameResponse} from "../types.ts";
import {dateTimeFormatOptions} from "../utils.ts";
import {Link} from "react-router-dom";

function GamesListItem(props: { game: GameResponse }) {
    return (
        <li key={props.game.gameId} className="game-box">
            <Link to={`/game/${props.game.gameId}`} className="game-title" title={props.game.name}>{props.game.name}</Link>
            <div
                className="game-date-created">
                {new Date(props.game.dateCreated).toLocaleString('en-US', dateTimeFormatOptions)}
            </div>
        </li>
    );
}

export default GamesListItem;