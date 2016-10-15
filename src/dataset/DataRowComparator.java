package dataset;

import java.io.Serializable;
import java.util.Comparator;


/**
 * This class is used to compare DataRow objects based off of the values of the CompareProperties object
 * 
 * @author Doug
 *
 */
public class DataRowComparator implements Comparator<DataRow>, Serializable
{
	/**
	 * Version id for writing this object to file
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * CompareProperties object holding values related to how to comare two DataRows
	 */
	private CompareProperties compareProperties;
	
	/**
	 * Default constructor, initializes a default CompareProperties object
	 */
	public DataRowComparator()
	{
		compareProperties = new CompareProperties();
	}
	
	/**
	 * Constructor which sets the compare properties to the given parameter
	 * 
	 * @param compareProperties CompareProperties for the comparisons
	 */
	public DataRowComparator(CompareProperties compareProperties)
	{
		this.compareProperties = compareProperties;
	}
	
	/**
	 * Sets the compare properties
	 * 
	 * @param compareProperties The compare properties
	 */
	protected void setCompareProperties(CompareProperties compareProperties)
	{
		this.compareProperties = compareProperties;
	}
	
	/**
	 * Returns the compare properties
	 * @return Returns the compare properties
	 */
	protected CompareProperties getCompareProperties()
	{
		return compareProperties;
	}

	//bad things that could happen and aren't explictly handles
		//null Integers
		//failed number conversion
	/**
	 * Compares two DataRow objects based on the CompareProperties object values
	 * @return -1 if dr1 &lt; dr2, 0 if dr1=dr2, 1 if dr1 &gt; dr2
	 */
	@Override
    public int compare(DataRow dr1, DataRow dr2)
    {
		int returnValue = 0;
		
		if(dr1 == null || dr2 == null)
		{
			throw new NullPointerException("Cannot compare null DataRows");
		}
		else
		{
						
			int comparableIndex = compareProperties.getComparableIndex();
			DataTypes comparableType = compareProperties.getComparableType();
			boolean ascending = compareProperties.isAscending();
			
			
			if(compareProperties.isSameParent())
			{
				if(dr1.getParentDataset() == null || dr2.getParentDataset() == null)
				{
					throw new NullPointerException("Can only compare DataRows that have a parent DataSet");
				}
				else if(dr1.getParentDataset() != dr2.getParentDataset())
				{
					throw new IllegalArgumentException("Can only compare DataRows that belong to the same DataSet");
				}
			}
				
				
			if(comparableType.equals(DataTypes.STRING))
			{
				returnValue = dr1.getColumn(comparableIndex).compareTo(dr2.getColumn(comparableIndex));
			}
			else if(comparableType.equals(DataTypes.BOOLEAN))
			{
				Boolean thisValue = Boolean.valueOf(dr1.getColumn(comparableIndex));
				Boolean otherValue = Boolean.valueOf(dr2.getColumn(comparableIndex));
					
				returnValue = thisValue.compareTo(otherValue);
			}
			else if(comparableType.equals(DataTypes.BYTE))
			{
				Byte thisValue = Byte.valueOf(dr1.getColumn(comparableIndex));
				Byte otherValue = Byte.valueOf(dr2.getColumn(comparableIndex));
					
				returnValue = thisValue.compareTo(otherValue);
			}
			else if(comparableType.equals(DataTypes.SHORT))
			{
				Short thisValue = Short.valueOf(dr1.getColumn(comparableIndex));
				Short otherValue = Short.valueOf(dr2.getColumn(comparableIndex));
					
				returnValue = thisValue.compareTo(otherValue);
			}
			else if(comparableType.equals(DataTypes.INTEGER))
			{
				Integer thisValue = Integer.valueOf(dr1.getColumn(comparableIndex));
				Integer otherValue = Integer.valueOf(dr2.getColumn(comparableIndex));
					
				returnValue = thisValue.compareTo(otherValue);
			}
			else if(comparableType.equals(DataTypes.LONG))
			{
				Long thisValue = Long.valueOf(dr1.getColumn(comparableIndex));
				Long otherValue = Long.valueOf(dr2.getColumn(comparableIndex));
					
				returnValue = thisValue.compareTo(otherValue);
			}
			else if(comparableType.equals(DataTypes.FLOAT))
			{
				Float thisValue = Float.valueOf(dr1.getColumn(comparableIndex));
				Float otherValue = Float.valueOf(dr2.getColumn(comparableIndex));
					
				returnValue = thisValue.compareTo(otherValue);
			}
			else if(comparableType.equals(DataTypes.DOUBLE))
			{
				Double thisValue = Double.valueOf(dr1.getColumn(comparableIndex));
				Double otherValue = Double.valueOf(dr2.getColumn(comparableIndex));
					
				returnValue = thisValue.compareTo(otherValue);
			}
			else
			{
				throw new UnsupportedOperationException("Uknown Comparable type");
			}
				
			if(!ascending)
			{
				returnValue = returnValue * -1;
			}	
		}

		return returnValue;
    }

}
