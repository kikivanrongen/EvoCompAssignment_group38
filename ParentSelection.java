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
    // Perform selection using correct algorithm
    switch (selectionAlg_)
    {
      case "arena" :
        return battleArenaSelection(population, nrParents);
      case "ranked-lin" :
        linearRanking(population);
        return stochasticUniversalSampling(population, nrParents);
      case "ranked-exp" :
        exponentialRanking(population);
        return stochasticUniversalSampling(population, nrParents);
      default:
        System.out.println("warning no valid parent selection method");
        return battleArenaSelection(population, nrParents);
    }
  }

  public ArrayList<Individual> stochasticUniversalSampling(ArrayList<Individual> population, int nrSelected)
  {
    int mu = population.size();
    ArrayList<Individual> selectedParents = new ArrayList<Individual>();

    // Calculate cumulative sum of probabilities
    double[] cumSumProbs = new double[mu];
    cumSumProbs[0] = population.get(0).prob;
    for (int i = 1; i < mu; i++)
    {
      cumSumProbs[i] = cumSumProbs[i-1] + population.get(i).prob;
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
        selectedParents.add(population.get(i));
        r += (1.0 / nrSelected);
        currentParents += 1;
      }
      i += 1;
    }

    // Return chosen parents
    return selectedParents;
  }

  public void linearRanking(ArrayList<Individual> parentProbs)
  {

    // Define parameters and array to store new probabilities
    double s = 1.5; //Komt deze uit literatuur? anders veranderen TODO
    int mu = parentProbs.size();
    //double[] rankedProbs = new double[mu];

    // Loop over full population
    for (int i = 0; i < mu; i++)
    {
      int rank = 0;

      // Loop over full population
      for (int j = 0; j < mu; j++)
      {
        // Count how many individuals score worse
        if (parentProbs.get(j).prob < parentProbs.get(i).prob)
        {
          rank++;
        }
      }

      // Calculate probability according to ranking
      parentProbs.get(i).prob = ((2 - s) / mu) + ((2 * rank * (s - 1)) / (mu * (mu - 1)));
    }
  }

  public void exponentialRanking(ArrayList<Individual> parentProbs)
  {
    // Define parameters and array to store new probabilities
    int mu = parentProbs.size(); //TODO is dit niet /2?
    // double[] rankedProbs = new double[mu];
    double norm_factor = 0.0;

    // Loop over full population
    for (int i = 0; i < mu; i++)
    {
      int rank = 0;

      // Loop over full population
      for (int j = 0; j < mu; j++)
      {
        // Count how many individuals score worse
        if (parentProbs.get(j).prob < parentProbs.get(i).prob)
        {
          rank++;
        }
      }

      // Calculate probability according to ranking
      parentProbs.get(i).prob = 1 - Math.exp(-rank);
      norm_factor += parentProbs.get(i).prob;
    }

    // double total = 0.0;
    // Normalize probabilities
    for (int i = 0; i < mu; i++)
    {
      parentProbs.get(i).prob /= norm_factor;
      // total += rankedProbs[i];
    }
  }

  public ArrayList<Individual> battleArenaSelection(ArrayList<Individual> population, int nrSelected)
  {
    // Initialize selection parameters
    int nrBattling = 10;
    ArrayList<Individual> selectedParents = new ArrayList<Individual>();
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
        int battlingParent = rnd_.nextInt(population.size() - 1);
        // Sanne: parent can battle himself? TODO

        if (population.get(battlingParent).prob > winnerProb)
        {
          winner = battlingParent;
          winnerProb = population.get(battlingParent).prob;
        }
      }

      // Store winner of battle
      selectedParents.add(population.get(winner));

    }


    return selectedParents;
  }


}
