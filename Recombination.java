import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

public class Recombination
{
	Random rnd_;
	String recombinationAlg_;

	public Recombination(String algType)
	{
		// Initialize object with correct selection algorithm
		recombinationAlg_ = algType;
	}

	public double[][] performRecombination(ArrayList<Individual> selectedParents)//with correct var
	{
		int nrTraits = player38.nrTraits;
		double alpha;
		int k;

		switch(recombinationAlg_)
		{
			case "discrete-pointwise":
				alpha = 0.5;
				return discretePointwiseRecombination(selectedParents, alpha);

			// case "discrete-tailswap":
			// 	k = ThreadLocalRandom.current().nextInt(0, nrTraits+1);
			// 	return discreteTailswapRecombination(selectedParents, k);
			//
			// case "arithmetic-whole":
			// 	alpha = 0.5;
			// 	return wholeArithmeticRecombination(selectedParents, alpha);
			//
			// case "arithmetic-simple":
			// 	k = ThreadLocalRandom.current().nextInt(0, nrTraits+1);
			// 	alpha = 0.5;
			// 	return simpleArithmeticRecombination(selectedParents, k, alpha);
			//
			// case "arithmetic-single":
			// 	alpha = 0.5;
			// 	return singleArithmeticRecombination(selectedParents, alpha);
			//
			// case "blendcrossover":
			// 	alpha = 0.5;
			// 	return blendCrossoverRecombination(selectedParents, alpha);
			default:
				System.out.println("No valid method chosen");
					alpha = 0.5;
					return discretePointwiseRecombination(selectedParents, alpha);
		}

	}

	public double[][] discretePointwiseRecombination(ArrayList<Individual> selectedParents, double alpha)
	{

		/*
		* Considers each allele separately, and randomly chooses a value
		* from parent x or y (with probability alpha) for child 1. The other
		* value goes to child 2.
		* Parameters: alpha
		*/

		int nrTraits = player38.nrTraits;

		// only working with an even number of parents
		int numChild = selectedParents.size() - selectedParents.size() % 2;
		double[][] children = new double[numChild][nrTraits];

		for (int ind = 0; ind < numChild; ind +=2)
		{
			for (int j = 0; j < nrTraits; j++)
			{
				// select random number
				double rndVal = Math.random();

				// selecting a parent with equal probability for each allele
				if (rndVal > alpha) {
					children[ind][j] = selectedParents.get(ind).genome[j];
					children[ind + 1][j] = selectedParents.get(ind + 1).genome[j];
				} else {
					children[ind][j] = selectedParents.get(ind + 1).genome[j];
					children[ind + 1][j] = selectedParents.get(ind).genome[j];
				}

			}

		}

		return children;
	}



	public double[][] discreteTailswapRecombination(ArrayList<double[]> selectedParents, int k)
	{
		/*
		* Two parents are recombined by switching their heads/tails
		* at a point k.
		* Parameters: k
		*/
		int nrTraits = player38.nrTraits;

		// only working with an even number of parents
		int numChild = selectedParents.size() - selectedParents.size() % 2;
		double[][] children = new double[numChild][nrTraits];

		for (int ind = 0; ind < numChild; ind +=2)
		{

			for (int j = 0; j < k; j++)
			{
				 children[ind][j] = selectedParents.get(ind)[j];
				 children[ind + 1][j] = selectedParents.get(ind + 1)[j];
			}

			for (int j = k; j < nrTraits; j++)
			{
				 children[ind][j] = selectedParents.get(ind + 1)[j];
				 children[ind + 1][j] = selectedParents.get(ind)[j];
			}

		}

		return children;
	}



	public double[][] wholeArithmeticRecombination(ArrayList<double[]> selectedParents, double alpha)
	{
		/*
		* The weighted average of the two parent alleles is taken to create
		* a new vector that lies 'in between' its parents, weighed by alpha.
		* Weighing happens using alpha.
		* Parameters: alpha
		*/

		int nrTraits = player38.nrTraits;

		// only working with an even number of parents
		int numChild = selectedParents.size() - selectedParents.size() % 2;
		double[][] children = new double[numChild][nrTraits];

		// weight given to first parent (other parent gets 1-alpha)
		// 0.5 most common according to book. We could also try other values,
		// or randomize on every run.

		for (int ind = 0; ind < numChild; ind +=2)
		{
				for (int j = 0; j < nrTraits; j++)
				{
					children[ind][j] = selectedParents.get(ind)[j] * alpha + selectedParents.get(ind + 1)[j] * (1-alpha);
					children[ind + 1][j] = selectedParents.get(ind + 1)[j] * alpha + selectedParents.get(ind)[j] * (1-alpha);
				}
		}

		return children;
	}



