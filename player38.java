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
		int nrTraits = player38.nrTraits;
		mutationType = "uniform_mutation"

		// init objects for the algorithm
		// ParentSelection parentSelector = new ParentSelection("arena");
		ParentSelection parentSelector = new ParentSelection("arena");
		Recombination recombinator = new Recombination("discrete-pointwise");
		Mutation mutator = new Mutation(mutationType);
		SurvivorSelection survivorSelector = new SurvivorSelection("worst");
		/*
		* INITIALIZATION
		*/

		// init population with random values between -5 and 5
		ArrayList<Individual> population = new Arraylist<Individual>();

		for (int j = 0; j < populationSize; j++)
		{
			Individual unit = new Individual(nrTraits,mutationType);
			double[] values = new double[nrTraits];
			for (int k = 0; k < nrTraits; k++)
			{
				values[k] = (rnd_.nextDouble() * 10.0) - 5.0; // normalize to [-5, 5]
			}
			unit.genome = values;
			population.add(unit);
		}

		// calculate fitness
		while(evals < evaluations_limit_-200)
		{

			// Compute scores per individual
			double maxScore = 0;
			double minScore = 1;

			// Check and save fitness for all individuals
			for (int j = 0; j < populationSize; j++)
			{
				population[j].score = (double) evaluation_.evaluate(population[i].genome);
				evals++;

				// save largest and smallest score for normalization
				if (population[j].score > maxScore){maxScore = population[j].score;}
				if (population[j].score < minScore)	{minScore = population[j].score;}
			}

			// Select parents from population
			//TODO: adapt parentSelection methods to ArrayList<Individual>
			ArrayList<Individual> selectedParents = parentSelector.performSelection(population, 50);
			Collections.shuffle(selectedParents);

			// Recombinate parents into children
			//TODO: adapt recombination naast discrete pointwise
			double[][] genomes = recombinator.performRecombination(selectedParents);
			int numChild = genomes.length;

			ArrayList<Individual> children = new Arraylist<Individual>();
			for (int j = 0; j < numChild; j++)
			{
				Individual unit = new Individual(nrTraits,mutationType);
				unit.genome = genomes[j];
				children.add(unit);
			}

			// Apply mutation to each child.
			children = mutator.performMutation(children);

			// Evaluate children
			for (int j = 0; j < numChild; j++) {
				children.get(j).score = (double) evaluation_.evaluate(children[j]);
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
