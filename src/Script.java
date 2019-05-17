import java.util.ArrayList;
import java.util.Random;

public class Script {
	static ArrayList<String> bases;
	static ArrayList<ArrayList<Integer>> initialArchs;
	
	public static void initialize ()
	{
		Random rand=new Random();
		initialArchs=new ArrayList<ArrayList<Integer>>();
		bases=new ArrayList<String>();
		bases.add("iris.arff");
		bases.add("diabetes.arff");
		//bases.add("hypothyroid.arff");
		//para cada numero de camadas iniciais (1 a 5)
		for (int i=1;i<=5;i++)
		{
			//para cada numero de redes iniciais por tamanho da camada
			for (int k=1;k<=3;k++)
			{
				ArrayList<Integer> temp= new ArrayList<Integer>();
				//preenche aleatoriamente as camadas.
				for (int j=0;j<i;j++)
				{
					temp.add(rand.nextInt(10)+1);
				}
				initialArchs.add(temp);
			}
		}
		// no final temos 15 redes de 1a 5 camadas (3 de cada) preenchidas com elementos
		// aleatorios.
	}
	public static void main (String Args[])
	{
		initialize();
		//para cada base
		
		for (String base : bases)
		{
			//para cada tecnica
			
			for (int tec=1;tec<2;tec++)
			{
				//para cada num de camadas
				for (int numLay=1;numLay<=5;numLay++)
				{
					int run=1;
					//para cada camada inicial
					for (ArrayList<Integer> initialArch : initialArchs)
					{
						//avalia as arquiteturas iniciais com numeros de camadas estabelecido
						//para essa iteracao.
						ArrayList<Integer> arch=new ArrayList<Integer>(initialArch);
						ArchitectureSelector sel=null;
						if (initialArch.size()!=numLay)
							continue;
						switch (tec)
						{
						case 0:
							sel=new GreedyHillClimb(arch,base);
							break;
						case 1:
							sel=new RandomHillClimb(arch,base);
							break;
						case 2:
							sel=new SimulatedAnnealing(arch,base);
							break;
						case 3:
							sel=new RandomArchitectureSelector(numLay,base,12,run++);
							break;
						}
						sel.run();
						
					}
				}
			}
		}
	}
}
