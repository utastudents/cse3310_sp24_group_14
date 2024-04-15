//Last modified: 4/14/24 by Anthony Timberman
package uta.cse3310;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;


public class PosTest extends TestCase{
    public PosTest(String testName) {
        super(testName);
    }

    public static Test suite(){
        return new TestSuite(PosTest.class);
    }

    public void testPosition(){
        // testing position functions to see if data is properly going from point A to point B

        // printing for demonstration
        System.out.println("----------------TESTING MESSAGING----------------");

        Position p1 = new Position(1, 4);
        System.out.println(p1);

        System.out.println("----------------TESTING COMPLETE-----------------");

        // messaging testing ends here
    }
}
