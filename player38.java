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

	public static int nrTraits = 10;


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
		int nrParents = populationSize / 2;
		int nrTraits = player38.nrTraits;

		// List that contains all options
		String[] options_mutation = {"uniform_mutation","gauss_mutation"};

		// init objects for the algorithm
		ParentSelection parentSelector = new ParentSelection("arena");
		// ParentSelection parentSelector = new ParentSelection("ranked-lin");
		// ParentSelection parentSelector = new ParentSelection("ranked-exp");
		Recombination recombinator = new Recombination("discrete-pointwise");
		SurvivorSelection survivorSelector = new SurvivorSelection("roundRobin");
		Mutation mutator = new Mutation("gauss_mutation");

		/*
		* INITIALIZATION
		*/

		// init population with random values between -5 and 5
		double[][] population = new double[populationSize][nrTraits];

		for (int j = 0; j < populationSize; j++)
		{
			for (int k = 0; k < nrTraits; k++)
			{
				population[j][k] = (rnd_.nextDouble() * 10.0) - 5.0; // normalize to [-5, 5]
			}
		}

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

			/*
			* PARENT SELECTION
			*/

			int[] parentsIndices = parentSelector.performSelection(parentProbs, nrParents);

			// store parents selected by selection algorithm
			ArrayList<double[]> selectedParents = new ArrayList<double[]>();

			for (int i = 0; i < parentsIndices.length; i++)
			{
				selectedParents.add(population[parentsIndices[i]]);
			}

			Collections.shuffle(selectedParents);

			/*
			* RECOMBINATION
			*/

			double[][] children = recombinator.performRecombination(selectedParents);

			/*
			* MUTATION
			*/

			int numChild = children.length;

			children = mutator.performMutation(children);

			/*
			* EVALUATION
			*/
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

			/*
			* SURVIVOR SELECTION
			*/

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

			double[][] newPopulation = survivorSelector.performSurvivorSelection(oldPopulation, allProbs, numChild);
			population = newPopulation;

		}
	}
}
