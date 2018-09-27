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

		int evals = 0;
		int populationSize = 100;
		int nrTraits = 10;

		// INITIALIZE
		// init population with random values between -5 and 5
		double[][] population = new double[populationSize][nrTraits];

		for (int j = 0; j < populationSize; j++)
		{
			for (int k = 0; k < nrTraits; k++)
			{
				population[j][k] = (rnd_.nextDouble() * 10.0) - 5.0; // normalize to [-5, 5]
			}
		}

		// init parent selector object
		ParentSelection parentSelector = new ParentSelection("arena");

		// calculate fitness
		while(evals < evaluations_limit_-200)
		{

			// variables for tracking parent evaluations
			double[] parentProbs = new double[populationSize];
			double[] parentScores = new double[populationSize];
			double maxScore = 0;
			double minScore = 1;

			// Check and save fitness for all parents
			for (int j = 0; j < populationSize; j++)
			{
				// calculate parent scores (not normalized)
				parentScores[j] = (double) evaluation_.evaluate(population[j]);
				evals++;

				// save largest and smallest score for normalization
				if (parentScores[j] > maxScore)
				{
					maxScore = parentScores[j];
				}

				if (parentScores[j] < minScore)
				{
					minScore = parentScores[j];
				}

			}

			// System.out.println("Minimum score obtained in this round: " + minScore);
			// System.out.println("Maximum score obtained in this round: " + maxScore);

			// normalize probabilities
			for (int i = 0; i < populationSize; i++)
			{
				// parentProbs[i] = (parentScores[i] - minScore) / (maxScore - minScore);
				parentProbs[i] = parentScores[i];
			}

			// select parents
			int[] parentsIndices = parentSelector.performSelection(parentProbs);

			// SELECT PARENTS used in creating offspring and randomize
			ArrayList<double[]> selectedParents = new ArrayList<double[]>();

			// add parents selected by selection algorithm
			for (int i = 0; i < parentsIndices.length; i++)
			{
				selectedParents.add(population[parentsIndices[i]]);
			}

			Collections.shuffle(selectedParents);

			// define nr of children and variable to store children
			int numChild = selectedParents.size();
			double[][] children = new double[numChild][nrTraits];

			int firstGroup = numChild;
			boolean unevenParents = (selectedParents.size() % 2) == 1;

			// check for uneven number of parents
			if (unevenParents)
			{
				// separate last three parents for different crossover
				firstGroup = numChild - 3;
			}

			// loop over all couples (two parents)
			for (int ind = 0; ind < firstGroup; ind += 2)
		 	{
				// pick a position to crossover and make 2 children
				int cut = rnd_.nextInt(nrTraits) & Integer.MAX_VALUE;

				for (int j = 0; j < cut; j++)
				{
					 children[ind][j] = selectedParents.get(ind)[j];
					 children[ind + 1][j] = selectedParents.get(ind + 1)[j];
				}

				for (int j = cut; j < nrTraits; j++)
				{
					 children[ind][j] = selectedParents.get(ind + 1)[j];
					 children[ind + 1][j] = selectedParents.get(ind)[j];
				}
	 		}

			// TODO: Kiki maakte 6 kinderen, maar mijn code verwachtte er maar 3 aangezien er 3 ouders zijn.  checken of dit zo nog klopt
			// perform crossover for threesome if present
			if (unevenParents == true)
			{

				// pick a position to crossover
				int cut = rnd_.nextInt(nrTraits) & Integer.MAX_VALUE;
				int ind = firstGroup;

				// create three children
				for (int j = 0; j < cut; j++)
				{
						// TODO: on some runs, an error is thrown here (line 177: java.lang.ArrayIndexOutOfBoundsException: -2)
						children[ind][j] = selectedParents.get(ind)[j];
						children[ind + 1][j] = selectedParents.get(ind + 1)[j];
						children[ind + 2][j] = selectedParents.get(ind + 2)[j];
						// children[ind + 3][j] = selectedParents.get(ind)[j];
						// children[ind + 4][j] = selectedParents.get(ind + 1)[j];
						// children[ind + 5][j] = selectedParents.get(ind + 2)[j];
				}

				for (int j = cut; j < nrTraits; j++)
				{
					children[ind][j] = selectedParents.get(ind + 1)[j];
					children[ind + 1][j] = selectedParents.get(ind + 2)[j];
					children[ind + 2][j] = selectedParents.get(ind)[j];
					// children[ind + 3][j] = selectedParents.get(ind + 2)[j];
					// children[ind + 4][j] = selectedParents.get(ind)[j];
					// children[ind + 5][j] = selectedParents.get(ind + 1)[j];
				}
			}

			// Apply mutation to each child.
			int rnd_idx = 0;
			for(int i=0; i < numChild; i++)
			{
				rnd_idx = rnd_.nextInt(nrTraits);
				double mutationFactor = rnd_.nextDouble() * 2.0 - 1.0;
				children[i][rnd_idx] = children[i][rnd_idx] * mutationFactor; // Kim dit moet anders nog (nu)
			}

			// evaluate scores of all children
			double[] childScores = new double[numChild];
			for (int j = 0; j < numChild; j++)
			{
				childScores[j] = (double) evaluation_.evaluate(children[j]);
				evals++;

				// update largest and smallest score including children
				if (childScores[j] > maxScore)
				{
					maxScore = childScores[j];
				}

				if (childScores[j] < minScore)
				{
					minScore = childScores[j];
				}
			}

			// combine children and parents into full population
			//ArrayList<double[]> oldPopulation = new ArrayList<double[]>();

			double[][] oldPopulation = new double[populationSize + numChild][nrTraits];
			double[] allScores = new double[populationSize + numChild];
			double[] allProbs = new double[populationSize + numChild];

			// copy parents
			for (int i = 0; i < populationSize; i++)
			{
				oldPopulation[i] = population[i];
				allScores[i] = parentScores[i];
			}

			// copy children
			for (int i = 0; i < numChild; i++)
			{
				oldPopulation[populationSize + i] = children[i];
				allScores[populationSize + i] = childScores[i];
			}

			// normalize probabilities
			for (int i = 0; i < populationSize + numChild; i++)
			{
				allProbs[i] = (allScores[i] - minScore) / (maxScore - minScore);
			}

			//shuffle population
			ArrayList<Integer> shuffleArray = new ArrayList<Integer>();

			for (int i = 0; i < populationSize + numChild; i++)
			{
				shuffleArray.add(i);
			}

			Collections.shuffle(shuffleArray);


			// ELIMINATE numChild individuals
			int elim = 0;
			int idx = 0;
			int[] eliminated = new int[populationSize + numChild];
			Arrays.fill(eliminated, 0);

			// TEMPORARY
			// calculate median probability - to select half of the population
			Arrays.sort(allProbs);
			double threshold = allProbs[populationSize];

			// eliminate until old population size is reached
			while (elim < numChild)
			{
				// TODO: check sign
				//if (eliminated[shuffleArray.get(idx)] == 0 && middle_value <= allProbs[shuffleArray.get(idx)])
				if (eliminated[shuffleArray.get(idx)] == 0 && allProbs[shuffleArray.get(idx)] <= threshold)
				{
					elim++;
					eliminated[shuffleArray.get(idx)] = 1;
				}

				// update counter, reset if necessary
				idx++;
				if (idx == populationSize + numChild)
				{
					idx = 0;
				}
			}

			// update population to all survivors
			for (int i = 0, j = 0; i < populationSize + numChild; i++)
			{
				if (eliminated[shuffleArray.get(i)] == 0)
				{
					population[j] = oldPopulation[shuffleArray.get(i)];
					j++;
				}
			}
		}
	}
}
