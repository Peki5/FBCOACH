# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
npm run dev       # Start Vite dev server (localhost:5173)
npm run build     # Production build
npm run preview   # Preview production build
```

No test or lint scripts are configured.

## Architecture

**Stack:** Vue 3 + Vue Router + Vuex + Axios + Tailwind CSS, built with Vite.

### API Configuration

The backend URL is set in `src/constants.js`:
```js
export const SPRING_URL = 'http://localhost:8080';         // dev
// export const SPRING_URL = 'https://fbcoach.onrender.com'; // prod
```
Switch by commenting/uncommenting these lines before deploying.

### API Requests

All HTTP calls go through `src/RequestHandler.js` — a singleton class wrapping Axios. It automatically injects the JWT Bearer token from `sessionStorage`:

```js
import RequestHandler from './RequestHandler';
import { SPRING_URL } from './constants';

const data = await RequestHandler.getRequest(SPRING_URL + '/team');
// Methods: getRequest, postRequest, putRequest, deleteRequest
```

### Authentication & State

- JWT token is stored in `sessionStorage` under the key `"user"` as `{ id, token }`.
- Vuex store (`src/store.js`) holds the user object and is persisted to `localStorage` via `vuex-persist`.
- Router guards in `src/router.js` redirect authenticated users away from `/login` and `/register`.

### Component Structure

Components follow a List → Edit/Details CRUD cycle. Each domain area (Players, Team, Training, Matches, Attendance, Health, Tactics, Reports) has a `List*.vue` and `Edit*.vue` pair. `PlayerDetails.vue` and `MatchDetails.vue` provide read-only detail views.

### Styling

Tailwind CSS with custom theme in `tailwind.config.js`:
- `fbcoach-primary`: `#334E33` (dark green)
- `fbcoach-secondary`: `#CAE7CA` (light green)
- Font: Roboto (loaded via Google Fonts in `index.html`)
