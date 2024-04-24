package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent {
    int GameId; // the game ID on the server
    Player PlayerIdx; // either an XPLAYER or an OPLAYER
    Position Button1; // Starting position of word
    Position Button2; // ending position of grid

    UserEvent() {

    }

    UserEvent(int _GameId, Player _PlayerIdx, Position _Button1, Position _Button2) {
        GameId = _GameId;
        PlayerIdx = _PlayerIdx;
        Button1 = _Button1;
        Button2 = _Button2;
    }
}
