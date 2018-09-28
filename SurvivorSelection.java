import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ParentSelection
{

  String survivorAlg_;
  Random rnd_;

  public SurvivorSelection(String algType)
  {
    // Initialize object with correct selection algorithm
    selectionAlg_ = algType;
    rnd_ = new Random();
    rnd_.setSeed(42);
  }

  public double[][] performSelection(double[][] oldPopulation, double[] allScores, int numChild)
  {
    // Perform selection using correct algorithm
    switch (selectionAlg_)
    {
      case "worst" :
        return replaceWorst(oldPopulation, allScores, numChild);
      case "elitism" :
        return elitism(oldPopulation, allScores, numChild);
    }

    // Return empty list if no case has been used
    double[][] errorList = new double[0][0];
    return errorList;
  }

  // individuals with worst fitness scores are eliminated
  public double[][] replaceWorst(double[][] oldPopulation, double[] allScores, int numChild)
  {

    // set parameters
    int populationSize = 100;
    int nrTraits = 10;

    // sort scores and the population accordingly
    double[] sortedScores = Arrays.sort(allScores);
    double[][] sortedPopulation = new double[populationSize + numChild][nrTraits];

    for (int i = 0; i < oldPopulation.length; i++)
    {
      sortedPopulation[i] = oldPopulation.get(sortedScores[i]);
    }

    // remove worst individuals
    double[][] newPopulation = sortedPopulation.removeRange(0,numChild);

    return newPopulation

  }

  // keep the individual with the best fitness value
  public double[][] elitism(double[][] oldPopulation, double[] allScores, int numChild)
  {
    int populationSize = 100;
    int nrTraits = 10;
    double[][] newPopulation = new double[populationSize][nrTraits]

    // find maximum fitness value and corresponding index
    double bestScore = Collections.max(allScores);
    int bestInd = oldPopulation.get(bestScore);

    int[] elimIndividuals = new int[numChild];
    for (i = 0; i < numChild; i++)
    {
      // select random individual
      int randomInd = rnd_.nextInteger(100);

      // ensure that fittest individual is not eliminated
      while randomInd == bestInd
      {
        randomInd = rnd_.nextInteger(100);
      }
      // select random individual to be eliminated
      elimIndividuals[i] = randomInd;
    }

    // REMOVE ELIMINATED INDIVIDUALS FROM POPULATION

  }


}
