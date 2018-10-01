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
