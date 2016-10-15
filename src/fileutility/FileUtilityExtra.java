package fileutility;


import java.io.*;
import dataset.Dataset;


public class FileUtilityExtra extends FileUtility
{
	public FileUtilityExtra()
	{
		
	}

	
	public Dataset readDataset(String fileName, Dataset Dataset) throws IOException
	{
		File file = new File(fileName);
		return readDataset(file, Dataset);
	}
	
	public Dataset readDataset(File fileName, Dataset dataset) throws IOException
	{		
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String line;
		
		while((line=bufferedReader.readLine()) != null)
		{
			dataset.addDataRow(line);
		}
		
		fileReader.close();
		bufferedReader.close();
		
		return dataset;
	}
	
	public Dataset readDataset(String fileName, Dataset dataset, HeaderRowOptions headerOption) throws IOException
	{
		File file = new File(fileName);
		return readDataset(file, dataset, headerOption);
	}
	
	public Dataset readDataset(File fileName, Dataset dataset, HeaderRowOptions headerOption) throws IOException
	{		
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String line = bufferedReader.readLine();
		
		if(line != null)
		{
			if(headerOption == HeaderRowOptions.NONE)
			{
				dataset.addDataRow(line);
			}
			else if(headerOption == HeaderRowOptions.ADD)
			{
				dataset.setHeaderRow(line);
			}
			
			while((line=bufferedReader.readLine()) != null)
			{
				dataset.addDataRow(line);
			}
		}
		
		fileReader.close();
		bufferedReader.close();
		
		return dataset;
	}
	
	
	public void writeBigDataSet(String fileName, Dataset dataset) throws IOException
	{
		File file = new File(fileName);
		writeDataset(file, dataset);
	}
	
	public void writeDataset(File fileName, Dataset dataset) throws IOException
	{
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		String theString = dataset.toString();
		bufferedWriter.write(theString, 0, theString.length());

		bufferedWriter.flush();
		fileWriter.close();
		bufferedWriter.close();
		
	}
	

}