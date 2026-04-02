package Project3;

import java.util.List;

/**
 * Main class to run the KnapsackSolver program.
 * 
 * This program loads a list of experiments for the space shuttle payload,
 * executes all three greedy strategies (rating-first, weight-first, ratio-first),
 * runs the brute-force optimal solution, and prints a comparison summary.
 * 
 * Outputs are formatted to support report generation, showing total weight,
 * total rating, and the list of chosen experiments for each strategy.
 * 
 * 
 * Team: Team3
 * Members: Agnes, Kelvin, Latifah, Jimmy
 * Course: CS 2430, 
 * Project: Programming Project 
 */
public class Main {

    /**
     * Main entry point for the program.
     * Initializes experiment data, creates the KnapsackSolver, and prints summary.
     * 
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        double weightLimit = 700;

        Experiment[] experiments = {
            new Experiment("Cloud Patterns",             36,  5),
            new Experiment("Solar Flares",               264, 9),
            new Experiment("Solar Power",                188, 6),
            new Experiment("Binary Stars",               203, 8),
            new Experiment("Relativity",                 104, 8),
            new Experiment("Seed Viability",             7,   4),
            new Experiment("Sun Spots",                  90,  2),
            new Experiment("Mice Tumors",                65,  8),
            new Experiment("Microgravity Plant Growth",  75,  5),
            new Experiment("Micrometeorites",            170, 9),
            new Experiment("Cosmic Rays",                80,  7),
            new Experiment("Yeast Fermentation",         27,  4)
        };

        KnapsackSolver solver = new KnapsackSolver(experiments, weightLimit);

        // Print comparison summary of all strategies
        printComparisonSummary(solver);
    }

    /**
     * Prints a comparison summary for all strategies.
     * 
     * Includes results for:
     * 
     *     Greedy by Rating
     *     Greedy by Weight
     *     Greedy by Rating-to-Weight Ratio
     *     Brute Force Optimal
     * 
     * 
     * @param solver the KnapsackSolver object containing experiments and strategies
     */
    public static void printComparisonSummary(KnapsackSolver solver) {
        List<Experiment> greedyRating = solver.greedyByRating();
        List<Experiment> greedyWeight = solver.greedyByWeight();
        List<Experiment> greedyRatio  = solver.greedyByRatio();
        List<Experiment> bruteOptimal = solver.bruteForce();

        printStrategy("GREEDY BY RATING", greedyRating);
        printStrategy("GREEDY BY WEIGHT", greedyWeight);
        printStrategy("GREEDY BY RATIO", greedyRatio);
        printStrategy("BRUTE FORCE OPTIMAL", bruteOptimal);
    }

    /**
     * Helper method to print results of a given strategy neatly.
     * 
     * @param title title of the strategy
     * @param experiments list of chosen experiments for the strategy
     */
    private static void printStrategy(String title, List<Experiment> experiments) {
        System.out.println("\n" + title);
        double totalWeight = 0;
        double totalRating = 0;
        for (Experiment e : experiments) {
            System.out.printf("%-25s | Weight: %.1f | Rating: %.1f%n",
                              e.getName(), e.getWeight(), e.getRating());
            totalWeight += e.getWeight();
            totalRating += e.getRating();
        }
        System.out.printf("Total Weight: %.1f%nTotal Rating: %.1f%n", totalWeight, totalRating);
    }

    /**
     * Computes the sum of ratings for a given list of experiments.
     * 
     * @param experiments list of experiments
     * @return total rating
     */
    private static double sumRating(List<Experiment> experiments) {
        double total = 0;
        for (Experiment e : experiments) {
            total += e.getRating();
        }
        return total;
    }
}
