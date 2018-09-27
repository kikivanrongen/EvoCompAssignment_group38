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

  public int[] performSelection(double[] parentProbs)
  {
    // Perform selection using correct algorithm
    switch (selectionAlg_)
    {
      case "arena" :
        return battleArenaSelection(parentProbs);
    }

    // Return empty list if no case has been used
    int[] errorList = new int[0];
    return errorList;
  }

  public int[] battleArenaSelection(double[] parentProbs)
  {
    // Initialize selection parameters
    int nrSelected = 50;
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
