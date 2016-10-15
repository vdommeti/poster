import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dataset.Dataset;
import fileutility.FileUtilityExtra;
import fileutility.HeaderRowOptions;

public class Stats
{
	private List<ExperimentData> experimentDataList;
	private String inputFilePath;
	
	public Stats(String inputFilePath)
	{
		experimentDataList = new ArrayList<ExperimentData>();
		this.inputFilePath = inputFilePath;
	}
	
	public void readData() throws IOException
	{
		FileUtilityExtra fileUtility = new FileUtilityExtra();
		Dataset dataset = new Dataset();
		fileUtility.readDataset(inputFilePath, dataset, HeaderRowOptions.ADD);
		
		for(int i=0; i<dataset.getNumberOfRows(); i++)
		{
			ExperimentData experimentData = new ExperimentData();
			
			int controlSamplesColumnIndex = dataset.getHeaderColumnIndexByString(Constants.CONTROL_SAMPLES);
			int treatmentSamplesColumnIndex = dataset.getHeaderColumnIndexByString(Constants.TREATMENT_SAMPLES);
			int controlMeanColumnIndex = dataset.getHeaderColumnIndexByString(Constants.CONTROL_MEAN);
			int treatmentMeanColumnIndex = dataset.getHeaderColumnIndexByString(Constants.TREATMENT_MEAN);
			int controlVarianceColumnIndex = dataset.getHeaderColumnIndexByString(Constants.CONTROL_VARIANCE);
			int treatmentVarianceColumnIndex = dataset.getHeaderColumnIndexByString(Constants.TREATMENT_VARIANCE);
			//int effectSizeColumnIndex = dataset.getHeaderColumnIndexByString(Constants.EFFECT_SIZE);
			
			int controlSamples = Integer.parseInt(dataset.getDataRowValue(i, controlSamplesColumnIndex));
			int treatmentSamples = Integer.parseInt(dataset.getDataRowValue(i, treatmentSamplesColumnIndex));
			double controlMean = Double.parseDouble(dataset.getDataRowValue(i, controlMeanColumnIndex));
			double treatmentMean = Double.parseDouble(dataset.getDataRowValue(i, treatmentMeanColumnIndex));
			double controlVariance = Double.parseDouble(dataset.getDataRowValue(i, controlVarianceColumnIndex));
			double treatmentVariance = Double.parseDouble(dataset.getDataRowValue(i, treatmentVarianceColumnIndex));
			double effectiveSampleSize = Utils.calculateEffectiveSampleSize(controlSamples, treatmentSamples);
			double effectSize = Utils.calculateEffectSize(controlSamples, treatmentSamples, controlMean, treatmentMean, controlVariance, treatmentVariance);
					
			experimentData.setControlNumberOfSamples(controlSamples);
			experimentData.setTreatmentNumberOfSamples(treatmentSamples);
			experimentData.setControlMean(controlMean);
			experimentData.setTreatmentMean(treatmentMean);
			experimentData.setControlVariance(controlVariance);
			experimentData.setTreatmentVariance(treatmentVariance);
			experimentData.setEffectiveSampleSize(effectiveSampleSize);
			experimentData.setEffectSize(effectSize);
			
			experimentDataList.add(experimentData);
		}
	}
	
	public void runEM()
	{
		//get some initial estimates of priors
		//perhaps try different initialProbability values later
		
		double initialProbability = 0.05;
		double initialVariance = 0.0246; //TODO HARD CODED for our data (should be calculated)
		
		ExpectationMaximization em = new ExpectationMaximization(initialProbability, initialVariance);
		em.runExpectationMaximization(experimentDataList);
		
		double probability = em.getExperimentProbability();
		double variance = em.getEffectSizesVariance();
		
		System.out.println(probability);
		System.out.println(variance);
		
	}

	public static void main(String[] args) throws IOException
	{
		Stats stats = new Stats(args[0]);
		stats.readData();
		stats.runEM();
	}
}


