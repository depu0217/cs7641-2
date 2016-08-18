
import rl.EpsilonGreedyStrategy;
import rl.ExplorationStrategy;
import rl.Policy;
import rl.PolicyIteration;
import rl.QLambda;
import rl.SarsaLambda;
import rl.SimpleMarkovDecisionProcess;
import rl.ValueIteration;
import shared.FixedIterationTrainer;
import shared.ThresholdTrainer;

/**
 * A Gambler's MDP 
 * 
 * 
 */
public class MDP_Gamble {
    /**
     * The main method
     * @param args ignored
     */
    public static void main(String[] args) {
         
        SimpleMarkovDecisionProcess mdp = new SimpleMarkovDecisionProcess();
        
        mdp.setRewards(new double[] {0,0,0,0,0,1});
        
        mdp.setTransitionMatrices(new double[][][] {
				{{1,0,0,0,0,0},{1 ,0, 0,0,0,0},{1 ,0,0,0,0,0 },{1,0,0,0,0,0},{1,0,0,0,0,0},{1,0,0,0,0,0}},
        		{{0,1,0,0,0,0},{.6,0,.4,0,0,0},{0 ,1,0,0,0,0 },{0,1,0,0,0,0},{0,1,0,0,0,0},{0,1,0,0,0,0}},
        		{{0,0,1,0,0,0},{0,.6,0,.4,0,0},{.6,0,0,0,.4,0},{0,0,1,0,0,0},{0,0,1,0,0,0},{0,0,1,0,0,0}},
        		{{0,0,0,1,0,0},{0,0,.6,0,.4,0},{0,.6,0,0,0,.4},{0,0,0,1,0,0},{0,0,0,1,0,0},{0,0,0,1,0,0}}, 		
        		{{0,0,0,0,1,0},{0,0,0,.4,0,.4},{0 ,0,0,0,1,0 },{0,0,0,0,1,0},{0,0,0,0,1,0},{0,0,0,0,1,0}},
				{{0,0,0,0,0,1},{0 ,0, 0,0,0,1},{0 ,0,0,0,0,1 },{0,0,0,0,0,1},{0,0,0,0,0,1},{0,0,0,0,0,1}}


        });

        
        
        mdp.setInitialState(3);
        // solve it
        ValueIteration vi = new ValueIteration(0.9, mdp);
        ThresholdTrainer tt = new ThresholdTrainer(vi);
        long startTime = System.currentTimeMillis();
        tt.train();
        Policy p = vi.getPolicy();
        long finishTime = System.currentTimeMillis();
        System.out.println("Value iteration learned : " + p);
        System.out.println("in " + tt.getIterations() + " iterations");
        System.out.println("and " + (finishTime - startTime) + " ms");
        
        PolicyIteration pi = new PolicyIteration(0.9, mdp);
        tt = new ThresholdTrainer(pi);
        startTime = System.currentTimeMillis();
        tt.train();
        p = pi.getPolicy();
        finishTime = System.currentTimeMillis();
        System.out.println("Policy iteration learned : " + p);
        System.out.println("in " + tt.getIterations() + " iterations");
        System.out.println("and " + (finishTime - startTime) + " ms");
        
        
        int iterations = 500000;
        
        QLambda ql = new QLambda(.5, 0.9, .3, .995, new EpsilonGreedyStrategy(0.3), mdp);
        FixedIterationTrainer fit = new FixedIterationTrainer(ql, iterations);
        startTime = System.currentTimeMillis();
        fit.train();
        p = ql.getPolicy();
        finishTime = System.currentTimeMillis();
        System.out.println("Q lambda learned : " + p);
        System.out.println("in " + iterations + " iterations");
        System.out.println("and " + (finishTime - startTime) + " ms");
        System.out.println("Acquiring " + ql.getTotalReward() + " reward");
        
       /* SarsaLambda sl = new SarsaLambda(.5, 0.9, .3, .995, new EpsilonGreedyStrategy(0.3), mdp);
        fit = new FixedIterationTrainer(sl, iterations);
        startTime = System.currentTimeMillis();
        fit.train();
        p = sl.getPolicy();
        finishTime = System.currentTimeMillis();
        System.out.println("Sarsa lambda learned : " + p);
        System.out.println("in " + iterations + " iterations");
        System.out.println("and " + (finishTime - startTime) + " ms");
        System.out.println("Acquiring " + sl.getTotalReward() + " reward");*/
                
    }

}
