/*
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;

*/
/**
 * All of these tests can be slightly updated to be correct with the actual output expected
 * Right now all of these are passing because they are using the expected output from 
 * the BackendPlaceholder class
 *//*

public class FrontendDeveloperTests extends ApplicationTest {
    
    */
/**
     * This method launches the JavaFX application that you would like to test
     * BeforeEach of your @Test methods are run.
     *//*

    @BeforeEach
    public void setup() throws Exception {
        Frontend.setBackend(new Backend(new DijkstraGraph()));
        ApplicationTest.launch(Frontend.class);
    }

    */
/**
     * Tests that the shortest path without checking the include walking distance 
     * checkbox returns what we would expect
     *//*

    @Test
    public void testShortestPathNoWalkingDistance() {
        Label resultsLabel = lookup("#resultsLabel").query();
        clickOn("#srcTextBox");
        write("Union South");
        clickOn("#dstTextBox");
        write("Computer Sciences and Statistics");
        clickOn("#searchButton");

        Assertions.assertTrue(resultsLabel.getText().contains(("Results List:")));
        Assertions.assertTrue(resultsLabel.getText().contains(("Union South")));
        Assertions.assertTrue(resultsLabel.getText().contains(("Computer Sciences and Statistics")));
    }


    */
/**
     * Tests that the shortest path without checking the include walking distance 
     * checkbox returns what we would expect
     *//*

    @Test
    public void testShortestPathWithWalkingDistance() {
        Label resultsLabel = lookup("#resultsLabel").query();
        clickOn("#srcTextBox");
        write("Union South");
        clickOn("#dstTextBox");
        write("Computer Sciences and Statistics");
        clickOn("#walkingTimesBox");
        clickOn("#searchButton");
        
        Assertions.assertTrue(resultsLabel.getText().contains(("Results List (with walking times):")));
        Assertions.assertTrue(resultsLabel.getText().contains(("Union South (176.0)--> Computer Sciences and Statistics")));
        Assertions.assertTrue(resultsLabel.getText().contains(("Approximate Total Time: 2min 56sec")));
    }

    */
/**
     * Tests the furthest from labels, button and output to make 
     * sure it is what we expect for certain inputs
     *//*

    @Test
    public void testFurthestLocation() {
        Label furthestFrom = lookup("#furthestFromLabel").query();
        clickOn("#locTextBox");
        write("Union South");
        clickOn("#furthestFromButton");

        Assertions.assertTrue(furthestFrom.getText().contains("Smith Residence Hall"));
        Assertions.assertTrue(furthestFrom.getText().contains("Approximate Total Time: 29min 53sec"));

    }

    */
/**
     * This method tests that when you click the About button the label becomes visible and the user sees 
     * the information. I was going to test the quit button but I don't know how to
     *//*

    @Test
    public void testAbout() {

        Label aboutLabel = lookup("#aboutLabel").query();
        clickOn("#about");

        
        Assertions.assertTrue(aboutLabel.getText().contains("This application can help you find paths between"));
    }

    */
/**
     * Tests that the shortest path without checking the include walking distance 
     * checkbox returns the correct error whem the second location is wrong
     *//*

    @Test
    public void testShortestPathBadFirstLocation() {
        Label srcLabel = lookup("#srcLabel").query();
        clickOn("#srcTextBox");
        write("Union North");
        clickOn("#dstTextBox");
        write("Computer Sciences and Statistics");
        clickOn("#searchButton");

        Assertions.assertTrue(srcLabel.getText().contains(("ERROR")));
    }
    */
/**
     * Tests that the shortest path without checking the include walking distance 
     * checkbox returns the correct error whem the second location is wrong
     *//*

    @Test
    public void testShortestPathBadSecondLocation() {
        Label dstLabel = lookup("#dstLabel").query();
        clickOn("#srcTextBox");
        write("Union South");
        clickOn("#dstTextBox");
        write("Computer Stuff and Statistics");
        clickOn("#searchButton");

        Assertions.assertTrue(dstLabel.getText().contains(("ERROR")));
    }
}
*/
