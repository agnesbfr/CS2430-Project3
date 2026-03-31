package Project3;

public class Main {

    public static void main(String[] args) {

        // the maximum weight the shuttle can carry in kg
        double weightLimit = 700;
        
        
        // all 12 experiments from the assignment
        // each has a name, weight in kg, and a scientific rating
        // format: new Experiment("name", weight, rating)
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

        // create the solver passing in the experiments and weight limit
        // all methods (greedy and brute force) will use this same data
        KnapsackSolver solver = new KnapsackSolver(experiments, weightLimit);

        // TODO Part 1 — call greedy methods here when ready
        // solver.greedyByRating();
        // solver.greedyByWeight();
        // solver.greedyByRatio();

        // run the brute force search across all 4096 possible subsets
        // this prints the top 3 valid subsets and returns the optimal one
        solver.bruteForce();
    }
}
