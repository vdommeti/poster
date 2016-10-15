package dataset;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * The DataSet class is used to hold a DataSet containing rows and column like an excel sheet.
 * The DataSet class contains a list of rows (which holds a list of columns).  The header row is treated separately and is not part of the row list.
 * All data are stores as Strings, however a list of columnDataTypes is used to store how the Strings should be interpreted (as int, double, etc...).
 * 
 * 
 * @author Doug
 *
 */
public class Dataset implements Serializable
{
	/**
	 * Version id for writing this object to file
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * The header row of the DataSet
	 */
	private DataRow headerRow;
	
	/**
	 * The list of Data rows for the DataSet
	 */
	private List<DataRow> dataRows;
	
	/**
	 * The list of column data types
	 */
	private List<DataTypes> columnDataTypes;
	
	/**
	 * The title of the DataSet
	 */
	private String title;

	/**
	 * Default constructor.
	 * Initializes the header row using the default constructor and sets the parent to "this"
	 * Initializes an empty list of data rows and column types
	 */
	public Dataset()
	{
		headerRow = new DataRow();
		headerRow.setParentDataSet(this);
		dataRows = new ArrayList<DataRow>();
		columnDataTypes = new ArrayList<DataTypes>();
	}

	/**
	 * Copy constructor
	 * Copies the title, header row, data rows, and row types to this DataSet
	 * 
	 * @param otherDataset Another DataSet
	 */
	public Dataset(Dataset otherDataset)
	{
		columnDataTypes = new ArrayList<DataTypes>();
		dataRows = new ArrayList<DataRow>();
		title = otherDataset.getTitle();
		
		headerRow = new DataRow(otherDataset.getHeaderRow());
		headerRow.setParentDataSet(this);

		for(int i=0; i<otherDataset.getNumberOfRows(); i++)
		{
			DataRow newDataRow = new DataRow(otherDataset.getDataRow(i));
			addDataRow(newDataRow);
		}
		
		for(int i=0; i<otherDataset.getNumberOfColumns(); i++)
		{
			columnDataTypes.add(otherDataset.getColumnDataType(i));
		}
		
	}

	/**
	 * Sets the title of the dataset
	 * 
	 * @param title The new title
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}
	
	/**
	 * Returns the title of the dataset
	 * 
	 * @return The title
	 */
	public String getTitle()
	{
		return title;
	}
	
	/**
	 * Sets the column data types to the new values specified
	 * 
	 * @param newColumnDataTypes A list of column data types
	 */
	public void setColumnDataTypes(List<DataTypes> newColumnDataTypes)
	{
		columnDataTypes.clear();
		
		for(DataTypes columnDataType : newColumnDataTypes)
		{
			columnDataTypes.add(columnDataType);
		}
	}
	
	/**
	 * Sets the column data types to the new values specified
	 * 
	 * @param newColumnDataTypes An array of new column data types
	 */
	public void setColumnDataTypes(DataTypes[] newColumnDataTypes)
	{
		columnDataTypes.clear();
		
		for(DataTypes columnDataType : newColumnDataTypes)
		{
			columnDataTypes.add(columnDataType);
		}
	}
	
	/**
	 * Sets the column data type at the specified index to the new data type
	 * 
	 * @param index The column index
	 * @param dataType The new data type
	 */
	public void setColumnDataType(int index, DataTypes dataType)
	{
		columnDataTypes.set(index, dataType);
	}
	
	/**
	 * Returns the data type of the column specified by the given index
	 * 
	 * @param index The column index
	 * @return The data type of the column at the given index
	 */
	public DataTypes getColumnDataType(int index)
	{
		return columnDataTypes.get(index);
	}

	/**
	 * Sets the header row to the specified DataRow
	 * 
	 * @param headerRow A new header row
	 */
	public void setHeaderRow(DataRow headerRow)
	{
		if(headerRow.getParentDataset() != null)
		{
			throw new IllegalArgumentException("Cannot set a header row which already has a parent DataSet");
		}
		
		this.headerRow = new DataRow(headerRow);
		this.headerRow.setParentDataSet(this);
	}

