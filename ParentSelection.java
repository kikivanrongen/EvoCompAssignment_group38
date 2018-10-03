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

  public int[] performSelection(double[] parentProbs, int nrParents)
  {
    double[] rankedProbs;

    // Perform selection using correct algorithm
    switch (selectionAlg_)
    {
      case "arena" :
        return battleArenaSelection(parentProbs, nrParents);
      case "ranked-lin" :
        rankedProbs = linearRanking(parentProbs);
        return stochasticUniversalSampling(rankedProbs, nrParents);
      case "ranked-exp" :
        rankedProbs = exponentialRanking(parentProbs);
        return stochasticUniversalSampling(rankedProbs, nrParents);
    }

    // Return empty list if no case has been used
    int[] errorList = new int[0];
    return errorList;
  }

  public int[] stochasticUniversalSampling(double[] parentProbs, int nrSelected)
  {
    int mu = parentProbs.length;
    int[] selectedParents = new int[nrSelected];

    // Calculate cumulative sum of probabilities
    double[] cumSumProbs = new double[mu];
    cumSumProbs[0] = parentProbs[0];
    for (int i = 1; i < mu; i++)
    {
      cumSumProbs[i] = cumSumProbs[i-1] + parentProbs[i];
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
        selectedParents[currentParents] = i;
        r += (1.0 / nrSelected);
        currentParents += 1;
      }
      i += 1;
    }

    // Return chosen parents
    return selectedParents;
  }

  public double[] linearRanking(double[] parentProbs)
  {

    // Define parameters and array to store new probabilities
    double s = 1.5;
    int mu = parentProbs.length;
    double[] rankedProbs = new double[mu];

    // Loop over full population
    for (int i = 0; i < mu; i++)
    {
      int rank = 0;

      // Loop over full population
      for (int j = 0; j < mu; j++)
      {
        // Count how many individuals score worse
        if (parentProbs[j] < parentProbs[i])
        {
          rank++;
        }
      }

      // Calculate probability according to ranking
      rankedProbs[i] = ((2 - s) / mu) + ((2 * rank * (s - 1)) / (mu * (mu - 1)));
    }

    // Return probabilities
    return rankedProbs;
  }

  public double[] exponentialRanking(double[] parentProbs)
  {

    // Define parameters and array to store new probabilities
    int mu = parentProbs.length;
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
        if (parentProbs[j] < parentProbs[i])
        {
          rank++;
        }
      }

      // Calculate probability according to ranking
      rankedProbs[i] = 1 - Math.exp(-rank);
      norm_factor += rankedProbs[i];
    }

    // double total = 0.0;
    // Normalize probabilities
    for (int i = 0; i < mu; i++)
    {
      rankedProbs[i] /= norm_factor;
      // total += rankedProbs[i];
    }
    // System.out.println(String.valueOf(total));

    // Return probabilities
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
