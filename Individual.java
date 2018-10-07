import java.util.Random;

public class Individual
{
  //Deze public maken is heel lelijk, maak wel private and getters and setters als het werkt
  public double[] genome;
  public double sigma;
  public double[] sigmaList;
  public double score;
  public double prob;
  //private double prob;

public Individual(int nrTraits, String mutationType) {
  genome = new double[nrTraits];
  //TODO: based on mutation type either initialize sigma, sigmalist or neither.
  if (mutationType.contains("onestep") {
    this.sigma = .2;
  } else if(mutationType.contains("nstep")) {
    this.sigmaList = double[nrTraits];
    for (i=0; i<nrTraits; i++) {
      //fill sigma
      sigmaList[i] = 0.2;
    }
  }
}




}
