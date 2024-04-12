package uta.cse3310;

public class Message{ //public class Message extends Player

    private Player sender;
    private String content;

    Message(Player sender, String content){
        this.sender = sender;
        this.content = content;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender = " + sender.getNick() +
                ", content = '" + content + '\'' +
                '}';
    }
}
