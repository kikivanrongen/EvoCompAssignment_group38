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
