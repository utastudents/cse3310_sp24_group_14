package uta.cse3310;
// User events are sent from the webpage to the server

public class UserEvent {
    int GameId; // the game ID on the server
    //PlayerType PlayerIdx; // either an XPLAYER or an OPLAYER
    int Button; // will be used to select words

    UserEvent() {

    }

    UserEvent(int _GameId, /*PlayerType _PlayerIdx,*/ int _Button) {
        GameId = _GameId;
        //PlayerIdx = _PlayerIdx;
        Button = _Button;
    }
}