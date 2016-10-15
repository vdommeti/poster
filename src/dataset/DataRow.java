package dataset;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * The DataRow class is used to hold a row of data similar to a row in an excel sheet.
 * This class uses an ArrayList of Strings to represent the columns in a row.
 * If the column exists but there is no value, an empty String is used.
 * 
 * @author Doug
 *
 */
public class DataRow implements Serializable
{
	/**
	 * Version id for writing this object to file
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The row of data.  The list of values in the row.
	 */
	private List<String> rowValues;
	
	/**
	 * The parent Dataset to which this DataRow belongs to
	 */
	private Dataset parentDataset;

	/**
	 * Default constructor.
	 * Initializes an empty list for rowValues
	 */
	public DataRow()
	{
		rowValues = new ArrayList<String>();
	}

	/**
	 * Copy constructor.
	 * Copies the values from the other data row to this one.
	 * 
	 * @param otherDataRow The data row to be copied
	 */
	public DataRow(DataRow otherDataRow)
	{
		rowValues = new ArrayList<String>(otherDataRow.getRowValues());
	}
	
	/**
	 * Constructor than takes a String array as column values
	 * 
	 * @param rowValues The values for each column in the row
	 */
	public DataRow(String[] rowValues)
	{
		this.rowValues = new ArrayList<String>(Arrays.asList(rowValues));
	}
	
	/**
	 * Constructor than takes a String list as column values
	 * 
	 * @param rowValues The values for each column in the row
	 */
	public DataRow(List<String> rowValues)
	{
		this.rowValues = rowValues;
	}

	/**
	 * Constructor than takes a String as column values/
	 * The String is assumed to be a comma separated list of values.
	 * 
	 * @param rowValues The values for each column in the row
	 */
	public DataRow(String rowValues)
	{
		this.rowValues = new ArrayList<String>(Arrays.asList(rowValues.split(",")));
	}

	/**
	 * Sets the parent DataSet for this DataRow
	 * 
	 * @param parentDataset The parent DataSet 
	 */
	protected void setParentDataSet(Dataset parentDataset)
	{
		this.parentDataset = parentDataset;
	}

	/**
	 * @return The parent DataSet
	 */
	protected Dataset getParentDataset()
	{
		return parentDataset;
	}
	
	/**
	 * Gets the data type for the column at the given index.
	 * 
	 * @param columnIndex The index of the column
	 * @return The DataType of the column at the given index
	 */
	public DataTypes getColumnDataType(int columnIndex)
	{
		return parentDataset.getColumnDataType(columnIndex);
	}
	
	/**
	 * @return Returns rowValues
	 */
	public List<String> getRowValues()
	{
		return rowValues;
	}

	/**
	 * Sets rowValues to the values of the aray of Strings.
	 * 
	 * @param newRowValues Array of Strings representing the row values
	 */
	public void setRowValues(String[] newRowValues)
	{
		rowValues = new ArrayList<String>(Arrays.asList(newRowValues));
	}

	/**
	 * Sets the value at the specified column index to the new value
	 * 
	 * @param index The index of the column for this row
	 * @param newValue The new value to set this row/column to
	 */
	public void setRowValue(int index, String newValue)
	{
		rowValues.set(index, newValue);
	}

	/**
	 * Adds a column to this row at the specified index.
	 * The value of the column is an empty String.
	 * 
	 * @param index The column index
	 */
	public void addColumn(int index)
	{
		rowValues.add(index, "");
	}
	
	/**
	 * Adds a column to the end of the row with the new value specified
	 * 
	 * @param value The value of the column
	 */
	public void addColumn(String value)
	{
		rowValues.add(value);
	}
	
	/**
	 * Adds a column to this row at the specified index with the specified value
	 * 
	 * @param index The column index
	 * @param value The value of the column
	 */
	public void addColumn(int index, String value)
	{
		rowValues.add(index, value);
	}

	/**
	 * Removes the column at the specified index
	 * 
	 * @param index The column index
	 * @return Returns the value of the column
	 */
	public String removeColumn(int index)
	{
		return rowValues.remove(index);
	}

	/**
	 * Returns the value at the specified index
	 * 
	 * @param index The column index
	 * @return The column value
	 */
	public String getColumn(int index)
	{
		return rowValues.get(index);
	}
	
	/**
	 * Returns the columns values as a List of Strings.
	 * The indices of the columns to include are specified by the index array.
	 * 
	 * @param index The array of column indices to include
	 * @return A list of values for the specified columns
	 */
	public List<String> getColumnList(int[] index)
	{
		List<String> result = new ArrayList<String>();
		
		for(int i=0; i<index.length; i++)
		{
			result.add(rowValues.get(index[i]));
		}
		
		return result;
	}

	/**
	 * Returns the row values as a String array
	 * 
	 * @return The array of values
	 */
	public String[] getRowStringArrayValues()
	{
		return rowValues.toArray(new String[rowValues.size()]);
	}

	/**
	 * Returns the number of columns
	 * 
	 * @return The number of columns
	 */
	public int getColumns()
	{
		return rowValues.size();
	}
	
	/**
	 * Removes white space for all values in the row
	 */
	public void trimAllSpace()
	{
		for(int i=0; i<rowValues.size(); i++)
		{
			rowValues.set(i, rowValues.get(i).trim());
		}
	}
	
	public String toHTMLString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append("<tr>");
		
		
		for(String column : rowValues)
		{
			sb.append(System.getProperty("line.separator"));
			sb.append("<td>");
			sb.append(column);
			sb.append(System.getProperty("line.separator"));
			sb.append("</td>");
		}
		
		sb.append(System.getProperty("line.separator"));
		sb.append("</tr>");
		
		return sb.toString();
	}

	/**
	 * Returns a comma separates list of values for the row data
	 */
	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		for(String value : rowValues)
		{
			sb.append(value);
			sb.append(",");
		}
		
		if(sb.length() > 0)
		{
			sb.deleteCharAt(sb.length()-1);
		}

		return sb.toString();
	}

	/**
	 * 
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rowValues == null) ? 0 : rowValues.hashCode());
		return result;
	}

	/**
	 * Determines if two DataRows are equal.  Only compares the rowValues field.
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		
		if (obj == null)
		{
			return false;
		}
		
		if (getClass() != obj.getClass())
		{
			return false;
		}
		
		DataRow other = (DataRow) obj;
		
		if (rowValues == null)
		{
			if (other.rowValues != null)
			{
				return false;
			}
		}
		else if (!rowValues.equals(other.rowValues))
		{
			return false;
		}
		
		return true;
	}
	
}