import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
public class Main {

    public static void main(String[] args) {

        Map<Integer, City> cities = null;

        try {
            //Read the cities from the file
            cities = CityReader.readCitiesFromFile("src/TSP_107.txt");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
;
        SA.SimulatedAnnealing simulatedAnnealing = new SA.SimulatedAnnealing();
        simulatedAnnealing.simulation();

        System.out.println("Distance: " + simulatedAnnealing.getbestSolution().getDistance());
        System.out.println("Tour: " + simulatedAnnealing.getbestSolution());

    }

    // Calculate the total distance of a tour
    public static double calculateTourDistance(List<City> tour) {
        double distance = 0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += tour.get(i).distanceTo(tour.get(i + 1));
        }
        // Add the distance back to the starting city to complete the tour
        distance += tour.get(tour.size() - 1).distanceTo(tour.get(0));
        return distance;
    }

    public static List<City> generateRandomTour(Map<Integer, City> cities) {
        // Create a list of all the city IDs
        List<Integer> cityIds = new ArrayList<>(cities.keySet());

        // Shuffle the list of city IDs to generate a random tour
        Collections.shuffle(cityIds);

        // Create a new list to hold the tour
        List<City> tour = new ArrayList<>();

        // Add each city in the shuffled order to the tour
        for (Integer cityId : cityIds) {
            tour.add(cities.get(cityId));
        }

        // Return the random tour
        return tour;
    }
}
