import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GreedyHillClimb implements ArchitectureSelector{
	private ArrayList<Integer> initialArch;
	private String fileName;
	Log log;
	public GreedyHillClimb(ArrayList<Integer> initialArch, String fileName)
	{
		this.initialArch=initialArch;
		this.fileName=fileName;
		log = new Log("GHC " +fileName.replace(".arff", "")+ " " +initialArch);
	}
	public void run()
	{
		Random rand=new Random();
		float top=0;
		ArrayList<Integer> currentArch = initialArch;

		//iterate
		try {
			top=new MLP(currentArch,fileName).classify();
			log.addLine(currentArch.toString() + " " + top);
			while(true)
			{
				float temp=0;
				ArrayList<Integer> tempArch = new ArrayList<Integer>();
				ArrayList<Integer> temp2Arch = new ArrayList<Integer>();
				System.out.println("Arch Atual: "+currentArch+"precisão: "+top);
				for (int camada=0;camada<currentArch.size();camada++)
				{
					for (int add=-1;add<2;add+=2)
					{
						temp2Arch.clear();
						temp2Arch.addAll(currentArch);
						temp2Arch.set(camada, temp2Arch.get(camada)+add);
						MLP net=new MLP(temp2Arch,fileName);
						System.out.println("camada "+camada+" incremento: "+add+" arq: "+temp2Arch+" precisao: " +net.classify());
						if(net.getResults()>temp)
						{
							temp=net.getResults();
							tempArch.clear();
							tempArch.addAll(temp2Arch);
						}
						log.addLine(temp2Arch.toString() + " " + net.getResults());
					}
				}
				if (temp<=top)
					break;
				top=temp;
				currentArch=tempArch;
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
