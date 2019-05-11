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
	
	public SimulatedAnnealing(int nLayers, String fileName, ArrayList<Integer> initialArch)
	{
		this.nLayers=nLayers;
		this.fileName=fileName;
		this.initialArch=initialArch;
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
		float top=0;
		ArrayList<Integer> currentArch = initialArch;
		//iterate
		try {
			top=new MLP(currentArch, fileName).classify();
			while(true)
			{
				float temp=0;
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
						//MLP net=new MLP(temp2Arch);
						neighborhood.add(temp2Arch);
						//System.out.println("camada "+camada+" incremento: "+add+" arq: "+temp2Arch+" precisao: " +net.classify());
						/*if(net.getResults()>temp)
						{
							temp=net.getResults();
							tempArch.clear();
							tempArch.addAll(temp2Arch);
						}*/
					}
				}
				if (this.isRandom())
				{
					ArrayList<Integer> neighbor=neighborhood.get(rand.nextInt(neighborhood.size()));
					MLP net=new MLP(neighbor, fileName);
					temp=net.classify();
					currentArch.clear();
					currentArch.addAll(neighbor);
					if (temp>top)
						top=temp;
				}
				else 
				{
					for (ArrayList<Integer> neighboor : neighborhood)
					{
						MLP net=(new MLP(neighboor,fileName));
						System.out.println("arq: "+neighboor+" precisao: " +net.classify());
						if(net.getResults()>top)
						{
							temp=net.getResults();
							tempArch.clear();
							tempArch.addAll(neighboor);
							break;
						}
					}
					if (temp<=top)
					{
						if (--countdown==0)
							break;
					}
					else 
					{
						countdown=maxStopped;
						
					}
				}
				if (temp>top)
				{
					top=temp;
					currentArch=tempArch;
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}