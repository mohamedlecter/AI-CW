

// import java.io.FileNotFoundException;
// import java.util.ArrayList;

// public class Main {

//     public static void main(String[] args) {
//         SimulatedAnnealing sa = new SimulatedAnnealing();
//         try {
//             sa.readDatasetFromFile("src/TSP_107.txt");
//         } catch (FileNotFoundException e) {
//             e.printStackTrace();
//             return;
//         }

//         ArrayList<SimulatedAnnealing.Point> bestRoute = sa.findBestRoute();
//         // System.out.println("Best route: " + bestRoute.toString());

//         for (SimulatedAnnealing.Point point : bestRoute) {
//             System.out.println(point.id);
//         }

//         System.out.println("Simulated Annealing Distance: " + sa.calculateDistance(bestRoute));
//         TwoOpt.main(args);

//     }

// }

