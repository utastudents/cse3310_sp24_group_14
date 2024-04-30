class App {
	constructor() {
		this.userSession = {
			uid: null,
			username: null,
			gameId: null,
			color: null,
			page: "home",
		};
		this.homePageDiv = document.getElementById("homePage");
		this.lobbyPageDiv = document.getElementById("lobbyPage");
		this.setupWebSocket();
	}

	setupWebSocket() {
		let serverUrl =
			"ws://" +
			window.location.hostname +
			`:${parseInt(window.location.port) + 100}`;
		this.websocket = new WebSocket(serverUrl);
		this.websocket.onmessage = (event) => this.handleMessage(event);
		this.websocket.onerror = (error) =>
			console.error("WebSocket error:", error);
		this.websocket.onopen = () =>
			console.log("WebSocket connection established");
		this.websocket.onclose = () => console.log("WebSocket connection closed");
	}

	handleMessage(event) {
		var data = JSON.parse(event.data);

		switch (data.action) {
			case "initialConnection":
				console.log("Connection established with UID:", data.uid);
				this.updateSession({ uid: data.uid });
				this.enterHome();
				break;
			case "enterLobby":
				this.enterLobby(data);
				break;
			case "updateChat":
				this.updateChat(data);
			case "playerJoined":
				if (data.username != this.userSession.username) {
					this.lobby.updateActivePlayersDisplay([{ username: data.username }]);
				}
				break;
			case "playerLeft":
				this.lobby.removeActivePlayer(data.username);
				break;
		}
	}

	enterHome() {
		console.log("New user joined home screen...");
		document.getElementById("mainContent").style.display = "block";
		this.home = new Home(this);
	}

	enterLobby(data) {
		this.lobby = new Lobby(this);
		this.userSession.username = data.username;
		if (data) {
			// if (data.games) {
			// 	this.lobby.updateLobbyDisplay(data.games);
			// }
			if (data.chatMessages) {
				this.lobby.updateChatDisplay(data.chatMessages);
			}
			if (data.activePlayers) {
				this.lobby.updateActivePlayersDisplay(data.activePlayers);
			}
			// if (data.leaderboard) {
			// 	this.lobby.updateLeaderboardDisplay(data.leaderboard);
			// }
		}
	}

	updateChat(data) {
		if (this.lobby) {
			this.lobby.updateChatDisplay([data]);
		}
	}

	updateSession(data) {
		const { uid, username, gameId, color, screen } = data;
		if (uid !== undefined) this.userSession.uid = uid;
		if (username !== undefined) this.userSession.username = username;
		if (gameId !== undefined) this.userSession.gameId = gameId;
		if (color !== undefined) this.userSession.color = color;
		if (screen !== undefined) this.userSession.screen = screen;
	}

	clearSession() {
		this.userSession = {
			uid: null,
			username: null,
			gameId: null,
			color: null,
			screen: "home",
		};
	}

	hideAllContent() {
		document.querySelectorAll("#mainContent > div").forEach((div) => {
			div.classList.add("hidden");
		});
	}
}

document.addEventListener("DOMContentLoaded", () => {
	const app = new App(); // Initializes and starts everything
});
