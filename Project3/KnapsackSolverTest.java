package Project3;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class KnapsackSolverTest {

    private Experiment[] experiments;
    private KnapsackSolver solver;

    @BeforeEach
    void setUp() {
        experiments = new Experiment[] {
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

        solver = new KnapsackSolver(experiments, 700);
    }

    @Test
    void testExperimentConstructorAndGetters() {
        Experiment e = new Experiment("Test Experiment", 50.5, 8.5);

        assertEquals("Test Experiment", e.getName());
        assertEquals(50.5, e.getWeight());
        assertEquals(8.5, e.getRating());
    }

    @Test
    void testGreedyByRatingDoesNotExceedWeightLimit() {
        List<Experiment> chosen = solver.greedyByRating();
        double totalWeight = getTotalWeight(chosen);

        assertTrue(totalWeight <= 700);
    }

    @Test
    void testGreedyByWeightDoesNotExceedWeightLimit() {
        List<Experiment> chosen = solver.greedyByWeight();
        double totalWeight = getTotalWeight(chosen);

        assertTrue(totalWeight <= 700);
    }

    @Test
    void testGreedyByRatioDoesNotExceedWeightLimit() {
        List<Experiment> chosen = solver.greedyByRatio();
        double totalWeight = getTotalWeight(chosen);

        assertTrue(totalWeight <= 700);
    }

    @Test
    void testBruteForceDoesNotExceedWeightLimit() {
        List<Experiment> chosen = solver.bruteForce();
        double totalWeight = getTotalWeight(chosen);

        assertTrue(totalWeight <= 700);
    }

    @Test
    void testGreedyByRatingExpectedSelection() {
        List<String> names = solver.greedyByRating()
                .stream()
                .map(Experiment::getName)
                .collect(Collectors.toList());

        assertEquals(List.of(
                "Solar Flares",
                "Micrometeorites",
                "Binary Stars",
                "Cloud Patterns",
                "Seed Viability"
        ), names);
    }

    @Test
    void testGreedyByWeightExpectedSelection() {
        List<String> names = solver.greedyByWeight()
                .stream()
                .map(Experiment::getName)
                .collect(Collectors.toList());

        assertEquals(List.of(
                "Seed Viability",
                "Yeast Fermentation",
                "Cloud Patterns",
                "Mice Tumors",
                "Microgravity Plant Growth",
                "Cosmic Rays",
                "Sun Spots",
                "Relativity",
                "Micrometeorites"
        ), names);
    }

    @Test
    void testGreedyByRatioExpectedSelection() {
        List<String> names = solver.greedyByRatio()
                .stream()
                .map(Experiment::getName)
                .collect(Collectors.toList());

        assertEquals(List.of(
                "Seed Viability",
                "Yeast Fermentation",
                "Cloud Patterns",
                "Mice Tumors",
                "Cosmic Rays",
                "Relativity",
                "Microgravity Plant Growth",
                "Micrometeorites",
                "Sun Spots"
        ), names);
    }

    @Test
    void testBruteForceFindsOptimalTotalRating() {
        List<Experiment> chosen = solver.bruteForce();

        assertEquals(692.0, getTotalWeight(chosen));
        assertEquals(53.0, getTotalRating(chosen));
    }

    @Test
    void testBruteForceExpectedSelection() {
        List<String> names = solver.bruteForce()
                .stream()
                .map(Experiment::getName)
                .collect(Collectors.toList());

        assertEquals(List.of(
                "Cloud Patterns",
                "Binary Stars",
                "Relativity",
                "Seed Viability",
                "Mice Tumors",
                "Micrometeorites",
                "Cosmic Rays",
                "Yeast Fermentation"
        ), names);
    }

    @Test
    void testBruteForceIsAtLeastAsGoodAsGreedyStrategies() {
        double bruteRating = getTotalRating(solver.bruteForce());
        double ratingGreedy = getTotalRating(solver.greedyByRating());
        double weightGreedy = getTotalRating(solver.greedyByWeight());
        double ratioGreedy = getTotalRating(solver.greedyByRatio());

        assertTrue(bruteRating >= ratingGreedy);
        assertTrue(bruteRating >= weightGreedy);
        assertTrue(bruteRating >= ratioGreedy);
    }

    @Test
    void testPrintResultOutputsTotals() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            solver.printResult(solver.bruteForce());
        } finally {
            System.setOut(originalOut);
        }

        String output = outputStream.toString();

        assertTrue(output.contains("Total Weight: 692.0"));
        assertTrue(output.contains("Total Rating: 53.0"));
        assertTrue(output.contains("Cloud Patterns"));
        assertTrue(output.contains("Micrometeorites"));
    }

    private double getTotalWeight(List<Experiment> list) {
        double total = 0;
        for (Experiment e : list) {
            total += e.getWeight();
        }
        return total;
    }

    private double getTotalRating(List<Experiment> list) {
        double total = 0;
        for (Experiment e : list) {
            total += e.getRating();
        }
        return total;
    }
}