
public class ExperimentData
{
	private int controlNumberOfSamples;
	private double controlMean;
	private double controlVariance;
	private int treatmentNumberOfSamples;
	private double treatmentMean;
	private double treatmentVariance;
	private double effectiveSampleSize;
	private double effectSize;
	
	public ExperimentData()
	{
		controlNumberOfSamples = -1;
		controlMean = -1.0;
		controlVariance = -1.0;
		treatmentNumberOfSamples = -1;
		treatmentMean = -1.0;
		treatmentVariance = -1.0;
		effectiveSampleSize = -1.0;
		effectSize = -1.0;
	}
	

	public int getControlNumberOfSamples()
	{
		return controlNumberOfSamples;
	}

	public void setControlNumberOfSamples(int controlNumberOfSamples)
	{
		this.controlNumberOfSamples = controlNumberOfSamples;
	}

	public double getControlMean()
	{
		return controlMean;
	}

	public void setControlMean(double controlMean)
	{
		this.controlMean = controlMean;
	}

	public double getControlVariance()
	{
		return controlVariance;
	}

	public void setControlVariance(double controlVariance)
	{
		this.controlVariance = controlVariance;
	}

	public int getTreatmentNumberOfSamples()
	{
		return treatmentNumberOfSamples;
	}

	public void setTreatmentNumberOfSamples(int treatmentNumberOfSamples)
	{
		this.treatmentNumberOfSamples = treatmentNumberOfSamples;
	}

	public double getTreatmentMean()
	{
		return treatmentMean;
	}

	public void setTreatmentMean(double treatmentMean)
	{
		this.treatmentMean = treatmentMean;
	}

	public double getTreatmentVariance()
	{
		return treatmentVariance;
	}

	public void setTreatmentVariance(double treatmentVariance)
	{
		this.treatmentVariance = treatmentVariance;
	}

	public double getEffectiveSampleSize()
	{
		return effectiveSampleSize;
	}

	public void setEffectiveSampleSize(double effectiveSampleSize)
	{
		this.effectiveSampleSize = effectiveSampleSize;
	}

	public double getEffectSize()
	{
		return effectSize;
	}

	public void setEffectSize(double effectSize)
	{
		this.effectSize = effectSize;
	}
}
