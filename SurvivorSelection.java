import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.*;

import java.lang.Object;

public class SurvivorSelection
{

  String survivorAlg_;
  Random rnd_;

  public SurvivorSelection(String algType)
  {
    // Initialize object with correct survivor algorithm
    survivorAlg_ = algType;
    rnd_ = new Random();
    rnd_.setSeed(42);
  }

  public double[][] performSurvivorSelection(double[][] oldPopulation, double[] allScores, int numChild)
  {
    // Perform selection using correct algorithm
    switch (survivorAlg_)
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

  /*
  individuals with worst fitness scores are eliminated
  */
  public double[][] replaceWorst(double[][] oldPopulation, double[] allScores, int numChild)
  {

    // set parameters
    int populationSize = 100;
    int nrTraits = 10;

    // sort scores and the population accordingly
    double[] sortedScores = new double[allScores.length];

    // make copy first
    for (int i = 0; i < allScores.length; i++)
    {
      sortedScores[i] = allScores[i];
    }

    Arrays.sort(sortedScores);

    List<double[]> newPopulationList = new ArrayList<double[]>();

    // convert population array to list in order to remove elements
    for (int m = 0; m < oldPopulation.length; m++)
    {
      double[] genes = new double[nrTraits];

      for (int n = 0; n < nrTraits; n++)
      {
        genes[n] = oldPopulation[m][n];
      }

      newPopulationList.addAll(Arrays.asList(genes));
    }

    // convert probability array to list, in order to use get()
    List<Double> allScoresList = new ArrayList<>();
    for (int l = 0; l < allScores.length; l++)
    {
      allScoresList.add(allScores[l]);
    }

    // remove individuals
    for (int j = 0; j < numChild; j++)
    {
      // set index value to null
      int idx = allScoresList.indexOf(sortedScores[j]);
      newPopulationList.set(idx,null);
    }

    // remove null elements
    while (newPopulationList.remove(null));

    // convert newPopulationList to array
    double[][] newPopulation = new double[populationSize][nrTraits];
    for (int k = 0; k < populationSize; k++)
    {
      newPopulation[k] = newPopulationList.get(k);
    }

    // check for correct population size
    if (newPopulation.length != populationSize)
    {
      double[][] errorList = new double[0][0];
      return errorList;
    }

    return newPopulation;

  }

  /*
  keep the individual with the best fitness value
  */
  public double[][] elitism(double[][] oldPopulation, double[] allScores, int numChild)
  {
    int populationSize = 100;
    int nrTraits = 10;
    double[][] newPopulation = new double[populationSize][nrTraits];

    // determine number of parents plus children
    int length = oldPopulation.length;

    // find maximum fitness value and corresponding index in population
    double bestScore = Arrays.stream(allScores).max().getAsDouble();
    int bestIndividual = Arrays.asList(allScores).indexOf(bestScore);

    // ROUND ROBIN TOURNAMENT FOR SELECTION OF SURVIVORS
    
    // store number of wins of each individual
    int[] wins = new int[length];

    for (int i = 0; i < length; i++)
    {
      int numWins = 0;
      int numOpponents = 0;
      // parameter for q

      // choose opponents by score
      while (numOpponents < 10)
      {
        int opp = rnd_.nextInt(length);

        // ensure that opponent and individual are not equal
        while (opp == i)
        {
          opp = rnd_.nextInt(length);
        }

        // determine win or no win
        if (allScores[i] > allScores[opp])
        {
          numWins += 1;
        }

        // select next opponent
        numOpponents += 1;
      }

      // store number of 'wins' in list
      wins[i] = numWins;
    }

    // sort scores and the population accordingly
    int[] sortedWins = new int[wins.length];

    // make copy first
    for (int j = 0; j < wins.length; j++)
    {
      sortedWins[j] = wins[j];
    }

    Arrays.sort(sortedWins);
    int middle = length/2;
    int medianWins = sortedWins[middle];

    List<Integer> elimIndividuals = new ArrayList<>();

    for (int k = 0; k < length; k++)
    {
      if ((wins[k] < medianWins) && (k != bestIndividual))
      {
        // put individual k in possible elimination list
        elimIndividuals.add(k);
      }
    }

    // determine surplus and remove
    Collections.shuffle(elimIndividuals);
    int surplus = elimIndividuals.size() - numChild;
    elimIndividuals.subList(0,surplus).clear();

    int ind = 0;

    // remove eliminated individuals
    for (int l = 0; l < length; l++)
    {
      // add individual to new population, if not in eliminated list
      if (!elimIndividuals.contains(l))
      {
        newPopulation[ind] = oldPopulation[l];
        ind++;
      }
    }

    // check for correct population size
    if (newPopulation.length != populationSize)
    {
      double[][] errorList = new double[0][0];
      return errorList;
    }

    return newPopulation;

  }

  /*
  evaluate each individual against others and choose new population accordingly
  */
  public double[][] roundRobin(double[][] oldPopulation, double[] allScores, int numChild)
  {
    int populationSize = 100;
    int nrTraits = 10;
    double[][] newPopulation = new double[populationSize][nrTraits];

    // determine number of parents plus children
    int length = oldPopulation.length;

    // store number of wins of each individual
    int[] wins = new int[length];

    for (int i = 0; i < length; i++)
    {
      int numWins = 0;
      int numOpponents = 0;
      // parameter for q

      // choose opponents by score
      while (numOpponents < 10)
      {
        int opp = rnd_.nextInt(length);

        // ensure that opponent and individual are not equal
        while (opp == i)
        {
          opp = rnd_.nextInt(length);
        }

        // determine win or no win
        if (allScores[i] > allScores[opp])
        {
          numWins += 1;
        }

        // select next opponent
        numOpponents += 1;
      }

      // store number of 'wins' in list
      wins[i] = numWins;
    }

    // sort scores and the population accordingly
    int[] sortedWins = new int[wins.length];

    // make copy first
    for (int j = 0; j < wins.length; j++)
    {
      sortedWins[j] = wins[j];
    }

    Arrays.sort(sortedWins);
    int middle = length/2;
    int medianWins = sortedWins[middle];

    List<Integer> elimIndividuals = new ArrayList<>();

    for (int k = 0; k < length; k++)
    {
      if (wins[k] < medianWins)
      {
        // put individual k in possible elimination list
        elimIndividuals.add(k);
      }
    }

    // determine surplus and remove
    Collections.shuffle(elimIndividuals);
    int surplus = elimIndividuals.size() - numChild;
    elimIndividuals.subList(0,surplus).clear();

    int ind = 0;

    // remove eliminated individuals
    for (int l = 0; l < length; l++)
    {
      // add individual to new population, if not in eliminated list
      if (!elimIndividuals.contains(l))
      {
        newPopulation[ind] = oldPopulation[l];
        ind++;
      }
    }

    // check for correct population size
    if (newPopulation.length != populationSize)
    {
      double[][] errorList = new double[0][0];
      return errorList;
    }

    return newPopulation;

  }
}
