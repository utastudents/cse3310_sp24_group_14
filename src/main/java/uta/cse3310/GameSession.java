package uta.cse3310;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Timer;

public class GameSession{ //public class GameSession extends Player
	public WordGrid wordGrid;
	private Map<Player, Integer> scores = new HashMap<>();
	private ArrayList<Player> players;
	private Timer timer;
	
	public void startGame()
	{
		
	}
	
	public void endGame()
	{
		
	}
	
	public boolean validateWordSelection(Position startPos, Position endPos)
	{
		return false; //changed this from endPos because endPos is a Position object and the function return type wants a boolean
	}
	
	public void updateScores(Player player, int score)
	{
		
	}
	
	public void startTimer(int duration)
	{
		
	}
	
	public void stopTimer()
	{
		
	}

	public static void main(String args[])
	{

	}
	
}
