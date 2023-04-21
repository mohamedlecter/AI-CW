public class City {
    private int id;
    private double x;
    private double y;
    public City(int id, double x, double y) {
        this.id = id;
        this.x = x;
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    @Override
    public String toString() {
        return "City " + id + " (" + x + ", " + y + ")";
    }

    public double distanceTo(City otherCity) {
        double xDistance = this.x - otherCity.x;
        double yDistance = this.y - otherCity.y;
        double distance = Math.sqrt(xDistance*xDistance + yDistance*yDistance);
        return distance;
    }

}
