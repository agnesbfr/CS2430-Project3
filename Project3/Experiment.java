package Project3;

public class Experiment {
    private String name;
    private double weight;
    private double rating;

    public Experiment(String name, double weight, double rating) {
        this.name = name;
        this.weight = weight;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public double getRating() {
        return rating;
    }
}
