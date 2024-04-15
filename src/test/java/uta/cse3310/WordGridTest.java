//Last modified: 4/14/24 by Anthony Timberman
package uta.cse3310;
import java.util.ArrayList;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class WordGridTest extends TestCase{
    public WordGridTest(String testName) {
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(WordGridTest.class);
    }

    public void testPosition(){
        testing position functions to see if data is properly going from point A to point B

        ArrayList<String> words = new ArrayList<String>(){
             {
                 add("cat");
                 add("bat");
                 add("rat");
                 add("tnt");
                 add("sad");
                 add("go");
                 add("rad");
                 add("mad");
                 add("fad");
                 add("run");
                 add("dun");
             }
         };
        Position start = new Position(2, 3);
        Position end = new Position(5, 6);
        String word = "cat";
        WordGrid wg = new WordGrid(6, words);

        // printing for demonstration
        System.out.println("----------------TESTING MESSAGING----------------");
        wg.placeWords();
        wg.generateGrid();

        System.out.println(wg.checkWordSelection(start, end));

        wg.getWord(start,end);
        // wg.highlightWord(word);
        System.out.println("T/F 'cat' is in the grid\n");
         System.out.println(wg.isWordPlaced(word)+"\n");
        System.out.println("----------------TESTING COMPLETE-----------------");

        //messaging testing ends here
    }
}
