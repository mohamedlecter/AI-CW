

// import java.awt.Color;
// import java.awt.geom.Point2D;
// import java.io.BufferedReader;
// import java.io.FileReader;
// import java.io.IOException;
// import java.util.ArrayList;

// import org.knowm.xchart.BitmapEncoder;
// import org.knowm.xchart.SwingWrapper;
// import org.knowm.xchart.XYChart;
// import org.knowm.xchart.XYChartBuilder;
// import org.knowm.xchart.XYSeries;
// import org.knowm.xchart.style.Styler.ChartTheme;
// import org.knowm.xchart.style.markers.SeriesMarkers;

// public class TwoOpt {

//     public static ArrayList<Point2D> alternate(ArrayList<Point2D> cities) {
//         ArrayList<Point2D> newTour;
//         double bestDist = routeLength(cities);
//         double newDist;
//         int swaps = 1;

//         while (swaps != 0) {
//             swaps = 0;
//             for (int i = 1; i < cities.size() - 2; i++) {
//                 for (int j = i + 1; j < cities.size() - 1; j++) {
//                     if ((cities.get(i).distance(cities.get(i - 1)) + cities.get(j + 1).distance(cities.get(j)))
//                             >= (cities.get(i).distance(cities.get(j + 1)) + cities.get(i - 1).distance(cities.get(j)))) {
//                         newTour = swap(cities, i, j);
//                         newDist = routeLength(newTour);
//                         if (newDist < bestDist) {
//                             cities = newTour;
//                             bestDist = newDist;
//                             swaps++;
//                         }
//                     }
//                 }
//             }
//         }
//         return cities;
//     }

//     private static ArrayList<Point2D> swap(ArrayList<Point2D> cities, int i, int j) {
//         ArrayList<Point2D> newTour = new ArrayList<>();
//         int size = cities.size();
//         for (int c = 0; c <= i - 1; c++) {
//             newTour.add(cities.get(c));
//         }
//         int dec = 0;
//         for (int c = i; c <= j; c++) {
//             newTour.add(cities.get(j - dec));
//             dec++;
//         }
//         for (int c = j + 1; c < size; c++) {
//             newTour.add(cities.get(c));
//         }
//         return newTour;
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


//     public static int routeLength(ArrayList<Point2D> cities) {
//         int result = 0;
//         Point2D prev = cities.get(cities.size() - 1);
//         for (Point2D city : cities) {
//             //Go through each city in turn
//             result += city.distance(prev);
//             //get distance from the previous city
//             prev = city;
//         }
//         return result;
//     }
//     protected static ArrayList<Point2D> nearest(ArrayList<Point2D> cities) {

//         ArrayList<Point2D> result = new ArrayList<>(); //holds final result.
//         Point2D currentCity = cities.remove(0); //set current city to first array item.
//         Point2D closestCity = cities.get(0); //set the closest city to new first array item.
//         Point2D possible; //for holding possible city.
//         double dist; //for holding distance between current city and possible city.
//         result.add(currentCity); //add current city to result array.
//         while (!cities.isEmpty()) { //loop until all cities have been added to result array.
//             dist = Double.MAX_VALUE; //reset dist to a high value for each outer loop.
//             for (int i = 0; i < cities.size(); i++) { //loop through remaining cities to find the closest to the current city.
//                 possible = cities.get(i); //set possible to next city in cities.
//                 if (currentCity.distance(possible) < dist) { //if the distance between the two is less than previous distances.
//                     dist = currentCity.distance(possible); //set new smallest distance.
//                     closestCity = possible; //set closest city to possible.
//                 }
//             }
//             cities.remove(closestCity); //remove the closest city from cities array.
//             result.add(closestCity); //add the closest city to the result array.
//             currentCity = closestCity; //set the current city to the closest city for next iteration.
//         }

//         return result;
//     }

//     public static void main(String[] args) {
//         ArrayList<Point2D> cities = loadTSPLib("src/TSP_107.txt"); //load a TSP from file.
//         // System.out.println("\nOriginal Tour: " + cities.toString()); //print the original tour.
//         ArrayList<Point2D> nearestNeighbor = nearest(cities); //find tour using the nearest neighbor algorithm.
//         // System.out.println("\n Nearest Neighbor Tour: " + nearestNeighbor.toString()); //print the tour.
//         ArrayList<Point2D> twoOpt = alternate(nearestNeighbor); //improve tour using 2-opt.
//         System.out.println("\n 2-Opt Tour: " + twoOpt.toString()); //print the tour.
//         System.out.println("\n Tour Length: " + routeLength(twoOpt)); //print the length of the tour.

//         XYChart chart = new XYChartBuilder()
//         .width(800)
//         .height(600)
//         .title("TSP Cities")
//         .xAxisTitle("X")
//         .yAxisTitle("Y")
//         .build();

//         // Add a new series to the chart
//         double[] xData = new double[twoOpt.size()];
//         double[] yData = new double[twoOpt.size()];
//         for (int i = 0; i < twoOpt.size(); i++) {
//             xData[i] = twoOpt.get(i).getX();
//             yData[i] = twoOpt.get(i).getY();
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

