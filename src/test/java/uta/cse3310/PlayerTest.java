import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PlayerTest extends TestCase{
	public PlayerTest(String testName){
		super(testName);
	}
	
	public static Test suite() {
		return new TestSuite(PlayerTest.class);
	}
	
	public void testSelectWordStart(){
		Player player = new Player ("TrialPlayer", 0, new ArrayList<>(), color.BLACK, new ArrayList<>());
		player.selectWordStart(new Position(0,0));
		assertEquals(1, player.getMarkCells().size());
		assertEquals(new Position(0,0), player.getMarkCells().get(0));
	}
	
	public void testContinueSelectWord(){
		Player player = new Player ("TrialPlayer", 0, new ArrayList<>(), color.BLACK, new ArrayList<>());
		player.selectWordStart(new Position(0,0));
		player.selectWordStart(new Position(1,1));
		assertEquals(2, player.getMarkCells().size());
		assertEquals(new Position(1,1), player.getMarkCells().get(1));
	
	}
	
	public void testSelectWordEndFound(){ //if player finds word
		Player player = new Player ("TrialPlayer", 0, new ArrayList<>(), color.BLACK, new ArrayList<>());
		player.getWordsFound().add("test");
		
		player.selectWordStart(new Position(0,0));
		player.continueSelectWord(new Position(0,1));
		player.selectWordEnd(new Position(0,2));
		
		assertEquals(0, player.getMarkCells().size());
		assertEquals(10,player.getScore());
	}
	
	public void testSelectWordEndNotFound(){ //if player doesn't find word
		Player player = new Player ("TrialPlayer", 0, new ArrayList<>(), color.BLACK, new ArrayList<>());
		
		player.selectWordStart(new Position(0,0));
		player.continueSelectWord(new Position(0,1));
		player.selectWordEnd(new Position(0,2));
		
		assertEquals(0, player.getMarkCells().size());
		assertEquals(0,player.getScore());
	
	}
	
	public void testGetMarkedWord(){
		Player player = new Player("TrialPlayer", 0, new ArrayList<>(), color.BLACK, new ArrayList<>());
		player.selectWordStart(new Position(0,0));
		player.continueSelectWord(new Position(0,1));
		player.selectWordEnd(new Position(0,2));
		
		assertEquals("correct", player.getMarkCells());
	
	}
	
	public void testPrevFound(){
		Player player = new Player("TrialPlayer", 0, new ArrayList<>(), color.BLACK, new ArrayList<>());
		player.getWordsFound().add("correct");
		
		assertTrue(player.prevFound("correct"));
		assertFalse(player.prevFound("incorrect"));
	}
	
	

}