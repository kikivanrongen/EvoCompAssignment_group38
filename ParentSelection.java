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
<<<<<<< HEAD
        linearRanking(population);
        return stochasticUniversalSampling(population, nrParents);
      case "ranked-exp" :
        exponentialRanking(population);
        return stochasticUniversalSampling(population, nrParents);
      default:
        System.out.println("warning no valid parent selection method");
        return battleArenaSelection(population, nrParents);
=======
        // rankedProbs = linearRanking(parentProbs);
        return linearRanking(parentProbs, nrParents);
      case "ranked-exp" :
        return exponentialRanking(parentProbs, nrParents);
        // return battleArenaSelection(rankedProbs, nrParents);
        // return stochasticUniversalSampling(rankedProbs, nrParents);
>>>>>>> cb746270aee9b6db472bb6fb596b369b5b4c885a
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

    // Initialize counters and random double
    int currentParents = 0;
    int i = 0;
    double r = (1.0 / nrSelected) * rnd_.nextDouble();

    // Perform untill all parents are selected
    while (currentParents < nrSelected)
    {
      // if (i >= 100)
      // {
      //   System.out.println("\ncurrentParents, r, i, cumSumProbs");
      //   System.out.println(String.valueOf(currentParents));
      //   System.out.println(String.valueOf(r));
      //   System.out.println(String.valueOf(i));
      //   System.out.println(Arrays.toString(cumSumProbs));
      //   System.out.println(Arrays.toString(parentProbs));
      // }

      while (r <= cumSumProbs[i])
      {
        //TODO: Nigel klopt dit nog???
        selectedParents.add(population.get(i));
        r += (1.0 / nrSelected);
        currentParents += 1;
      }
      i += 1;
    }

    // System.out.println(Arrays.toString(selectedParents));

    // Return chosen parents
    return selectedParents;
  }

<<<<<<< HEAD
  public void linearRanking(ArrayList<Individual> parentProbs)
=======
  public int[] linearRanking(double[] parentProbs, int nrSelected)
>>>>>>> cb746270aee9b6db472bb6fb596b369b5b4c885a
  {
    // Define parameters and array to store new probabilities
<<<<<<< HEAD
    double s = 1.5; //Komt deze uit literatuur? anders veranderen TODO
    int mu = parentProbs.size();
    //double[] rankedProbs = new double[mu];
=======
    int[] selectedParents = new int[nrSelected];
    int mu = parentProbs.length;
    double[] rankedProbs = new double[mu];
    int[] ranks = new int[mu];
    double s = 1.0;
>>>>>>> cb746270aee9b6db472bb6fb596b369b5b4c885a

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
<<<<<<< HEAD
      parentProbs.get(i).prob = ((2 - s) / mu) + ((2 * rank * (s - 1)) / (mu * (mu - 1)));
    }
  }

  public void exponentialRanking(ArrayList<Individual> parentProbs)
  {
    // Define parameters and array to store new probabilities
    int mu = parentProbs.size(); //TODO is dit niet /2?
    // double[] rankedProbs = new double[mu];
=======
      rankedProbs[i] = ((2 - s) / mu) + ((2 * rank * (s - 1)) / (mu * (mu - 1)));
      ranks[i] = rank;
    }

    // Normalize probabilities and make cumulative
    for (int i = 0; i < mu; i++)
    {
      // rankedProbs[i] /= norm_factor;
      if (i > 0)
      {
        rankedProbs[i] += rankedProbs[i - 1];
      }
    }
    // System.out.println(Arrays.toString(rankedProbs));

    // Initialize counters and random double
    int currentParents = 0;
    int iter = 0;
    double r = (1.0 / nrSelected) * rnd_.nextDouble();

    // Perform untill all parents are selected
    while (currentParents < nrSelected)
    {
      while (r <= rankedProbs[iter])
      {
        selectedParents[currentParents] = iter;
        r += (1.0 / nrSelected);
        currentParents += 1;
      }
      iter += 1;
    }

    // Loop over full population
    for (int i = 0; i < nrSelected; i++)
    {
      // Loop over full population
      for (int j = 0; j < mu; j++)
      {
        // Match rank with label of individual
        if (selectedParents[i] == ranks[j])
        {
          selectedParents[i] = j;
          break;
        }
      }
    }

    // Return probabilities
    return selectedParents;
  }

  public int[] exponentialRanking(double[] parentProbs, int nrSelected)
  {
    // Define parameters and array to store new probabilities
    int[] selectedParents = new int[nrSelected];
    int mu = parentProbs.length;
    double[] rankedProbs = new double[mu];
    int[] ranks = new int[mu];
>>>>>>> cb746270aee9b6db472bb6fb596b369b5b4c885a
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
<<<<<<< HEAD
      parentProbs.get(i).prob = 1 - Math.exp(-rank);
      norm_factor += parentProbs.get(i).prob;
=======
      rankedProbs[i] = 1 - Math.exp(-i);
      norm_factor += rankedProbs[i];
      ranks[i] = rank;
>>>>>>> cb746270aee9b6db472bb6fb596b369b5b4c885a
    }

    // Normalize probabilities and make cumulative
    for (int i = 0; i < mu; i++)
    {
<<<<<<< HEAD
      parentProbs.get(i).prob /= norm_factor;
      // total += rankedProbs[i];
    }
=======
      rankedProbs[i] /= norm_factor;
      if (i > 0)
      {
        rankedProbs[i] += rankedProbs[i - 1];
      }
    }
    // System.out.println(Arrays.toString(ranks));
    // System.out.println(Arrays.toString(parentProbs));

    int flag = 1;
    for (int i = 0; i < mu; i++)
    {
      if (ranks[i] != 0)
      {
        flag = 0;
      }
    }
    if (flag == 1)
    {
      flag = ranks[mu + 10];
    }

    // Initialize counters and random double
    int currentParents = 0;
    int iter = 0;
    double r = (1.0 / nrSelected) * rnd_.nextDouble();

    // Perform untill all parents are selected
    while (currentParents < nrSelected)
    {
      while (r <= rankedProbs[iter])
      {
        selectedParents[currentParents] = iter;
        r += (1.0 / nrSelected);
        currentParents += 1;
      }
      iter += 1;
    }

    // Loop over full population
    for (int i = 0; i < nrSelected; i++)
    {
      // Loop over full population
      for (int j = 0; j < mu; j++)
      {
        // Match rank with label of individual
        if (selectedParents[i] == ranks[j])
        {
          selectedParents[i] = j;
          break;
        }
      }
    }

    // Return probabilities
    return selectedParents;
>>>>>>> cb746270aee9b6db472bb6fb596b369b5b4c885a
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
