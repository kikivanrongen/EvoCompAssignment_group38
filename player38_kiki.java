import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;
import java.lang.Math;

public class player38_kiki implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;

	public player38_kiki()
	{
		rnd_ = new Random();
	}

	//public ArrayList generateRandomInitialSample

	public static void main(String[] args)
	{
		System.out.println("Test");
	}

	public void setSeed(long seed)
	{
		// Set seed of algortihms random process
		rnd_.setSeed(seed);
	}

	public void setEvaluation(ContestEvaluation evaluation)
	{
		// Set evaluation problem used in the run
		evaluation_ = evaluation;

		// Get evaluation properties
		Properties props = evaluation.getProperties();
        // Get evaluation limit
        evaluations_limit_ = Integer.parseInt(props.getProperty("Evaluations"));
		// Property keys depend on specific evaluation
		// E.g. double param = Double.parseDouble(props.getProperty("property_name"));
        boolean isMultimodal = Boolean.parseBoolean(props.getProperty("Multimodal"));
        boolean hasStructure = Boolean.parseBoolean(props.getProperty("Regular"));
        boolean isSeparable = Boolean.parseBoolean(props.getProperty("Separable"));

		// Do sth with property values, e.g. specify relevant settings of your algorithm
        if(isMultimodal){
            // Do sth
        }else{
            // Do sth else
        }
    }

	public void run()
	{
		// Run your algorithm here

        int evals = 0;

        // init population
		double[][] population = new double[100][10];

		for (int j = 0; j < 100; j++)
		{
		    for (int k = 0; k < 10; k++)
		    {
		    	population[j][k] = rnd_.nextDouble();
		    }
		}

		// calculate fitness
	    while (evals < evaluations_limit_)
		{
	        // Select parents
		    double[] parentProbs = new double[100];

		    for (int j = 0; j < 100; j++)
		    {
		    	parentProbs[j] = (double) evaluation_.evaluate(population[j]);
		    }

		    // TODO: Scale probs to contain no zeros and no ones, between 0 and 1.

		    // Select parents
		    ArrayList<double[]> selectedParents = new ArrayList<double[]>();

		    for (int i = 0; i < 100; i++)
		    {
		    	if (rnd_.nextDouble() >= parentProbs[i]) // moet dit niet een bepaalde threshold zijn afhankelijk van de fitness??
				{
				    selectedParents.add(population[i]);
				}
		    }

		    int num_child = selectedParents.size();

		    // Apply crossover / mutation operators

		    // Shuffle parents to increase diversity
		    Collections.shuffle(selectedParents);
		    double[][] children = new double[num_child][10];
		    int idx = 0;

				int firstGroup = num_child;
				boolean unevenParents = false;

        // check for uneven number of parents
        if (num_child % 2 == 1)
				{
					// seperate last three parents for different crossover
					firstGroup = num_child - 3;
					unevenParents = true;
        }

		    for (int i = 0; i < firstGroup/2; i++)
		    {
				        // pick a position to crossover and make 2 children
						int cut = rnd_.nextInt()%10;

						for (int j = 0; j < cut; j++)
						{
						    children[idx][j] = population[idx][j];
						    children[idx+1][j] = population[idx+1][j];
						}

						for (int j = cut; j < 10; j++)
						{
						    children[idx][j] = population[idx+1][j];
						    children[idx+1][j] = population[idx][j];
						}

						idx += 2;
		    }

				if (unevenParents == true)
				{

					// pick a position to crossover
					int cut = rnd_.nextInt()%10;

					// create six children
					for (int j = 0; j < cut; j++)
					{

							children[idx][j] = population[idx][j];
							children[idx+1][j] = population[idx][j];
							children[idx+2][j] = population[idx+1][j];
							children[idx+3][j] = population[idx+1][j];
							children[idx+4][j] = population[idx+2][j];
							children[idx+5][j] = population[idx+2][j];

					}

					for (int j = cut; j < 10; j++)
					{

						children[idx][j] = population[idx+1][j];
						children[idx+1][j] = population[idx+2][j];
						children[idx+2][j] = population[idx][j];
						children[idx+3][j] = population[idx+2][j];
						children[idx+4][j] = population[idx][j];
						children[idx+5][j] = population[idx+1][j];

					}
				}

        // Apply mutation to each child. Sanne:Willen we dit echt? increased randomness extreem!
        // for(int i=0; i<children.size(); i++)
        // {
        //     int rnd_idx = rnd_.nextInt()%10;
        //     children[i][rnd_idx] = rnd_.nextDouble();
        // }

	        // Select survivors
		    double[] childProbs = new double[num_child];

		    for (int j = 0; j < 100; j++)
		    {
		    	childProbs[j] = (double) evaluation_.evaluate(population[j]);
		    }

		    // TODO: Scale probs combined with parentProbs

		    // TODO: Elimininate num_child individuals

	        evals++;

	    }

			/*
			This function sorts the population, based on normalized fitness values. All
			individuals are assigned a probability to be selected as parent, according to
			their rank. Probabilities are exponentially decaying and add up to 1.
			*/

			/* TODO: Hoe zorg ik ervoor dat het juiste individu geselecteerd wordt als
			de volgorde niet meer klopt (doordat het nu gesorteerd is op prob.)
			*/

			public ArrayList<double> rankBasedSelection(int popSize, double[] parentProbabilities)
			{

				// create new array with sorted parent probabilities
				double[] sortedParentProbs = new double[popSize];
				sortedParentProbs = Arrays.sort(parentProbabilities);

				double[] selectionProbs = new double[popSize];
				double[] normSelectionProbs = new double[popSize];
				double normFactor = 0;

				// calculate parent selection probability according to rank
				for (int i = 0; i < popSize; i++)
				{
					selectionProbs[i] = 1 - Math.exp(i);
					normFactor += selectionProbs[i];
				}

				// normalize result for unity purposes
				for (int j = 0; j < popSize; j++)
				{
					normSelectionProbs[j] = selectionProbs[j] / normFactor;
					System.out.print(normSelectionProbs[j]);
				}

				return normSelectionProbs;

			}
	}
}
