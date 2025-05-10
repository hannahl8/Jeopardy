export interface ClueResponse {
    clueId: number;
    question: string;
    answer: string;
    value: number;
    wasPicked: boolean;
    categoryId: number;
}

export interface CategoryResponse {
    categoryId: number;
    name: string;
    gameId: number;
}

export interface GameResponse {
    gameId: number;
    name: string;
    dateCreated: Date;
}

export interface GameDetails {
    game: GameResponse;
    categories: CategoryResponse[];
    clues: ClueResponse[];
}

export interface ClueRequest {
    question: string;
    answer: string;
    value: number;
}

export interface CategoryRequest {
    name: string;
    clueRequests: ClueRequest[];
}

export interface GameRequest {
    name: string;
    categoryRequests: CategoryRequest[];
}

export interface PlayerRequest {
    name: string;
    gameId: string;
}

export interface Player {
    playerId: number;
    name: string;
    score: number;
    gameId: number;
}

export interface ServerErrorResponse {
    reason: string;
    message: string;
    fieldName: string;
    error: boolean;
}