	/**
	 * Sets the header row to the values in the String array
	 * 
	 * @param headerRow A String array for the new header row values
	 */
	public void setHeaderRow(String[] headerRow)
	{
		this.headerRow = new DataRow(headerRow);
		this.headerRow.setParentDataSet(this);
	}

	/**
	 * Sets the header row to the values specified by a comma separated String
	 * 
	 * @param headerRow A comma separated String of column values
	 */
	public void setHeaderRow(String headerRow)
	{
		this.headerRow = new DataRow(headerRow.split(","));
		this.headerRow.setParentDataSet(this);
	}
	
	/**
	 * Sets the value of the header row at the specified index to the new value
	 * 
	 * @param columnIndex The column index
	 * @param newValue The new header row value
	 */
	public void setHeaderRowValue(int columnIndex, String newValue)
	{
		headerRow.setRowValue(columnIndex, newValue);
	}

	/**
	 * Returns the header row
	 * 
	 * @return The reader row
	 */
	public DataRow getHeaderRow()
	{
		return headerRow;
	}

	/**
	 * Returns a String array for the header row
	 * 
	 * @return A String array for the header row
	 */
	public String[] getHeaderStringArrayRow()
	{
		return headerRow.getRowStringArrayValues();
	}

	/**
	 * Returns the header row value specified at the given index
	 * 
	 * @param index The column index
	 * @return The header row value
	 */
	public String getHeaderRowValue(int index)
	{
		return headerRow.getColumn(index);
	}
    
    /**
     * Returns the index of the column speficied by the iven column name
     * 
     * @param name The name of the column
     * @return The index of the column
     * @throws RuntimeException Throws an excpetion if no column can be found
     */
    public int getHeaderColumnIndexByString(String name) throws RuntimeException
    {
    	int index = -1;
    	
    	for(int i = 0; i < headerRow.getColumns() && index == -1; i++)
    	{
    		if(headerRow.getColumn(i).equals(name))
    		{
    			index = i;
    		}
    	}

    	if(index == -1)
    	{
    		throw new RuntimeException("No header column found with value = " + "\"" + name + "\"");
    	}
    	
    	return index;
    }

	/**
	 * Adds the given DataRow to the DataSet
	 * 
	 * @param dataRow The DataRow to add
	 */
	public void addDataRow(DataRow dataRow)
	{
		if(dataRow.getParentDataset() != null)
		{
			throw new IllegalArgumentException("Cannot add a DataRow to a DataSet that already belongs to another DataSet");
		}
		else
		{
			dataRow.setParentDataSet(this);
			dataRows.add(dataRow);
		}
	}
	
	/**
	 * Adds a list of DataRows to the DataSet
	 * 
	 * @param dataRows The list of DataRows to add
	 */
	public void addMultipleDataRows(List<DataRow> dataRows)
	{
		for (DataRow dataRow: dataRows)
		{
			if(dataRow.getParentDataset() == null)
			{
				addDataRow(dataRow);
			}
			else
			{
				addDataRow(new DataRow(dataRow));
			}
		}
	}

	/**
	 * Adds a DataRow to the DataSet given as an array of Strings
	 * 
	 * @param dataRowArray An array of Strings representing a DataRow
	 */
	public void addDataRow(String[] dataRowArray)
	{
		DataRow dataRow = new DataRow(dataRowArray);
		dataRow.setParentDataSet(this);
		dataRows.add(dataRow);
	}

	/**
	 * Adds a DataRow to the DataSet given as a comma separated String of values
	 * 
	 * @param dataRowString An comma separated String representing a DataRow
	 */
	public void addDataRow(String dataRowString)
	{
		DataRow dataRow = new DataRow(dataRowString);
		dataRow.setParentDataSet(this);
		dataRows.add(dataRow);
	}
		
