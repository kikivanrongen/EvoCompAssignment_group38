import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ParentSelection
{

  String selectionAlg_;

  public ParentSelection(String algType)
  {
    // Initialize object with correct selection algorithm
    selectionAlg_ = algType;
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
    System.out.println(parentProbs[0]);


    int[] myList = new int[10];
    return myList;
  }


}
