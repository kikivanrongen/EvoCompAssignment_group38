import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ParentSelection
{

  String selectionAlg_;
  Random rnd_;

  public ParentSelection(String algType)
  {
    // Initialize object with correct selection algorithm
    selectionAlg_ = algType;
    rnd_ = new Random();
    rnd_.setSeed(42);
  }

  public ArrayList<Individual> performSelection(ArrayList<Individual> population, int nrParents)
  {
    double[] rankedProbs;

    // Perform selection using correct algorithm
    switch (selectionAlg_)
    {
      case "arena" :
        return battleArenaSelection(population, nrParents);
      case "ranked-lin" :
        rankedProbs = linearRanking(population);
        return stochasticUniversalSampling(rankedProbs, nrParents);
      case "ranked-exp" :
        rankedProbs = exponentialRanking(population);
        return stochasticUniversalSampling(rankedProbs, nrParents);
      default:
        System.out.println("warning no valid parent selection method");
        return battleArenaSelection(population, nrParents);
    }
  }

  public ArrayList<Individual> stochasticUniversalSampling(ArrayList<Individual> parentProbs, int nrSelected)
  {
    int mu = parentProbs.size();

    // Calculate cumulative sum of probabilities
    double[] cumSumProbs = new double[mu];
    cumSumProbs[0] = population.get(0).probs;
    for (int i = 1; i < mu; i++)
    {
      cumSumProbs[i] = cumSumProbs[i-1] + population.get(i).probs;
    }

    // Initialize counters and radius
    int currentParents = 0;
    int i = 0;
    double r = (1.0 / nrSelected) * rnd_.nextDouble();

    // Perform untill all parents are selected
    while (currentParents < nrSelected)
    {
      while (r <= cumSumProbs[i])
      {
        //TODO: Nigel klopt dit nog???
        selectedParents.add(oldPopulation.get(i);
        r += (1.0 / nrSelected);
        currentParents += 1;
      }
      i += 1;
    }

    // Return chosen parents
    return selectedParents;
  }

  public ArrayList<Individual> linearRanking(ArrayList<Individual> parentProbs)
  {

    // Define parameters and array to store new probabilities
    double s = 1.5; //Komt deze uit literatuur? anders veranderen TODO
    int mu = parentProbs.size();
    double[] rankedProbs = new double[mu];

    // Loop over full population
    for (int i = 0; i < mu; i++)
    {
      int rank = 0;

      // Loop over full population
      for (int j = 0; j < mu; j++)
      {
        // Count how many individuals score worse
        if (parentProbs.get(j).probs < parentProbs.get(i).probs)
        {
          rank++;
        }
      }

      // Calculate probability according to ranking
      rankedProbs.get(i).probs = ((2 - s) / mu) + ((2 * rank * (s - 1)) / (mu * (mu - 1)));
    }

    // Return probabilities
    return rankedProbs;
  }

  public ArrayList<Individual> exponentialRanking(ArrayList<Individual> parentProbs)
  {
    // Define parameters and array to store new probabilities
    int mu = parentProbs.size(); //TODO is dit niet /2?
    double[] rankedProbs = new double[mu];
    double norm_factor = 0.0;

    // Loop over full population
    for (int i = 0; i < mu; i++)
    {
      int rank = 0;

      // Loop over full population
      for (int j = 0; j < mu; j++)
      {
        // Count how many individuals score worse
        if (parentProbs.get(j).probs < parentProbs.get(i).probs)
        {
          rank++;
        }
      }

      // Calculate probability according to ranking
      rankedProbs.get(i).probs = 1 - Math.exp(-rank);
      norm_factor += rankedProbs.get(i).probs;
    }

    // double total = 0.0;
    // Normalize probabilities
    for (int i = 0; i < mu; i++)
    {
      rankedProbs.get(i).probs /= norm_factor;
      // total += rankedProbs[i];
    }
    // TODO: waarschijnlijk kan t void zijn
    return rankedProbs;
  }

  public int[] battleArenaSelection(double[] parentProbs, int nrSelected)
  {
    // Initialize selection parameters
    int nrBattling = 5;
    int[] selectedParents = new int[nrSelected];
    double winnerProb;
    int winner = 0;

    // Perform as much battles as needed parents
    for (int i = 0; i < nrSelected; i++)
    {
      // Reset highest probability
      winnerProb = -100.0;

      // Find highest probability in participating individuals
      for (int j = 0; j < nrBattling; j++)
      {
        int battlingParent = rnd_.nextInt(parentProbs.length - 1);

        if (parentProbs[battlingParent] > winnerProb)
        {
          winner = battlingParent;
          winnerProb = parentProbs[battlingParent];
        }
      }

      // Store winner of battle
      selectedParents[i] = winner;

    }

    // Return all winners
    return selectedParents;
  }


}
