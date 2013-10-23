// Fig. 25.31: DisplayQueryResults.java
// Display the contents of the Authors table in the
// Books database.
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;

public class DisplayQueryResults extends JFrame 
{
   // JDBC driver, database URL, username and password
   static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
   static final String DATABASE_URL = "jdbc:mysql://localhost/hotel";
   static final String USERNAME= "root";
   																									static final String PASSWORD= "namber913";
   
   // default query retrieves all data from authors table
   static final String DEFAULT_QUERY = "SELECT Room.rmID, Room.roomType, Room.occupied FROM Room, Cost WHERE Room.roomType = Cost.roomType ORDER BY price, rmID ASC;";
   
   private ResultSetTableModel tableModel;
   private JTextArea queryArea;
   private JLabel hotelInfo;
   
   // create ResultSetTableModel and GUI
   public DisplayQueryResults() 
   {   
      super("Hotel Reservation System");
        
      // create ResultSetTableModel and display database table
      try 
      {
         // create TableModel for results of query SELECT * FROM authors
         tableModel = new ResultSetTableModel(JDBC_DRIVER, DATABASE_URL,
        		 USERNAME, PASSWORD, DEFAULT_QUERY);

         // set up JTextArea in which user types queries
         queryArea = new JTextArea(DEFAULT_QUERY, 3, 100);
         queryArea.setWrapStyleWord(true);
         queryArea.setLineWrap(true);
         
/*
         JScrollPane scrollPane = new JScrollPane(queryArea,
        		 ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
        		 ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
*/
         
         // set up JButton for submitting queries
//         JButton submitButton = new JButton("Submit Query");
         
         
         // set up JTextField for hotel information
         hotelInfo = new JLabel("Welcome to the Hotel Reservation System!");
         Box hotelInfoBox = Box.createHorizontalBox();
         hotelInfoBox.add(hotelInfo);
         
         // set up JButton for submitting query to check room availability
         JButton checkRmAvail = new JButton("Check Room Availability");
         
         // set up JButton for checking prices
         JButton checkCost = new JButton("Check Hotel Prices");
         
      // set up JButton for checking reservations
         JButton checkReservation = new JButton("Check Reservations");
         
      // set up JButton for checking customers
         JButton checkCustomers = new JButton("Check Customers");

         // create Box to manage placement of queryArea and 
         // submitButton in GUI
         Box buttonBox = Box.createHorizontalBox();
//         buttonBox.add(scrollPane);
//         buttonBox.add(submitButton);
         buttonBox.add(checkRmAvail);
         buttonBox.add(checkCost);
         buttonBox.add(checkReservation);
         buttonBox.add(checkCustomers);

         // create JTable delegate for tableModel 
         JTable resultTable = new JTable(tableModel);
         
         // place GUI components on content pane
         add(hotelInfoBox, BorderLayout.NORTH);
         add(buttonBox, BorderLayout.CENTER);
         add(new JScrollPane(resultTable), BorderLayout.SOUTH);

/*
         // create event listener for submitButton
         submitButton.addActionListener( 
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed(ActionEvent event)
               {
                  // perform a new query
                  try 
                  {
                     tableModel.setQuery(queryArea.getText());
                  } // end try
                  catch (SQLException sqlException) 
                  {
                     JOptionPane.showMessageDialog(null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE);
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery(DEFAULT_QUERY);
                        queryArea.setText(DEFAULT_QUERY);
                     } // end try
                     catch (SQLException sqlException2) 
                     {
                        JOptionPane.showMessageDialog(null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE);
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
         
                        System.exit(1); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener
*/         
         
         
      // create event listener for submitButton
         checkRmAvail.addActionListener(
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed(ActionEvent event)
               {
                  // perform a new query
                  try 
                  {
                     tableModel.setQuery("SELECT COUNT(Room.roomType), Room.roomType, price FROM Room, Cost WHERE Occupied = False AND Room.roomType = Cost.roomType GROUP BY Room.roomType ORDER BY price ASC");
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery(DEFAULT_QUERY);
                        queryArea.setText(DEFAULT_QUERY);
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
         
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener
         
         
      // create event listener for submitButton
         checkCost.addActionListener( 
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed(ActionEvent event)
               {
                  // perform a new query
                  try 
                  {
                     tableModel.setQuery("SELECT * FROM Cost ORDER BY price ASC");
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery(DEFAULT_QUERY);
                        queryArea.setText(DEFAULT_QUERY);
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
         
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener
         
      // create event listener for submitButton
         checkReservation.addActionListener( 
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed(ActionEvent event)
               {
               	String customerID = JOptionPane.showInputDialog("What is your customer ID?", null);
               	String reservationLastName = JOptionPane.showInputDialog("What is your last name?", null);
                  // perform a new query
                  try 
                  {
                	  tableModel.setQuery("SELECT resID, lastName, Reservation.cID, checkIn, checkOut, rmID, pay FROM Reservation, Customer WHERE Reservation.cID = Customer.cID AND Customer.cID = " + customerID + " AND lastName = '" + reservationLastName + "' ORDER BY resID, checkIn, checkOut ASC;");
/*
resID	lastName	cID		checkIn			checkOut		rmID		pay
5		Spartan		1		2013-10-22		2013-10-22		101			75.0
7		Spartan		1		2013-10-22		2013-10-22		102			75.0
10		Spartan		1		2013-10-23		2013-10-23		301			150.0
11		Spartan		1		2013-10-23		2013-10-23		200			100.0
*/
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery(DEFAULT_QUERY);
                        queryArea.setText(DEFAULT_QUERY);
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
         
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener
         
      // create event listener for submitButton
         checkCustomers.addActionListener( 
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed(ActionEvent event)
               {
                  // perform a new query
                  try 
                  {
                     tableModel.setQuery("SELECT * FROM Customer;");
/*
cID		lastName	firstName	address					city		zipCode		phoneNum		creditCardNum			updatedAt
1		Spartan		Sammy		1 Washington Square		San Jose	95112		4089241000		1234567890123456		2013-10-22
 */
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query 
                     // by executing default query
                     try 
                     {
                        tableModel.setQuery(DEFAULT_QUERY);
                        queryArea.setText(DEFAULT_QUERY);
                     } // end try
                     catch ( SQLException sqlException2 ) 
                     {
                        JOptionPane.showMessageDialog( null, 
                           sqlException2.getMessage(), "Database error", 
                           JOptionPane.ERROR_MESSAGE );
         
                        // ensure database connection is closed
                        tableModel.disconnectFromDatabase();
         
                        System.exit( 1 ); // terminate application
                     } // end inner catch                   
                  } // end outer catch
               } // end actionPerformed
            }  // end ActionListener inner class          
         ); // end call to addActionListener

         
         
         setSize(500, 250); // set window size
         pack();
         setVisible(true); // display window  
      } // end try
      catch (ClassNotFoundException classNotFound) 
      {
         JOptionPane.showMessageDialog(null, 
        		 "MySQL driver not found", "Driver not found",
        		 JOptionPane.ERROR_MESSAGE);
         
         System.exit(1); // terminate application
      } // end catch
      catch (SQLException sqlException) 
      {
         JOptionPane.showMessageDialog(null, sqlException.getMessage(), 
        		 "Database error", JOptionPane.ERROR_MESSAGE);
               
         // ensure database connection is closed
         tableModel.disconnectFromDatabase();
         
         System.exit(1);   // terminate application
      } // end catch
      
      // dispose of window when user quits application (this overrides
      // the default of HIDE_ON_CLOSE)
      setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      
      // ensure database connection is closed when user quits application
      addWindowListener(
      
         new WindowAdapter() 
         {
            // disconnect from database and exit when window has closed
            public void windowClosed(WindowEvent event)
            {
            	tableModel.disconnectFromDatabase();
            	System.exit( 0 );
            } // end method windowClosed
         } // end WindowAdapter inner class
      ); // end call to addWindowListener
   } // end DisplayQueryResults constructor
   
   // execute application
   public static void main(String args[]) 
   {
      new DisplayQueryResults();     
   } // end main
} // end class DisplayQueryResults



/**************************************************************************
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