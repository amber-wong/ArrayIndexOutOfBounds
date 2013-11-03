/**************************************************************************
 * Code modified and borrowed from:										  *
 * 																		  *
 * (C) Copyright 1992-2005 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/

// Fig. 25.28: ResultSetTableModel.java
// A TableModel that supplies ResultSet data to a JTable.
import java.sql.*;

import javax.swing.*;
import javax.swing.table.*;

// ResultSet rows and columns are counted from 1 and JTable 
// rows and columns are counted from 0. When processing 
// ResultSet rows or columns for use in a JTable, it is 
// necessary to add 1 to the row or column number to manipulate
// the appropriate ResultSet column (i.e., JTable column 0 is 
// ResultSet column 1 and JTable row 0 is ResultSet row 1).
public class ResultSetTableModel extends AbstractTableModel {
	private Connection connection;
	private Statement statement;
	private ResultSet resultSet;
	private ResultSetMetaData metaData;
	private int numberOfRows;
	
	static final String DEFAULT_QUERY = "SELECT Room.rmID, Room.roomType, Room.occupied FROM Room, Cost WHERE Room.roomType = Cost.roomType ORDER BY price, rmID ASC;";
	
	// keep track of database connection status
	private boolean connectedToDatabase = false;
	
	// constructor initializes resultSet and obtains its meta data object; determines number of rows
	public ResultSetTableModel(String driver, String url, String username, String password, String query)
		throws SQLException, ClassNotFoundException {
		// load database driver class
		Class.forName(driver);
		
		// connect to database
		connection = DriverManager.getConnection(url, username, password);

		// create Statement to query database
		statement = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);

		// update database connection status
		connectedToDatabase = true;
		
		// set query and execute it
		setQuery(query);
	} // end constructor ResultSetTableModel
	
	// get class that represents column type
	public Class getColumnClass(int column) throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");
		
		// determine Java class of column
		try {
			String className = metaData.getColumnClassName(column + 1);

			// return Class object that represents className
			return Class.forName(className);
		} // end try
		catch (Exception exception) {
			exception.printStackTrace();
		} // end catch
		
		return Object.class; // if problems occur above, assume type Object
	} // end method getColumnClass
	
	// get number of columns in ResultSet
	public int getColumnCount() throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");
		
		// determine number of columns
		try {
			return metaData.getColumnCount();
		} // end try
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch
		
		return 0; // if problems occur above, return 0 for number of columns
	} // end method getColumnCount
	
	// get name of a particular column in ResultSet
	public String getColumnName(int column) throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");

		// determine column name
		try {
			return metaData.getColumnName(column + 1); 
		} // end try
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch
		
		return ""; // if problems, return empty string for column name
	} // end method getColumnName
	
	// to is a string which includes the new name and the data type which it stores
	public void renameColumn(String table, String from, String to) throws IllegalStateException, SQLException {
	  String query = String.format("ALTER TABLE %s RENAME COLUMN %s to %s;", table, from, to);
	  setQuery(query);
	}
	
	// return number of rows in ResultSet
	public int getRowCount() throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase) 
			throw new IllegalStateException("Not Connected to Database");
		
		return numberOfRows;
	} // end method getRowCount
	
	// obtain value in particular row and column
	public Object getValueAt(int row, int column) throws IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase)
			throw new IllegalStateException("Not Connected to Database");
		
		// obtain a value at specified ResultSet row and column
		try {
			resultSet.absolute(row + 1);
			return resultSet.getObject(column + 1);
		} // end try
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch
		
		return ""; // if problems, return empty string object
	} // end method getValueAt
   
    // set new database query string
	public void setQuery(String query) throws SQLException, IllegalStateException {
		// ensure database connection is available
		if (!connectedToDatabase) 
			throw new IllegalStateException("Not Connected to Database");
		
		//Temporary approach how to deal with update query
		if (!query.trim().substring(0, 1).equalsIgnoreCase("s")) {
			statement.executeUpdate(query);
		}
		else {
			// specify query and execute it
			resultSet = statement.executeQuery(query);
			
			// obtain meta data for ResultSet
			metaData = resultSet.getMetaData();
			
			// determine number of rows in ResultSet
			resultSet.last();                   // move to last row
			numberOfRows = resultSet.getRow();  // get row number
			
			
			// Alert the user if an inputted value does not exist in the database.
			if (numberOfRows == 0) {
				JOptionPane.showMessageDialog (null, "We're sorry, one or more values does not exist in our system. Please try again.", "Error message", JOptionPane.PLAIN_MESSAGE);
/*
				try {
					setQuery(DEFAULT_QUERY);
				}
				catch(SQLException sqlException2) {
					JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
					disconnectFromDatabase();
				}
*/
			}
			
			// notify JTable that model has changed
			fireTableStructureChanged();
		}
	} // end method setQuery
	
	// close Statement and Connection
	public void disconnectFromDatabase() {
		if (!connectedToDatabase)
			return;
		
		// close Statement and Connection
		try {
			statement.close();
			connection.close();
		} // end try
		catch (SQLException sqlException) {
			sqlException.printStackTrace();
		} // end catch
		finally { // update database connection status
			connectedToDatabase = false;
		} // end finally
	} // end method disconnectFromDatabase        
}  // end class ResultSetTableModel