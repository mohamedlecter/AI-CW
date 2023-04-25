import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class GeneticAlgorithm {

    private static final int POPULATION_SIZE = 100;
    private static final int NUM_GENERATIONS = 100;
    private static final double MUTATION_RATE = 0.1;
    static int [][] distanceMatrix;

    public static void main(String[] args) throws IOException {
        // Read input from file
        int[][] distanceMatrix = readInputFromFile("src/TSP_107.txt");

        // Initialize population
        Population population = new Population(distanceMatrix, POPULATION_SIZE);

        // Evolve population
        for (int i = 0; i < NUM_GENERATIONS; i++) {
            population.evolve();
        }

        // Get best tour
        Chromosome bestTour = population.getBestTour();

        // Print best tour and fitness
        System.out.println("Best Tour: " + bestTour);
        System.out.println("Fitness: " + bestTour.getFitness());
    }

    private static int[][] readInputFromFile(String filename) throws IOException {
        ArrayList<int[]> cityData = new ArrayList<>();
        Scanner scanner = new Scanner(new File(filename));
        while (scanner.hasNext()) {
            int id = scanner.nextInt();
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            cityData.add(new int[]{id, x, y});
        }
        scanner.close();
        int numCities = cityData.size();
        int[][] distanceMatrix = new int[numCities][numCities];

        // Calculate distance between cities using Euclidean distance
        for (int i = 0; i < numCities; i++) {
            int[] city1 = cityData.get(i);
            for (int j = 0; j < numCities; j++) {
                int[] city2 = cityData.get(j);
                int xDiff = city1[1] - city2[1];
                int yDiff = city1[2] - city2[2];
                int distance = (int) Math.sqrt(xDiff * xDiff + yDiff * yDiff);
                distanceMatrix[i][j] = distance;
            }
        }

        return distanceMatrix;
    }


    static class Population {
        private ArrayList<Chromosome> chromosomes;

        public Population(int[][] distanceMatrix, int populationSize) {
            chromosomes = new ArrayList<>();
            for (int i = 0; i < populationSize; i++) {
                chromosomes.add(new Chromosome(distanceMatrix.length));
            }
            calculateFitness(distanceMatrix);
            sortChromosomes();
        }

        public void evolve() {
            ArrayList<Chromosome> newGeneration = new ArrayList<>();

            // Elitism - keep the best chromosome
            newGeneration.add(chromosomes.get(0));

            // Crossover
            for (int i = 0; i < chromosomes.size() / 2 - 1; i++) {
                Chromosome parent1 = selectParent();
                Chromosome parent2 = selectParent();
                Chromosome child1 = parent1.crossover(parent2);
                Chromosome child2 = parent2.crossover(parent1);
                newGeneration.add(child1);
                newGeneration.add(child2);
            }

            // Mutate
            for (Chromosome chromosome : newGeneration) {
                chromosome.mutate(MUTATION_RATE);
            }
            // Calculate fitness for new generation
            calculateFitness(distanceMatrix);
            // Sort chromosomes
            sortChromosomes();

            // Replace old generation with new generation
            chromosomes = newGeneration;
        }

        public Chromosome getBestTour() {
            return chromosomes.get(0);
        }

        private Chromosome selectParent() {
            // Tournament selection
            int tournamentSize = 5;
            ArrayList<Chromosome> tournament = new ArrayList<>();
            for (int i = 0; i < tournamentSize; i++) {
                int index = (int) (Math.random() * chromosomes.size());
                tournament.add(chromosomes.get(index));
            }
            sortChromosomes(tournament);
            return tournament.get(0);
        }

        private void calculateFitness(int[][] distanceMatrix) {
            for (Chromosome chromosome : chromosomes) {
                chromosome.calculateFitness(distanceMatrix);
            }
        }

        private void sortChromosomes() {
            Collections.sort(chromosomes);
        }

        private void sortChromosomes(ArrayList<Chromosome> list) {
            Collections.sort(list);
        }
    }

    static class Chromosome implements Comparable<Chromosome> {
        private int[] genes;
        private int fitness;

        public Chromosome(int numCities) {
            genes = new int[numCities];
            for (int i = 0; i < numCities; i++) {
                genes[i] = i;
            }
            shuffleGenes();
        }

        public void calculateFitness(int[][] distanceMatrix) {
            int totalDistance = 0;
            for (int i = 0; i < genes.length - 1; i++) {
                int city1 = genes[i];
                int city2 = genes[i + 1];
                totalDistance += distanceMatrix[city1][city2];
            }
            int lastCity = genes[genes.length - 1];
            int firstCity = genes[0];
            totalDistance += distanceMatrix[lastCity][firstCity];
            fitness = totalDistance;
        }

        public Chromosome crossover(Chromosome other) {
            int[] childGenes = new int[genes.length];

            // Copy a random subset of genes from this chromosome
            int startIndex = (int) (Math.random() * genes.length);
            int endIndex = (int) (Math.random() * genes.length);
            if (startIndex > endIndex) {
                int temp = startIndex;
                startIndex = endIndex;
                endIndex = temp;
            }
            for (int i = startIndex; i <= endIndex; i++) {
                childGenes[i] = genes[i];
            }

            // Copy remaining genes from other chromosome
            int index = 0;
            for (int i = 0; i < genes.length; i++) {
                int gene = other.genes[i];
                if (!containsGene(childGenes, gene)) {
                    while (childGenes[index] != 0) {
                        index = (index + 1) % genes.length;
                    }
                    childGenes[index] = gene;
                }
            }

            Chromosome child = new Chromosome(genes.length);
            child.genes = childGenes;
            return child;
        }

        public void mutate(double mutationRate) {
            for (int i = 0; i < genes.length; i++) {
                if (Math.random() < mutationRate) {
                    int j = (int) (Math.random() * genes.length);
                    int temp = genes[i];
                    genes[i] = genes[j];
                    genes[j] = temp;
                }
            }
        }

        public int getFitness() {
            return fitness;
        }

        private void shuffleGenes() {
            for (int i = genes.length - 1; i > 0 ;i--){
                int index = (int) (Math.random() * (i + 1));
                int temp = genes[index];
                genes[index] = genes[i];
                genes[i] = temp;
            }
        }

        private boolean containsGene(int[] genes, int gene) {
            for (int i = 0; i < genes.length; i++) {
                if (genes[i] == gene) {
                    return true;
                }
            }
            return false;
        }

        @Override
        public int compareTo(Chromosome other) {
            return Integer.compare(this.fitness, other.fitness);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int gene : genes) {
                sb.append(gene).append(" ");
            }
            return sb.toString();
        }
    }


//    private static int[][] readInputFile(String filename) {
//        // Read input file and create distance matrix
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(filename));
//            String line;
//            ArrayList<String> cityList = new ArrayList<>();
//            ArrayList<Integer> xList = new ArrayList<>();
//            ArrayList<Integer> yList = new ArrayList<>();
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(" ");
//                cityList.add(parts[0]);
//                xList.add(Integer.parseInt(parts[1]));
//                yList.add(Integer.parseInt(parts[2]));
//            }
//            int numCities = cityList.size();
//            int[][] distanceMatrix = new int[numCities][numCities];
//            for (int i = 0; i < numCities; i++) {
//                for (int j = 0; j < numCities; j++) {
//                    int x1 = xList.get(i);
//                    int y1 = yList.get(i);
//                    int x2 = xList.get(j);
//                    int y2 = yList.get(j);
//                    int distance = (int) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
//                    distanceMatrix[i][j] = distance;
//                }
//            }
//            br.close();
//            return distanceMatrix;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
}