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
         // these parallel lists store every valid combo we find
        // valid means total weight is at or under the weight limit
        // all three lists stay in sync — same index = same combo
        List<List<Experiment>> validSubsets = new ArrayList<>();
        List<Double> validRatings = new ArrayList<>();
        List<Double> validWeights = new ArrayList<>();
        
        // loop through every possible subset from 0 to 4095
        // each number in binary represents one unique combination
        for (int subset = 0; subset < 4096; subset++) {

             // start fresh for each new combo
            List<Experiment> chosen = new ArrayList<>();
            double totalWeight = 0;
            double totalRating = 0;
            
             // check each of the 12 experiments one at a time
            // i goes from 0 to 11, one index per experiment
            for (int i = 0; i < experiments.length; i++) {
                
                // this is the key bit-checking line
                // (1 << i) shifts the number 1 left by i positions
                // creating a binary number with only bit i ON
                if ((subset & (1 << i)) != 0) {

                    // this experiment is IN this combo
                    // add it to our chosen list and update totals
                    chosen.add(experiments[i]);
                    totalWeight += experiments[i].getWeight();
                    totalRating += experiments[i].getRating();
                }
                // if the bit was OFF we skip this experiment
                // and move on to check the next one
            }

             // after checking all 12 experiments for this combo
            // check if the total weight fits on the shuttle
            // if it does, this is a valid combo worth saving
            if (totalWeight <= weightLimit) {
                validSubsets.add(chosen);
                validRatings.add(totalRating);
                validWeights.add(totalWeight);
            }
            // if it does not fit we throw this combo away
            // and move on to the next subset number
        }

        // sort all valid subsets by rating from highest to lowest
        // using bubble sort on all three parallel lists together
        // so they stay aligned with each other after every swap
        for (int i = 0; i < validSubsets.size() - 1; i++) {
            for (int j = 0; j < validSubsets.size() - 1 - i; j++) {

                // if the current combo has a lower rating than the next
                // swap them so the higher rating moves to the front
                if (validRatings.get(j) < validRatings.get(j + 1)) {

                    // swap all three lists at positions j and j+1
                    // so each combo stays matched to its correct totals
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

         // print each experiment that is in this combo
        for (Experiment e : chosen) {
            System.out.println(e.getName() + " | Weight: " + e.getWeight() + " | Rating: " + e.getRating());
            totalWeight += e.getWeight();
            totalRating += e.getRating();
        }
         // print the weight and rating totals for this combo
        System.out.println("\nTotal Weight: " + totalWeight);
        System.out.println("Total Rating: " + totalRating);
    }
}
