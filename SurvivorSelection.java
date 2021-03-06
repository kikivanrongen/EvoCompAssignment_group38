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
      // case "elitism" :
      //   return elitism(oldPopulation, numChild);
      case "roundRobin":
        int q = 20;
        return roundRobin(oldPopulation, q, numChild);
      default:
        int q2 = 10;
      return roundRobin(oldPopulation,q2, numChild);
    }
  }

  /*
  individuals with worst fitness scores are eliminated
  */
  public ArrayList<Individual> replaceWorst(ArrayList<Individual> oldPopulation, int numChild)
  {
    // set parameters
    int populationSize = oldPopulation.size()-numChild;
    int nrTraits = player38.nrTraits;

    //TODO: dit geeft error bij runnen...
    ArrayList<Individual> sortedPop = sortPopulation(oldPopulation);
    // Collections.sort(oldPopulation, new Comparator<Individual>() {
    //   @Override
    //   public int compare(Individual p1, Individual p2) {
    //       return Double.compare(p1.score, p2.score);
    //   }
    // });

    // remove individuals
    for (int j = 0; j < numChild; j++)
    {
      sortedPop.remove(0); //0 because index shifts
    }

    // check for correct population size
    if (sortedPop.size() != populationSize)
    {
      System.out.println("Something went wrong in SurvivorSelection' s population size");
      return sortedPop;
    }

    return sortedPop;

  }

  //Function that loops through population until worst score is 0 and best score is -1
  //TODO: iemand dit checken
  public ArrayList<Individual> sortPopulation(ArrayList<Individual> pop) {
    int[] corInd = new int[pop.size()];
    for (int i=0; i < corInd.length; i++) {
      corInd[i] = i;
    }
    int swaps = 10;
    while (swaps != 0) {
      swaps=0;
      for (int i=0; i < corInd.length-1; i++) {
        if (pop.get(corInd[i]).score > pop.get(corInd[i+1]).score) {
          // if neighbours are ordered wrong, swap them.
          int temp = corInd[i];
          corInd[i] = corInd[i+1];
          corInd[i+1] = temp;
          swaps += 1;
        }
      }
    }
    //Fill arraylist in correct order
    ArrayList<Individual> sorted = new ArrayList<Individual>();
    for (int i=0; i<pop.size(); i++) {
      sorted.add(pop.get(corInd[i]));
    }
    return sorted;
  }

  /*
  keep the individual with the best fitness value
  */
  // public ArrayList<Individual> elitism(ArrayList<Individual> oldPopulation, int numChild)
  // {
  //   // set parameters
  //   int populationSize = oldPopulation.size()-numChild;
  //   int nrTraits = player38.nrTraits;
  //
  //   // determine number of parents plus children
  //   int length = oldPopulation.size();
  //
  //   // find maximum fitness value and corresponding index in population
  //   double bestScore = Arrays.stream(allProbs).max().getAsDouble();
  //   int bestIndividual = Arrays.asList(allProbs).indexOf(bestScore);
  //
  //   // TODO: Algorithm for survivor selection: MAKE SURE NO INDIVIDUALS ARE TWICE SELECTED
  //   List<Integer> elimIndividuals = new ArrayList<Integer>();
  //   for (int i = 0; i < numChild; i++)
  //   {
  //     // select random individual
  //     int randomInd = rnd_.nextInt(length);
  //
  //     // ensure that fittest individual is not eliminated
  //     while (randomInd == bestIndividual && elimIndividuals.contains(randomInd))
  //     {
  //       randomInd = rnd_.nextInt(length);
  //     }
  //     // select random individual to be eliminated
  //     elimIndividuals.add(randomInd);
  //   }
  //
  //   int ind = 0;
  //
  //   // remove eliminated individuals
  //   for (int j = 0; j < length; j++)
  //   {
  //     // add individual to new population, if not in eliminated list
  //     if (!(elimIndividuals.contains(j)))
  //     {
  //       newPopulation[ind] = oldPopulation[j];
  //       ind++;
  //     }
  //   }
  //
  //   // check for correct population size
  //   if (newPopulation.length != populationSize)
  //   {
  //     double[][] errorList = new double[0][0];
  //     return errorList;
  //   }
  //
  //   return newPopulation;

  // }

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
    int[] sortedWins = new int[wins.length];
    System.arraycopy(wins, 0, sortedWins, 0, wins.length);
    Arrays.sort(sortedWins);
    int medianWins = sortedWins[wins.length/2];

    ArrayList<Integer> elimIndividuals = new ArrayList<Integer>();

    for (int k = 0; k < length; k++)
    {
      if (wins[k] <= medianWins)
      {
        // put individual k in possible elimination list
        elimIndividuals.add(k);
      }
    }
    int surplus = elimIndividuals.size() - numChild;

    // determine surplus and remove
    Collections.shuffle(elimIndividuals);
    elimIndividuals.subList(0,surplus).clear();

    // sort (reversed) and eliminate from population
    Collections.sort(elimIndividuals, Collections.reverseOrder());
    for (int i = 0; i < elimIndividuals.size(); i++)
    {
      oldPopulation.remove(elimIndividuals.get(i).intValue());
    }


    // check for correct population size
    if (oldPopulation.size() != populationSize)
    {
      System.out.println("Something went wrong in SurvivorSelection' s population size");
      return new ArrayList<Individual>();
    }
    return oldPopulation;
  }
}
