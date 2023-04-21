import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SA {

    public static class SimulatedAnnealing {

        private SingleTour best;

        public void simulation() {
            double temperature = 10000;
            double coolingRate = 0.000001;


            SingleTour currentSolution = new SingleTour();
            currentSolution.generateTour();

            System.out.println("Original distance: " + currentSolution.getDistance());

            best = new SingleTour(currentSolution.getTour());

            while (temperature > 1) {
                SingleTour newSolution = new SingleTour(currentSolution.getTour());

                //pick two cities and swap them as next solution
                int randomIndex1 = (int) (currentSolution.getTourSize() * Math.random());
                City city1 = newSolution.getCity(randomIndex1);
                int randomIndex2 = (int) (currentSolution.getTourSize() * Math.random());
                City city2 = newSolution.getCity(randomIndex2);
                newSolution.setCity(randomIndex2, city1);
                newSolution.setCity(randomIndex1, city2);

                double currentEnergy = currentSolution.getDistance();
                double neighbourEnergy = newSolution.getDistance();

                if (acceptanceProbability(currentEnergy, neighbourEnergy, temperature) > Math.random()) {
                    currentSolution = new SingleTour(newSolution.getTour());
                }

                if (currentSolution.getDistance() < best.getDistance()) {
                    best = new SingleTour(currentSolution.getTour());
                }
                temperature *= 1 - coolingRate;
            }

        }

        public SingleTour getbestSolution() {
            return best;
        }

        private double acceptanceProbability(double currentEnergy, double neighbourEnergy, double temperature) {
            if (neighbourEnergy < currentEnergy) {
                return 1;
            }

            return Math.exp((currentEnergy - neighbourEnergy) / temperature);
        }

    }
}