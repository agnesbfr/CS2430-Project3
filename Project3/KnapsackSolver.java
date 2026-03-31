package Project3;

import java.util.ArrayList;
import java.util.List;

public class KnapsackSolver {

    // the array of all 12 experiments passed in from Main
    // all methods in this class use this same array
    private Experiment[] experiments;

    // the maximum weight the shuttle can carry in kg
    private double weightLimit;

    // constructor — receives the experiments and weight limit
    // from Main so every method here can access them
    public KnapsackSolver(Experiment[] experiments, double weightLimit) {
        this.experiments = experiments;
        this.weightLimit = weightLimit;
    }

    // ---- Part 1 ----

    public List<Experiment> greedyByRating() {
        return new ArrayList<>();
    }

    public List<Experiment> greedyByWeight() {
        return new ArrayList<>();
    }

    public List<Experiment> greedyByRatio() {
        return new ArrayList<>();
    }

    //Part 2
    //Kevin Ajongbah

    // --------------------------------------------------------
    // BRUTE FORCE METHOD
    //
    // This method checks every single possible combination
    // of experiments to find the true optimal solution.
    //
    // WHY 4096 SUBSETS:
    // Each of the 12 experiments is either IN or OUT of the
    // shuttle payload. That is like 12 on/off switches.
    // The total number of combinations is:
    //   2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 x 2 = 4096
    // So we check all 4096 and keep the best valid ones.
    //
    // HOW THE BINARY TRICK WORKS:
    // If you write the numbers 0 to 4095 in binary, each one
    // naturally represents one unique combination of switches.
    //
    //   0    = 000000000000 = no experiments selected
    //   1    = 000000000001 = only experiment 1 selected
    //   3    = 000000000011 = experiments 1 and 2 selected
    //   4095 = 111111111111 = all 12 experiments selected
    //
    // Looping from 0 to 4095 gives us every combo for free.
    // --------------------------------------------------------
    public List<Experiment> bruteForce() {

        // these parallel lists store every valid combo we find
        // valid means total weight is at or under the weight limit
        // all three lists stay in sync — same index = same combo
        List<List<Experiment>> validSubsets = new ArrayList<>();
        List<Double>           validRatings = new ArrayList<>();
        List<Double>           validWeights = new ArrayList<>();

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
                //
                // example for i = 3:
                //   1      = 000000000001
                //   1 << 3 = 000000001000
                //
                // (subset & (1 << i)) checks if that bit is ON
                // in our current subset number
                // if the result is not zero, experiment i is included
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
                    double           tempRating  = validRatings.get(j);
                    double           tempWeight  = validWeights.get(j);

                    validSubsets.set(j, validSubsets.get(j + 1));
                    validRatings.set(j, validRatings.get(j + 1));
                    validWeights.set(j, validWeights.get(j + 1));

                    validSubsets.set(j + 1, tempSubset);
                    validRatings.set(j + 1, tempRating);
                    validWeights.set(j + 1, tempWeight);
                }
            }
        }

        // print the top 3 valid subsets clearly
        // index 0 is always the optimal since we sorted by rating
        System.out.println("========================================");
        System.out.println("     BRUTE FORCE: Top 3 Valid Subsets   ");
        System.out.println("========================================");

        // loop through the top 3 results and print each one
        for (int i = 0; i < Math.min(3, validSubsets.size()); i++) {

            // label the very first result as the optimal solution
            // it has the highest rating of all valid combos
            if (i == 0) {
                System.out.println("\n#1 --- OPTIMAL SOLUTION ---");
            } else {
                System.out.println("\n#" + (i + 1) + " ---");
            }

            // print each experiment that is in this combo
            System.out.println("Experiments chosen:");
            for (Experiment e : validSubsets.get(i)) {
                System.out.println("  - " + e.getName()
                    + " | Weight: " + e.getWeight() + "kg"
                    + " | Rating: " + e.getRating());
            }

            // print the weight and rating totals for this combo
            System.out.println("Total Weight : " + validWeights.get(i) + " kg");
            System.out.println("Total Rating : " + validRatings.get(i));
        }

        System.out.println("\n========================================\n");

        // return the single best combo (index 0) so Main can
        // use it for the comparison summary against the greedy results
        return validSubsets.get(0);
    }


    // --------------------------------------------------------
    // DYNAMIC PROGRAMMING
    //
    // WHY WE NEED DP:
    // Brute force works fine for 12 experiments (4096 checks)
    // but for 50 experiments that would be 2^50 = over
    // 1 quadrillion checks. That is not feasible at all.
    // DP solves the same problem in a fraction of the time
    // by breaking it into smaller subproblems and storing
    // those answers so we never repeat the same calculation.
    //
    // TABLE SETUP:
    //   rows    = 0 to 12, one row per experiment
    //   columns = 0 to 700, one column per possible weight
    //   cell[i][w] = best rating achievable using only
    //                the first i experiments with weight limit w
    //
    // FILLING THE TABLE:
    //   start by setting row 0 to all zeros
    //   (no experiments available means no rating possible)
    //
    //   for each experiment i from 1 to 12:
    //       for each weight limit w from 0 to 700:
    //
    //           option A = skip experiment i
    //                    = cell[i-1][w]
    //                    (keep the same best rating from row above)
    //
    //           option B = include experiment i
    //                    only if experiment[i].weight <= w
    //                    = experiment[i].rating
    //                      + cell[i-1][w - experiment[i].weight]
    //                    (its rating plus best from remaining weight)
    //
    //           cell[i][w] = whichever of A or B is higher
    //
    // READING THE ANSWER:
    //   cell[12][700] = the highest possible total rating
    //
    // TRACING BACK WHICH EXPERIMENTS WERE CHOSEN:
    //   start at cell[12][700]
    //   if cell[i][w] differs from cell[i-1][w]:
    //       experiment i was included
    //       move to cell[i-1][w - experiment[i].weight]
    //   else:
    //       experiment i was skipped
    //       move to cell[i-1][w]
    //   repeat until you reach row 0
    //
    // EFFICIENCY vs BRUTE FORCE:
    //   brute force = O(2^n), doubles for every new experiment
    //   DP          = O(n x W), n = experiments, W = weight limit
    //   for 12 items: brute force = 4096 checks
    //                 DP          = 12 x 700 = 8400 table cells
    //   for 50 items: brute force = over 1 quadrillion checks
    //                 DP          = 50 x 700 = 35000 table cells
    // --------------------------------------------------------
    public List<Experiment> dynamicProgramming() {
        // stub — returns empty list until extra credit is implemented
        return new ArrayList<>();
    }
}
