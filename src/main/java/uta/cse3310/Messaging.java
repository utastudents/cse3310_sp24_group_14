package uta.cse3310;

import java.util.ArrayList;

public class Messaging{ //public class Messaging extends Message (previous)


    public static void Send(Player sender, Player reciever, String message){
//  Will deliver desired message so only Reciever can see
        Message msg = new Message(sender, message);
        reciever.messages.add(msg);
    };
    public static void Broadcast(Player sender, ArrayList<Player> players, String message){
//  Will deliver desired message for full lobby
        Message msg = new Message(sender, message);
        int i = players.size();
        // i > 0 is making sure players exist before running anything
        if(i > 0){
            for(int x = 0; x < i; x++){
                players.get(x).messages.add(msg);
            }
        }
    };
};
