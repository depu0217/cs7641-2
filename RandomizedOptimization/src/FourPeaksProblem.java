import java.util.Arrays;

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
import opt.ga.SingleCrossOver;
import opt.ga.GenericGeneticAlgorithmProblem;
import opt.ga.GeneticAlgorithmProblem;
import opt.ga.MutationFunction;
import opt.ga.StandardGeneticAlgorithm;
import opt.prob.GenericProbabilisticOptimizationProblem;
import opt.prob.MIMIC;
import opt.prob.ProbabilisticOptimizationProblem;
import shared.FixedIterationTrainer;

public class FourPeaksProblem {
    private static final int N = 80;
    
    private static final int T = N/10;
    private static final int M = 10;
    
    public static void main(String[] args) {
        int[] ranges = new int[N];
        Arrays.fill(ranges, 2);
        EvaluationFunction ef = new FourPeaksEvaluationFunction(T);
        Distribution odd = new DiscreteUniformDistribution(ranges);
        NeighborFunction nf = new DiscreteChangeOneNeighbor(ranges);
        MutationFunction mf = new DiscreteChangeOneMutation(ranges);
        CrossoverFunction cf = new SingleCrossOver();
        Distribution df = new DiscreteDependencyTree(.1, ranges); 
        HillClimbingProblem hcp = new GenericHillClimbingProblem(ef, odd, nf);
        GeneticAlgorithmProblem gap = new GenericGeneticAlgorithmProblem(ef, odd, mf, cf);
        ProbabilisticOptimizationProblem pop = new GenericProbabilisticOptimizationProblem(ef, odd, df);
        long start;
        
        
        for (int i = 0; i < M; i++) {
        	        start = System.currentTimeMillis();
	        RandomizedHillClimbing rhc = new RandomizedHillClimbing(hcp);      
	        FixedIterationTrainer fit_rhc = new FixedIterationTrainer(rhc, 200000);
	        fit_rhc.train();
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
	        //System.out.println(ef.value(ga.getOptimal())); 
        }  
        
        
        for (int i = 0; i < M; i++) {

	        start = System.currentTimeMillis();
	        MIMIC mimic = new MIMIC(200,5, pop);
	        FixedIterationTrainer fit_m = new FixedIterationTrainer(mimic, 1000);
	        fit_m.train();
	        System.out.println(i+","+"MIMIC"+","+ef.value(mimic.getOptimal())+"," + (System.currentTimeMillis() - start));
	        //System.out.println(ef.value(mimic.getOptimal())); 
        	 
        }
        
        
        }
    }

