import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;

import dist.DiscreteDependencyTree;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import opt.DiscreteChangeOneNeighbor;
import opt.EvaluationFunction;
import opt.GenericHillClimbingProblem;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.example.*;
import opt.ga.CrossoverFunction;
import opt.ga.DiscreteChangeOneMutation;
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.ga.UniformCrossOver;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

public class KnapsackProblem {
    /** Random number generator */
    private static final Random random = new Random();
    /** The number of items */
    private static final int NUM_ITEMS = 40;
    /** The number of copies each */
    private static final int COPIES_EACH = 4;
    /** The maximum weight for a single element */
    private static final double MAX_WEIGHT = 50;
    /** The maximum volume for a single element */
    private static final double MAX_VOLUME = 50;
    /** The volume of the knapsack */
    private static final double KNAPSACK_VOLUME = 
         MAX_VOLUME * NUM_ITEMS * COPIES_EACH * .4;
    
    private static final int M = 10;
    
    
    /**
     * The test main
     * @param args ignored
     */
    public static void main(String[] args) {
        int[] copies = new int[NUM_ITEMS];
        Arrays.fill(copies, COPIES_EACH);
        double[] weights = new double[NUM_ITEMS];
        double[] volumes = new double[NUM_ITEMS];
        /**for (int i = 0; i < NUM_ITEMS; i++) {
            weights[i] = random.nextDouble() * MAX_WEIGHT;
            volumes[i] = random.nextDouble() * MAX_VOLUME;
        }
        
        
        saveArray("data/myks_weight.data",weights);
        saveArray("data/myks_volumes.data",volumes);
        **/
        weights=loadArray("data/myks_weight.data");
        volumes=loadArray("data/myks_volumes.data");
        
        int[] ranges = new int[NUM_ITEMS];
        Arrays.fill(ranges, COPIES_EACH + 1);
        EvaluationFunction ef = new KnapsackEvaluationFunction(weights, volumes, KNAPSACK_VOLUME, copies);
        Distribution odd = new DiscreteUniformDistribution(ranges);
        NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
        MutationFunction mf = new DiscreteChangeOneMutation(ranges);
        CrossoverFunction cf = new UniformCrossOver();
        Distribution df = new DiscreteDependencyTree(.1, ranges); 
        HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
        GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
        ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);
        long start;
        
        for (int i = 0; i < M; i++) {
        	start = System.currentTimeMillis();
	        RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
	        FixedIterationTrainer fit = new FixedIterationTrainer(rhc, 200000);
	        fit.train();
	        System.out.println(i+","+"RHC"+","+ef.value(rhc.getOptimal())+"," + (System.currentTimeMillis() - start));
        }
        
        for (int i = 0; i < M; i++) {
	        start = System.currentTimeMillis();
	        SimulatedAnnealing sa = new SimulatedAnnealing(1E12, .95, hcp);
	        FixedIterationTrainer fit_sa = new FixedIterationTrainer(sa, 200000);
	        fit_sa.train();
	        System.out.println(i+","+"SA"+","+ef.value(sa.getOptimal())+"," + (System.currentTimeMillis() - start));
        }
        
        for (int i = 0; i < M; i++) {
	        start = System.currentTimeMillis();
	        StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 100, 10, gap);
	        FixedIterationTrainer fit_ga = new FixedIterationTrainer(ga, 1000);
	        fit_ga.train();
	        System.out.println(i+","+"GA"+","+ef.value(ga.getOptimal())+"," + (System.currentTimeMillis() - start));
        }
        
        for (int i = 0; i < M; i++) {
	        start = System.currentTimeMillis();
	        MIMIC mimic = new MIMIC(200, 100, pop);
	        FixedIterationTrainer fit_m = new FixedIterationTrainer(mimic, 1000);
	        fit_m.train();
	        System.out.println(i+","+"MIMIC"+","+ef.value(mimic.getOptimal())+"," + (System.currentTimeMillis() - start));
        }
    }

    private static void saveArray(String filename, double [] output_veld) {
        try {
           FileOutputStream fos = new FileOutputStream(filename);
           ObjectOutputStream out = new ObjectOutputStream(fos);
           out.writeObject(output_veld);
           out.flush();
           out.close();
        }
        catch (IOException e) {
            System.out.println(e); 
        }
     }

     private static double [] loadArray(String filename) {
         try {
           FileInputStream fis = new FileInputStream(filename);
           ObjectInputStream in = new ObjectInputStream(fis);
           double[] gelezen_veld = (double[])in.readObject();
           in.close();
           return gelezen_veld;
         }
         catch (Exception e) {
             System.out.println(e);
         }
         return null;
     }


}
