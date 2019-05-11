import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomHillClimb implements ArchitectureSelector{
	private ArrayList<Integer> initialArch;
	private String fileName;
	
	public RandomHillClimb(ArrayList<Integer> initialArch, String fileName)
	{
		this.initialArch=initialArch;
		this.fileName=fileName;
	}
	public void run()
	{
		Random rand=new Random();
		float top=0;
		ArrayList<Integer> currentArch = initialArch;
		ArrayList<ArrayList<Integer>> neighborhood = new ArrayList<ArrayList<Integer>>();
		//iterate
		try {
			top=new MLP(currentArch,fileName).classify();
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