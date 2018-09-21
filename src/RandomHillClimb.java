import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomHillClimb {
	public void run()
	{
		int nLayers=3;
		Random rand=new Random();
		float top=0;
		ArrayList<Integer> currentArch = new ArrayList<Integer>();
		ArrayList<ArrayList<Integer>> neighborhood = new ArrayList<ArrayList<Integer>>();
		//random initialize
		/*for (int j=0;j<60;j++)
		{
			currentArch = new ArrayList<Integer>();*/
		for(int i=0;i<nLayers;i++)
		{
			currentArch.add(rand.nextInt(10)+1);
		}/*
			try {
				MLP net=new MLP(currentArch);
				System.out.println(j+ " " + net.classify());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		//iterate
		try {
			top=new MLP(currentArch).classify();
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
				for (ArrayList<Integer> neighboor : neighborhood)
				{
					MLP net=(new MLP(neighboor));
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
					break;
				top=temp;
				currentArch=tempArch;
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