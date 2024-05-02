package uta.cse3310;

import junit.framework.TestCase;
import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class WordGridTest extends TestCase {
    private WordGrid wordGrid;
    private List<Word> sampleWords;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        // Initialize with some sample words
        sampleWords = Arrays.asList(new Word("HELLO"), new Word("WORLD"), new Word("JAVA"));
        wordGrid = new WordGrid(sampleWords);
    }

    public void testGridInitialization() {
        assertNotNull("Grid should be initialized", wordGrid.getGrid());
        // Ensure that the grid size is as expected
        assertEquals("Grid size should be 20x20", 20, wordGrid.getGrid().length);
        assertEquals("Grid size should be 20x20", 20, wordGrid.getGrid()[0].length);
    }

}
