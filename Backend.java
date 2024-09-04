import java.util.List;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.NoSuchElementException;

import java.io.File;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Backend implements BackendInterface {
    private GraphADT<String, Double> graph;
    private List<String> locations = new LinkedList<>();

    public Backend(GraphADT<String, Double> graph) {
        this.graph = graph;
    }

    /**
     * Loads graph data from a dot file.
     *
     * @param filename the path to a dot file to read graph data from
     * @throws IOException if there was a problem reading in the specified file
     */
    @Override
    public void loadGraphData(String filename) throws IOException {
        File file;
        try {
            file = new File(filename);
        } catch(NullPointerException e) {
            throw new IOException("File not found");
        }

        Scanner s = new Scanner(file);

        while(s.hasNextLine()) {
            String line = s.nextLine();
            // Skips a line that isn't indented (So the first and last line)
            if (line.charAt(0) != '\t')
                continue;

            // Parses through each line by splitting it by " and using the matcher class to parse the weight
            String[] partition = line.split("\"");
            String pred = partition[1];
            String succ = partition[3];

            String label = partition[4];
            Pattern p = Pattern.compile("[\\d\\.]+");
            Matcher m = p.matcher(label);

            m.find();
            double weight = Double.parseDouble(label.substring(m.start(), m.end()));

            if (!locations.contains(pred))
                locations.add(pred);
            if (!locations.contains(succ))
                locations.add(succ);

            graph.insertNode(pred);
            graph.insertNode(succ);
            graph.insertEdge(pred, succ, weight);
        }
    }

    /**
     * Returns a list of all locations (nodes) available on the backend's graph.
     *
     * @return list of all location names
     */
    @Override
    public List<String> getListOfAllLocations() {
        return locations;
    }

    /**
     * Return the sequence of locations along the shortest path from startLocation to endLocation, or
     * en empty list if no such path exists.
     *
     * @param startLocation the start location of the path
     * @param endLocation   the end location of the path
     * @return a list with the nodes along the shortest path from startLocation to endLocation, or
     * an empty list if no such path exists
     */
    @Override
    public List<String> findShortestPath(String startLocation, String endLocation) {
        if (!graph.containsNode(startLocation) || !graph.containsNode(endLocation) || startLocation == endLocation)
            return new LinkedList<>();

        return graph.shortestPathData(startLocation, endLocation);
    }

    /**
     * Return the walking times in seconds between each two nodes on the shortest path from startLocation
     * to endLocation, or an empty list of no such path exists.
     *
     * @param startLocation the start location of the path
     * @param endLocation   the end location of the path
     * @return a list with the walking times in seconds between two nodes along the shortest path from
     * startLocation to endLocation, or an empty list if no such path exists
     */
    @Override
    public List<Double> getTravelTimesOnPath(String startLocation, String endLocation) {
        if (!graph.containsNode(startLocation) || !graph.containsNode(endLocation) || startLocation == endLocation)
            return new LinkedList<>();

        List<String> path = graph.shortestPathData(startLocation, endLocation);
        List<Double> pathTimes = new LinkedList<>();

        for(int i = 0; i < path.size() - 1; i++) {
            pathTimes.add(graph.shortestPathCost(path.get(i), path.get(i+1)));
        }

        return pathTimes;
    }

    /**
     * Return the most distant location from startLocation that is reachable in the graph.
     *
     * @param startLocation the location to find the most distant location for
     * @return the location that is most distant (has the longest overall walking time)
     * @throws NoSuchElementException if startLocation does not exist
     */
    @Override
    public String getMostDistantLocation(String startLocation) throws NoSuchElementException {
        if (!graph.containsNode(startLocation))
            throw new NoSuchElementException("Start location is not in the graph");

       String mostDistantLocation = null;
       double maxCost = Integer.MIN_VALUE;

        for(String each : locations) {
            double eachCost;

            try {
                eachCost = graph.shortestPathCost(startLocation, each);
            } catch (NoSuchElementException e) {
                continue;
            }

            if (eachCost > maxCost) {
                maxCost = eachCost;
                mostDistantLocation = each;
            }
        }

        return mostDistantLocation;
    }
}
