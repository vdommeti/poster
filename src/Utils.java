
import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

public class Utils {
	public static double calculateEffectiveSampleSize(int n1, int n2) {
		return 1.0 / ((1.0 / n1) + (1.0 / n2));
	}

	public static double calculateEffectSize(int n1, int n2, double mean1, double mean2, double variance1,
			double variance2) {
		double effectSize = -1.0;

		double pooledVariance = ((variance1 * (n1 - 1)) + (variance2 * (n2 - 1))) / (n1 + n2 - 2);
		effectSize = (mean1 - mean2) / Math.sqrt(pooledVariance);

		return effectSize;
	}

	public static double calculateVariance(List<Double> effectSizeList)
	{
		return Math.pow(calculateStandardError(effectSizeList), 2);
	}

	public static double calculateStandardError(List<Double> effectSizeList)
	{
		DescriptiveStatistics descriptiveStatistics = new DescriptiveStatistics();
		
		for (int i = 0; i < effectSizeList.size(); i++)
		{
			descriptiveStatistics.addValue(effectSizeList.get(i));
		}
		
		return descriptiveStatistics.getStandardDeviation();
	}
}
