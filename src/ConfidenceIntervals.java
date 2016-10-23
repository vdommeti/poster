import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ConfidenceIntervals 
{
	public List<ExperimentData> getBootStrapSamples(List<ExperimentData> experimentDataList) 
	 {
		Random random = ConstantRNG.getRNG();
		List<ExperimentData> resampleExperimentDataList = new ArrayList<>();
		for(int i=0;i<experimentDataList.size();i++)
		 {
			int j=random.nextInt(experimentDataList.size());
			resampleExperimentDataList.add(experimentDataList.get(j));
		 }
		return resampleExperimentDataList;
	}
}
