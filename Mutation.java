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
  }

  public ArrayList<Individual> performMutation(ArrayList<Individual> population) {
    // First determine mutation type and run the corresponding function.
    switch(this.mutation_type) {
      case "uniform_mutation":
        double threshold = .1;
        return this.uniformMutation(population, threshold);
        case "gauss_mutation":
        double sd = 0.5;
        return this.gaussMutation(population, sd);
        // case "uncorrelated_onestep":
        // //TODO: nadenken over hoe sigma lijsten terug te sturen.
        //double teta = 1/ Math.sqrt(population.length);
        // return this.onestepMutation(population);
        // case "uncorrelated_nstep":
        // return this.nsizeMutation(population);
        // case "correlated_mutation":
        // return this.correlatedMutation(population);
        default:
        System.out.println("WARNING: no valid mutation method was provided");
        return this.deprecatedMutation(population);
    }
  }

  private ArrayList<Individual> uniformMutation(ArrayList<Individual> population, double threshold) {
  /*  1. uniform mutation: Dit is wat jij oorspronkelijk deed.
    Je moet dat eerst kiezen voor welke je flipt. (positionwise mutation probability)
    En dan kies je gewoon ene random getal binnen de grenzen. */
    int count = 0;
    int nrTraits = population.get(0).genome.length;
      for (int i =0; i<population.size(); i++) {
        //for each individual
        for (int j=0; j<nrTraits; j++) {
          //For each allele determine whether to flip it.
          if (rnd_.nextDouble() < threshold) {
            //And flip it
            count += 1;
            population.get(i).genome[j] = rnd_.nextDouble() *10.0 -5.0;
          }
        }
      }
      System.out.println(count);
      return population;
  }

/* Function that for each allele randomly samples noise from gaussian zero
distribution.
*/
  private ArrayList<Individual> gaussMutation(ArrayList<Individual> population, double sd) {
      for (int i =0; i<population.length; i++) {
        for (int j=0; j<player38.nrTraits; j++) {
          //For every individual for every gene in his genotype do:
          population.get(i).genome[j] += this.rnd_.nextGaussian() * sd;
          //Make sure individual stays within bounds
          if( population.get(i).genome[j] < -5) {
            population.get(i).genome[j] = -5;
          } else if ( population.get(i).score[j] > 5) {
            population.get(i).genome[j] = 5;
          }
          System.out.println(population.get(i).score[j]) ;
        }
      }

      return population;
  }

  private ArrayList<Individual> onestepMutation(ArrayList<Individual> population, double teta) {
    /*3. Uncorrelated mutation with 1 step size: Also mutate sigma.
    - Each individual has a step Size:
    Je moet dus een variabele step size maken double[populationSize]
    At each time step (i.e. once per individual) you mutate sigma with * e^Gamma,
    where Gamma is randomly samples from gaussian * teta
    After which each allele is random hoeveelheid uit gaussian * deze berekende sigma
    NOTE: sigma niet kleiner dan threshold,als wel dan is set je sigma to threshold
    NOTE: teta is vaak 1/wortel populationSize */

    for (int i =0; i<population.length; i++) {
      sigma = population.get(i).sigma;
      double gamma = rnd_.nextGaussian() * teta;
      sd = sigma * exp(gamma);
      if (sd < 0.001) {
        sd = 0.001;
      }
      population.get(i).sigma = sd;

      for (int j=0; j<player38.nrTraits; j++) {
        //For every individual for every gene in his genotype do:
        population.get(i).genome[j] += this.rnd_.nextGaussian() * sd;
        //Make sure individual stays within bounds
        if( population.get(i).genome[j] < -5) {
          population.get(i).genome[j] = -5;
        } else if ( population.get(i).genome[j] > 5) {
          population.get(i).genome[j] = 5;
        }
        System.out.println(population.get(i).genome[j]) ;
      }
    }


    return population;
  }


  private ArrayList<Individual> nstepMutation(ArrayList<Individual> population, double param) {
    for (int i =0; i<population.length; i++) {
      sigma = population.get(i).sigma;
      for (int j=0; j<player38.nrTraits; j++) {
        //For every individual for every gene in his genotype do:
        sigma[j];

        population.get(i).score[j] += this.rnd_.nextGaussian() * sd;
        //Make sure individual stays within bounds
        if( population.get(i).score[j] < -5) {
          population.get(i).score[j] = -5;
        } else if ( population.get(i).score[j] > 5) {
          population.get(i).score[j] = 5;
        }
        System.out.println(population.get(i).score[j]) ;
      }
    }
  }

  private ArrayList<Individual> deprecatedMutation (ArrayList<Individual> population) {
    int n = population.size();
    int nrTraits = population.get(0).score.length;

    // Apply mutation to each child.
    int rnd_idx = 0;
    for(int i=0; i < n; i++)
    {
      rnd_idx = rnd_.nextInt(nrTraits);
      double mutationFactor = rnd_.nextDouble() * 2.0 - 1.0;
      population.get(i).score[rnd_idx] = population.get(i).score[rnd_idx] * mutationFactor; // Kim dit moet anders nog (nu)
    }
    return population;
  }
}
