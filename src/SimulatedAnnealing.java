// import java.awt.Color;
// import java.awt.geom.Point2D;
// import java.io.BufferedReader;
// import java.io.File;
// import java.io.FileNotFoundException;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.Random;
// import java.util.Scanner;

// import org.knowm.xchart.BitmapEncoder;
// import org.knowm.xchart.SwingWrapper;
// import org.knowm.xchart.XYChart;
// import org.knowm.xchart.XYChartBuilder;
// import org.knowm.xchart.XYSeries;
// import org.knowm.xchart.style.markers.SeriesMarkers;

// public class SimulatedAnnealing {

//     private static final double INITIAL_TEMPERATURE = 10000;
//     private static final double FINAL_TEMPERATURE = 1;
//     private static final double COOLING_FACTOR = 0.000001;
//     private static final int ITERATIONS_PER_TEMPERATURE = 107;

//     private static Random random;

//     public SimulatedAnnealing() {
//         random = new Random();
//     }
    
//     public static ArrayList<Point2D> loadTSPLib(String fName) {
//         ArrayList<Point2D> result = new ArrayList<>();
//         BufferedReader br = null;   
//         try {
//             String currentLine;
//             int dimension = 0;
//             boolean readingNodes = false;
//             br = new BufferedReader(new FileReader(fName));
//             while ((currentLine = br.readLine()) != null) {
//                 if (currentLine.contains("EOF")) {
//                     readingNodes = false;
//                     if (result.size() != dimension) {
//                         System.out.println("Error loading cities");
//                         System.exit(-1);
//                     }
//                 }
//                 if (readingNodes) {
//                     String[] tokens = currentLine.split(" ");
//                     float x = Float.parseFloat(tokens[1].trim());
//                     float y = Float.parseFloat(tokens[2].trim());
//                     Point2D city = new Point2D.Float(x, y);
//                     result.add(city);
//                 }
//                 if (currentLine.contains("DIMENSION")) {
//                     String[] tokens = currentLine.split(":");
//                     dimension = Integer.parseInt(tokens[1].trim());
//                 }
//                 if (currentLine.contains("NODE_COORD_SECTION")) {
//                     readingNodes = true;
//                 }
//             }
//         } catch (IOException e) {
//             e.printStackTrace();
//         } finally {
//             try {
//                 if (br != null) br.close();
//             } catch (IOException ex) {
//                 ex.printStackTrace();
//             }
//         }
//         return result;
//     }
    
//     public static ArrayList<Point2D> findBestRoute(ArrayList<Point2D> cities) {
//         // Initialize with a random order
//         ArrayList<Point2D> currentRoute = new ArrayList<Point2D>(cities);
//          Collections.shuffle(currentRoute, random);
//          double currentDistance = calculateDistance(currentRoute);

//         // Perform simulated annealing
//         double temperature = INITIAL_TEMPERATURE;
//         while (temperature > FINAL_TEMPERATURE) {
//             for (int i = 0; i < ITERATIONS_PER_TEMPERATURE; i++) {
//                 // Generate a new neighbor route by swapping two random points
//                 // ArrayList<Point> neighborRoute = new ArrayList<Point>(currentRoute);
//                 ArrayList<Point2D> neighborRoute = new ArrayList<Point2D>(currentRoute);
//                 int index1 = random.nextInt(cities.size());
//                 int index2 = random.nextInt(cities.size());
//                 Collections.swap(neighborRoute, index1, index2);
//                 double neighborDistance = calculateDistance(neighborRoute);

//                 // Accept the new route if it's better or with a certain probability if it's worse
//                 double delta = neighborDistance - currentDistance;
//                 if (delta < 0 || Math.exp(-delta / temperature) > random.nextDouble()) {
//                     currentRoute = neighborRoute;
//                     currentDistance = neighborDistance;
//                 }
//             }
//             temperature *= COOLING_FACTOR;
//         }
//         return currentRoute;
//      }

//      public static void main(String[] args) {
//         ArrayList<Point2D> cities = loadTSPLib("src/TSP_107.txt"); //load a TSP from file.

