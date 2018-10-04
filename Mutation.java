/*
Mutation options
4. uncorrelated mutations with n step sizes: Elke trait heeft eigen stepsize (is goed voor die bent cigar??)
sigma is dus een array van length nrTraits.
Je moet dus een avriable step size maken: double[populationSize][nrTraits]
At each allele, bereken je zowel de nieuwe sigma als de nieuwe x
sigma_i' = sigma_i* e ^(gausswaarde * t + gausswaarde * t')
waar t = 1/(sqrt(2scrt(n))) en t' = 1/ sqrt(2n) waar t'is common base (dus die kan je doen over hele unit)
Voor elke sigma is een boundary rule van toepassing.


5. Correlated mutations: come complicated maths...
*/

import java.util.Random;

public class Mutation
{
  private String mutation_type;
  Random rnd_;

  public Mutation(String mutation_type)
  {
    this.mutation_type = mutation_type;
    rnd_ = new Random();
    // TODO: for some mutation types initialize a global step size array
  }

  public double[][] performMutation(double[][] population) {
    // First determine mutation type and run the corresponding function.
    switch(this.mutation_type) {
      case "uniform_mutation":
        double threshold = .1;
        return this.uniformMutation(population, threshold);
        case "gauss_mutation":
        double sd = 0.5;
        return this.gaussMutation(population, sd);
        // case "uncorrelated_onesize":
        // //TODO: nadenken over hoe sigma lijsten terug te sturen.
        //double teta = 1/ Math.sqrt(population.length);
        // return this.onestopMutation(population);
        // case "uncorrelated_nsize":
        // return this.nsizeMutation(population);
        // case "correlated_mutation":
        // return this.correlatedMutation(population);
        default:
        System.out.println("WARNING: no valid mutation method was provided");
        return this.deprecatedMutation(population);
    }
  }

  private double[][] uniformMutation(double[][] population, double threshold) {
  /*  1. uniform mutation: Dit is wat jij oorspronkelijk deed.
    Je moet dat eerst kiezen voor welke je flipt. (positionwise mutation probability)
    En dan kies je gewoon ene random getal binnen de grenzen. */
    int count = 0;
    int nrTraits = population[0].length;
      for (int i =0; i<population.length; i++) {
        //for each individual
        for (int j=0; j<nrTraits; j++) {
          //For each allele determine whether to flip it.
          if (rnd_.nextDouble() < threshold) {
            //And flip it
            count += 1;
            population[i][j] = rnd_.nextDouble() *10.0 -5.0;
          }
        }
      }
      System.out.println(count);
      return population;
  }

/* Function that for each allele randomly samples noise from gaussian zero
distribution.
*/
  private double[][] gaussMutation(double[][] population, double sd) {
      for (int i =0; i<population.length; i++) {
        for (int j=0; j<player38.nrTraits; j++) {
          //For every individual for every gene in his genotype do:
          population[i][j] += this.rnd_.nextGaussian() * sd;
          //Make sure individual stays within bounds
          if( population[i][j]<-5) {
            population[i][j] = -5;
          } else if (population[i][j] > 5) {
            population[i][j] = 5;
          }
          System.out.println(population[i][j]) ;
        }
      }

      return population;
  }

  private double[][] onestepMutation(double[][] population, double teta) {
    /*3. Uncorrelated mutation with 1 step size: Also mutate sigma.
    - Each individual has a step Size:
    Je moet dus een variabele step size maken double[populationSize]
    At each time step (i.e. once per individual) you mutate sigma with * e^Gamma, where Gamma is randomly samples from gaussian * teta
    After which each allele is random hoeveelheid uit gaussian * deze berekende sigma en dan round..
    NOTE: sigma niet kleiner dan threshold,als wel dan is set je sigma to threshold
    NOTE: teta is vaak 1/wortel populationSize */

    return population;
  }

  private double[][] deprecatedMutation (double[][] population) {
    int n = population.length;
    int nrTraits = population[0].length;

    // Apply mutation to each child.
    int rnd_idx = 0;
    for(int i=0; i < n; i++)
    {
      rnd_idx = rnd_.nextInt(nrTraits);
      double mutationFactor = rnd_.nextDouble() * 2.0 - 1.0;
      //Alter line below to reflect new population class
      population[i][rnd_idx] = population[i][rnd_idx] * mutationFactor; // Kim dit moet anders nog (nu)
    }
    return population;
  }
}