//        // Show the chart
//        new SwingWrapper<>(chart).displayChart();
//         // Save the chart to a file
//         try {
//             BitmapEncoder.saveBitmap(chart, "tsp_cities.png", BitmapEncoder.BitmapFormat.PNG);
//             System.out.println("Chart saved to tsp_cities.png");
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }
// }



import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler.ChartTheme;
import org.knowm.xchart.style.markers.SeriesMarkers;

public class TwoOpt {

    public static ArrayList<Point2D> alternate(ArrayList<Point2D> cities) {
        ArrayList<Point2D> newTour;
        double bestDist = routeLength(cities);
        double newDist;
        int swaps = 1;

        while (swaps != 0) {
            swaps = 0;
            for (int i = 1; i < cities.size() - 2; i++) {
                for (int j = i + 1; j < cities.size() - 1; j++) {
                    if ((cities.get(i).distance(cities.get(i - 1)) + cities.get(j + 1).distance(cities.get(j)))
                            >= (cities.get(i).distance(cities.get(j + 1)) + cities.get(i - 1).distance(cities.get(j)))) {
                        newTour = swap(cities, i, j);
                        newDist = routeLength(newTour);
                        if (newDist < bestDist) {
                            cities = newTour;
                            bestDist = newDist;
                            swaps++;
                        }
                    }
                }
            }
        }
        return cities;
    }

    private static ArrayList<Point2D> swap(ArrayList<Point2D> cities, int i, int j) {
        ArrayList<Point2D> newTour = new ArrayList<>();
        int size = cities.size();
        for (int c = 0; c <= i - 1; c++) {
            newTour.add(cities.get(c));
        }
        int dec = 0;
        for (int c = i; c <= j; c++) {
            newTour.add(cities.get(j - dec));
            dec++;
        }
        for (int c = j + 1; c < size; c++) {
            newTour.add(cities.get(c));
        }
        return newTour;
    }

    public static ArrayList<Point2D> loadTSPLib(String fName) {
        ArrayList<Point2D> result = new ArrayList<>();
        BufferedReader br = null;   
        try {
            String currentLine;
            int dimension = 0;
            boolean readingNodes = false;
            br = new BufferedReader(new FileReader(fName));
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


    public static int routeLength(ArrayList<Point2D> cities) {
        int result = 0;
        Point2D prev = cities.get(cities.size() - 1);
        for (Point2D city : cities) {
            //Go through each city in turn
            result += city.distance(prev);
            //get distance from the previous city
            prev = city;
        }
        return result;
    }
    protected static ArrayList<Point2D> nearest(ArrayList<Point2D> cities) {

        ArrayList<Point2D> result = new ArrayList<>(); //holds final result.
        Point2D currentCity = cities.remove(0); //set current city to first array item.
        Point2D closestCity = cities.get(0); //set the closest city to new first array item.
        Point2D possible; //for holding possible city.
        double dist; //for holding distance between current city and possible city.
        result.add(currentCity); //add current city to result array.
        while (!cities.isEmpty()) { //loop until all cities have been added to result array.
            dist = Double.MAX_VALUE; //reset dist to a high value for each outer loop.
            for (int i = 0; i < cities.size(); i++) { //loop through remaining cities to find the closest to the current city.
                possible = cities.get(i); //set possible to next city in cities.
                if (currentCity.distance(possible) < dist) { //if the distance between the two is less than previous distances.
                    dist = currentCity.distance(possible); //set new smallest distance.
                    closestCity = possible; //set closest city to possible.
                }
            }
            cities.remove(closestCity); //remove the closest city from cities array.
            result.add(closestCity); //add the closest city to the result array.
            currentCity = closestCity; //set the current city to the closest city for next iteration.
        }

        return result;
    }

    public static void main(String[] args) {
        ArrayList<Point2D> cities = loadTSPLib("src/TSP_107.txt"); //load a TSP from file.
        // System.out.println("\nOriginal Tour: " + cities.toString()); //print the original tour.
        ArrayList<Point2D> nearestNeighbor = nearest(cities); //find tour using the nearest neighbor algorithm.
        // System.out.println("\n Nearest Neighbor Tour: " + nearestNeighbor.toString()); //print the tour.
        ArrayList<Point2D> twoOpt = alternate(nearestNeighbor); //improve tour using 2-opt.
        System.out.println("\n 2-Opt Tour: " + twoOpt.toString()); //print the tour.
        System.out.println("\n Tour Length: " + routeLength(twoOpt)); //print the length of the tour.

        XYChart chart = new XYChartBuilder()
        .width(800)
        .height(600)
        .title("TSP Cities")
        .xAxisTitle("X")
        .yAxisTitle("Y")
        .build();

        // Add a new series to the chart
        double[] xData = new double[twoOpt.size()];
        double[] yData = new double[twoOpt.size()];
        for (int i = 0; i < twoOpt.size(); i++) {
            xData[i] = twoOpt.get(i).getX();
            yData[i] = twoOpt.get(i).getY();
        }
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
            BitmapEncoder.saveBitmap(chart, "tsp_cities.png", BitmapEncoder.BitmapFormat.PNG);
            System.out.println("Chart saved to tsp_cities.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


            