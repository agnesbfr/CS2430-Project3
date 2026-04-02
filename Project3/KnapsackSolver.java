package Project3;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Comparator;

/**
 * KnapsackSolver handles selection of experiments using different strategies.
 *
 * Strategies implemented:
 * Greedy by Rating, Greedy by Weight, Greedy by Rating-to-Weight Ratio,
 * and Brute-force Optimal solution.
 *
 * All strategies respect the maximum weight limit of the shuttle.
 *
 * Team: Team3
 * Members: Agnes, Kelvin, Latifah, Jimmy
 * Course: CS 2430, 
 * Project: Programming Project 
 */
public class KnapsackSolver {

    private Experiment[] experiments;
    private double weightLimit;

    /**
     * Constructor to set experiments and weight limit.
     *
     * @param experiments array of all experiments
     * @param weightLimit maximum allowed weight for the payload
     */
    public KnapsackSolver(Experiment[] experiments, double weightLimit) {
        this.experiments = experiments;
        this.weightLimit = weightLimit;
    }

    /**
     * Greedy strategy: selects experiments with highest rating first.
     *
     * @return list of chosen experiments without exceeding weight limit
     */
    public List<Experiment> greedyByRating() {
        List<Experiment> sorted = new ArrayList<>();
        Collections.addAll(sorted, experiments);

        sorted.sort(Comparator.comparingDouble(Experiment::getRating).reversed());

        List<Experiment> chosen = new ArrayList<>();
        double totalWeight = 0;
        for (Experiment e : sorted) {
            if (totalWeight + e.getWeight() <= weightLimit) {
                chosen.add(e);
                totalWeight += e.getWeight();
            }
        }
        return chosen;
    }

    /**
     * Greedy strategy: selects experiments with lowest weight first.
     *
     * @return list of chosen experiments without exceeding weight limit
     */
    public List<Experiment> greedyByWeight() {
        List<Experiment> sorted = new ArrayList<>();
        Collections.addAll(sorted, experiments);

        sorted.sort(Comparator.comparingDouble(Experiment::getWeight));

        List<Experiment> chosen = new ArrayList<>();
        double totalWeight = 0;
        for (Experiment e : sorted) {
            if (totalWeight + e.getWeight() <= weightLimit) {
                chosen.add(e);
                totalWeight += e.getWeight();
            }
        }
        return chosen;
    }

    /**
     * Greedy strategy: selects experiments with highest rating-to-weight ratio first.
     *
     * @return list of chosen experiments without exceeding weight limit
     */
    public List<Experiment> greedyByRatio() {
        List<Experiment> sorted = new ArrayList<>();
        Collections.addAll(sorted, experiments);

        sorted.sort(Comparator.comparingDouble(
                e -> -(e.getRating() / e.getWeight()) // descending order
        ));

        List<Experiment> chosen = new ArrayList<>();
        double totalWeight = 0;
        for (Experiment e : sorted) {
            if (totalWeight + e.getWeight() <= weightLimit) {
                chosen.add(e);
                totalWeight += e.getWeight();
            }
        }
        return chosen;
    }

    /**
     * Brute-force strategy: evaluates all possible subsets of experiments
     * to find the subset with the highest total rating under weight limit.
     *
     * @return list of experiments with maximum total rating
     */
    public List<Experiment> bruteForce() {
        List<List<Experiment>> validSubsets = new ArrayList<>();
        List<Double> validRatings = new ArrayList<>();
        List<Double> validWeights = new ArrayList<>();

        for (int subset = 0; subset < 4096; subset++) {
            List<Experiment> chosen = new ArrayList<>();
            double totalWeight = 0;
            double totalRating = 0;

            for (int i = 0; i < experiments.length; i++) {
                if ((subset & (1 << i)) != 0) {
                    chosen.add(experiments[i]);
                    totalWeight += experiments[i].getWeight();
                    totalRating += experiments[i].getRating();
                }
            }

            if (totalWeight <= weightLimit) {
                validSubsets.add(chosen);
                validRatings.add(totalRating);
                validWeights.add(totalWeight);
            }
        }

       
        for (int i = 0; i < validSubsets.size() - 1; i++) {
            for (int j = 0; j < validSubsets.size() - 1 - i; j++) {
                if (validRatings.get(j) < validRatings.get(j + 1)) {
                    List<Experiment> tempSubset = validSubsets.get(j);
                    double tempRating = validRatings.get(j);
                    double tempWeight = validWeights.get(j);

                    validSubsets.set(j, validSubsets.get(j + 1));
                    validRatings.set(j, validRatings.get(j + 1));
                    validWeights.set(j, validWeights.get(j + 1));

                    validSubsets.set(j + 1, tempSubset);
                    validRatings.set(j + 1, tempRating);
                    validWeights.set(j + 1, tempWeight);
                }
            }
        }

        return validSubsets.get(0);
    }

    /**
     * Prints chosen experiments along with total weight and total rating.
     *
     * @param chosen list of selected experiments
     */
    public void printResult(List<Experiment> chosen) {
        double totalWeight = 0;
        double totalRating = 0;

        for (Experiment e : chosen) {
            System.out.println(e.getName() + " | Weight: " + e.getWeight() + " | Rating: " + e.getRating());
            totalWeight += e.getWeight();
            totalRating += e.getRating();
        }

        System.out.println("\nTotal Weight: " + totalWeight);
        System.out.println("Total Rating: " + totalRating);
    }
}
