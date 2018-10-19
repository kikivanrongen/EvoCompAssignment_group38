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
		String[] parselopts = {"arena", "ranked-lin", "ranked-exp"};
		String[] recopts = {"discrete-pointwise", "discrete-tailswap", "arithmetic-whole", "arithmetic-simple", "arithmetic-single", "blendcrossover", "none"};
		String[] mutopts = {"uniform", "gauss", "uncorrelated-onestep", "uncorrelated-nstep"};
		String[] surselopts = {"worst", "elitism", "roundRobin"};

	//	int[] funcs = new int[]{0,2,2,2}; // cigar aw__1, number 1 best
	//	int[] funcs = new int[]{0,5,0,2}; // schaffers bl_un, number 1 best
		// int[] funcs = new int[]{0,4,2,2}; // katsura a1_1, number 1 best


		// int[] funcs = new int[]{0,3,1,2}; // cigar as_ga
		// int[] funcs = new int[]{0,4,1,2}; // Katsuura a1_ga

		// for submission
		int[] funcs = new int[]{0,5,1,2};

		// Deze gebruiken met vastgestelde waardes voor alpha als je wil loopen door alle mogelijke combinaties van functies
	//	int[][] funcIndex = new int[][]{{0, 0, 0, 2},{0, 1, 0, 2},{0, 2, 0, 2},{0, 3, 0, 2},{0, 4, 0, 2},{0, 5, 0, 2},{0, 0, 1, 2},{0, 1, 1, 2},{0, 2, 1, 2},{0, 3, 1, 2},{0, 4, 1, 2},{0, 5, 1, 2},{0, 0, 2, 2},{0, 1, 2, 2},{0, 2, 2, 2},{0, 3, 2, 2},{0, 4, 2, 2},{0, 5, 2, 2},{0, 0, 3, 2},{0, 1, 3, 2},{0, 2, 3, 2},{0, 3, 3, 2},{0, 4, 3, 2},{0, 5, 3, 2}};


		// double[] alphas = new double[]{0.1, 0.3, 0.5}; //0 1 2
		double[] alphas = new double[]{0.1, 0.4, 0.5}; //0 1 2
		double[] stds = new double[]{0.01, 0.05, 0.1, 0.2, 0.5}; //0 1 2 3 4

		int[][] setups = new int[][] {
			{0,0}, {0,1},	{0,2}, {1,0},	{1,1}, {1,2},
			{2,0}, {2,1},	{2,2}, {3,0}, {3,1}, {3,2},
			{4,0}, {4,1},	{4,2} };

		int iter = 1;//Integer.parseInt(System.getProperty("iter"));
		int[] setup = setups[iter];
		double alpha = alphas[setup[1]];
		double std = stds[setup[0]];
		System.out.println("alpha: " + alpha);
		System.out.println("sigma: " + std);

		int evals = 0;
		int populationSize = 100;
		int nrTraits = player38.nrTraits;
		String mutationType = mutopts[funcs[2]];

		// init objects for the algorithm
		// ParentSelection parentSelector = new ParentSelection("arena");
		ParentSelection parentSelector = new ParentSelection(parselopts[funcs[0]]);
		Recombination recombinator = new Recombination(recopts[funcs[1]]);
		Mutation mutator = new Mutation(mutationType);
		SurvivorSelection survivorSelector = new SurvivorSelection(surselopts[funcs[3]]);


		// Diversity
		//System.out.println(recopts[funcs[1]]);
		//System.out.println(mutationType);

		// Array for storing values for diversity
		ArrayList<Double> diversityArray = new ArrayList<Double>();

		// init population with random values between -5 and 5
		ArrayList<Individual> population = new ArrayList<Individual>();

		for (int j = 0; j < populationSize; j++)
		{
			Individual unit = new Individual(nrTraits,mutationType, std);
			double[] values = new double[nrTraits];
			for (int k = 0; k < nrTraits; k++)
			{
				values[k] = (rnd_.nextDouble() * 10.0) - 5.0; // normalize to [-5, 5]
			}
			unit.genome = values;
			population.add(unit);
		}

		int generations = -1;

		// calculate fitness
		while(evals < evaluations_limit_-200)
		{

			// CALCULATING DIVERSITY
			// niet weggooien plzzzz
			// Diversity is measured per generation, as the total Manhattan distance between all points

			generations +=1;
			double diversity = 0;

			// for (int j = 0; j < populationSize; j++)
			// {
			// 	for (int k = 0; k < populationSize; k++)
			// 	{
			// 		double[] individual1 = population.get(j).genome;
			// 		double[] individual2 = population.get(k).genome;
			//
			// 		for (int l = 0; l < individual1.length; l++)
			// 		{
			// 			diversity += Math.abs(individual1[l] - individual2[l]);
			// 		}
			// 	}
			// }
			// // diversityArray.add(diversity);
			// System.out.println(diversity);

			// Compute scores per individual
			double maxScore = 0;
			double minScore = 1;

			// Check and save fitness for all individuals
			for (int j = 0; j < populationSize; j++)
			{
				//TODO: rounsRobin geeft hier error
				population.get(j).score = (double) evaluation_.evaluate(population.get(j).genome);
				evals++;

				// save largest and smallest score for normalization
				if (population.get(j).score > maxScore){maxScore = population.get(j).score;}
				if (population.get(j).score < minScore)	{minScore = population.get(j).score;}
			}

			// Select parents from population
			ArrayList<Individual> selectedParents = parentSelector.performSelection(population, 50);
			Collections.shuffle(selectedParents);

			// Recombinate parents into children
			double[][] genomes = recombinator.performRecombination(selectedParents, alpha);
			int numChild = genomes.length;

			ArrayList<Individual> children = new ArrayList<Individual>();
			for (int j = 0; j < numChild; j++)
			{
				Individual unit = new Individual(nrTraits,mutationType, std);
				unit.genome = genomes[j];
				children.add(unit);
			}

			// Apply mutation to each child.
			children = mutator.performMutation(children, std);

			// Evaluate children
			for (int j = 0; j < numChild; j++) {
				children.get(j).score = (double) evaluation_.evaluate(children.get(j).genome);
				evals++; }

			// Combine children and parents into new population
			ArrayList<Individual> oldPopulation = population;
			for (int i = 0; i < numChild; i++) { oldPopulation.add(children.get(i));	}

			// Eliminate num child individuals
			Collections.shuffle(oldPopulation);
			population = survivorSelector.performSurvivorSelection(oldPopulation,numChild);

		}
	}
}
