import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Random;

import dist.DiscreteDependencyTree;
import dist.DiscretePermutationDistribution;
import dist.DiscreteUniformDistribution;
import dist.Distribution;
import opt.SwapNeighbor;
import opt.GenericHillClimbingProblem;
import opt.HillClimbingProblem;
import opt.NeighborFunction;
import opt.RandomizedHillClimbing;
import opt.SimulatedAnnealing;
import opt.example.*;
import opt.ga.CrossoverFunction;
import opt.ga.SwapMutation;
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;


public class TravelingSalesman {
    /** The n value */
    private static final int N = 50;
    private static final int M = 10;
    
    /**
     * The test main
     * @param args ignored
     */
    public static void main(String[] args) {
        Random random = new Random();
        long start;
        // create the random points
        double[][] points = new double[N][2];
        for (int i = 0; i < points.length; i++) {
            points[i][0] = random.nextDouble();
            points[i][1] = random.nextDouble();   
        }
        
        /**saveArray("data/mytsp.data",points);
        **/       
        points=loadArray("data/mytsp.data");
        
        
        // for rhc, sa, and ga we use a permutation based encoding
        
        TravelingSalesmanEvaluationFunction ef = new TravelingSalesmanRouteEvaluationFunction(points);
        Distribution odd = new DiscretePermutationDistribution(N);
        NeighborFunction nf = new SwapNeighbor();
        MutationFunction mf = new SwapMutation();
        CrossoverFunction cf = new TravelingSalesmanCrossOver(ef);
        HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
        GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
        
          // for mimic we use a sort encoding         
        
        ef = new TravelingSalesmanSortEvaluationFunction(points);
        int[] ranges = new int[N];
        Arrays.fill(ranges, N);
        odd = new  DiscreteUniformDistribution(ranges);
        Distribution df = new DiscreteDependencyTree(.1, ranges); 
        ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);
        
       
        
        for (int i = 0; i < M; i++) {

        start = System.currentTimeMillis();
        RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
        FixedIterationTrainer fit_rhc = new FixedIterationTrainer(rhc, 200000);
        fit_rhc.train();
        System.out.println(i+","+"RHC"+","+ef.value(rhc.getOptimal())+"," + (System.currentTimeMillis() - start));
        } 
 
        for (int i = 0; i < M; i++) {
        start = System.currentTimeMillis();     
        SimulatedAnnealing sa = new SimulatedAnnealing(1E12, .99, hcp);
        FixedIterationTrainer fit_sa = new FixedIterationTrainer(sa, 200000);
        fit_sa.train();
        //System.out.println(i+","+"SA"+","+ef.value(sa.getOptimal())+"," + (System.currentTimeMillis() - start));
        System.out.println(ef.value(sa.getOptimal())); 
        } 
        
        for (int i = 0; i < M; i++) {
        start = System.currentTimeMillis();
        
        StandardGeneticAlgorithm ga = new StandardGeneticAlgorithm(200, 100, 100, gap);
        FixedIterationTrainer fit_ga = new FixedIterationTrainer(ga, 1000);
        fit_ga.train();
        //System.out.println(i+","+"GA"+","+ef.value(ga.getOptimal())+"," + (System.currentTimeMillis() - start));
        System.out.println(ef.value(ga.getOptimal())); 
        }
        
        
        for (int i = 0; i < M; i++) {
        start = System.currentTimeMillis();     
        
        MIMIC mimic = new MIMIC(200, 100, pop);
        FixedIterationTrainer fit_m = new FixedIterationTrainer(mimic, 1000);
        fit_m.train();
        System.out.println(i+","+"MIMIC"+","+ef.value(mimic.getOptimal())+"," + (System.currentTimeMillis() - start));
        } 
        
    }
    
    
    private static void saveArray(String filename, double [][] output_veld) {
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

     private static double [][] loadArray(String filename) {
         try {
           FileInputStream fis = new FileInputStream(filename);
           ObjectInputStream in = new ObjectInputStream(fis);
           double[][] gelezen_veld = (double[][])in.readObject();
           in.close();
           return gelezen_veld;
         }
         catch (Exception e) {
             System.out.println(e);
         }
         return null;
     }


}
