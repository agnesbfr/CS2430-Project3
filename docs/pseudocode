MAIN:
    create experiments list:
        experiment 1:  name="Cloud Patterns",                        weight=36,  rating=5
        experiment 2:  name="Solar Flares",                             weight=264, rating=9
        experiment 3:  name="Solar Power",                             weight=188, rating=6
        experiment 4:  name="Binary Stars",                             weight=203, rating=8
        experiment 5:  name="Relativity",                                  weight=104, rating=8
        experiment 6:  name="Seed Viability",                           weight=7,   rating=4
        experiment 7:  name="Sun Spots",                                 weight=90,  rating=2
        experiment 8:  name="Mice Tumors",                             weight=65,  rating=8
        experiment 9:  name="Microgravity Plant Growth",         weight=75,  rating=5
        experiment 10: name="Micrometeorites",                        weight=170, rating=9
        experiment 11: name="Cosmic Rays",                             weight=80,  rating=7
        experiment 12: name="Yeast Fermentation",                   weight=27,  rating=4

    weightLimit = 700

    ratingResult = call greedyByRating(experiments, weightLimit)
    print ratingResult

    weightResult = call greedyByWeight(experiments, weightLimit)
    print weightResult

    ratioResult = call greedyByRatio(experiments, weightLimit)
    print ratioResult

    bruteResult = call bruteForce(experiments, weightLimit)
    print bruteResult

    call printSummary(ratingResult, weightResult, ratioResult, bruteResult)


═══════════════════════════════════════════════════
FUNCTION greedyByRating(experiments, weightLimit):

    sort experiments by rating, highest to lowest

    totalWeight = 0
    totalRating = 0
    chosen = empty list

    for each experiment in sorted list:
        if totalWeight + experiment.weight <= weightLimit:
            add experiment to chosen
            totalWeight += experiment.weight
            totalRating += experiment.rating

    return chosen, totalWeight, totalRating


═══════════════════════════════════════════════════
FUNCTION greedyByWeight(experiments, weightLimit):

    sort experiments by weight, lowest to highest

    totalWeight = 0
    totalRating = 0
    chosen = empty list

    for each experiment in sorted list:
        if totalWeight + experiment.weight <= weightLimit:
            add experiment to chosen
            totalWeight += experiment.weight
            totalRating += experiment.rating

    return chosen, totalWeight, totalRating


═══════════════════════════════════════════════════
FUNCTION greedyByRatio(experiments, weightLimit):

    for each experiment:
        experiment.ratio = experiment.rating / experiment.weight

    sort experiments by ratio, highest to lowest

    totalWeight = 0
    totalRating = 0
    chosen = empty list

    for each experiment in sorted list:
        if totalWeight + experiment.weight <= weightLimit:
            add experiment to chosen
            totalWeight += experiment.weight
            totalRating += experiment.rating

    return chosen, totalWeight, totalRating

     
═══════════════════════════════════════════════════
FUNCTION bruteForce(experiments, weightLimit):

    validSubsets = empty list

    for subset = 0 to 4095:

        totalWeight = 0
        totalRating = 0
        chosen = empty list

        for i = 0 to 11:
            if bit i is ON in subset:
                add experiments[i] to chosen
                totalWeight += experiments[i].weight
                totalRating += experiments[i].rating

        if totalWeight <= weightLimit:
            add (chosen, totalWeight, totalRating) to validSubsets

    sort validSubsets by totalRating, highest to lowest

    return top 3 from validSubsets


═══════════════════════════════════════════════════
FUNCTION printSummary(ratingResult, weightResult, ratioResult, bruteResult):

    print "===== COMPARISON SUMMARY ====="

    print "Greedy by Rating:"
        print chosen experiments, totalWeight, totalRating

    print "Greedy by Weight:"
        print chosen experiments, totalWeight, totalRating

    print "Greedy by Ratio:"
        print chosen experiments, totalWeight, totalRating

    print "Brute Force Optimal:"
        print chosen experiments, totalWeight, totalRating

    if any greedy result totalRating == bruteResult[0] totalRating:
        print which greedy strategies matched the optimal
    else:
        print none of the greedy strategies matched the optimal


═══════════════════════════════════════════════════
/*
 * DYNAMIC PROGRAMMING PSEUDOCODE 
 *
 * create a table with:
 *     rows    = 0 to 12 (one per experiment)
 *     columns = 0 to 700 (one per possible weight)
 *     each cell = best rating achievable so far
 *
 * fill first row with all zeros (no experiments = no rating)
 *
 * for each experiment (row 1 to 12):
 *     for each weight limit (column 0 to 700):
 *
 *         option A = skip this experiment
 *                  = value from row above, same column
 *
 *         option B = include this experiment (only if it fits)
 *                  = experiment.rating + value from row above
 *                    at column (currentWeight - experiment.weight)
 *
 *         cell value = whichever of A or B is higher
 *
 * answer = bottom right cell (row 12, column 700)
 *
 * to find which experiments were chosen:
 *     trace back through the table from bottom right
 *     if value changed from row above, that experiment was included
 */
