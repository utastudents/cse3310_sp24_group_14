package uta.cse3310;

public class Message{ //public class Message extends Player

    private Player Sender;
    private String Content;

    
    Message(Player player, String Message){
        Sender = player;
        Content = Message;
    }

}
