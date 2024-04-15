import java.util.ArrayList;
import java.util.List;

public class ChatBox {
    private List<String> chatHistory;

    public ChatBox() {
        this.chatHistory = new ArrayList<>();
    }

    public void sendToAll(Player sender, String message) {
        // Placeholder logic: Send message to all players
        String formattedMessage = "[" + sender.getNick() + "]: " + message;
        chatHistory.add(formattedMessage);
        // Here you would typically send the message to all players in the session
        // For now, we'll just print it to simulate sending
        System.out.println("Message sent to all: " + formattedMessage);
    }

    public void displayHistory(Player player) {
        // Placeholder logic: Display chat history to the player
        System.out.println("Chat history for " + player.getNick() + ":");
        for (String message : chatHistory) {
            System.out.println(message);
        }
    }

    public void collapse() {
        // Placeholder logic: Collapse the chat box
        System.out.println("Chat box collapsed.");
    }
    
    private static class WordFilter {
        public static String censor(String message) {
            // Placeholder logic to censor inappropriate words
            return message; // Implement your censoring logic here
        }
}
