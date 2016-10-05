import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
public class Stats {

	public static void main(String[] args) {
	List<Double> listOne = new ArrayList<Double>();
	for(int i =0;i<10;i++)
	{
		listOne.add((Math.random()*10.0));
	}
	
	DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
	for(int i=0;i<listOne.size();i++)
	descriptiveStatistics.addValue(listOne.get(i));
	double mean = descriptiveStatistics.getMean();
	double variance = descriptiveStatistics.getVariance();
	double stdDeviation = Math.sqrt(variance);
	System.out.println("mean is " + mean + " variance is "+ variance);
	NormalDistribution d;
	d = new NormalDistribution(0, .04);
	double p = d.density(0.01);
	
	System.out.println(" p value is " + p);
	int[] nControl = new int[10];
	int[] nTreatment = new int[10];
	for(int i=0;i<nControl.length;i++)
		nControl[i]= (int) (Math.random()*10.0);
	for(int i=0;i<nTreatment.length;i++)
		nTreatment[i]= (int) (Math.random()*10.0);
		
	float effectiveSampleSize = (float) (1/((1.0/nControl.length)+(1.0/nTreatment.length)));
    float meanOfnControl=0, meanOfnTreatment=0, nT, nC;
    nC = nControl.length;
    nT = nTreatment.length;
    meanOfnControl = Mean(nControl);
    meanOfnTreatment = Mean(nTreatment);

	float bigDelta = meanOfnControl- meanOfnTreatment;
	float var1 = Variance(meanOfnControl, nControl);
	System.out.println("var is "+ var1);
	float var2 = Variance(meanOfnTreatment, nTreatment);
	System.out.println("var is "+ var2);
	
	float pooledVar = PooledVar(var1, var2, nT, nC, effectiveSampleSize);
	System.out.println("Capital V is "+ pooledVar);
	
	float effectSize = EffectSize(bigDelta, pooledVar);
	System.out.println("EffectSize i.e small delta is "+ effectSize);
	}
	
	public static float Mean(int[] data)
	{  float sum=0;
		for(int i=0;i<data.length;i++)
			 sum = sum + data[i];
		return sum/data.length;
	}

	public static float Variance(float meanOfnControl, int[] nControl)
	{ 
		float sum=0,var=0;
		for(int i=0;i<nControl.length;i++)
			sum = sum+ (meanOfnControl - nControl[i])*(meanOfnControl - nControl[i]);
		    var = sum/ nControl.length;
		return var;
	}
	
	public static float PooledVar(float var1, float var2, float nT, float nC, float nE)
	{   float actualVar = 0;
	    actualVar = nE * ((var1/nC )+(var2/nT));
	    
		return actualVar ;
	}
	
	public static float EffectSize(float bigDelta, float actualVar)
	{  float effectSize=0;
	  effectSize = (float) (bigDelta/ Math.sqrt(actualVar));
		return effectSize;
	}
}


