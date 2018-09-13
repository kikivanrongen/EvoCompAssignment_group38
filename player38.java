import org.vu.contest.ContestSubmission;
import org.vu.contest.ContestEvaluation;

import java.util.Random;
import java.util.Properties;

public class player38 implements ContestSubmission
{
	Random rnd_;
	ContestEvaluation evaluation_;
    private int evaluations_limit_;
	
	public player38()
	{
		rnd_ = new Random();
	}
	
	//public ArrayList generateRandomInitialSample
	
	public static void main(String[] args) {
		System.out.println("Test");
		run();
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
		// Run your algorithm here
        
        int evals = 0;
        
        // init population
        double[][] population = new double[100][10];
		
		for (int j = 0; j < 100; j++)
		{
		    for (int k = 0; k < 10; k++)
		    {
		    	population[j][k] = rnd_.nextDouble();
		    }
		}
        
        // calculate fitness
        while(evals<evaluations_limit_){
            // Select parents
            // Apply crossover / mutation operators
            double child[] = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};
            // Check fitness of unknown fuction
            Double fitness = (double) evaluation_.evaluate(child);
            evals++;
            // Select survivors
        }

	}
}
