/*
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.testfx.framework.junit5.ApplicationTest;

import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BackendDeveloperTests extends ApplicationTest {

    */
/**
     * Tests the loadGraphData() method using a try block and getListOfAllLocations()
     *//*

    @Test
    public void test1() {
        GraphADT<String, Double> testGraph = new DijkstraGraph();
        BackendInterface backend = new Backend(testGraph);

        // Tests if IOException is thrown for an invalid file name
        try {
            backend.loadGraphData("fake.dot");
            Assertions.fail();
        } catch(IOException e) { }

        // Tests if a valid file name doesn't throw an exception
        try {
            backend.loadGraphData("campus.dot");
        } catch(IOException e) {
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        // Tests if the list is empty and probes a couple of locations to see if the data was correctly loaded
        List<String> testLocations = backend.getListOfAllLocations();
        Assertions.assertTrue(!testLocations.isEmpty() && testLocations.contains("Memorial Union") &&
                testLocations.contains("Atmospheric, Oceanic and Space Sciences"));
    }

    */
/**
     * Tests the findShortestPath() method
     *//*

    @Test
    public void test2() {
        GraphADT<String, Double> testGraph = new DijkstraGraph();
        BackendInterface backend = new Backend(testGraph);

        try {
            backend.loadGraphData("campus.dot");
        } catch(IOException e) {
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        List<String> actualPath = Arrays.asList("Union South", "Computer Sciences and Statistics", "Meiklejohn House", "Chemistry Building");

        List<String> testPath = backend.findShortestPath("Union South", "Chemistry Building");
        Assertions.assertTrue(testPath.equals(actualPath));
    }

    */
/**
     * Test the getTravelTimesOnPath() method
     *//*

    @Test
    public void test3() {
        GraphADT<String, Double> testGraph = new DijkstraGraph();
        BackendInterface backend = new Backend(testGraph);

        try {
            backend.loadGraphData("campus.dot");
        } catch(IOException e) {
            System.out.println(e.getMessage());
            Assertions.fail();
        }

        List<Double> actualTimes = Arrays.asList(176.0, 164.20000000000002, 128.9);

        List<Double> testTimes =  backend.getTravelTimesOnPath("Union South", "Chemistry Building");
        Assertions.assertTrue(testTimes.equals(actualTimes));
    }

    */
/**
     * Tests the getMostDistantLocation() method
     *//*

    @Test
    public void test4() {
        GraphADT<String, Double> testGraph = new DijkstraGraph();
        BackendInterface backend = new Backend(testGraph);

        try {
            backend.loadGraphData("campus.dot");
        } catch(IOException e) {
            System.out.println(e.getMessage());
            Assertions.fail();
        }

	String actualLocation = "Smith Residence Hall";
        String testLocation = backend.getMostDistantLocation("Union South");

	Assertions.assertTrue(testLocation.equals(actualLocation));
    }

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
     * checkbox returns what we would expect with working backend
     *//*

    @Test
    public void testIntegration1() {
        Label resultsLabel = lookup("#resultsLabel").query();
        clickOn("#srcTextBox");
        write("Union South");
        clickOn("#dstTextBox");
        write("Chemistry Building");
        clickOn("#searchButton");

        Assertions.assertTrue(resultsLabel.getText().contains(("Results List:")));
        Assertions.assertTrue(resultsLabel.getText().contains(("Union South")));
        Assertions.assertTrue(resultsLabel.getText().contains(("Computer Sciences and Statistics")));
        Assertions.assertTrue(resultsLabel.getText().contains(("Meiklejohn House")));
        Assertions.assertTrue(resultsLabel.getText().contains(("Chemistry Building")));
    }

    */
/**
     * Tests the furthest from labels, button and output to make 
     * sure it is what we expect for certain inputs with working backend
     *//*

    @Test
    public void testIntegration2() {
        Label furthestFrom = lookup("#furthestFromLabel").query();
        clickOn("#locTextBox");
        write("Union South");
        clickOn("#furthestFromButton");

        Assertions.assertTrue(furthestFrom.getText().contains("Smith Residence Hall"));
        Assertions.assertTrue(furthestFrom.getText().contains("Approximate Total Time: 29min 53sec"));

    }

}
*/