	/**
	 * Returns the list of data rows
	 * @return Returns the list of data rows
	 */
	public List<DataRow> getDataRows()
	{
		return dataRows;
	}

	/**
	 * Returns the DataRow at the specified index
	 * 
	 * @param index The row index
	 * @return The DataRow at the given index
	 */
	public DataRow getDataRow(int index)
	{
		return dataRows.get(index);
	}

	/**
	 * Returns a String representation of the DataRow at the speficied index
	 * 
	 * @param index The row index
	 * @return A String of the DataRow at the given index
	 */
	public String getDataStringRow(int index)
	{
		return dataRows.get(index).toString();
	}

	/**
	 * Returns a String array of the DataRow at the specified index
	 * 
	 * @param index The row index
	 * @return A String array of the DataRow at the given index
	 */
	public String[] getDataStringArrayRow(int index)
	{
		return dataRows.get(index).getRowStringArrayValues();
	}

	/**
	 * Returns the value at the given row and column index
	 * 
	 * @param rowIndex The row index
	 * @param columnIndex The column index
	 * @return The value at the given row and column indices
	 */
	public String getDataRowValue(int rowIndex, int columnIndex)
	{
		return dataRows.get(rowIndex).getColumn(columnIndex);
	}
	
	/**
	 * Removes the DataRow at the specified index
	 * 
	 * @param index The index of the DataRow to remove
	 */
	public void removeDataRow(int index)
	{
		dataRows.remove(index);
	}
	
	/**
	 * Adds a column after the given index with a default type of STRING
	 * Calls addColumn with the index and the data type
	 * 
	 * @param columnIndex The column index
	 */
	public void addColumn(int columnIndex)
	{
		addColumn(columnIndex, DataTypes.STRING);
	}

	/**
	 * Adds a column after the given index with given data type
	 * Adds a column to the header row and all the data rows accordingly
	 * 
	 * @param columnIndex The column index
	 * @param columnDataType The data type
	 */
	public void addColumn(int columnIndex, DataTypes columnDataType)
	{
		headerRow.addColumn(columnIndex);
		columnDataTypes.add(columnIndex, columnDataType);
		
		for(DataRow row : dataRows)
		{
			row.addColumn(columnIndex);
		}
	}
	
	/**
	 * Removes the column at the specified index from the header row and all data rows
	 * 
	 * @param columnIndex The column index to remove
	 */
	public void removeColumn(int columnIndex)
	{
		headerRow.removeColumn(columnIndex);
		columnDataTypes.remove(columnIndex);
		
		for(DataRow row : dataRows)
		{
			row.removeColumn(columnIndex);
		}
	}

	/**
	 * Returns the list of row values for a given column including the header row
	 * 
	 * @param columnIndex The column index
	 * @return A list of Strings row each row value
	 */
	public List<String> getColumn(int columnIndex)
	{
		List<String> column = new ArrayList<String>();
		column.add(headerRow.getColumn(columnIndex));
		
		for(DataRow row : dataRows)
		{
			column.add(row.getColumn(columnIndex));
		}

		return column;
	}
	
	/**
	 * Returns the list of row values for a given column not including the header row
	 * 
	 * @param columnIndex The column index
	 * @return A list of Strings row each row value
	 */
	public List<String> getColumnWithoutHeader(int columnIndex)
	{
		List<String> column = new ArrayList<String>();
		
		for(DataRow row : dataRows)
		{
			column.add(row.getColumn(columnIndex));
		}

		return column;
	}
	
	/**
	 * Sets the value at a given row and column index to the new specified value
	 * 
	 * @param rowIndex The row index
	 * @param columnIndex The column index
	 * @param newValue The new value
	 */
	public void setCellValue(int rowIndex, int columnIndex, String newValue)
	{
		dataRows.get(rowIndex).setRowValue(columnIndex, newValue);
	}

	/**
	 * Returns the number of rows
	 * 
	 * @return The number fo rows
	 */
	public int getNumberOfRows()
	{
		return dataRows.size();
	}
	
