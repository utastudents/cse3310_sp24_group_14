package uta.cse3310;

public class ServerEvent {
    //PlayerType YouAre; // Either an XPLAYER or a YPLAYER; add to gamesession, should be player 1,2,3, or 4
    int GameId;
    int ClientId;
    String ServerEvent;
    String Version = "no version";
}