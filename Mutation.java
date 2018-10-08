/*
Mutation options
5. Correlated mutations: come complicated maths...
*/

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        case "uncorrelated_onestep":
          //TODO: nadenken over hoe sigma lijsten terug te sturen.
          double teta = 1/ Math.sqrt(population.size());
          return this.onestepMutation(population, teta);
        case "uncorrelated_nstep":
          double[] tetas = new double[2];
          tetas[0] = 1/ Math.sqrt(2*Math.sqrt(population.size()));
          tetas[1] = 1/ Math.sqrt(2*population.size());
          return this.nstepMutation(population, tetas);
        // case "correlated_mutation":
          //return this.correlatedMutation(population);
        default:
          System.out.println("WARNING: no valid mutation method was provided");
          return this.deprecatedMutation(population);
    }
  }

  private ArrayList<Individual> uniformMutation(ArrayList<Individual> population, double threshold) {
  /*  1. uniform mutation: Dit is wat jij oorspronkelijk deed.
    Je moet dat eerst kiezen voor welke je flipt. (positionwise mutation probability)
    En dan kies je gewoon ene random getal binnen de grenzen. */
    int nrTraits = population.get(0).genome.length;
      for (int i =0; i<population.size(); i++) {
        //for each individual
        for (int j=0; j<nrTraits; j++) {
          //For each allele determine whether to flip it.
          if (rnd_.nextDouble() < threshold) {
            //And flip it
            population.get(i).genome[j] = rnd_.nextDouble() *10.0 -5.0;
          }
        }
      }
      return population;
  }

/* Function that for each allele randomly samples noise from gaussian zero
distribution.
*/
  private ArrayList<Individual> gaussMutation(ArrayList<Individual> population, double sd) {
      for (int i =0; i<population.size(); i++) {
        for (int j=0; j<player38.nrTraits; j++) {
          //For every individual for every gene in his genotype do:
          population.get(i).genome[j] += this.rnd_.nextGaussian() * sd;
          //Make sure individual stays within bounds
          if( population.get(i).genome[j] < -5) {
            population.get(i).genome[j] = -5;
          } else if ( population.get(i).genome[j] > 5) {
            population.get(i).genome[j] = 5;
          }
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

    for (int i =0; i<population.size(); i++) {
      double sigm = population.get(i).sigma;
      double gamma = rnd_.nextGaussian() * teta;
      double sd = sigm * Math.exp(gamma);
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


  private ArrayList<Individual> nstepMutation(ArrayList<Individual> population, double[] teta) {
  /*  4. uncorrelated mutations with n step sizes: Elke trait heeft eigen stepsize (is goed voor die bent cigar??)
    sigma is dus een array van length nrTraits.
    sigma_i' = sigma_i* e ^(gausswaarde * t + gausswaarde * t')
    waar t = 1/(sqrt(2scrt(n))) en t' = 1/ sqrt(2n) waar t'is common base (dus die kan je doen over hele unit)
    Voor elke sigma is een boundary rule van toepassing. */

    for (int i =0; i<population.size(); i++) {
      double sigm[] = population.get(i).sigmaList;
      for (int j=0; j<player38.nrTraits; j++) {
        //For every individual for every gene in his genotype do:
        double sd = sigm[j];
        if (sd < 0.001) {
          sd = 0.001;
        }

        double[] gamma = {rnd_.nextGaussian() * teta[0], rnd_.nextGaussian() * teta[1]};
        sd = sigm[j]*Math.exp(gamma[0]+gamma[1]);
        sigm[j] = sd;

        population.get(i).genome[j] += this.rnd_.nextGaussian() * sd;
        //Make sure individual stays within bounds
        if( population.get(i).genome[j] < -5) {
          population.get(i).genome[j] = -5;
        } else if ( population.get(i).genome[j] > 5) {
          population.get(i).genome[j] = 5;
        }
        System.out.println(population.get(i).genome[j]) ;
      }
      population.get(i).sigmaList = sigm;
    }
    return population;
  }

  private ArrayList<Individual> deprecatedMutation (ArrayList<Individual> population) {
    int n = population.size();
    int nrTraits = population.get(0).genome.length;

    // Apply mutation to each child.
    int rnd_idx = 0;
    for(int i=0; i < n; i++)
    {
      rnd_idx = rnd_.nextInt(nrTraits);
      double mutationFactor = rnd_.nextDouble() * 2.0 - 1.0;
      population.get(i).genome[rnd_idx] = population.get(i).genome[rnd_idx] * mutationFactor; // Kim dit moet anders nog (nu)
    }
    return population;
  }
}
