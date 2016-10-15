import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
public class Stats {

	public static void main(String[] args) {
		int i=0,j=0;
		List<List<Double>> control = new ArrayList<List<Double>>();
		List<Double> controlMean, treatmentMean, controlVariance, treatmentVariance, effectSize, effectiveSampleSize;
		for(i =0;i<5;i++)
		{
			control.add(new ArrayList<Double>());
			for(j=0;j<5;j++)
			{
				control.get(i).add(j, (double) (Math.random()*10));
			}
			
		}
		System.out.println("control group data is \n"+control);
		
		List<List<Double>> treatment = new ArrayList<List<Double>>();
		for(i =0;i<5;i++)
		{
			treatment.add(new ArrayList<Double>());
			for(j=0;j<5;j++)
			{
				treatment.get(i).add(j, Math.floor((double) (Math.random()*10.0)));
			}
			
		}
		System.out.println("treatment group data is\n"+treatment);
		
		//Calculating means and variance for the experiments under two groups
		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
		controlMean = new ArrayList<Double>();
		controlVariance = new ArrayList<>();
		treatmentMean = new ArrayList<>();
		treatmentVariance = new ArrayList<>();
		
		for(i=0;i<5;i++){
	    java.util.Iterator<Double> controlIterator = control.get(i).iterator();
		
		while(controlIterator.hasNext())
		{
			descriptiveStatistics.addValue(controlIterator.next());
		}
		controlMean.add(i, descriptiveStatistics.getMean());
		controlVariance.add(i, Math.floor(descriptiveStatistics.getVariance()*100/100));
		descriptiveStatistics.clear();
	   }
	    System.out.println("Control mean is\n"+controlMean);
	    System.out.println("Contro variance is \n"+ controlVariance);
	    
	    for(i=0;i<5;i++){
		    java.util.Iterator<Double> treatmentIterator = treatment.get(i).iterator();
			
			while(treatmentIterator.hasNext())
			{
				descriptiveStatistics.addValue(treatmentIterator.next());
			}
			treatmentMean.add(i, descriptiveStatistics.getMean());
			treatmentVariance.add(i, descriptiveStatistics.getVariance());
			descriptiveStatistics.clear();
		   }
	    System.out.println("Treatment mean is\n"+controlMean);
	    System.out.println("Treatment variance is \n"+ controlVariance);
	    
	    //Calculating effect Size and Effective Sample Size
	    effectSize = new ArrayList<>();
		effectiveSampleSize = new ArrayList<>();
		double effectiveSampleSizeValue=0,pooledVariance =0,effectSizeValue=0;
		List<Double> oneOverEffectiveSampleSize = new ArrayList<>();
		
		for(i=0;i<5;i++)
		{
			effectiveSampleSizeValue =1/((1.0/control.get(i).size())+(1.0/treatment.get(i).size()));
			oneOverEffectiveSampleSize.add(i, 1/effectiveSampleSizeValue);
			pooledVariance = effectiveSampleSizeValue*((controlVariance.get(i)/control.get(i).size())+(treatmentVariance.get(i)/treatment.get(i).size()));
			effectSizeValue =(controlMean.get(i)-treatmentMean.get(i))/Math.sqrt(pooledVariance);
			effectiveSampleSize.add(i,effectiveSampleSizeValue);
			effectSize.add(i, effectSizeValue);
		}
	    System.out.println("effective Sample Size is \n"+ effectiveSampleSize);
	    System.out.println("effect Size is \n"+ effectSize);
	
	
	//to calculate Bayes Factor and posterior probabilities
	    List<Double>  posterior;
	    List<Double> weightAvg1, weightAvg2 ;
	    weightAvg1 = new ArrayList<>();
	    weightAvg2 = new ArrayList<>();
	    posterior = new ArrayList<>();
	    double capitalV, prior = 0;
	    double avgOfPosteriors;
	    descriptiveStatistics.clear();
	    for(i=0;i<5;i++)
	    {
	    	descriptiveStatistics.addValue(effectSize.get(i));
	    }
	    capitalV = descriptiveStatistics.getVariance();
	    System.out.println(" Capital V value is "+capitalV);
	    List<Double> bayesFactor = new ArrayList<>();
	    double numerator, denomenator;
	    
	    // everything should be in loop
	    // i.e follow EM algorithm which says
	    // update posterior Pi
	    // update probability with AvgPosteriors calculated from first step
	    // update capitalV .
	    //repeat until converge
	     
	    double diff =0, epsilon =.01E-50;
	    j=0;
	    prior = 0.05;
	    
	    do
	    {
	        posterior.clear();
	    	bayesFactor.clear();
	    	System.out.println("prior is"+ prior);
	    	
	    for(i=0;i<5;i++)
	    {   
	    	numerator = new NormalDistribution(0, (1/effectiveSampleSize.get(i))+Math.pow(capitalV, 2)).density(effectSize.get(i));
	    	denomenator = new NormalDistribution(0, 1/effectiveSampleSize.get(i)).density(prior);
	    	//System.out.println("denomenator is "+ denomenator);
	        bayesFactor.add(i, numerator/denomenator);
	        posterior.add(i,( prior/(1-prior))*bayesFactor.get(i));
	    }
	    /*System.out.println("prior is "+ prior);
	    System.out.println("bayes factor is "+ bayesFactor);*/
	    System.out.println("posterior list is "+ posterior);
	    double sumOfPosteriors = 0;
	    for(i=0;i<5;i++)
	    {
	    	sumOfPosteriors = sumOfPosteriors+ posterior.get(i);
	    }
	    
	     avgOfPosteriors = sumOfPosteriors/5;
	    // System.out.println("avg of posteriors is"+ avgOfPosteriors);
	    
	   for(i=0;i<5;i++)
	   {
		   weightAvg1.add(i, ((Math.pow(effectSize.get(i), 2)*posterior.get(i))/sumOfPosteriors));
		   weightAvg2.add(i, ((1/effectiveSampleSize.get(i))*posterior.get(i))/sumOfPosteriors);
	   }
	   double sumOfWeightAvg1=0, sumOfWeightAvg2=0;
	   for(i=0;i<5;i++)
	   {
		  sumOfWeightAvg1 = sumOfWeightAvg1+ weightAvg1.get(i);
		  sumOfWeightAvg2 = sumOfWeightAvg2+weightAvg2.get(i);
	   }
	   double diffOfWeights = sumOfWeightAvg1- sumOfWeightAvg2;
	   System.out.println("diff of weights is "+diffOfWeights);
	   double store = capitalV;
	    
	   if(diffOfWeights>0)
	   {
		   capitalV = diffOfWeights;
	   }
	   else
	   {   
		   descriptiveStatistics.clear();
		   for(i=0;i<5;i++)
		   descriptiveStatistics.addValue(oneOverEffectiveSampleSize.get(i));
		   double avgOfOneOverEffectiveSampleSize = descriptiveStatistics.getMean();
		  // System.out.println("avg of one over effective sample size is "+ avgOfOneOverEffectiveSampleSize);
		   capitalV = 4*avgOfOneOverEffectiveSampleSize;
		   
	   }
	   diff = Math.abs(store-capitalV);
	   System.out.println("capital v is "+ capitalV +" at iteration " + j);
	   prior = avgOfPosteriors;
	   j++;
	   if(j==5)
	   {
		   diff=epsilon-1;  // if it reaches j=5 then we get error of array out of bounds while cal numerator
	   }
	 }while(diff>epsilon);
	}
}


