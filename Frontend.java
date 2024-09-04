import java.util.List;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Frontend extends Application implements FrontendInterface {

    //static variable to access backend object
    private static Backend back;
    //static variable that holds a list of every location on the graph
    private static List<String> allLocations;
    //variable that holds results list
    private static List<String> results;
    //variable that holds list of walking times corresponding
    //to results list
    private static List<Double> walkingTimes;
    //variable that keeps track if the checkbox is selected or not
    private static boolean checkBoxSelected;
    //Saving my results label object as a static method so that I can
    //adjust it each time it is called instead of it overwriting itself
    private static Label resultsLabel;

    /**
     * Constructor to initialize backend
     *
     * @param back
     */
    public static void setBackend(Backend back) {
        Frontend.back = back;
        //also set up allLocations instance var and resultsLabel
        try {
            back.loadGraphData("campus.dot");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        Frontend.allLocations = Frontend.back.getListOfAllLocations();
    }


    /**
     * @param stage
     */
    @Override
    public void start(Stage stage) {
        Pane root = new Pane();

        createAllControls(root);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Madison Area Walking Paths Application");
        stage.show();
    }


    /**
     * Creates all controls in the GUI.
     *
     * @param parent the parent pane that contains all controls
     */
    @Override
    public void createAllControls(Pane parent) {
        createShortestPathControls(parent);
        createPathListDisplay(parent);
        createAdditionalFeatureControls(parent);
        createAboutAndQuitControls(parent);
    }

    /**
     * Creates the controls for the shortest path search.
     *
     * @param parent the parent pane that contains all controls
     */
    @Override
    public void createShortestPathControls(Pane parent) {
        //create start selector label
        Label srcLabel = new Label("Your Starting Location: ");
        srcLabel.setLayoutX(32);
        srcLabel.setLayoutY(16);
        srcLabel.setId("srcLabel");
        parent.getChildren().add(srcLabel);

        //create start selector textbox for user to enter into
        TextField srcTextBox = new TextField();
        srcTextBox.setLayoutX(200);
        srcTextBox.setLayoutY(16);
        parent.getChildren().add(srcTextBox);
        srcTextBox.setId("srcTextBox");

        //create end selector label
        Label dstLabel = new Label("Your End Location: ");
        dstLabel.setLayoutX(32);
        dstLabel.setLayoutY(48);
        dstLabel.setId("dstLabel");
        parent.getChildren().add(dstLabel);

        //create end selector textbox for user to enter into
        TextField dstTextBox = new TextField();
        dstTextBox.setLayoutX(200);
        dstTextBox.setLayoutY(48);
        parent.getChildren().add(dstTextBox);
        dstTextBox.setId("dstTextBox");

        Button searchButton = new Button("Search For Shortest Path");
        searchButton.setLayoutX(32);
        searchButton.setLayoutY(80);
        parent.getChildren().add(searchButton);
        searchButton.setId("searchButton");

        //initialize our resultsLabel before it is ever needed
        resultsLabel = new Label();
        resultsLabel.setId("resultsLabel");

        //update all the positions and add to be displayed
        resultsLabel.setLayoutX(32);
        resultsLabel.setLayoutY(112);
        parent.getChildren().add(resultsLabel);

        //Now we need to add the EventHandlers to each

        //when the search button is clicked we need to generate the results list,
        //use what is in the text boxes. Then save to static variables and call createPathListDisplay
        //method, that is where it will get nicely formatted and displayed
        searchButton.addEventHandler(ActionEvent.ACTION, (event) -> {
            //Need to check that text in src and dst are actual nodes, if they are not should
            //change above text box to ERROR location isn't a option, try again
            if (Frontend.allLocations.contains(srcTextBox.getText()) && Frontend.allLocations.contains(dstTextBox.getText())) {
                srcLabel.setText("Your Starting Location: ");
                dstLabel.setText("Your End Location: ");

                //use lambda expression to easily set static vars
                Frontend.results = back.findShortestPath(srcTextBox.getText(), dstTextBox.getText());
                Frontend.walkingTimes = back.getTravelTimesOnPath(srcTextBox.getText(), dstTextBox.getText());

                //now call createPathListDisplay method to update the display now when searchButton is pressed
                createPathListDisplay(parent);
            } else {
                if (!Frontend.allLocations.contains(srcTextBox.getText())) {
                    srcLabel.setText("Your Starting Location:\nERROR: LOCATION DNE");
                } else dstLabel.setText("Your End Location:\nERROR: LOCATION DNE");
            }
        });

    }

    /**
     * Creates the controls for displaying the shortest path returned by the search.
     *
     * @param parent the parent pane that contains all controls
     */
    @Override
    public void createPathListDisplay(Pane parent) {
        //this method is called whenever the search button is clicked
        //need to format and update the results list labels with the new info

        //here is where we will format the string and then update our resultsLabel
        //first check if our results list is empty, either no path exists or they haven't tried to find
        //any path yet
        if (Frontend.results == null || Frontend.results.isEmpty()) {
            //If checkBox is selected then we should include the walking times results
            if (Frontend.checkBoxSelected) {
                String buildingString = "";
                buildingString = "Results List (with walking times):\nNo Path Between locations Selected\n(Or you haven't selected a location yet)\n\n";
                resultsLabel.setText(buildingString);
            } else {
                String buildingString = "";
                buildingString = "Results List:\nNo Path Between locations Selected\n(Or you haven't selected a location yet)\n\n";
                resultsLabel.setText(buildingString);
            }
        }
        //here is where we formatt the actual string using results list
        else {
            if (!Frontend.checkBoxSelected) {
                String buildingString = "";
                buildingString = "Results List:\n";

                //First build the results list string
                for (String location : Frontend.results) {
                    buildingString += "\t" + location + "\n";
                }
                resultsLabel.setText(buildingString);
            }
            //Now build the results list with walking times if the checkbox is clicked
            else {
                String buildingString = "";
                buildingString += "Results List (with walking times):\n";

                //this loop should build a nice output list with walking times
                for (int i = 0; i < Frontend.results.size() - 1; i++) {
                    buildingString += "\t" + results.get(i) + " (" + walkingTimes.get(i) + ")--> " + results.get(i + 1) + "\n";
                }
                //now add a total time to the bottom
                double totalSec = 0;
                for (double val : walkingTimes) {
                    totalSec += val;
                }
                int seconds = (int) totalSec % 60;
                int minutes = (int) (totalSec / 60) % 60;
                buildingString += "\tApproximate Total Time: " + minutes + "min " + seconds + "sec";
                resultsLabel.setText(buildingString);
            }

        }

    }

    /**
     * Creates controls for the two features in addition to the shortest path search.
     *
     * @param parent parent pane that contains all controls
     */
    @Override
    public void createAdditionalFeatureControls(Pane parent) {
        this.createTravelTimesBox(parent);
        this.createFurthestDestinationControls(parent);

    }

    /**
     * Creates the checkbox to add travel times in the result display.
     *
     * @param parent parent pane that contains all controls
     */
    @Override
    public void createTravelTimesBox(Pane parent) {

        //Need to create checkbox here with label
        CheckBox walkingTimesBox = new CheckBox();
        walkingTimesBox.setId("walkingTimesBox");

        Label checkBoxLabel = new Label();
        checkBoxLabel.setText("Include Walking Times");


        //Set up eventHandler for CheckBox, update private static var
        walkingTimesBox.addEventHandler(MouseEvent.MOUSE_CLICKED, (event) -> {
            if (walkingTimesBox.isSelected()) {
                Frontend.checkBoxSelected = true;
            } else Frontend.checkBoxSelected = false;

        });

        //update checkbox and label positions
        walkingTimesBox.setLayoutX(240);
        walkingTimesBox.setLayoutY(80);
        checkBoxLabel.setLayoutX(260);
        checkBoxLabel.setLayoutY(80);
        parent.getChildren().add(walkingTimesBox);
        parent.getChildren().add(checkBoxLabel);
    }

    /**
     * Creates controls to search for a maximally distant location.
     *
     * @param parent parent pane that contains all controls
     */
    @Override
    public void createFurthestDestinationControls(Pane parent) {

        //Create label 
        Label locationSelector = new Label("Location Selector: ");
        locationSelector.setLayoutX(480);
        locationSelector.setLayoutY(16);
        parent.getChildren().add(locationSelector);

        //create textBox for user to type starting location into
        TextField locTextBox = new TextField();
        locTextBox.setLayoutX(600);
        locTextBox.setLayoutY(16);
        parent.getChildren().add(locTextBox);
        locTextBox.setId("locTextBox");

        //create furthest location button
        Button furthestFromButton = new Button("Find Furthest Location");
        furthestFromButton.setLayoutX(480);
        furthestFromButton.setLayoutY(48);
        parent.getChildren().add(furthestFromButton);
        furthestFromButton.setId("furthestFromButton");

        //This is label that will get updated when button is clicked
        Label furthestFromLabel = new Label("Most Distance Location: ");
        furthestFromLabel.setLayoutX(480);
        furthestFromLabel.setLayoutY(80);
        parent.getChildren().add(furthestFromLabel);
        furthestFromLabel.setId("furthestFromLabel");


        furthestFromButton.addEventHandler(ActionEvent.ACTION, (event) -> {
            //first check that the text in the text box is a valid location
            if (Frontend.allLocations.contains(locTextBox.getText())) {
                //if the location does exists call getMostDistantLocation with it and add it to the output label
                String buildingString = "";

                buildingString += "Most Distant Location: " + Frontend.back.getMostDistantLocation(locTextBox.getText());

                //Here I am adding a feature so the total time walked will also appear
                Frontend.back.findShortestPath(locTextBox.getText(), Frontend.back.getMostDistantLocation(locTextBox.getText()));
                List<Double> listOfTimes = Frontend.back.getTravelTimesOnPath(locTextBox.getText(), Frontend.back.getMostDistantLocation(locTextBox.getText()));

                //Need to get total seconds of travel then convert to min and sec
                double totalSec = 0;
                for (double var : listOfTimes) {
                    totalSec += var;
                }

                int seconds = (int) totalSec % 60;
                int minutes = (int) (totalSec / 60) % 60;
                buildingString += "\nApproximate Total Time: " + minutes + "min " + seconds + "sec";

                furthestFromLabel.setText(buildingString);

            } else {
                locTextBox.setText("Furthest Location Selector:\nERROR LOCATION DOES NOT EXIST, try again");
            }
        });


    }

    /**
     * Creates an about and quit button.
     *
     * @param parent parent pane that contains all controls
     */
    @Override
    public void createAboutAndQuitControls(Pane parent) {

        Button about = new Button("About");
        about.setLayoutX(32);
        about.setLayoutY(560);
        parent.getChildren().add(about);
        about.setId("about");

        Button quit = new Button("Quit");
        quit.setLayoutX(96);
        quit.setLayoutY(560);
        parent.getChildren().add(quit);
        quit.setId("quit");

        quit.addEventHandler(ActionEvent.ACTION, (event) -> Platform.exit());

        //About button is a little more difficult, lets just add a text box in the bottom 
        //right whenever it is clicked
        Label aboutLabel = new Label();
        about.addEventHandler(ActionEvent.ACTION, (event) -> {
            String aboutString = """
                    --------------------------------ABOUT--------------------------------
                    This application can help you find paths between
                    specific locations and also includes a few more
                    helpful features. Type in start and end locations
                    (places near UW's campus) to generate the shortest
                    path along the path of other known buildings.
                    Toggle 'show walking times' to include the time it
                    would take to walk your path. You can also pick a
                    location on UW-Campus and find the furthest known
                    location to that starting position
                    That is it for this application, enjoy! 
                    """;

            aboutLabel.setText(aboutString);
        });
        aboutLabel.setLayoutX(400);
        aboutLabel.setLayoutY(300);
        parent.getChildren().add(aboutLabel);
        aboutLabel.setId("aboutLabel");
    }

}
