import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
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

	public static void main(String[] args) {
		System.out.println("Test");
		//run();
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


		// init population DONE
		double[][] population = new double[100][10];

		for (int j = 0; j < 100; j++)
		{
			for (int k = 0; k < 10; k++)
			{
				population[j][k] = rnd_.nextDouble();
			}
		 }

		// calculate fitness
		while(evals<evaluations_limit_-200)
		{

			//Check of dit zo kan of dat init vereist is.
			// Calculate fitness Potential parents
				double[] parentScores = new double[100];
				double lenParentProbs = 0.0;


				for (int j = 0; j < 100; j++)
				{
					parentScores[j] = (double) evaluation_.evaluate(population[j]);
					evals++;
					lenParentProbs += parentScores[j] * parentScores[j];
				}

				// calc length of probabilities vector
				lenParentProbs = Math.sqrt(lenParentProbs);
				double[] parentProbs = new double[100];

				// normalize probabilities TODO: Aanpassen zodat schaalt naar hoogste waarde. [nigel]
				for (int i = 0; i < 100; i++)
				{
					parentProbs[i] = parentScores[i] / lenParentProbs;
				}


		    // Select parents
		    ArrayList<double[]> selectedParents = new ArrayList<double[]>();

		    for (int i = 0; i < 100; i++)
		    {
					if (rnd_.nextDouble() <= parentProbs[i]) // TODO: klopt de sign zo?
					{
						selectedParents.add(population[i]);
					}
		    }

		    int num_child = selectedParents.size();

		    Collections.shuffle(selectedParents);

				double[][] children = new double[num_child][10];

		 	 // TODO: [Kiki] Aanpassen voor oneven ouders ouderss
		 	 for (int ind = 0; ind < num_child-1; ind += 2)
		 	 {
		 		 // pick a position to crossover and make 2 children
		 		 int cut = rnd_.nextInt(10)  & Integer.MAX_VALUE;

		 		 for (int j = 0; j < cut; j++)
		 		 {
		 				 children[ind][j] = selectedParents.get(ind)[j];
		 				 children[ind+1][j] = selectedParents.get(ind+1)[j];
		 		 }

		 		 for (int j = cut; j < 10; j++)
		 		 {
		 				 children[ind][j] = selectedParents.get(ind+1)[j];
		 				 children[ind+1][j] = selectedParents.get(ind)[j];
		 		 }

		 	}



			// Apply mutation to each child.
			int rnd_idx = 0;
			for(int i=0; i<children.length; i++)
			{
				rnd_idx = rnd_.nextInt(10);
				children[i][rnd_idx] = rnd_.nextDouble();
			}

			double[] childScores = new double[num_child];

	    for (int j = 0; j < num_child; j++)
	    {
				childScores[j] = (double) evaluation_.evaluate(children[j]);
				evals++;
			}

			double[][] oldPopulation = new double[100+num_child][10];
			double[] allScores = new double[100+num_child];

			//In deze loopjes bijhouden wat de hoogste waarde is en dan daarnaar schalen??
			for (int i =0; i<100; i++)
		  {
				oldPopulation[i] = population[i];
				allScores[i] = parentScores[i];
		  }
	    for (int i = 0; i<num_child; i++)
	    {
				oldPopulation[100+i] = children[i];
				allScores[100] = childScores[i];
	    }

			// TODO: Scale Probs. [nigel?]
			double[] allProbs = allScores;

		  // Elimininate num_child individuals
      int elim = 0;
    	int idx = 0;

			for (int i = 0; i<oldPopulation.length; i++)
			{
			// Children have a lower prob to be eliminated since they are looped through last.
			  if (idx<100)
				{
					if (elim < num_child-1)
					{
						if (rnd_.nextDouble() <= allProbs[i])
						{
							population[idx] = oldPopulation[i];
							elim ++;
							idx ++;
						}
					} else {
						population[idx] = oldPopulation[i];
						idx ++;
					}
				}
			}


	    }
	}
}
