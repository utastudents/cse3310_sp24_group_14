package uta.cse3310;

import java.util.ArrayList;
import java.util.List;

public class Chatroom {
    private List<Message> messages;
    private List<Player> participants;

    public Chatroom() {
        this.messages = new ArrayList<>();
        this.participants = new ArrayList<>();
    }

    /**
     * Adds a player to the chat room.
     *
     * @param player The player to add.
     */
    public void addParticipant(Player player) {
        if (!participants.contains(player)) {
            participants.add(player);
        }
    }

    /**
     * Removes a player from the chat room.
     *
     * @param player The player to remove.
     */
    public void removeParticipant(Player player) {
        participants.remove(player);
    }

    /**
     * Posts a message to the chat room and notifies all participants.
     *
     * @param sender  The player sending the message.
     * @param content The content of the message.
     */
    public void postMessage(Message message) {
        messages.add(message);
    }

   

    /**
     * Gets the chat history.
     *
     * @return A list of messages.
     */
    public List<Message> getMessages() {
        return new ArrayList<>(messages); // Returns a copy to prevent modification from outside
    }
}
