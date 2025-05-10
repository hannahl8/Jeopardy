// export const apiUrl =
//     `${location.protocol}//${location.hostname}:` +
//     `${location.port === '5173' ? '8080' : location.port}` +
//     `${import.meta.env.BASE_URL}/api`

export const apiUrl =
    `${location.protocol}//${location.hostname}:8081` +
    `${import.meta.env.BASE_URL}/api`;

export const siteImagePrefix = `${import.meta.env.BASE_URL}/site-images/`;

export function sleep(ms: number): Promise<void> {
    return new Promise(resolve => setTimeout(resolve, ms));
}

export const mainPage = "/";
export const createGamePage = "/create";
export const gamePage = "/game/:gameId";

export const dateTimeFormatOptions: Intl.DateTimeFormatOptions = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    hour: '2-digit',
    minute: '2-digit',
    second: '2-digit'
};