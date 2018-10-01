import java.util.Random;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Recombination
{
	String recombinationAlg_;

	public Recombination(String algType)
	{
		// Initialize object with correct selection algorithm
		recombinationAlg_ = algType;
	}

	public double[][] performRecombination(ArrayList<double[]> selectedParents)//with correct var
	{
		switch(recombinationAlg_)
		{
			case "discrete":
				return discreteRecombination(selectedParents);
		}

		double[][] errorList = new double[0][0];
		return errorList;
	}

	public double[][] discreteRecombination(ArrayList<double[]> selectedParents)
	{

		int nrTraits = player38.nrTraits;

		// only working with an even number of parents
		int numChild = selectedParents.size() - selectedParents.size() % 2;
		double[][] children = new double[numChild][nrTraits];

		for (int ind = 0; ind < numChild; ind +=2)
		{
			for (int j = 0; j < nrTraits; j++)
			{
				// select random number
				double rndInt = Math.random();

				// selecting a parent with equal probability for each allele
				if (rndInt > 0.5) {
					children[ind][j] = selectedParents.get(ind)[j];
					children[ind + 1][j] = selectedParents.get(ind + 1)[j];
				} else {
					children[ind][j] = selectedParents.get(ind + 1)[j];
					children[ind + 1][j] = selectedParents.get(ind)[j];
				}

			}

		}

		return children;
	}

}
