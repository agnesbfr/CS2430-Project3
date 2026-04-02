package Project3;

/**
 * Represents a single experiment with a name, weight, and scientific rating.
 *
 * Instances of this class are used by the KnapsackSolver to select optimal subsets.
 *
 * Team: Team3
 * Members: Agnes, Kelvin, Latifah, Jimmy
 * Course: CS 2430, 
 * Project: Programming Project 
 */
public class Experiment {
    private String name;
    private double weight;
    private double rating;

    /**
     * Constructor to create an Experiment object.
     *
     * @param name the name of the experiment
     * @param weight the weight of the experiment in kilograms
     * @param rating the scientific rating of the experiment
     */
    public Experiment(String name, double weight, double rating) {
        this.name = name;
        this.weight = weight;
        this.rating = rating;
    }

    /**
     * Returns the name of the experiment.
     *
     * @return experiment name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the weight of the experiment.
     *
     * @return experiment weight in kilograms
     */
    public double getWeight() {
        return weight;
    }

    /**
     * Returns the scientific rating of the experiment.
     *
     * @return experiment rating
     */
    public double getRating() {
        return rating;
    }
}
