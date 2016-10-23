
import java.util.Random;

public class ConstantRNG
{
	private static Random random = new Random(12);

	private ConstantRNG()
	{
		//
	}

	public static void createNewRNG()
	{
		random = new Random(12);
	}

	public static Random getRNG()
	{
		return random;
	}

	public static double getNextNumberRawU()
	{
		return random.nextDouble();
	}

	public static double getNextNumberRawG()
	{
		return random.nextGaussian();
	}

	public static int getNextInt()
	{
		return random.nextInt();
	}

	public static int getNextInt(int lowerBound, int upperBound)
	{
		int adjustedUpperBound = (upperBound+1) - lowerBound;
		int randomNumber = random.nextInt(adjustedUpperBound);
		int adjustedNumber = randomNumber + lowerBound;

		return adjustedNumber;
	}

	public static double getNextNumberUniform(double lowerBound, double upperBound)
	{
		double randomNumber = getNextNumberRawU();

		randomNumber = (lowerBound + randomNumber * (upperBound - lowerBound));

		// round to 2 decimal places
		randomNumber = (Math.floor(randomNumber * 100)) / 100;

		return randomNumber;
	}

	//randomly select index from "value distribution"
	//might be wrong terminology, thus name subjected to change
	//values array does not need to be normalized beforehand
	public static int getNextIntFromDistribution(double[] distributions)
	{
		double sum = 0;
		
		for(double v: distributions)
		{
			sum += v;
		}
		
		double counter = 0;
		double randomChoice = ConstantRNG.getNextNumberRawU();
		
		int selected = -1;
		
		for(int i = 0; i<distributions.length; i++)
		{
			//System.out.println(counter+ " " + randomChoice);
			//System.out.println(distributions[i]);
			counter += distributions[i]/sum;
			if(randomChoice < counter) 
			{
				selected = i;
				break;
			}
		}

		if(selected == -1)
		{
			//if this happens it means there's a bug
			System.out.println("there's a bug in SoftMaxPolicy.getNextIntFromDistribution");
		}
		return selected;
		
	}
}
