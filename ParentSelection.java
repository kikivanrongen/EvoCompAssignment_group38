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
    // System.out.println("BAS");

    int nrSelected = 10;
    int nrBattling = 10;
    int[] selectedParents = new int[nrSelected];
    double winnerProb;
    int winner = 0;

    for (int i = 0; i < nrSelected; i++)
    {

      winnerProb = -100.0;

      for (int j = 0; j < nrBattling; j++)
      {
        int battlingParent = rnd_.nextInt(parentProbs.length - 1);
        // System.out.println(battlingParent);
        // System.out.println(parentProbs[battlingParent]);

        if (parentProbs[battlingParent] > winnerProb)
        {
          winner = battlingParent;
          winnerProb = parentProbs[battlingParent];
        }
      }

      selectedParents[i] = winner;

    }

    // for (int i = 0; i < nrSelected; i++)
    // {
    //   System.out.println(selectedParents[i]);//, parentProbs[i]);
    // }

    return selectedParents;
  }


}
