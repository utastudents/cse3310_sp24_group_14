class Lobby {
	constructor(app) {
		this.app = app;
		this.lobbyDiv = document.getElementById("lobbyPage");
		this.chatDiv = document.querySelector(".messagesContainer");
		this.leaderboardDiv = document.getElementById("leaderboard");
		this.sendButton = document.getElementById("sendMessageButton");

		this.sendButton.addEventListener("click", () => this.sendMessage());
		this.activePlayersDiv = document.querySelector(".active-players"); // Make sure this exists in your HTML

		this.showLobbyDisplay();
	}

	showLobbyDisplay() {
		this.app.hideAllContent();
		this.lobbyDiv.classList.remove("hidden");
	}

	updateLobbyDisplay(games) {
		this.lobbyDiv.innerHTML = ""; // Clear current content
		games.forEach((game) => {
			let gameElement = document.createElement("div");
			gameElement.innerHTML = `Game: ${game.gameName} (${game.numPlayers}/${game.maxPlayers}) - ${game.status}`;
			this.lobbyDiv.appendChild(gameElement);
		});
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

	updateLeaderboardDisplay(leaderboard) {
		this.leaderboardDiv.innerHTML = ""; // Clear current content
		leaderboard.forEach((player) => {
			let playerElement = document.createElement("p");
			playerElement.textContent = `${player.username}: ${player.score}`;
			this.leaderboardDiv.appendChild(playerElement);
		});
	}
}
