# AnimePlay
Seekho assessment, displaying Anime details with Jikan API

✨ Features Implemented
1️⃣ Anime List Screen
A modern, scrollable list that displays key information about each anime retrieved from the Jikan API or from the cached Room database.

**Features**
1) Fetches anime list from Jikan API
2) Automatically falls back to Room local database when offline
3) UI elements shown per anime:
  - Poster image
  - Title
  - Episode count
  - Rating
  - Rank
  - Duration/runtime
4) LazyColumn with smooth scrolling
5) Loading, empty, and error UI states
6) Efficient image loading using Glide
7) Automatic local caching after each successful fetch(Room DB)


2️⃣ Anime Details Screen
A comprehensive screen showing all available details of the selected anime.

**Features**

1) Displays all list-screen information plus extended details such as:
  - Synopsis (story summary)
  - Season / Release season
  - Aired date range
  - Duration per episode
  - Airing status
  - Genres
  - Studios
  - Producers
2) Clean, structured design using sections & labels
3) Scrollable page with Material 3 styling
4) Uses data from  Room directly eliminating the need for using Another API call.

**Watch Trailer Button**

1) If the anime includes a trailer URL, a Watch Trailer button is shown
2) Opens an in-app Floating YouTube Player built using:
  A draggable overlay window
  WebView/YouTube embedded video player
  Play/Pause/Close interactions
  The video player floats above the UI and remains draggable even if the user scrolls
  When URL is missing, the button is hidden,


🧭 Architecture & Data Flow

1) MVVM (Model–View–ViewModel)
2) Repository pattern integrating:
  Remote (Jikan API)
  Local (Room DB)
3) StateFlow for reactive updates
4) Coroutines for async, cancellation-safe operations
5) Navigation Compose for screen transitions

🧠 Assumptions Made
1) Device must have internet at least once for initial data to be cached.
2) Jikan API fields are consistent and not null for core properties.
3) Trailer playback depends on YouTube embed availability.
4) Exact UI design was interpreted where not explicitly provided.
5) Filtering or search functionality is outside the scope unless required.

⚠️ Known Limitations

1) No offline video playback; trailer requires internet.
2) Floating YouTube player behavior may vary based on device WebView version.
3) Database caching is overwrite-based, not differential sync.
4) Minimal automated unit/UI tests due to assessment timeframe.
5) No advanced error recovery (e.g., retry queues or partial fetch merges).
6) Accessibility (TalkBack, font scaling) partially implemented.


📌 Implementation Note (Important)

To optimize network usage and reduce unnecessary API calls, the Details API endpoint was intentionally not used.
All details shown in the Details Screen are retrieved from the List API response, which already contains all required fields such as:
  Synopsis
  Aired date
  Season information
  Duration
  Genres
  Studios
  Producers
  Rating, rank, episodes, etc.
These details are cached in the Room Database during the List API fetch.
When the user opens the Details Screen, the data is loaded directly from Room, eliminating an extra network call and improving performance, responsiveness, and offline support.

This approach follows the principle of Single Source of Truth (SSOT) and reduces the API hit count significantly.