	public double[][] simpleArithmeticRecombination(ArrayList<double[]> selectedParents, int k, double alpha)
	{
		/*
		* Up to a recombination point k, allele values come from a single parent.
		* After this recombination point, the values are a weighted average
		* of the two parents (weighed by factor alpha).
		* Parameters: k, alpha
		*/

		int nrTraits = player38.nrTraits;

		// only working with an even number of parents
		int numChild = selectedParents.size() - selectedParents.size() % 2;
		double[][] children = new double[numChild][nrTraits];

		// recombination point k now randomized for every parent pair

		// weighing factors now set to 0.5 (most common according to book)
		// we could use a different value or randomize between 0 and 1

		for (int ind = 0; ind < numChild; ind +=2)
		{
			for (int j = 0; j < k; j++)
			{
				 children[ind][j] = selectedParents.get(ind)[j];
				 children[ind + 1][j] = selectedParents.get(ind + 1)[j];
			}

			for (int j = k; j < nrTraits; j++)
			{
				 children[ind][j] = selectedParents.get(ind + 1)[j] * alpha + selectedParents.get(ind)[j] * (1-alpha);
				 children[ind + 1][j] = selectedParents.get(ind + 1)[j] * (1-alpha) + selectedParents.get(ind)[j] * alpha;
			}

		}

		return children;
	}



	public double[][] singleArithmeticRecombination(ArrayList<double[]> selectedParents, double alpha)
	{
		/*
		* In each parent, a single point becomes the weighted average
		* of its two parents; the other points within the two parents are not changed.
		* Weighing happens using alpha. Which  allele is combined is randomly
		* determined for each pair of parents.
		* Parameters: alpha
		*/

		int nrTraits = player38.nrTraits;

		// only working with an even number of parents
		int numChild = selectedParents.size() - selectedParents.size() % 2;
		double[][] children = new double[numChild][nrTraits];

		// weight given to first parent (other parent gets 1-alpha)
		// 0.5 most common according to book. We could also try other values,
		// or randomize on every run.

		for (int ind = 0; ind < numChild; ind +=2)
		{
			int k = ThreadLocalRandom.current().nextInt(0, nrTraits+1);

			for (int j = 0; j < nrTraits; j++)
			{
				if (j == k) {
					children[ind][j] = selectedParents.get(ind)[j] * alpha + selectedParents.get(ind + 1)[j] * (1-alpha);
					children[ind + 1][j] = selectedParents.get(ind + 1)[j] * alpha + selectedParents.get(ind)[j] * (1-alpha);
				} else {
					children[ind][j] = selectedParents.get(ind)[j];
					children[ind + 1][j] = selectedParents.get(ind + 1)[j];
				}

			}
		}

		return children;
	}



	public double[][] blendCrossoverRecombination(ArrayList<double[]> selectedParents, double alpha)
	{
		/*
		* Offspring can lie outside parents. Offspring point in a range around
		* the parent points.; range is determined by alpha
		* Parameters: alpha
		*/

		int nrTraits = player38.nrTraits;

		// only working with an even number of parents
		int numChild = selectedParents.size() - selectedParents.size() % 2;
		double[][] children = new double[numChild][nrTraits];

		// 0.5 reportedly leads to the best results, but we could change this

		for (int ind = 0; ind < numChild; ind +=2)
		{
			for (int j = 0; j < nrTraits; j++)
			{
				double distance = Math.abs(selectedParents.get(ind)[j] - selectedParents.get(ind+1)[j]);

				double lowerVal = (selectedParents.get(ind)[j] < selectedParents.get(ind+1)[j]) ? selectedParents.get(ind)[j] : selectedParents.get(ind+1)[j];
				double higherVal = (selectedParents.get(ind)[j] > selectedParents.get(ind+1)[j]) ? selectedParents.get(ind)[j] : selectedParents.get(ind+1)[j];

				double min = lowerVal - alpha * distance;
				double max = higherVal + alpha * distance;

				children[ind][j] = ThreadLocalRandom.current().nextDouble(min, max);
				children[ind + 1][j] = ThreadLocalRandom.current().nextDouble(min, max);
			}
		}

		return children;

	}



}
