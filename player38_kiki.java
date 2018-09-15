import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Collections;

public class player38 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;

	public player38()
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
		    	if (rnd_.nextDouble() >= parentProbs[i])
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

        // check for uneven number of parents
        if (num_child % 2 == 1){

          // recombine last three parents and create three children

        }

		    for (int i = 0; i < Math.floor(num_child/2); i++)
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

		    // TODO: fix the lost parent in case of uneven number of parents?

                    // Apply mutation to each child. Sanne:Willen we dit echt? increased randomness extreem!
                    for(int i=0; i<children.size(); i++)
                    {
                        rnd_idx = rnd_.nextInt()%10;
                        children[i][rnd_idx] = rnd_.nextDouble();
                    }

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
	}
}
