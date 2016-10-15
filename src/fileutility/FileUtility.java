package fileutility;


import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class FileUtility
{
	public FileUtility()
	{
		
	}

	public List<String> readStrings(String fileName) throws IOException
	{
		File file = new File(fileName);
		return readStrings(file);
	}
	
	public List<String> readStrings(File fileName) throws IOException
	{
		List<String> stringList = new ArrayList<String>();
		
		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		String line;
		
		while((line=bufferedReader.readLine()) != null)
		{
			stringList.add(line);
		}
		
		fileReader.close();
		bufferedReader.close();
		
		return stringList;
	}
	
	public String readString(BufferedReader bufferedReader, boolean close) throws IOException
	{
		String theString = bufferedReader.readLine();
		
		if(close)
		{
			bufferedReader.close();
		}
		
		return theString;
	}

	public void writeStrings(String fileName, List<String> stringList) throws IOException
	{
		File file = new File(fileName);
		writeStrings(file, stringList);
	}
	
	public void writeStrings(File fileName, List<String> stringList) throws IOException
	{
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		
		for(int i=0; i<stringList.size(); i++)
		{
			String theString = stringList.get(i).concat("\n");
			bufferedWriter.write(theString, 0, theString.length());
		}
		

		bufferedWriter.flush();
		fileWriter.close();
		bufferedWriter.close();
	}
	
	public void writeString(String fileName, String theString) throws IOException
	{
		File file = new File(fileName);
		writeString(file, theString);
		
	}
	
	public void writeString(File fileName, String theString) throws IOException
	{
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		bufferedWriter.write(theString, 0, theString.length());
		bufferedWriter.close();
		
	}

	public void writeString(String fileName, String theString, int offset, int length) throws IOException
	{
		File file = new File(fileName);
		writeString(file, theString, offset, length);
	}
	
	public void writeString(File fileName, String theString, int offset, int length) throws IOException
	{
		FileWriter fileWriter = new FileWriter(fileName);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		bufferedWriter.write(theString, offset, length);
		
		bufferedWriter.flush();
		fileWriter.close();
		bufferedWriter.close();
	}

	public void writeString(File fileName, String theString, boolean append) throws IOException
	{
		FileWriter fileWriter = new FileWriter(fileName, append);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		
		bufferedWriter.write(theString, 0, theString.length());
		
		bufferedWriter.flush();
		fileWriter.close();
		bufferedWriter.close();		
	}
	
	public String writeString(BufferedWriter bufferedWriter, String theString, boolean close) throws IOException
	{
		bufferedWriter.write(theString, 0, theString.length());
		
		if(close)
		{
			bufferedWriter.close();
		}
		
		return theString;
	}
	
	public String writeString(BufferedWriter bufferedWriter, String theString, int offset, int length, boolean close) throws IOException
	{
		bufferedWriter.write(theString, offset, length);
		
		if(close)
		{
			bufferedWriter.close();
		}
		
		return theString;
	}
	
	//////////////////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unused")
	public ArrayList<String[]> readCSVFile(String fileName) throws IOException
	{
		Character delimiter = ',';
		Character quote = '\"';
		Character newLine = '\n';
		Character newLine2 = '\r';

		ArrayList<String[]> stringList = new ArrayList<String[]>();
		ArrayList<String> row = new ArrayList<String>();

		FileReader fileReader = new FileReader(fileName);
		BufferedReader bufferedReader = new BufferedReader(fileReader);

		String line;
		StringBuilder sb = new StringBuilder();
		boolean inQuote = false;
		int rowIndex = 0;

		while((line=bufferedReader.readLine()) != null)
		{
			for(int i=0; i<line.length(); i++)
			{
				Character currentChar = new Character(line.charAt(i));

				if(inQuote)
				{
					if(currentChar.equals(quote))
					{
						/*if((i-1 >= 0 && (new Character(line.charAt(i-1))).equals(quote)) || (i+1 < line.length() && (new Character(line.charAt(i+1))).equals(quote)))
						{
							//quote inside quote
						}*/
						if(i-1 >= 0 && (new Character(line.charAt(i-1))).equals(quote))
						{
							//end of quoted text
							inQuote = false;
						}
						else if(i+1 < line.length() && (new Character(line.charAt(i+1))).equals(quote))
						{
							//quote inside quote
							inQuote = false;
						}
						else
						{
							//end of quoted text
							inQuote = false;
						}

						sb.append(currentChar);
					}
					else if(currentChar.equals(delimiter))
					{
						sb.append(currentChar);						
					}
					else
					{
						sb.append(currentChar);
					}
				}
				else
				{
					if(currentChar.equals(quote))
					{
						sb.append("\"");
						inQuote = true;
					}
					else if(currentChar.equals(delimiter))
					{
						row.add(sb.toString());
						sb = new StringBuilder();
					}
					else
					{
						sb.append(currentChar);
					}
				}
			}

			if(!inQuote)
			{
				row.add(sb.toString());
				sb = new StringBuilder();

				String[] rowArray = new String[1];
				rowArray = row.toArray(rowArray);

				stringList.add(rowArray);
				row.clear();
			}
			else
			{
				sb.append("\n");
			}
		}
		
		bufferedReader.close();

		
		return stringList;
	}
	

	public void writeCSVFile(String fileName, ArrayList<String[]> rows) throws IOException
	{
		ArrayList<String> rowList = new ArrayList<String>();

		for(int i=0; i<rows.size(); i++)
		{
			String[] columns = rows.get(i);

			StringBuilder rowString = new StringBuilder();

			for(int j=0; j<columns.length; j++)
			{
				if(!columns[j].equals(""))
				{
					rowString.append(columns[j]);
				}

				if((j+1) != columns.length)
				{
					rowString.append(",");
				}
			}

			rowList.add(rowString.toString());
		}

		writeStrings(fileName, rowList);
	}
	
	/////////////////////////////////////////////////////////////////////
	
	//only works for files of int size
	public byte[] readBytes(String fileName) throws IOException
	{
		File file = new File(fileName);
		return readBytes(file);
	}
	
	//only works for files of int size
	public byte[] readBytes(File fileName) throws IOException
	{
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);

		int fileSize = (int)fileName.length();

		byte[]  bytes = new byte[fileSize];
		bis.read(bytes);

		fis.close();	
		bis.close();

		return bytes;
	}
	
	//only works for files of int size
	public byte[] readBytes(String fileName, int offset, int length) throws IOException
	{
		File file = new File(fileName);
		return readBytes(file);
	}

	//only works for files of int size
	public byte[] readBytes(File fileName, int offset, int length) throws IOException
	{
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);

		int fileSize = (int)fileName.length();

		byte[]  bytes = new byte[fileSize];
		bis.read(bytes, offset, length);

		fis.close();		
		bis.close();

		return bytes;
	}

	public void writeBytes(String fileName, byte[] bytes) throws IOException
	{
		File file = new File(fileName);
		writeBytes(file, bytes);
	}
	
	public void writeBytes(File fileName, byte[] bytes) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		bos.write(bytes);

		bos.flush();
		fos.close();
		bos.close();
	}
	
	public void writeBytes(String fileName, byte[] bytes, int offset, int length) throws IOException
	{
		File file = new File(fileName);
		writeBytes(file, bytes, offset, length);
	}
	
	public void writeBytes(File fileName, byte[] bytes, int offset, int length) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		bos.write(bytes, offset, length);

		bos.flush();
		fos.close();
		bos.close();
	}

	public void writeBytes(String fileName, Object object) throws IOException
	{
		FileOutputStream fos = new FileOutputStream(fileName);
		BufferedOutputStream bos = new BufferedOutputStream(fos);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
   		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(object);
		byte[] bytes = baos.toByteArray();

		bos.write(bytes);

		bos.flush();
		fos.close();
		bos.close();
	}
	

	/////////////////////////////////////////////////////////////////
	
	
	public List<Object> readObjects(String filePath) throws IOException, ClassNotFoundException
	{
		List<Object> objectList = new ArrayList<Object>();
		
		FileInputStream fis = new FileInputStream(filePath);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ObjectInputStream ois = new ObjectInputStream(bis);

		int numberOfObjects = 0;
		Object object;

		object = ois.readObject();

		if(object != null)
		{
			//assume first object is a number representing the number of objects

			numberOfObjects = (int)object;
		}


		for(int i=0; i<numberOfObjects; i++)
		{
			object = ois.readObject();
			objectList.add(object);
		}

		fis.close();
		bis.close();
		ois.close();
		
		return objectList;
	}
	

	public void writeObjects(String filePath, List<?> objectList) throws IOException, ClassNotFoundException
	{
		FileOutputStream fos = new FileOutputStream(filePath);
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		ObjectOutputStream oos = new ObjectOutputStream(bos);

		for(int i=0; i<objectList.size(); i++)
		{
			oos.writeObject(objectList.get(i));
		}
		
		oos.flush();
		fos.close();
		bos.close();
		oos.close();
	}
	

	public void convertCharset(String inputPath, String outputPath, String charset) throws IOException
	{
		FileReader fileReader = new FileReader(inputPath);
		BufferedReader bufferedReader = new BufferedReader(fileReader);
		
		FileOutputStream fileOutputStream = new FileOutputStream(outputPath);
		OutputStreamWriter outputStreamWriter = new  OutputStreamWriter(fileOutputStream, charset);

		String line;
		
		while((line=bufferedReader.readLine()) != null)
		{
			if(line.length() > 0)
			{
				outputStreamWriter.write(line, 0, line.length());
				outputStreamWriter.write("\n");
			}
		}
		
		
		bufferedReader.close();
		outputStreamWriter.close();
	}

}