
public class Script {
	public static void main (String Args[])
	{
		GreedyHillClimb greedy=new GreedyHillClimb();
		RandomHillClimb random=new RandomHillClimb();
		SimulatedAnnealing sa=new SimulatedAnnealing();
		
		sa.run();
		greedy.run();
		random.run();
	}
}
