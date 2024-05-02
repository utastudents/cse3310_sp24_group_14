class Lobby {
	constructor(app) {
		this.app = app;
		this.lobbyDiv = document.getElementById("lobbyPage");
		this.chatDiv = document.querySelector(".messagesContainer");
		this.leaderboardDiv = document.getElementById("leaderboard");
		this.sendButton = document.getElementById("sendMessageButton");
		this.activePlayersDiv = document.querySelector(".active-players"); // Make sure this exists in your HTML
		this.gamesListDiv = document.querySelector(".games-list"); // Ensure this is the correct ID for the games list container
		this.leaderboardList = document.querySelector(".leaderboard-list");

		this.showLobbyDisplay();

		this.sendButton.addEventListener("click", () => this.sendMessage());

		document.getElementById("createGame").addEventListener("click", () => {
			this.createGame();
		});
	}

	joinGame(gameId) {
		// Retrieve the username from the app's user session
		const username = this.app.userSession.username;

		if (!username) {
			alert("Username is not set. Please make sure you're logged in.");
			return;
		}

		// Construct the message object to send to the server
		const message = {
			action: "joinGame",
			gameId: gameId,
			username: username,
		};

		// Send the message as a JSON string to the WebSocket server
		this.app.websocket.send(JSON.stringify(message));

		// Optionally, add UI feedback or transitions here
		console.log(`Request sent to join game: ${gameId} for user: ${username}`);
	}

	updateLeaderboardDisplay(leaderboardData) {
		this.leaderboardList.innerHTML = "";

		leaderboardData.forEach((player) => {
			const playerDiv = document.createElement("div");
			playerDiv.className = "leaderboard-content";

			const playerNameP = document.createElement("p");
			playerNameP.className = "leaderboard-player";
			playerNameP.textContent = player.username;

			const playerScoreP = document.createElement("p");
			playerScoreP.className = "leaderboard-score";
			playerScoreP.textContent = player.score;

			playerDiv.appendChild(playerNameP);
			playerDiv.appendChild(playerScoreP);

			this.leaderboardList.appendChild(playerDiv);
		});
	}

	createGame() {
		const roomName = document.getElementById("roomName").value;
		const roomSize = document.querySelector(
			'input[name="roomSize"]:checked'
		).value;

		if (roomName && roomSize) {
			const gameDetails = {
				action: "createGame",
				roomName: roomName,
				roomSize: parseInt(roomSize),
				creator: this.app.userSession.username,
			};
			this.app.websocket.send(JSON.stringify(gameDetails));
		} else {
			alert("Please enter a room name and select a room size.");
		}
	}

	updateGamesDisplay(gamesData) {
		this.gamesListDiv.innerHTML = ""; // Clear the existing games list

		gamesData.forEach((game) => {
			const gameEntry = document.createElement("div");
			gameEntry.className = "game-entry";
			gameEntry.setAttribute("data-game-id", game.gameId); // Set the game ID as a data attribute for easy access

			const gameDetails = document.createElement("div");
			gameDetails.className = "game-details";

			const gameNameDiv = document.createElement("div");
			gameNameDiv.className = "game-name";
			gameNameDiv.textContent = game.gameName;

			const playerCountDiv = document.createElement("div");
			playerCountDiv.className = "player-count";
			playerCountDiv.textContent = `${game.numPlayers}/${game.maxPlayers}`;

			gameDetails.appendChild(gameNameDiv);
			gameDetails.appendChild(playerCountDiv);

			const joinButton = document.createElement("button");
			joinButton.className = "join-game"; // Added a class for clarity
			joinButton.textContent = "Join";

			// Attach the click event listener to the Join button
			joinButton.addEventListener("click", () => this.joinGame(game.gameId));

			gameEntry.appendChild(gameDetails);
			gameEntry.appendChild(joinButton);

			// Append the complete game entry to the games list container
			this.gamesListDiv.appendChild(gameEntry);
		});
	}

	addGameToList(game) {
		const gameEntry = document.createElement("div");
		gameEntry.setAttribute("data-game-id", game.gameId);
		gameEntry.className = "game-entry";
		gameEntry.innerHTML = `<span>${game.gameName} - ${game.numPlayers}/${game.maxPlayers}</span>
                               <button>Join</button>`;
		this.gamesListDiv.appendChild(gameEntry);
	}
	showLobbyDisplay() {
		this.app.hideAllContent();
		this.lobbyDiv.classList.remove("hidden");
	}

	removeActivePlayer(username) {
		const players = this.activePlayersDiv.querySelectorAll(".player-bubble");
		players.forEach((playerDiv) => {
			if (playerDiv.textContent === username) {
				this.activePlayersDiv.removeChild(playerDiv);
			}
		});
	}

	updateActivePlayersDisplay(activePlayers) {
		// Add each active player to the active players list in the lobby UI
		activePlayers.forEach((player) => {
			const playerBubble = document.createElement("div");
			playerBubble.className = "player-bubble";
			playerBubble.textContent = player.username; // assuming the username property exists
			this.activePlayersDiv.appendChild(playerBubble);
		});
	}

	updateActivePlayers(playerName) {
		// Check if the activePlayersDiv is present
		if (this.activePlayersDiv) {
			const newPlayerBubble = document.createElement("div");
			newPlayerBubble.className = "player-bubble";
			newPlayerBubble.textContent = playerName; // Display the username of the new player
			this.activePlayersDiv.appendChild(newPlayerBubble);
		}
	}

	sendMessage() {
		const input = document.querySelector(".messageTextbox"); // Get the message input element
		const message = input.value.trim(); // Trim the message
		if (message) {
			// Construct the message object
			const messageData = {
				sender: this.app.userSession.username, // Assume username is stored in app.userSession
				message: message,
				timestamp: new Date().toISOString(), // ISO timestamp for the message
			};

			// Send the message to the server via WebSocket
			const messageToSend = JSON.stringify({
				action: "sendMessage",
				data: messageData,
			});
			this.app.websocket.send(messageToSend);

			// Clear the input after sending
			input.value = "";
		}
	}

	updateChatDisplay(messages) {
		// Assuming this.chatDiv is correctly referencing the container for messages
		// this.chatDiv.innerHTML = ""; // Clear current content

		messages.forEach((msg) => {
			// Create the container for each message
			let messageBubble = document.createElement("div");
			messageBubble.className = "messageBubble";

			// Create and append the sender element
			let sender = document.createElement("div");
			sender.className = "sender";
			sender.textContent = msg.sender;
			messageBubble.appendChild(sender);

			// Create and append the message content element
			let message = document.createElement("div");
			message.className = "message";
			message.textContent = msg.message;
			messageBubble.appendChild(message);

			// Append the formatted message bubble to the chat container
			this.chatDiv.appendChild(messageBubble);
		});
	}
}
