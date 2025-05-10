import axios, {AxiosResponse} from 'axios';
import {GameDetails, GameRequest, GameResponse, Player, PlayerRequest, ServerErrorResponse} from "./types.ts";
import {apiUrl} from "./utils.ts";

export const fetchGames = async (): Promise<GameResponse[]> => {
    const response = await axios.get(`${apiUrl}/games`);
    return response.data as GameResponse[];
};

export async function createGame(
    createGame: GameRequest
): Promise<GameDetails | ServerErrorResponse> {
    console.log(JSON.stringify(createGame));
    try {
        const response: AxiosResponse<GameDetails> = await axios.post(
            `${apiUrl}/games/create`,
            createGame,
            {
                withCredentials: true,
                headers: {
                    "Content-Type": "application/json",
                },
            }
        );

        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error) && error.response) {
            return error.response.data;
        } else {
            throw new Error("An unexpected error occurred");
        }
    }
}

export const fetchGame = async (gameId: number): Promise<GameDetails | ServerErrorResponse> => {
    const response = await axios.get(`${apiUrl}/games/${gameId}/details`);
    return response.data as GameDetails;
}

export const createPlayer = async (
    playerRequest: PlayerRequest
): Promise<Player | ServerErrorResponse> => {
    console.log(JSON.stringify(playerRequest));
    try {
        const response: AxiosResponse<Player> = await axios.post(
            `${apiUrl}/games/${playerRequest.gameId}/players`,
            playerRequest,
            {
                withCredentials: true,
                headers: {
                    "Content-Type": "application/json",
                },
            }
        );
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error) && error.response) {
            return error.response.data;
        } else {
            throw new Error("An unexpected error occurred");
        }
    }
};

export const fetchPlayers = async (gameId: number): Promise<Player[] | ServerErrorResponse> => {
    const response = await axios.get(`${apiUrl}/games/${gameId}/players`);
    return response.data as Player[];
}

// delete player is "games/{game-id}/players/{player-id}")
export const deletePlayer = async (gameId: number, playerId: number): Promise<void | ServerErrorResponse> => {
    try {
        await axios.delete(`${apiUrl}/games/${gameId}/players/${playerId}`);
    } catch (error) {
        if (axios.isAxiosError(error) && error.response) {
            return error.response.data;
        } else {
            throw new Error("An unexpected error occurred");
        }
    }
};