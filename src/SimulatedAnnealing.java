import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class SimulatedAnnealing implements ArchitectureSelector{
	float heat = 100;
	float decayRate = 3;
	int maxStopped=3;
	int countdown=maxStopped;
	int nLayers;
	ArrayList<Integer> initialArch;
	String fileName;
	Log log;
	
	public SimulatedAnnealing(ArrayList<Integer> initialArch, String fileName)
	{
		this.nLayers=initialArch.size();
		this.fileName=fileName;
		this.initialArch=initialArch;
		log = new Log("SA " +fileName.replace(".arff", "")+ " " +initialArch);
	}
	
	boolean isRandom()
	{
		Random rand=new Random();
		heat-=decayRate;
		if (heat<rand.nextInt()%100)
			return false;
		return true;
	}
	
	
	public void run()
	{
		ArrayList<ArrayList<Integer>> neighborhood = new ArrayList<ArrayList<Integer>>();
		int nLayers=3;
		Random rand=new Random();
		float top=-1;//top precision
		float current=-1;//current precision
		ArrayList<Integer> currentArch = initialArch;
		//iterate
		try {
			top=current=new MLP(currentArch, fileName).classify();
			log.addLine(currentArch.toString()+" "+top + " inicial");
			//itera
			while(true)
			{
				ArrayList<Integer> tempArch = new ArrayList<Integer>();
				ArrayList<Integer> temp2Arch = new ArrayList<Integer>();
				System.out.println("Arch Atual: "+currentArch+"precisão: "+top);
				neighborhood = new ArrayList<ArrayList<Integer>>();
				for (int camada=0;camada<currentArch.size();camada++)
				{
					for (int add=-1;add<2;add+=2)
					{
						temp2Arch=new ArrayList<Integer>();
						temp2Arch.addAll(currentArch);
						temp2Arch.set(camada, temp2Arch.get(camada)+add);
						neighborhood.add(temp2Arch);
					}
				}
				if (this.isRandom())
				{
					ArrayList<Integer> neighbor=neighborhood.get(rand.nextInt(neighborhood.size()));
					MLP net=new MLP(neighbor, fileName);
					current=net.classify();
					log.addLine(neighbor+ " " + current + " random");
					currentArch.clear();
					currentArch.addAll(neighbor);
				}
				else 
				{
					//faz uma iteração gulosa.
					boolean climbed=false;
					for (ArrayList<Integer> neighboor : neighborhood)
					{
						
						MLP net=(new MLP(neighboor,fileName));
						System.out.println("arq: "+neighboor+" precisao: " +net.classify());
						if(net.getResults()>current)
						{
							climbed=true;
							current=net.getResults();
							currentArch.clear();
							currentArch.addAll(neighboor);
						}
						log.addLine(neighboor+" "+ net.getResults()+" greedy");
					}
					if (!climbed)
					{
						if (--countdown==0)
							break;
					}
					else 
					{
						countdown=maxStopped;
						
					}
				}
				if (current>top)
				{
					top=current;
				}
			}
			log.save();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}