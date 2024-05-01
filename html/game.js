class Game {
	constructor(app) {
		this.app = app;
		this.gamePageDiv = document.getElementById("gamePage");
		this.gameGrid = document.getElementById("gameGrid");
		this.wordBankDiv = document.querySelector(".word-bank");
		this.wordBankList = document.querySelector(".word-bank-list");
		this.startButton = document.getElementById("startGameButton");
		this.playersList = document.querySelector(".players-list");
		this.preGameLobby = this.gamePageDiv.querySelector(".pre-game-lobby");
		this.selectedCells = [];

		// Event listeners for game actions
		this.startButton.addEventListener("click", () => this.startGameClicked());
	}

	startGameClicked() {
		const startGameMessage = {
			action: "startGame",
			gameId: this.gameId,
		};
		this.app.websocket.send(JSON.stringify(startGameMessage));
	}

	updateGamePage(data) {
		this.gameGrid.classList.remove("hidden");
		this.preGameLobby.classList.add("hidden");
		this.wordBankDiv.classList.remove("hidden");
		this.playersList.classList.remove("hidden");

		this.updatePlayersList(data.players);
		this.updateWordBank(data.wordBank);
		this.updateGameGrid(data.gridData);
	}

	updateWordBank(wordBank) {
		// Clear existing word bank entries
		this.wordBankList.innerHTML = "";

		// Create a list element to hold all the words
		const ul = document.createElement("ul");
		this.wordBankList.appendChild(ul);

		wordBank.forEach((wordItem) => {
			const li = document.createElement("li");
			// Check if the word is found and apply the 'found' class for styling
			if (wordItem.found) {
				li.classList.add("found");
			}
			li.textContent = wordItem.text;
			ul.appendChild(li);
		});
	}

	updateGameGrid(gridData) {
		this.gameGrid.innerHTML = ""; // Clear the grid for new data
		let rowIdx = 0;
		gridData.forEach((row) => {
			row.forEach((cell, colIdx) => {
				const gridCell = document.createElement("div");
				gridCell.className = "grid-cell";
				gridCell.id = "gridCell";
				gridCell.textContent = cell.letter; // Assume gridData contains letter information
				gridCell.style.backgroundColor = cell.color || "transparent"; // Use default color if not specified
				// gridCell.dataset.position = `${rowIdx}-${colIdx}`; // Store position in format "row-col"
				gridCell.dataset.row = rowIdx;
				gridCell.dataset.col = colIdx;
				// Add event listener for clicking on a cell
				gridCell.addEventListener("click", () =>
					this.handleCellClick(gridCell)
				);

				this.gameGrid.appendChild(gridCell);
			});
			rowIdx++;
		});
	}

	handleCellClick(cell) {
		if (this.selectedCells.length < 2) {
			cell.style.backgroundColor = "yellow";
			this.selectedCells.push(cell);
		}

		if (this.selectedCells.length === 2) {
			this.sendWordAttempt();
		}
	}

	sendWordAttempt() {
		const startCell = this.selectedCells[0];
		const endCell = this.selectedCells[1];

		const start = {
			row: parseInt(startCell.dataset.row),
			col: parseInt(startCell.dataset.col),
		};
		const end = {
			row: parseInt(endCell.dataset.row),
			col: parseInt(endCell.dataset.col),
		};

		// Prepare the message to send to the backend
		const wordAttempt = {
			action: "checkWord",
			gameId: this.gameId,
			start: start,
			end: end,
			username: this.app.userSession.username,
		};
		this.app.websocket.send(JSON.stringify(wordAttempt));

		// Reset selection regardless of result
		this.resetSelections();
	}

	resetSelections() {
		this.selectedCells.forEach((cell) => {
			cell.style.backgroundColor = ""; // Remove the yellow background
		});
		this.selectedCells = []; // Clear the array after sending the word attempt
	}

	updatePlayersList(playersList) {
		this.playersList.innerHTML = "";
		playersList.forEach((player) => {
			const playerDiv = document.createElement("div");
			playerDiv.className = "player-entry";
			playerDiv.backgroundColor = player.color;

			const playerNameSpan = document.createElement("span");
			playerNameSpan.textContent = player.username;

			const playerScoreSpan = document.createElement("span");
			playerScoreSpan.className = "score";
			playerScoreSpan.textContent = player.score;

			playerDiv.appendChild(playerNameSpan);
			playerDiv.appendChild(playerScoreSpan);

			this.playersList.appendChild(playerDiv);
		});
	}

	setupPregame(data) {
		this.gameGrid.classList.add("hidden");
		this.preGameLobby.classList.remove("hidden");
		this.wordBankDiv.classList.add("hidden");
		this.playersList.classList.add("hidden");

		this.gameId = data.gameId;
		// Setup players already in the game
		const playersContainer = this.gamePageDiv.querySelector(
			".waiting-players-box"
		);
		playersContainer.innerHTML = ""; // Clear existing entries
		data.players.forEach((player) => {
			let playerDiv = document.createElement("div");
			playerDiv.className = "player-entry";
			playerDiv.textContent = player.username;
			playersContainer.appendChild(playerDiv);
		});

		const startGameButton = this.gamePageDiv.querySelector("#startGameButton");
		if (data.players.length === data.roomSize) {
			startGameButton.disabled = false;
			startGameButton.style.backgroundColor = "#4CAF50";
			startGameButton.textContent = "Start Game";
		} else {
			startGameButton.disabled = true;
			startGameButton.style.backgroundColor = "#ccc";
			startGameButton.textContent = "Waiting for players...";
		}
	}
}