//         ArrayList<Point2D> bestRoute = findBestRoute(cities);
//         double bestDistance = calculateDistance(bestRoute);
//         System.out.println("Simulated Annealing Distance: " + bestDistance);
//         System.out.println("Best Route: " + bestRoute);
    
//         // Create and customize the chart
//         XYChart chart = new XYChartBuilder()
//         .width(800)
//         .height(600)
//         .title("TSP Cities")
//         .xAxisTitle("X")
//         .yAxisTitle("Y")
//         .build();

//         // Add a new series to the chart
//         double[] xData = new double[bestRoute.size()];
//         double[] yData = new double[bestRoute.size()];
//         for (int i = 0; i < bestRoute.size(); i++) {
//             xData[i] = bestRoute.get(i).getX();
//             yData[i] = bestRoute.get(i).getY();
//         }
//         // Series to make the chart
//         XYSeries citySeries = chart.addSeries("Cities", xData, yData);

//         // Initializing the color of the cities
//         citySeries.setMarker(SeriesMarkers.CIRCLE);
//         citySeries.setMarkerColor(Color.red);

//         // Series to make the path to connect the cities
//         XYSeries pathSeries = chart.addSeries("Path", xData, yData);

//         // Colours set for the path connecting cities
//         pathSeries.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
//         pathSeries.setLineColor(Color.black);

//         // Show the chart
//         new SwingWrapper<>(chart).displayChart();

//         // Save the chart to a file
//         try {
//         BitmapEncoder.saveBitmap(chart, "SA.png", BitmapEncoder.BitmapFormat.PNG);
//         System.out.println("Chart saved to SA.png");
//         } catch (IOException e) {
//         e.printStackTrace();
//      }
// }


//     double calculateDistance(ArrayList<Point2D> route) {
//         double distance = 0;
//         for (int i = 0; i < route.size() - 1; i++) {
//             distance += route.get(i).distance(route.get(i + 1));
//         }
//         distance += route.get(route.size() - 1).distance(route.get(0));
//         return distance;
//     }

