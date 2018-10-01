import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ParentSelection
{

  String survivorAlg_;
  Random rnd_;

  public SurvivorSelection(String algType)
  {
    // Initialize object with correct selection algorithm
    selectionAlg_ = algType;
    rnd_ = new Random();
    rnd_.setSeed(42);
  }

  public double[][] performSelection(double[][] oldPopulation, double[] allScores, int numChild)
  {
    // Perform selection using correct algorithm
    switch (selectionAlg_)
    {
      case "worst" :
        return replaceWorst(oldPopulation, allScores, numChild);
      case "elitism" :
        return elitism(oldPopulation, allScores, numChild);
      case "roundRobin":
        return roundRobin(oldPopulation, allScores, numChild);
    }

    // Return empty list if no case has been used
    double[][] errorList = new double[0][0];
    return errorList;
  }

  // individuals with worst fitness scores are eliminated
  public double[][] replaceWorst(double[][] oldPopulation, double[] allScores, int numChild)
  {

    // set parameters
    int populationSize = 100;
    int nrTraits = 10;

    // sort scores and the population accordingly
    double[] sortedScores = Arrays.sort(allScores);
    double[][] sortedPopulation = new double[populationSize + numChild][nrTraits];

    for (int i = 0; i < oldPopulation.length; i++)
    {
      sortedPopulation[i] = oldPopulation.get(sortedScores[i]);
    }

    // remove worst individuals
    double[][] newPopulation = sortedPopulation.removeRange(0,numChild);

    return newPopulation;

  }

  // keep the individual with the best fitness value
  public double[][] elitism(double[][] oldPopulation, double[] allScores, int numChild)
  {
    int populationSize = 100;
    int nrTraits = 10;
    double[][] newPopulation = new double[populationSize][nrTraits];

    // determine number of parents plus children
    int length = oldPopulation.length;

    // find maximum fitness value and corresponding index in population
    double bestScore = Collections.max(allScores);
    int bestIndividual = allScores.indexOf(bestScore);

    int[] elimIndividuals = new int[numChild];
    for (i = 0; i < numChild; i++)
    {
      // select random individual
      int randomInd = rnd_.nextInteger(length);

      // ensure that fittest individual is not eliminated
      while randomInd == bestIndividual
      {
        randomInd = rnd_.nextInteger(length);
      }
      // select random individual to be eliminated
      elimIndividuals[i] = randomInd;
    }

    // remove eliminated individuals
    for (i = 0; i < length; i++)
    {
      // add individual to new population, if not in eliminated list
      if i != elimIndividuals[i]
      {
        newPopulation[i] = oldPopulation[i];
      }
    }

    return newPopulation;

  }

  // evaluate each individual against others and choose new population accordingly
  public double[][] roundRobin(double[][] oldPopulation, double[] allScores, int numChild)
  {
    int populationSize = 100;
    int nrTraits = 10;

    // determine number of parents plus children
    int length = oldPopulation.length;

    // store number of wins of each individual
    int[] wins = new int[length];

    for (i = 0; i < length; i++)
    {
      int numWins = 0;
      int numOpponents = 0;

      // choose opponents by score
      while numOpponents < 10
      {
        int opp = rnd_.nextInteger(length);

        // ensure that opponent and individual are not equal
        while opp == i
        {
          opp = rnd_.nextInteger(length);
        }

        // determine win or no win
        if allScores[i] > allScores[opp]
        {
          numWins += 1;
        }

        // select next opponent
        numOpponents += 1
      }

      // store number of 'wins' in list
      wins[i] = numWins;
    }

    // select individuals with most 'wins'
    int[] sortedWins = new int[length];
    sortedWins = Arrays.sort(wins);

    double[][] sortedPopulation = new double[populationSize + numChild][nrTraits];

    for (j = 0; j < length; j++)
    {
      int idx = wins.indexOf(sortedWins[j]); // zijn er niet te veel individuen met evenveel 'wins'???
      sortedPopulation[j] = oldPopulation[idx];
    }

    // remove individuals with fewest 'wins'
    double[][] newPopulation = sortedPopulation.removeRange(0,numChild);

    return newPopulation;

  }


}