	/**
	 * Returns the number of columns in the header row
	 * 
	 * @return The number of columns in the header row
	 */
	public int getNumberOfColumns()
	{
		return headerRow.getColumns();
	}
	
	/**
	 * Returns the number of columns in the specified row
	 * 
	 * @param rowIndex The row index
	 * @return The number of columns for the given row
	 */
	public int getNumberOfColumns(int rowIndex)
	{
		return dataRows.get(rowIndex).getColumns();
	}
	
	/**
	 * Trims all white space for each row and column
	 */
	public void trimAllSpace()
	{
		headerRow.trimAllSpace();
		
		for(DataRow row : dataRows)
		{
			row.trimAllSpace();
		}
	}
	
	/**
	 * Returns a list of unique values in the given column
	 * 
	 * @param columnIndex The column index
	 * @return A list of Strings containing the unique values
	 */
	public List<String> getUniqueValues(int columnIndex)
	{
		List<String> allValue = getColumnWithoutHeader(columnIndex);
		Set<String> uniqueValue = new HashSet<String>(allValue);
		return new ArrayList<String>(uniqueValue);
	}

	/**
	 * Finds all rows that contains a value at the specified column
	 * 
	 * @param columnIndex The column index
	 * @param value The search valie
	 * @return A list of DataRows
	 */
	public List<DataRow> getRowsWithValue(int columnIndex, String value)
	{
		List<DataRow> result = new ArrayList<DataRow>();
		
		for(DataRow row: dataRows)
		{
			if(row.getColumn(columnIndex).equals(value))
			{
				result.add(row);
			}
		}
		
		return result;
	}
	
	/**
	 * Returns an empty row with no parent and with the same number of columns as the header row
	 * 
	 * @return Empty DataRow
	 */
	public DataRow getBlankRow()
	{
		int num = getNumberOfColumns();
		List<String> rowValues = new ArrayList<String>();
		
		for(int i=0;i<num;i++)
		{
			rowValues.add("");
		}
		
		DataRow row = new DataRow(rowValues);
		return row;
	}
	
	/**
	 * Adds a blank/empty DataRow to the end of this DataSet
	 */
	public void addBlankRow()
	{
		addDataRow(getBlankRow());
	}

	/**
	 * Sorts the DataSet by the given column index
	 * Calls sort(int columnIndex, DataTypes comparableType) with a default data type of STRING
	 * 
	 * @param columnIndex The column to sort by
	 */
	public void sort(int columnIndex)
	{
		sort(columnIndex, DataTypes.STRING);
	}
	
	/**
	 * Sorts the DataSet by the given column index and data type
	 * Calls sort(int columnIndex, DataTypes comparableType, boolean sameParent) with a default same parent value of true
	 * 
	 * @param columnIndex The column index to sort by
	 * @param comparableType The data type to sort by
	 */
	public void sort(int columnIndex, DataTypes comparableType)
	{
		sort(columnIndex, comparableType, true);
	}
	
	/**
	 * Sorts the DataSet by the given column index and data type and same parent value
	 * Calls sort with a default ordering of true = ascending order
	 * 
	 * @param columnIndex The column index to sort by
	 * @param comparableType The data type to sort by
	 * @param sameParent Whether or not to check if the data rows share the same parent
	 */
	public void sort(int columnIndex, DataTypes comparableType, boolean sameParent)
	{
		sort(columnIndex, comparableType, sameParent, true);
	}
	