// }
import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class SimulatedAnnealing {

    // Read the city locations from a file with the specified structure
    public static List<Point2D> readCities(String fileName) throws IOException {
    //     List<Point2D> cities = new ArrayList<>();
    //     BufferedReader reader = new BufferedReader(new FileReader(fileName));
    //     String line;
    //     boolean readingCities = false;
    //     while ((line = reader.readLine()) != null) {
    //         if (line.startsWith("NODE_COORD_SECTION")) {
    //             readingCities = true;
    //         } else if (readingCities) {
    //             String[] tokens = line.split("\\s+");
    //             int id = Integer.parseInt(tokens[0]);
    //             double x = Double.parseDouble(tokens[1]);
    //             double y = Double.parseDouble(tokens[2]);
    //             cities.add(new Point2D.Double(x, y));
    //         }
    //     }
    //     reader.close();
    //     return cities;
    // }
        ArrayList<Point2D> result = new ArrayList<>();
        BufferedReader br = null;   
        try {
            String currentLine;
            int dimension = 0;
            boolean readingNodes = false;
            br = new BufferedReader(new FileReader(fileName));
            while ((currentLine = br.readLine()) != null) {
                if (currentLine.contains("EOF")) {
                    readingNodes = false;
                    if (result.size() != dimension) {
                        System.out.println("Error loading cities");
                        System.exit(-1);
                    }
                }
                if (readingNodes) {
                    String[] tokens = currentLine.split(" ");
                    float x = Float.parseFloat(tokens[1].trim());
                    float y = Float.parseFloat(tokens[2].trim());
                    Point2D city = new Point2D.Float(x, y);
                    result.add(city);
                }
                if (currentLine.contains("DIMENSION")) {
                    String[] tokens = currentLine.split(":");
                    dimension = Integer.parseInt(tokens[1].trim());
                }
                if (currentLine.contains("NODE_COORD_SECTION")) {
                    readingNodes = true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    // Compute the total distance of a tour
    public static double tourDistance(List<Point2D> tour) {
        double distance = 0.0;
        for (int i = 0; i < tour.size() - 1; i++) {
            distance += tour.get(i).distance(tour.get(i + 1));
        }
        distance += tour.get(tour.size() - 1).distance(tour.get(0));
        return distance;
    }

    // Swap two cities in a tour
    public static void swapCities(List<Point2D> tour, int i, int j) {
        Point2D temp = tour.get(i);
        tour.set(i, tour.get(j));
        tour.set(j, temp);
    }

    // Simulated Annealing algorithm
    public static List<Point2D> simulatedAnnealing(List<Point2D> initialTour, double initialTemperature,
            double coolingRate, int iterations) {
        List<Point2D> currentTour = new ArrayList<>(initialTour);
        List<Point2D> bestTour = new ArrayList<>(initialTour);
        double currentEnergy = tourDistance(currentTour);
        double bestEnergy = currentEnergy;
        Random random = new Random();

        for (int i = 0; i < iterations; i++) {
            // Choose two random cities to swap
            int j = random.nextInt(currentTour.size());
            int k = random.nextInt(currentTour.size());

            // Swap the cities
            swapCities(currentTour, j, k);

            // Compute the new energy and decide whether to accept the new tour
            double newEnergy = tourDistance(currentTour);
            if (newEnergy < currentEnergy) {
                currentEnergy = newEnergy;
                if (newEnergy < bestEnergy) {
                    bestTour = new ArrayList<>(currentTour);
                    bestEnergy = newEnergy;
                }
            } else if (Math.exp((currentEnergy - newEnergy) / initialTemperature) < random.nextDouble()) {
                // Accept the new tour with a probability proportional to the temperature
                currentEnergy = newEnergy;
            } else {
                // Revert the swap
                swapCities(currentTour, j, k);
            }

            // Cool the temperature
            initialTemperature *= coolingRate;
        }

        return bestTour;
    }

    public static void main(String[] args) {
        try {
            // Read the city locations
            List<Point2D> cities = readCities("src/TSP_107.txt");

            // Generate an initial tour by shuffling the cities
            List<Point2D> initialTour = new ArrayList<>(cities);
            Collections.shuffle(initialTour);

            // Run the simulated annealing algorithm to optimize the tour
            double initialTemperature = 100.0;
            double coolingRate = 0.003;
            int iterations = 100000;
            List<Point2D> optimizedTour = simulatedAnnealing(initialTour, initialTemperature, coolingRate, iterations);

            // Print the optimized tour
            System.out.println("Optimized Tour:" + optimizedTour);

            double[] xData = new double[optimizedTour.size()];
            double[] yData = new double[optimizedTour.size()];

            for (int i = 0; i < optimizedTour.size(); i++) {
                xData[i] = optimizedTour.get(i).getX();
                yData[i] = optimizedTour.get(i).getY();
            }
            
            // Print the optimized tour
            System.out.println("Optimized Tour:");
            for (int i = 0; i < optimizedTour.size(); i++) {
                System.out.printf("%d: (%f, %f)\n", i + 1, xData[i], yData[i]);
            }
            System.out.println("Total Distance: " + tourDistance(optimizedTour));

            XYChart chart = new XYChartBuilder()
            .width(800)
            .height(600)
            .title("SA")
            .xAxisTitle("X")
            .yAxisTitle("Y")
            .build();
    
            // Series to make the chart
            XYSeries citySeries = chart.addSeries("Cities", xData, yData);
    
            // Initializing the color of the cities
            citySeries.setMarker(SeriesMarkers.CIRCLE);
            citySeries.setMarkerColor(Color.red);
    
            // Series to make the path to connect the cities
            XYSeries pathSeries = chart.addSeries("Path", xData, yData);
    
            // Colours set for the path connecting cities
            pathSeries.setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);
            pathSeries.setLineColor(Color.black);
    
           // Show the chart
           new SwingWrapper<>(chart).displayChart();
            // Save the chart to a file
            try {
                BitmapEncoder.saveBitmap(chart, "SA.png", BitmapEncoder.BitmapFormat.PNG);
                System.out.println("Chart saved to SA.png");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
