import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SingleTour {
    private List<City> tour = new ArrayList<>();
    private double distance = 0;

    public SingleTour() {
        for (int i = 0; i < Repo.getNumOfCities(); ++i) {
            tour.add(null);
        }
    }

    public SingleTour(List<City> tour) {
        List<City> currentTour = new ArrayList<>();

        for (int i = 0; i < tour.size(); ++i) {
            currentTour.add(null);
        }

        for (int i = 0; i < tour.size(); ++i) {
            currentTour.set(i, tour.get(i));
        }
        this.tour = currentTour;
    }


    public double getDistance() {
        if (distance == 0) {
            int tourDistance = 0;
            for (int cityIndex = 0; cityIndex < getTourSize(); ++cityIndex) {
                City from = getCity(cityIndex);
                City destination = cityIndex + 1 < getTourSize() ///check 13.11
                        ? getCity(cityIndex + 1)
                        : getCity(0);
                tourDistance += from.distanceTo(destination);
            }
            this.distance = tourDistance;
        }
        return distance;
    }
    public List<City> getTour() {
        return tour;
    }

    public void setTour(List<City> tour) {
        this.tour = tour;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public City getCity(int tourPosition) {
        return tour.get(tourPosition);
    }

    public int getTourSize() {
        return tour.size();
    }

    public void generateTour() {
        for (int cityIndex = 0; cityIndex < Repo.getNumOfCities(); ++cityIndex) {
            setCity(cityIndex, Repo.getCity(cityIndex));
        }
        Collections.shuffle(tour);
    }

    public void setCity(int cityIndex, City city) {
        tour.set(cityIndex, city);
        distance = 0;
    }

    @Override
    public String toString() {
        return ""  + tour + ",";
    }
//    private List<City> tour = new ArrayList<>();
//    private  double distance = 0;
//
//    public SingleTour(){
//        for (int i = 0; i<Repo.getNumOfCities(); ++i){
//            tour.add(null);
//        }
//    }
//
//    public SingleTour(List<City> tour){
//        List<City> currTour = new ArrayList<>();
//
//        for (int i =0; i< tour.size(); ++i){
//            currTour.add(null);
//        }
//
//        for (int i =0; i< tour.size(); ++i){
//            currTour.set(i, tour.get(i));
//        }
//
//        this.tour = currTour;
//    }
//    public double getDistance(){
//
//        if(distance == 0){
//            int tourDistance = 0;
//            for (int cityIndex = 0; cityIndex < getTourSize(); ++cityIndex){
//                City fromCity = getCity(cityIndex);
//                City destinationCity;
//
//                if(cityIndex + 1 < getTourSize()){
//                    destinationCity = getCity(cityIndex + 1);
//                } else {
//                    destinationCity = getCity(0);
//                }
//                tourDistance += fromCity.distanceTo(destinationCity);
//            }
//            this.distance = tourDistance;
//        }
//        return this.distance;
//    }
//
//    public List<City> getTour(){
//        return this.tour;
//    }
//
//    public void generateIndividual(){
//        for(int cityIndex = 0; cityIndex < Repo.getNumOfCities(); ++cityIndex){
//            setCity(cityIndex, Repo.getCity(cityIndex));
//
//            Collections.shuffle(tour);
//        }
//    }
//
//
//
//    public City getCity(int tourPosition){
//        return tour.get(tourPosition);
//    }
//
//    public void setCity(int cityIndex, City city) {
//        this.tour.set(cityIndex,  city);
//        this.distance = 0;
//    }
//    public int getTourSize(){
//        return this.tour.size();
//    }
//
//    @Override
//    public String toString(){
//        String s = " ";
//
//        for (int i = 0; i<getTourSize(); ++i){
//            s += getCity(i) + " -> ";
//        }
//        return s;
//    }
}