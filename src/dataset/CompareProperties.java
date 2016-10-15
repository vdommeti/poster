package dataset;


import java.io.Serializable;

/**
 * This class is used to hold all the properties used when comparing DataRows
 * 
 * @author Doug
 *
 */
public class CompareProperties implements Serializable
{
	/**
	 * Version id for writing this object to file
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Column index to compare
	 */
	private int comparableIndex;
	
	/**
	 * Data type to compare
	 */
	private DataTypes comparableType;
	
	/**
	 * Whether or not the rows should be sorted in ascending order
	 */
	private boolean ascending;
	
	/**
	 * Whether or not the comparison should check if the data rows are from the same parent
	 */
	private boolean sameParent;

	/**
	 * Default constructor
	 * Sets comparableIndex = -1
	 * Sets comparableType = DataTypes.STRING;
	 * Sets ascending = true
	 * Sets sameParent = false
	 */
	public CompareProperties()
	{
		comparableIndex = -1;
		comparableType = DataTypes.STRING;
		ascending = true;
		sameParent = false;
	}
	
	/**
	 * Copy Constructor
	 * 
	 * @param compareProperties Other compare properties
	 */
	public CompareProperties(CompareProperties compareProperties)
	{
		this.comparableIndex = compareProperties.getComparableIndex();
		this.comparableType = compareProperties.getComparableType();
		this.ascending = compareProperties.isAscending();
	}

	/**
	 * Sets the comparable index
	 * 
	 * @param comparableIndex The column index to compare
	 */
	public void setComparableIndex(int comparableIndex)
	{
		this.comparableIndex = comparableIndex;
	}

	/**
	 * Returns the comparable index
	 * 
	 * @return comparableIndex The column index to compare values with
	 */
	public int getComparableIndex()
	{
		return comparableIndex;
	}
	
	/**
	 * Sets the ascending value
	 * 
	 * @param ascending Whether or not the data should be sorted in ascending order
	 */
	public void setAscending(boolean ascending)
	{
		this.ascending = ascending;
	}

	/**
	 * Returns the ascending value
	 * 
	 * @return Ascending
	 */
	public boolean isAscending()
	{
		return ascending;
	}

	/**
	 * Sets the comparable type
	 * 
	 * @param comparableType The type of comparison
	 */
	public void setComparableType(DataTypes comparableType)
	{
		this.comparableType = comparableType;
	}

	/**
	 * Returns the comparable type
	 * 
	 * @return comparableType The type of comparison
	 */
	public DataTypes getComparableType()
	{
		return comparableType;
	}

	/**
	 * Sets the sameParent value
	 * 
	 * @param sameParent Whether or not to only compare DataRows of the same parent
	 */
	public void setSameParent(boolean sameParent)
	{
		this.sameParent = sameParent;
	}
	
	/**
	 * Returns the sameParent value
	 * 
	 * @return sameParent
	 */
	public boolean isSameParent()
	{
		return sameParent;
	}


	
}