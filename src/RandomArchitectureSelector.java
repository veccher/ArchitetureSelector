import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class RandomArchitectureSelector implements ArchitectureSelector{
	private int nLayers;
	private String fileName;
	private int numRuns;
	private MLP bestMLP;
	private Log log;
	
	public RandomArchitectureSelector(int nLayers, String fileName, int numRuns, int thisRun)
	{
		this.nLayers=nLayers;
		this.fileName=fileName;
		this.numRuns=numRuns;
		log = new Log("random " +fileName.replace(".arff", "")+ " " +nLayers+" layers run "+thisRun);
	}
	
	public void run()
	{
		Random rand=new Random();
		float top=0;
		ArrayList<Integer> currentArch = new ArrayList<Integer>();
		//roda numRuns vezes, avaliando uma rede aleatória cada uma das vezes;
		for (int j=0;j<numRuns;j++)
		{
			currentArch = new ArrayList<Integer>();
			for(int i=0;i<nLayers;i++)
			{
				currentArch.add(rand.nextInt(10)+1);
			}
			try {
				MLP net=new MLP(currentArch,fileName);
				System.out.println(j+ " " + net.classify());
				log.addLine(currentArch.toString() + " " + net.getResults());
				if (bestMLP==null || net.getResults() > bestMLP.getResults())
					bestMLP=net;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