	/**
	 * Sorts the dataset by creating a ComparePropoperties object and setting all the fields.
	 * Then creates a DataRowComparator object with the CompareProperties object
	 * Calls Collections.sort with the data rows and the data row comparator
	 * 
	 * @param columnIndex The column index to sort by
	 * @param comparableType The data type to sort by
	 * @param sameParent Whether or not to check if the data rows share the same parent
	 * @param ascending Whether to sort in ascending or descending order
	 */
	public void sort(int columnIndex, DataTypes comparableType, boolean sameParent, boolean ascending)
	{
		CompareProperties compareProperties = new CompareProperties();
		DataRowComparator dataRowComparator = new DataRowComparator(compareProperties);
		
		compareProperties.setComparableIndex(columnIndex);
		compareProperties.setComparableType(comparableType);
		compareProperties.setSameParent(sameParent);
		compareProperties.setAscending(ascending);

		Collections.sort(dataRows, dataRowComparator);
	}
	
	/**
	 * Functions similiarly to sort(int columnIndex, DataTypes comparableType, boolean sameParent, boolean ascending)
	 * Calls Collections.sort with a sub list of data rows specified by the start and end indices
	 * 
	 * @param startIndex Starting index to sort
	 * @param endIndex Ending index to sort
	 * @param columnIndex The column index to sort by
	 * @param comparableType The data type to sort by
	 * @param sameParent Whether or not to check if the data rows share the same parent
	 * @param ascending Whether to sort in ascending or descending order
	 */
	public void sort(int startIndex, int endIndex, int columnIndex, DataTypes comparableType, boolean sameParent, boolean ascending)
	{
		CompareProperties compareProperties = new CompareProperties();
		DataRowComparator dataRowComparator = new DataRowComparator(compareProperties);
		
		compareProperties.setComparableIndex(columnIndex);
		compareProperties.setComparableType(comparableType);
		compareProperties.setAscending(ascending);
		compareProperties.setSameParent(sameParent);

		List<DataRow> test = dataRows.subList(startIndex, endIndex);
		Collections.sort(test, dataRowComparator);
	}
	
	public String toHTMLString(boolean hasBorders)
	{
		StringBuilder sb = new StringBuilder();
		
		if(hasBorders)
		{
			sb.append("<table border = \"1\">");
		}
		else
		{
			sb.append("<table>");
		}
		
		sb.append(System.getProperty("line.separator"));
		sb.append(headerRow.toHTMLString());
		
		for(DataRow dataRow : dataRows)
		{
			sb.append(System.getProperty("line.separator"));
			sb.append(dataRow.toHTMLString());
		}
		
		sb.append(System.getProperty("line.separator"));
		sb.append("</table>");
		
		return sb.toString();
	}

	/**
	 * Calls toString for the header row and each data row
	 * A new line is used to separate each row
	 */
	public String toString()
	{
		StringBuilder sb = new StringBuilder();

		sb.append(headerRow.toString());

		sb.append(System.getProperty("line.separator"));

		//might be faster not to call to string for the individual rows
		for(int i=0; i<dataRows.size(); i++)
		{
			sb.append(dataRows.get(i).toString());
			sb.append(System.getProperty("line.separator"));
		}

		sb.deleteCharAt(sb.length()-1);

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
		result = prime * result + ((columnDataTypes == null) ? 0 : columnDataTypes.hashCode());
		result = prime * result + ((dataRows == null) ? 0 : dataRows.hashCode());
		result = prime * result + ((headerRow == null) ? 0 : headerRow.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/**
	 * Determines if the object is equal to this DataSet
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
		
		Dataset other = (Dataset) obj;
		
		if (columnDataTypes == null)
		{
			if (other.columnDataTypes != null)
			{
				return false;
			}
		}
		else if (!columnDataTypes.equals(other.columnDataTypes))
		{
			return false;
		}
		if (dataRows == null)
		{
			if (other.dataRows != null)
			{
				return false;
			}
		}
		else if (!dataRows.equals(other.dataRows))
		{
			return false;
		}
		
		if (headerRow == null)
		{
			if (other.headerRow != null)
			{
				return false;
			}
		}
		else if (!headerRow.equals(other.headerRow))
		{
			return false;
		}
		
		if(title == null)
		{
			if (other.title != null)
			{
				return false;
			}
		}
		else if (!title.equals(other.title))
		{
			return false;
		}
		
		return true;
	}
	
	
}