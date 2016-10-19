import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;

//Specific implementation for our work
public class ExpectationMaximization
{
	//probability of an experiment having a difference between conditions ('p')
	double experimentProbability;
	
	//variance of effect sizes ('V')
	double effectSizesVariance;
	
	//when to determine convergence of EM
	double epsilon;
	
	int iterationCount =1; ;
	
	
	
	public ExpectationMaximization()
	{
		experimentProbability = -1.0;
		effectSizesVariance = -1.0;
		epsilon = 0.01;
	}
	
	public ExpectationMaximization(double experimentProbability, double effectSizesVariance)
	{
		this.experimentProbability = experimentProbability;
		this.effectSizesVariance = effectSizesVariance;
		this.epsilon = 0.00001;
	}
	
	public ExpectationMaximization(double experimentProbability, double effectSizesVariance, double epsilon)
	{
		this.experimentProbability = experimentProbability;
		this.effectSizesVariance = effectSizesVariance;
		this.epsilon = epsilon;
	}

	public double getExperimentProbability()
	{
		return experimentProbability;
	}

	public void setExperimentProbability(double experimentProbability)
	{
		this.experimentProbability = experimentProbability;
	}

	public double getEffectSizesVariance()
	{
		return effectSizesVariance;
	}

	public void setEffectSizesVariance(double effectSizesVariance)
	{
		this.effectSizesVariance = effectSizesVariance;
	}
	
	public double getEpsilon()
	{
		return epsilon;
	}

	public void setEpsilon(double epsilon)
	{
		this.epsilon = epsilon;
	}
	
	public void runExpectationMaximization(List<ExperimentData> experimentDataList)
	{
		
		
		double posteriorExperimentProbability = Double.MAX_VALUE;
		double difference = Math.abs(experimentProbability - posteriorExperimentProbability);
		
		//TODO
		//LEARN HYPER PRIORS FROM STAN MODEL HERE
		
		
		while(difference > epsilon)
		{
			System.out.println("EM Iteration " + iterationCount);
			
			ArrayList<Double> posteriorProbabilityList = new ArrayList<Double>();
			
			//Step 1: Update posterior probabilities
			
			for(int i=0; i<experimentDataList.size(); i++)
			{
				double numeratorVariance = (1.0 / experimentDataList.get(i).getEffectiveSampleSize()) + Math.pow(effectSizesVariance, 2);
				double denominatorVariance = 1.0 / experimentDataList.get(i).getEffectiveSampleSize();
				
				NormalDistribution numeratorDistribution = new NormalDistribution(0, Math.sqrt(numeratorVariance));
				NormalDistribution denominatorDistribution = new NormalDistribution(0, Math.sqrt(denominatorVariance));
				
				double numerator = numeratorDistribution.density(experimentDataList.get(i).getEffectSize());
		    	double denominator = denominatorDistribution.density(experimentDataList.get(i).getEffectSize());
		    	
		    	double bayesFactor = numerator/denominator;
		    	double priorOdds = experimentProbability / (1.0 - experimentProbability);
		    	double posteriorOdds = bayesFactor * priorOdds;
		    	double posteriorProbability = posteriorOdds / (1.0 + posteriorOdds);
		    	
		    	posteriorProbabilityList.add(posteriorProbability);
			}
			
			
			
			//Step 2: Update experimentProbability ('p')
		
			double sumOfPosteriors = 0.0;
			
			for(int i=0; i<posteriorProbabilityList.size(); i++)
			{
				sumOfPosteriors = sumOfPosteriors + posteriorProbabilityList.get(i);
			}
			
			posteriorExperimentProbability = sumOfPosteriors / posteriorProbabilityList.size();
			
			
			
			//Step 3: Update effectSizesVariance ('V')
			
			double sumEffectiveSampleSize = 0.0;
			
			for(int i=0; i<experimentDataList.size(); i++)
			{
				sumEffectiveSampleSize = sumEffectiveSampleSize + experimentDataList.get(i).getEffectiveSampleSize();
			}
			
			double lowerBound = 4 * 1.0 / ((sumEffectiveSampleSize / experimentDataList.size()));
			double varianceSquared = 0.0;
			double weightedAverage1 = 0.0;
			double weightedAverage2 = 0.0;
			
			for(int i=0; i<posteriorProbabilityList.size(); i++)
			{
				weightedAverage1 = weightedAverage1 + (Math.pow(experimentDataList.get(i).getEffectSize(), 2) * posteriorProbabilityList.get(i)) / sumOfPosteriors;
				weightedAverage2 = weightedAverage2 + (1.0 / experimentDataList.get(i).getEffectiveSampleSize()) * posteriorProbabilityList.get(i) / sumOfPosteriors;
			}
			
			varianceSquared = weightedAverage1- weightedAverage2;
			
			if(varianceSquared<lowerBound)
			{
				varianceSquared = lowerBound;
			}
			
			effectSizesVariance = Math.sqrt(varianceSquared);
			
			
			
			//Recalculate difference and set experimentProbability
			
			difference = Math.abs(experimentProbability - posteriorExperimentProbability);
			experimentProbability = posteriorExperimentProbability;
			
			System.out.println("Probability = " + experimentProbability);
			iterationCount++;
		}
		
	}
	
}
