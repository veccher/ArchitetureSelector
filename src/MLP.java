import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import weka.classifiers.functions.MultilayerPerceptron;
import weka.core.Instances;
import weka.core.Utils;
import weka.core.converters.ArffLoader.ArffReader;

public class MLP implements Runnable{
	Instances data;
	private String options;
	public MultilayerPerceptron model;
	public float precision;
	
	private void setOptions(ArrayList<Integer> layers) {
		options=new String("-H ");
		boolean first=true;
		for (Integer layerSize:layers){
			if(!first)
				options+=",";
			options+=layerSize.toString();
			first=false;
		}
		if (options.equals("-H"))
			options="";
		//System.out.println(options);
	}
	public MLP (ArrayList<Integer> layers, String fileName) throws IOException
	{
		BufferedReader reader =new BufferedReader(new FileReader(fileName));
		ArffReader arff = new ArffReader(reader);
		this.data = arff.getData();
		data.setClassIndex(data.numAttributes() - 1);
		setOptions(layers);
		//new Thread(this).start();
	}
	public synchronized Float classify() throws Exception {
		int acertoTotal=0;
		int testeTotal=0;
		for (int i=0;i<10;i++)
		{
			data.randomize(new java.util.Random(0));
			//int acertosCamada=0;
			int j=0;
			Instances trainSet=data.trainCV(10,i);
			Instances testSet=data.testCV(10,i);
			model = new MultilayerPerceptron();
			((MultilayerPerceptron) model).setOptions(Utils.splitOptions(options));
			model.buildClassifier(trainSet);
			
			testeTotal+=testSet.size();
			for (j=0;j<testSet.size();j++)
			{
				if(model.classifyInstance(testSet.get(j))==testSet.get(j).classValue())
				{
					//acertosCamada++;
					acertoTotal++;
				}
			}
			
			//System.out.print("taxa de acerto no conjunto="+acertosCamada*100f/j+"%\n");
			
		}
		this.precision=acertoTotal*100f/testeTotal;
		notify();
		return acertoTotal*100f/testeTotal;	
	}
	float getResults()
	{
		return this.precision;
	}
	@Override
	public void run() {
		try {
			this.precision=this.classify();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public int compareTo(MLP other) {
		return ((Float)other.getResults()).compareTo(this.getResults());
	}
}
