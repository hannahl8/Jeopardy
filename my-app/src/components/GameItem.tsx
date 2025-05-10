import {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {deletePlayer, fetchGame, fetchPlayers} from "../services";
import {GameDetails, Player, ServerErrorResponse} from "../types";
import "./GameItem.css"
import PlayerFormItem from "./PlayerFormItem.tsx";

export default function GameItem() {

    const {gameId} = useParams<{ gameId: string }>();
    const [gameDetails, setGameDetails] = useState<GameDetails | null>(null);
    const [error, setError] = useState<ServerErrorResponse | null>(null);
    const [loading, setLoading] = useState<boolean>(true);
    const [players, setPlayers] = useState<Player[]>([]);


    useEffect(() => {
        if (!gameId) return;

        const loadGame = async () => {
            setLoading(true);
            const result = await fetchGame(Number(gameId));

            if ('error' in result && result.error && isServerErrorResponse(result)) {
                setError(result);
                setGameDetails(null);
            } else {
                setGameDetails(result as GameDetails);
                setError(null);
            }


            setLoading(false);
        };

        loadGame();
    }, [gameId]);

    function isServerErrorResponse(obj: any): obj is ServerErrorResponse {
        return obj && typeof obj.reason === 'string' &&
            typeof obj.message === 'string' &&
            typeof obj.fieldName === 'string';
    }

    function getCluesByCategoryId(categoryId: number) {
        return gameDetails?.clues.filter(clue => clue.categoryId === categoryId) || [];
    }

    const handlePlayerAdded = () => {
        if (gameId) {
            fetchPlayers(Number(gameId)).then((result) => {
                if (!("error" in result)) {
                    setPlayers(result as Player[]);
                }
            });
        }
    };

    useEffect(() => {
        if (!gameId) return;

        const loadPlayers = async () => {
            const result = await fetchPlayers(Number(gameId));
            if (!("error" in result)) {
                setPlayers(result as Player[]);
            }
        };

        loadPlayers();
    }, [gameId]);


    const handlePlayerDeleted = (playerId: number) => {
        if (gameId) {
            deletePlayer(Number(gameId), playerId)
                .then(() => {
                    setPlayers((prevPlayers) =>
                        prevPlayers.filter((player) => player.playerId !== playerId)
                    );
                })
                .catch((error) => {
                    console.error("Error deleting player:", error);
                });
        }
    };

    if (loading) return <p>Loading game details...</p>;
    if (error) return <p>Error: {error.message}</p>;
    if (!gameDetails) return <p>No game found.</p>;

    return (
        <div className="jeopardy-page">
            <h1 className="jeopardy-game-name">{gameDetails.game.name}</h1>
            <ul className="jeopardy-board">
                {gameDetails.categories.map(category => (
                    <li key={category.categoryId} className="category">
                        <p className="category-name">{category.name}</p>
                        <ul className="list-of-clues">
                            {getCluesByCategoryId(category.categoryId).map(clue => (
                                <li key={clue.clueId} className="clue">{clue.value}</li>
                            ))}
                        </ul>
                    </li>
                ))}
            </ul>
            <div className="players">
                <h2>Players</h2>
                <ul className="players-list">
                    {players.map((player) => (
                        <li key={player.playerId}>
                            {player.name} - {player.score} points
                            <button
                                onClick={() => handlePlayerDeleted(player.playerId)}
                                className="delete-button"
                            >
                                Delete
                            </button>
                        </li>
                    ))}
                </ul>
                <PlayerFormItem onPlayerAdded={handlePlayerAdded}/>
            </div>
        </div>
    );
};