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

  public ArrayList<Individual> performSurvivorSelection(ArrayList<Individual> oldPopulation, int numChild)
  {
    // Perform selection using correct algorithm
    switch (survivorAlg_)
    {
      case "worst" :
        return replaceWorst(oldPopulation, numChild);
      case "elitism" :
        return elitism(oldPopulation, numChild);
      case "roundRobin":
        int q = 10;
        return roundRobin(oldPopulation, q, numchild);
      default:
      return roundRobin(oldPopulation, numchild);
    }
    return errorList;
  }

  /*
  individuals with worst fitness scores are eliminated
  */
  public ArrayList<Individual> replaceWorst(ArrayList<Individual> oldPopulation, int numChild)
  {
    // set parameters
    int populationSize = oldPopulation.size()-numChild;
    int nrTraits = player38.nrTraits;

    Collections.sort(oldPopulation, new Comparator<Individual>() {
      @Override
      public int compare(Individual p1, Individual p2) {
          return Double.compare(p1.score, p2.score;
      }
    });

    // remove individuals
    for (int j = 0; j < numChild; j++)
    {
      oldPopulation.remove(0); //0 because index shifts
    }

    // check for correct population size
    if (newPopulation.size() != populationSize)
    {
      System.out.println("Something went wrong in SurvivorSelection' s population size");
      return -1;
    }

    return oldPopulation;

  }

  /*
  keep the individual with the best fitness value
  */
  public ArrayList<Individual> elitism(ArrayList<Individual> oldPopulation, int numChild)
  {
    // set parameters
    int populationSize = oldPopulation.size()-numChild;
    int nrTraits = player38.nrTraits;

    // determine number of parents plus children
    int length = oldPopulation.size();

    // find maximum fitness value and corresponding index in population
    double bestScore = Arrays.stream(allProbs).max().getAsDouble();
    int bestIndividual = Arrays.asList(allProbs).indexOf(bestScore);

    // TODO: Algorithm for survivor selection: MAKE SURE NO INDIVIDUALS ARE TWICE SELECTED
    List<Integer> elimIndividuals = new ArrayList<Integer>();
    for (int i = 0; i < numChild; i++)
    {
      // select random individual
      int randomInd = rnd_.nextInt(length);

      // ensure that fittest individual is not eliminated
      while (randomInd == bestIndividual && elimIndividuals.contains(randomInd))
      {
        randomInd = rnd_.nextInt(length);
      }
      // select random individual to be eliminated
      elimIndividuals.add(randomInd);
    }

    int ind = 0;

    // remove eliminated individuals
    for (int j = 0; j < length; j++)
    {
      // add individual to new population, if not in eliminated list
      if (!(elimIndividuals.contains(j)))
      {
        newPopulation[ind] = oldPopulation[j];
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
  public ArrayList<Individual> roundRobin(ArrayList<Individual> oldPopulation, int q, int numChild)
  {
    // set parameters
    int populationSize = oldPopulation.size()-numChild;
    int nrTraits = player38.nrTraits;

    // determine number of parents plus children
    int length = oldPopulation.size();

    // store number of wins of each individual
    int[] wins = new int[length];

    for (int i = 0; i < length; i++)
    {
      int numWins = 0;
      int numOpponents = 0;
      // parameter for q

      // choose opponents by score
      while (numOpponents < q)
      {
        int opp = rnd_.nextInt(length);

        // ensure that opponent and individual are not equal
        while (opp == i)
        {
          opp = rnd_.nextInt(length);
        }

        // determine win or no win
        if (oldPopulation.get(i).score > oldPopulation.get(opp).score)
        {
          numWins += 1;
        }
        numOpponents += 1;
       // select next opponent
      }

      // store number of 'wins' in list
      wins[i] = numWins;
    }

    // sort scores and the population accordingly
    int[] sortedWins = wins;

    Arrays.sort(sortedWins);
    int medianWins = sortedWins[wins.length/2];

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

    // remove eliminated individuals
    for (int l = 0; l < elimIndividuals.size(); l++)
    {
      oldPopulation.remove(l)
    }

    // check for correct population size
    if (oldPopulation.size() != populationSize)
    {
      System.out.println("Something went wrong in SurvivorSelection' s population size");
      return -1;
    }
    return oldPopulation;
  }
}
