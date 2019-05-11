import java.util.ArrayList;
import java.util.Random;

public class Script {
	static ArrayList<String> bases;
	static ArrayList<ArrayList<Integer>> initialArchs;
	{
		Random rand=new Random();
		bases=new ArrayList<String>();
		bases.add("iris.arff");
		bases.add("diabetes.arff");
		bases.add("hypothyroid.arff");
		//para cada n�mero de camadas iniciais (1 � 5)
		for (int i=1;i<=5;i++)
		{
			//para cada n�mero de redes iniciais por tamanho da camada
			for (int k=1;k<=3;k++)
			{
				ArrayList<Integer> temp= new ArrayList<Integer>();
				//preenche aleatoriamente as camadas.
				for (int j=0;j<i;i++)
				{
					temp.add(rand.nextInt(10)+1);
				}
				initialArchs.add(temp);
			}
		}
		// no final temos 15 redes de 1 � 5 camadas (3 de cada) preenchidas com elementos
		// aleat�rios.
	}
	public static void main (String Args[])
	{
		//para cada base
		for (String base : bases)
		{
			//para cada t�cnica
			for (int tec=0;tec<4;tec++)
			{
				//para cada num de camadas
				for (int numLay=1;numLay<=5;numLay++)
				{
					//para cada camada inicial
					for (ArrayList<Integer> initialArch : initialArchs)
					{
						//avalia as arquiteturas iniciais com n�meros de camadas estabelecido
						//para essa itera��o.
						if (initialArch.size()!=numLay)
							continue;
						//executa os testes.
					}
					
				}
			}
		}
	}
}
