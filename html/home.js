const usernameInput = document.getElementById("username");
const homePage = document.getElementById("homePage");
const lobbyPage = document.getElementById("lobbyScreen");

class Home {
	constructor(app) {
		this.app = app;
		this.setupUIElements();
		this.setupEventListeners();
	}

	setupUIElements() {
		this.usernameInput = document.getElementById("username");
		this.usernameSubmit = document.querySelector(".enterLobby");
		this.homePageDiv = document.getElementById("homepage");
		this.homePageDiv.classList.remove("hidden");
	}

	setupEventListeners() {
		// Event listener for the username submission
		this.usernameSubmit.addEventListener("click", () =>
			this.handleUsernameSubmit()
		);
	}

	handleUsernameSubmit() {
		const username = this.usernameInput.value.trim();
		if (username) {
			this.app.updateSession({ username: username }); // Update the user session with the username
			this.sendConnectionMessage(username); // Send a message to the server indicating a new connection
			// this.app.enterLobby({ username: username }); // Transition to the lobby
		} else {
			alert("Please enter a username.");
		}
	}

	sendConnectionMessage(username) {
		// Assuming the WebSocket connection is initialized and maintained in the App class
		const message = {
			action: "enterLobby",
			username: username,
		};
		this.app.websocket.send(JSON.stringify(message));
	}
}
