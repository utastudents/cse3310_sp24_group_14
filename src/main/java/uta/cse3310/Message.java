package uta.cse3310;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Message {
    private String content;
    private Player sender;
    private LocalDateTime timestamp;

    public Message(Player sender, String content) {
        this.sender = sender;
        this.content = content;
        this.timestamp = LocalDateTime.now();  // Set the timestamp to the current time
    }

    public String getContent() {
        return content;
    }

    public Player getSender() {
        return sender;
    }

    public String getTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public String toString() {
        return String.format("[%s] %s: %s", getTimestamp(), sender.getUsername(), getContent());
    }
}
