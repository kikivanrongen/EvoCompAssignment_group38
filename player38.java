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
		String[] mutopts = {"uniform", "gauss", "uncorrelated-onestep", "uncorrelated-nstep"};
		String[] recopts = {"discrete-pointwise", "discrete-tailswap", "arithmetic-whole", "arithmetic-simple", "arithmetic-single", "blendcrossover", "none"};
		String[] parselopts = {"arena", "ranked-lin", "ranked-exp"};
		String[] surselopts = {"worst", "elitism", "roundRobin"};

		int[][] funcIndex = new int[][]{
																		{0, 0, 0, 2},
																		{0, 1, 0, 2},
																		{0, 2, 0, 2},
																		{0, 3, 0, 2},
																		{0, 4, 0, 2},
																		{0, 5, 0, 2},
																		{0, 0, 1, 2},
																		{0, 1, 1, 2},
																		{0, 2, 1, 2},
																		{0, 3, 1, 2},
																		{0, 4, 1, 2},
																		{0, 5, 1, 2},
																		{0, 0, 2, 2},
																		{0, 1, 2, 2},
																		{0, 2, 2, 2},
																		{0, 3, 2, 2},
																		{0, 4, 2, 2},
																		{0, 5, 2, 2},
																		{0, 0, 3, 2},
																		{0, 1, 3, 2},
																		{0, 2, 3, 2},
																		{0, 3, 3, 2},
																		{0, 4, 3, 2},
																		{0, 5, 3, 2}
																};

		// double[] alphas = new double[]{0, 0.1, 0.2, 0.3, 0.4, 0.5,
		// 																0.6, 0.7, 0.8, 0.9, 1};
		// double[] stds = new double[]{0.01, 0.02, 0.05, 0.1, 0.2, 0.5, 1};
		double[] alphas = new double[]{0, 0.2, 0.4, 0.6, 0.8, 1};
		double[] stds = new double[]{0.01, 0.05, 0.1, 0.2, 0.5, 1};

		int[][] setups = new int[][]{{0, 0, 0}, {0, 1, 0}, {0, 2, 0}, {0, 3, 0}, {0, 4, 0}, {0, 5, 0}, {1, 0, 0}, {1, 1, 0}, {1, 2, 0}, {1, 3, 0}, {1, 4, 0}, {1, 5, 0}, {2, 0, 0}, {2, 1, 0}, {2, 2, 0}, {2, 3, 0}, {2, 4, 0}, {2, 5, 0}, {3, 0, 0}, {3, 1, 0}, {3, 2, 0}, {3, 3, 0}, {3, 4, 0}, {3, 5, 0}, {4, 0, 0}, {4, 1, 0}, {4, 2, 0}, {4, 3, 0}, {4, 4, 0}, {4, 5, 0}, {5, 0, 0}, {5, 1, 0}, {5, 2, 0}, {5, 3, 0}, {5, 4, 0}, {5, 5, 0}, {6, 0, 0}, {6, 1, 0}, {6, 2, 0}, {6, 3, 0}, {6, 4, 0}, {6, 5, 0}, {7, 0, 0}, {7, 1, 0}, {7, 2, 0}, {7, 3, 0}, {7, 4, 0}, {7, 5, 0}, {8, 0, 0}, {8, 1, 0}, {8, 2, 0}, {8, 3, 0}, {8, 4, 0}, {8, 5, 0}, {9, 0, 0}, {9, 1, 0}, {9, 2, 0}, {9, 3, 0}, {9, 4, 0}, {9, 5, 0}, {10, 0, 0}, {10, 1, 0}, {10, 2, 0}, {10, 3, 0}, {10, 4, 0}, {10, 5, 0}, {11, 0, 0}, {11, 1, 0}, {11, 2, 0}, {11, 3, 0}, {11, 4, 0}, {11, 5, 0}, {12, 0, 0}, {12, 1, 0}, {12, 2, 0}, {12, 3, 0}, {12, 4, 0}, {12, 5, 0}, {13, 0, 0}, {13, 1, 0}, {13, 2, 0}, {13, 3, 0}, {13, 4, 0}, {13, 5, 0}, {14, 0, 0}, {14, 1, 0}, {14, 2, 0}, {14, 3, 0}, {14, 4, 0}, {14, 5, 0}, {15, 0, 0}, {15, 1, 0}, {15, 2, 0}, {15, 3, 0}, {15, 4, 0}, {15, 5, 0}, {16, 0, 0}, {16, 1, 0}, {16, 2, 0}, {16, 3, 0}, {16, 4, 0}, {16, 5, 0}, {17, 0, 0}, {17, 1, 0}, {17, 2, 0}, {17, 3, 0}, {17, 4, 0}, {17, 5, 0}, {18, 0, 0}, {18, 1, 0}, {18, 2, 0}, {18, 3, 0}, {18, 4, 0}, {18, 5, 0}, {19, 0, 0}, {19, 1, 0}, {19, 2, 0}, {19, 3, 0}, {19, 4, 0}, {19, 5, 0}, {20, 0, 0}, {20, 1, 0}, {20, 2, 0}, {20, 3, 0}, {20, 4, 0}, {20, 5, 0}, {21, 0, 0}, {21, 1, 0}, {21, 2, 0}, {21, 3, 0}, {21, 4, 0}, {21, 5, 0}, {22, 0, 0}, {22, 1, 0}, {22, 2, 0}, {22, 3, 0}, {22, 4, 0}, {22, 5, 0}, {23, 0, 0}, {23, 1, 0}, {23, 2, 0}, {23, 3, 0}, {23, 4, 0}, {23, 5, 0}, {0, 0, 1}, {0, 1, 1}, {0, 2, 1}, {0, 3, 1}, {0, 4, 1}, {0, 5, 1}, {1, 0, 1}, {1, 1, 1}, {1, 2, 1}, {1, 3, 1}, {1, 4, 1}, {1, 5, 1}, {2, 0, 1}, {2, 1, 1}, {2, 2, 1}, {2, 3, 1}, {2, 4, 1}, {2, 5, 1}, {3, 0, 1}, {3, 1, 1}, {3, 2, 1}, {3, 3, 1}, {3, 4, 1}, {3, 5, 1}, {4, 0, 1}, {4, 1, 1}, {4, 2, 1}, {4, 3, 1}, {4, 4, 1}, {4, 5, 1}, {5, 0, 1}, {5, 1, 1}, {5, 2, 1}, {5, 3, 1}, {5, 4, 1}, {5, 5, 1}, {6, 0, 1}, {6, 1, 1}, {6, 2, 1}, {6, 3, 1}, {6, 4, 1}, {6, 5, 1}, {7, 0, 1}, {7, 1, 1}, {7, 2, 1}, {7, 3, 1}, {7, 4, 1}, {7, 5, 1}, {8, 0, 1}, {8, 1, 1}, {8, 2, 1}, {8, 3, 1}, {8, 4, 1}, {8, 5, 1}, {9, 0, 1}, {9, 1, 1}, {9, 2, 1}, {9, 3, 1}, {9, 4, 1}, {9, 5, 1}, {10, 0, 1}, {10, 1, 1}, {10, 2, 1}, {10, 3, 1}, {10, 4, 1}, {10, 5, 1}, {11, 0, 1}, {11, 1, 1}, {11, 2, 1}, {11, 3, 1}, {11, 4, 1}, {11, 5, 1}, {12, 0, 1}, {12, 1, 1}, {12, 2, 1}, {12, 3, 1}, {12, 4, 1}, {12, 5, 1}, {13, 0, 1}, {13, 1, 1}, {13, 2, 1}, {13, 3, 1}, {13, 4, 1}, {13, 5, 1}, {14, 0, 1}, {14, 1, 1}, {14, 2, 1}, {14, 3, 1}, {14, 4, 1}, {14, 5, 1}, {15, 0, 1}, {15, 1, 1}, {15, 2, 1}, {15, 3, 1}, {15, 4, 1}, {15, 5, 1}, {16, 0, 1}, {16, 1, 1}, {16, 2, 1}, {16, 3, 1}, {16, 4, 1}, {16, 5, 1}, {17, 0, 1}, {17, 1, 1}, {17, 2, 1}, {17, 3, 1}, {17, 4, 1}, {17, 5, 1}, {18, 0, 1}, {18, 1, 1}, {18, 2, 1}, {18, 3, 1}, {18, 4, 1}, {18, 5, 1}, {19, 0, 1}, {19, 1, 1}, {19, 2, 1}, {19, 3, 1}, {19, 4, 1}, {19, 5, 1}, {20, 0, 1}, {20, 1, 1}, {20, 2, 1}, {20, 3, 1}, {20, 4, 1}, {20, 5, 1}, {21, 0, 1}, {21, 1, 1}, {21, 2, 1}, {21, 3, 1}, {21, 4, 1}, {21, 5, 1}, {22, 0, 1}, {22, 1, 1}, {22, 2, 1}, {22, 3, 1}, {22, 4, 1}, {22, 5, 1}, {23, 0, 1}, {23, 1, 1}, {23, 2, 1}, {23, 3, 1}, {23, 4, 1}, {23, 5, 1}, {0, 0, 2}, {0, 1, 2}, {0, 2, 2}, {0, 3, 2}, {0, 4, 2}, {0, 5, 2}, {1, 0, 2}, {1, 1, 2}, {1, 2, 2}, {1, 3, 2}, {1, 4, 2}, {1, 5, 2}, {2, 0, 2}, {2, 1, 2}, {2, 2, 2}, {2, 3, 2}, {2, 4, 2}, {2, 5, 2}, {3, 0, 2}, {3, 1, 2}, {3, 2, 2}, {3, 3, 2}, {3, 4, 2}, {3, 5, 2}, {4, 0, 2}, {4, 1, 2}, {4, 2, 2}, {4, 3, 2}, {4, 4, 2}, {4, 5, 2}, {5, 0, 2}, {5, 1, 2}, {5, 2, 2}, {5, 3, 2}, {5, 4, 2}, {5, 5, 2}, {6, 0, 2}, {6, 1, 2}, {6, 2, 2}, {6, 3, 2}, {6, 4, 2}, {6, 5, 2}, {7, 0, 2}, {7, 1, 2}, {7, 2, 2}, {7, 3, 2}, {7, 4, 2}, {7, 5, 2}, {8, 0, 2}, {8, 1, 2}, {8, 2, 2}, {8, 3, 2}, {8, 4, 2}, {8, 5, 2}, {9, 0, 2}, {9, 1, 2}, {9, 2, 2}, {9, 3, 2}, {9, 4, 2}, {9, 5, 2}, {10, 0, 2}, {10, 1, 2}, {10, 2, 2}, {10, 3, 2}, {10, 4, 2}, {10, 5, 2}, {11, 0, 2}, {11, 1, 2}, {11, 2, 2}, {11, 3, 2}, {11, 4, 2}, {11, 5, 2}, {12, 0, 2}, {12, 1, 2}, {12, 2, 2}, {12, 3, 2}, {12, 4, 2}, {12, 5, 2}, {13, 0, 2}, {13, 1, 2}, {13, 2, 2}, {13, 3, 2}, {13, 4, 2}, {13, 5, 2}, {14, 0, 2}, {14, 1, 2}, {14, 2, 2}, {14, 3, 2}, {14, 4, 2}, {14, 5, 2}, {15, 0, 2}, {15, 1, 2}, {15, 2, 2}, {15, 3, 2}, {15, 4, 2}, {15, 5, 2}, {16, 0, 2}, {16, 1, 2}, {16, 2, 2}, {16, 3, 2}, {16, 4, 2}, {16, 5, 2}, {17, 0, 2}, {17, 1, 2}, {17, 2, 2}, {17, 3, 2}, {17, 4, 2}, {17, 5, 2}, {18, 0, 2}, {18, 1, 2}, {18, 2, 2}, {18, 3, 2}, {18, 4, 2}, {18, 5, 2}, {19, 0, 2}, {19, 1, 2}, {19, 2, 2}, {19, 3, 2}, {19, 4, 2}, {19, 5, 2}, {20, 0, 2}, {20, 1, 2}, {20, 2, 2}, {20, 3, 2}, {20, 4, 2}, {20, 5, 2}, {21, 0, 2}, {21, 1, 2}, {21, 2, 2}, {21, 3, 2}, {21, 4, 2}, {21, 5, 2}, {22, 0, 2}, {22, 1, 2}, {22, 2, 2}, {22, 3, 2}, {22, 4, 2}, {22, 5, 2}, {23, 0, 2}, {23, 1, 2}, {23, 2, 2}, {23, 3, 2}, {23, 4, 2}, {23, 5, 2}, {0, 0, 3}, {0, 1, 3}, {0, 2, 3}, {0, 3, 3}, {0, 4, 3}, {0, 5, 3}, {1, 0, 3}, {1, 1, 3}, {1, 2, 3}, {1, 3, 3}, {1, 4, 3}, {1, 5, 3}, {2, 0, 3}, {2, 1, 3}, {2, 2, 3}, {2, 3, 3}, {2, 4, 3}, {2, 5, 3}, {3, 0, 3}, {3, 1, 3}, {3, 2, 3}, {3, 3, 3}, {3, 4, 3}, {3, 5, 3}, {4, 0, 3}, {4, 1, 3}, {4, 2, 3}, {4, 3, 3}, {4, 4, 3}, {4, 5, 3}, {5, 0, 3}, {5, 1, 3}, {5, 2, 3}, {5, 3, 3}, {5, 4, 3}, {5, 5, 3}, {6, 0, 3}, {6, 1, 3}, {6, 2, 3}, {6, 3, 3}, {6, 4, 3}, {6, 5, 3}, {7, 0, 3}, {7, 1, 3}, {7, 2, 3}, {7, 3, 3}, {7, 4, 3}, {7, 5, 3}, {8, 0, 3}, {8, 1, 3}, {8, 2, 3}, {8, 3, 3}, {8, 4, 3}, {8, 5, 3}, {9, 0, 3}, {9, 1, 3}, {9, 2, 3}, {9, 3, 3}, {9, 4, 3}, {9, 5, 3}, {10, 0, 3}, {10, 1, 3}, {10, 2, 3}, {10, 3, 3}, {10, 4, 3}, {10, 5, 3}, {11, 0, 3}, {11, 1, 3}, {11, 2, 3}, {11, 3, 3}, {11, 4, 3}, {11, 5, 3}, {12, 0, 3}, {12, 1, 3}, {12, 2, 3}, {12, 3, 3}, {12, 4, 3}, {12, 5, 3}, {13, 0, 3}, {13, 1, 3}, {13, 2, 3}, {13, 3, 3}, {13, 4, 3}, {13, 5, 3}, {14, 0, 3}, {14, 1, 3}, {14, 2, 3}, {14, 3, 3}, {14, 4, 3}, {14, 5, 3}, {15, 0, 3}, {15, 1, 3}, {15, 2, 3}, {15, 3, 3}, {15, 4, 3}, {15, 5, 3}, {16, 0, 3}, {16, 1, 3}, {16, 2, 3}, {16, 3, 3}, {16, 4, 3}, {16, 5, 3}, {17, 0, 3}, {17, 1, 3}, {17, 2, 3}, {17, 3, 3}, {17, 4, 3}, {17, 5, 3}, {18, 0, 3}, {18, 1, 3}, {18, 2, 3}, {18, 3, 3}, {18, 4, 3}, {18, 5, 3}, {19, 0, 3}, {19, 1, 3}, {19, 2, 3}, {19, 3, 3}, {19, 4, 3}, {19, 5, 3}, {20, 0, 3}, {20, 1, 3}, {20, 2, 3}, {20, 3, 3}, {20, 4, 3}, {20, 5, 3}, {21, 0, 3}, {21, 1, 3}, {21, 2, 3}, {21, 3, 3}, {21, 4, 3}, {21, 5, 3}, {22, 0, 3}, {22, 1, 3}, {22, 2, 3}, {22, 3, 3}, {22, 4, 3}, {22, 5, 3}, {23, 0, 3}, {23, 1, 3}, {23, 2, 3}, {23, 3, 3}, {23, 4, 3}, {23, 5, 3}, {0, 0, 4}, {0, 1, 4}, {0, 2, 4}, {0, 3, 4}, {0, 4, 4}, {0, 5, 4}, {1, 0, 4}, {1, 1, 4}, {1, 2, 4}, {1, 3, 4}, {1, 4, 4}, {1, 5, 4}, {2, 0, 4}, {2, 1, 4}, {2, 2, 4}, {2, 3, 4}, {2, 4, 4}, {2, 5, 4}, {3, 0, 4}, {3, 1, 4}, {3, 2, 4}, {3, 3, 4}, {3, 4, 4}, {3, 5, 4}, {4, 0, 4}, {4, 1, 4}, {4, 2, 4}, {4, 3, 4}, {4, 4, 4}, {4, 5, 4}, {5, 0, 4}, {5, 1, 4}, {5, 2, 4}, {5, 3, 4}, {5, 4, 4}, {5, 5, 4}, {6, 0, 4}, {6, 1, 4}, {6, 2, 4}, {6, 3, 4}, {6, 4, 4}, {6, 5, 4}, {7, 0, 4}, {7, 1, 4}, {7, 2, 4}, {7, 3, 4}, {7, 4, 4}, {7, 5, 4}, {8, 0, 4}, {8, 1, 4}, {8, 2, 4}, {8, 3, 4}, {8, 4, 4}, {8, 5, 4}, {9, 0, 4}, {9, 1, 4}, {9, 2, 4}, {9, 3, 4}, {9, 4, 4}, {9, 5, 4}, {10, 0, 4}, {10, 1, 4}, {10, 2, 4}, {10, 3, 4}, {10, 4, 4}, {10, 5, 4}, {11, 0, 4}, {11, 1, 4}, {11, 2, 4}, {11, 3, 4}, {11, 4, 4}, {11, 5, 4}, {12, 0, 4}, {12, 1, 4}, {12, 2, 4}, {12, 3, 4}, {12, 4, 4}, {12, 5, 4}, {13, 0, 4}, {13, 1, 4}, {13, 2, 4}, {13, 3, 4}, {13, 4, 4}, {13, 5, 4}, {14, 0, 4}, {14, 1, 4}, {14, 2, 4}, {14, 3, 4}, {14, 4, 4}, {14, 5, 4}, {15, 0, 4}, {15, 1, 4}, {15, 2, 4}, {15, 3, 4}, {15, 4, 4}, {15, 5, 4}, {16, 0, 4}, {16, 1, 4}, {16, 2, 4}, {16, 3, 4}, {16, 4, 4}, {16, 5, 4}, {17, 0, 4}, {17, 1, 4}, {17, 2, 4}, {17, 3, 4}, {17, 4, 4}, {17, 5, 4}, {18, 0, 4}, {18, 1, 4}, {18, 2, 4}, {18, 3, 4}, {18, 4, 4}, {18, 5, 4}, {19, 0, 4}, {19, 1, 4}, {19, 2, 4}, {19, 3, 4}, {19, 4, 4}, {19, 5, 4}, {20, 0, 4}, {20, 1, 4}, {20, 2, 4}, {20, 3, 4}, {20, 4, 4}, {20, 5, 4}, {21, 0, 4}, {21, 1, 4}, {21, 2, 4}, {21, 3, 4}, {21, 4, 4}, {21, 5, 4}, {22, 0, 4}, {22, 1, 4}, {22, 2, 4}, {22, 3, 4}, {22, 4, 4}, {22, 5, 4}, {23, 0, 4}, {23, 1, 4}, {23, 2, 4}, {23, 3, 4}, {23, 4, 4}, {23, 5, 4}, {0, 0, 5}, {0, 1, 5}, {0, 2, 5}, {0, 3, 5}, {0, 4, 5}, {0, 5, 5}, {1, 0, 5}, {1, 1, 5}, {1, 2, 5}, {1, 3, 5}, {1, 4, 5}, {1, 5, 5}, {2, 0, 5}, {2, 1, 5}, {2, 2, 5}, {2, 3, 5}, {2, 4, 5}, {2, 5, 5}, {3, 0, 5}, {3, 1, 5}, {3, 2, 5}, {3, 3, 5}, {3, 4, 5}, {3, 5, 5}, {4, 0, 5}, {4, 1, 5}, {4, 2, 5}, {4, 3, 5}, {4, 4, 5}, {4, 5, 5}, {5, 0, 5}, {5, 1, 5}, {5, 2, 5}, {5, 3, 5}, {5, 4, 5}, {5, 5, 5}, {6, 0, 5}, {6, 1, 5}, {6, 2, 5}, {6, 3, 5}, {6, 4, 5}, {6, 5, 5}, {7, 0, 5}, {7, 1, 5}, {7, 2, 5}, {7, 3, 5}, {7, 4, 5}, {7, 5, 5}, {8, 0, 5}, {8, 1, 5}, {8, 2, 5}, {8, 3, 5}, {8, 4, 5}, {8, 5, 5}, {9, 0, 5}, {9, 1, 5}, {9, 2, 5}, {9, 3, 5}, {9, 4, 5}, {9, 5, 5}, {10, 0, 5}, {10, 1, 5}, {10, 2, 5}, {10, 3, 5}, {10, 4, 5}, {10, 5, 5}, {11, 0, 5}, {11, 1, 5}, {11, 2, 5}, {11, 3, 5}, {11, 4, 5}, {11, 5, 5}, {12, 0, 5}, {12, 1, 5}, {12, 2, 5}, {12, 3, 5}, {12, 4, 5}, {12, 5, 5}, {13, 0, 5}, {13, 1, 5}, {13, 2, 5}, {13, 3, 5}, {13, 4, 5}, {13, 5, 5}, {14, 0, 5}, {14, 1, 5}, {14, 2, 5}, {14, 3, 5}, {14, 4, 5}, {14, 5, 5}, {15, 0, 5}, {15, 1, 5}, {15, 2, 5}, {15, 3, 5}, {15, 4, 5}, {15, 5, 5}, {16, 0, 5}, {16, 1, 5}, {16, 2, 5}, {16, 3, 5}, {16, 4, 5}, {16, 5, 5}, {17, 0, 5}, {17, 1, 5}, {17, 2, 5}, {17, 3, 5}, {17, 4, 5}, {17, 5, 5}, {18, 0, 5}, {18, 1, 5}, {18, 2, 5}, {18, 3, 5}, {18, 4, 5}, {18, 5, 5}, {19, 0, 5}, {19, 1, 5}, {19, 2, 5}, {19, 3, 5}, {19, 4, 5}, {19, 5, 5}, {20, 0, 5}, {20, 1, 5}, {20, 2, 5}, {20, 3, 5}, {20, 4, 5}, {20, 5, 5}, {21, 0, 5}, {21, 1, 5}, {21, 2, 5}, {21, 3, 5}, {21, 4, 5}, {21, 5, 5}, {22, 0, 5}, {22, 1, 5}, {22, 2, 5}, {22, 3, 5}, {22, 4, 5}, {22, 5, 5}, {23, 0, 5}, {23, 1, 5}, {23, 2, 5}, {23, 3, 5}, {23, 4, 5}, {23, 5, 5}};


		int iter = Integer.parseInt(System.getProperty("iter"));
		// System.out.println(String.valueOf(iter));
		//int[] setup = setups[iter];
		// System.out.println(Arrays.toString(setup));
		int[] funcs = funcIndex[iter];
		double alpha = 0.5;
		double std = 0.01;

		int evals = 0;
		int populationSize = 100;
		int nrTraits = player38.nrTraits;
		String mutationType = mutopts[funcs[2]];

		// init objects for the algorithm
		// ParentSelection parentSelector = new ParentSelection("arena");
		ParentSelection parentSelector = new ParentSelection(parselopts[funcs[0]]);
		Recombination recombinator = new Recombination(recopts[funcs[1]]);
		Mutation mutator = new Mutation(mutationType);
		SurvivorSelection survivorSelector = new SurvivorSelection(surselopts[funcs[3]]);

		//TODO: Errors in Blendcrossover; StochastifUniversalSampling; populationsize of roundrobin en in elitism.

		// Array for storing values for diversity
		ArrayList<Double> diversityArray = new ArrayList<Double>();

		// init population with random values between -5 and 5
		ArrayList<Individual> population = new ArrayList<Individual>();

		for (int j = 0; j < populationSize; j++)
		{
			Individual unit = new Individual(nrTraits,mutationType, std);
			double[] values = new double[nrTraits];
			for (int k = 0; k < nrTraits; k++)
			{
				values[k] = (rnd_.nextDouble() * 10.0) - 5.0; // normalize to [-5, 5]
			}
			unit.genome = values;
			population.add(unit);
		}

		int generations = -1;

		// calculate fitness
		while(evals < evaluations_limit_-200)
		{

			// CALCULATING DIVERSITY
			// niet weggooien plzzzz
			// Diversity is measured per generation, as the total Manhattan distance between all points

			// generations +=1;
			// double diversity = 0;
			//
			// for (int j = 0; j < populationSize; j++)
			// {
			// 	for (int k = 0; k < populationSize; k++)
			// 	{
			// 		double[] individual1 = population.get(j).genome;
			// 		double[] individual2 = population.get(k).genome;
			//
			// 		for (int l = 0; l < individual1.length; l++)
			// 		{
			// 			diversity += Math.abs(individual1[l] - individual2[l]);
			// 		}
			// 	}
			// }
			// diversityArray.add(diversity);


			// Compute scores per individual
			double maxScore = 0;
			double minScore = 1;

			// Check and save fitness for all individuals
			for (int j = 0; j < populationSize; j++)
			{
				//TODO: rounsRobin geeft hier error
				population.get(j).score = (double) evaluation_.evaluate(population.get(j).genome);
				evals++;

				// save largest and smallest score for normalization
				if (population.get(j).score > maxScore){maxScore = population.get(j).score;}
				if (population.get(j).score < minScore)	{minScore = population.get(j).score;}
			}

			// Select parents from population
			ArrayList<Individual> selectedParents = parentSelector.performSelection(population, 50);
			Collections.shuffle(selectedParents);

			// Recombinate parents into children
			double[][] genomes = recombinator.performRecombination(selectedParents, alpha);
			int numChild = genomes.length;

			ArrayList<Individual> children = new ArrayList<Individual>();
			for (int j = 0; j < numChild; j++)
			{
				Individual unit = new Individual(nrTraits,mutationType, std);
				unit.genome = genomes[j];
				children.add(unit);
			}

			// Apply mutation to each child.
			children = mutator.performMutation(children, std);

			// Evaluate children
			for (int j = 0; j < numChild; j++) {
				children.get(j).score = (double) evaluation_.evaluate(children.get(j).genome);
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
