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
   static final String PASSWORD= "password";

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
         hotelInfo.setFont(new Font("Arial", Font.BOLD, 16));
         hotelInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
         Box hotelInfoBox = Box.createHorizontalBox();
         hotelInfoBox.add(hotelInfo);
         
         // set up JButton for submitting query to check room availability
         JButton checkRmAvail = new JButton("Check Room Availability");
         
         // set up JButton for checking prices
         JButton checkCost = new JButton("Check Hotel Prices");
         
         // set up JButton for checking customers
         JButton checkCustomers = new JButton("Check Customers");
         
         // set up JButton for checking reservations
         JButton checkReservation = new JButton("Check Reservations");
         
         // set up JButton for adding customer
         JButton newCustomer = new JButton("New Customer");
         
         // set up JButton for checking customers
         JButton newReservation = new JButton("Place Reservation");

         // create Box to manage placement of queryArea and 
         // submitButton in GUI
         Box buttonBox = Box.createHorizontalBox();
//         buttonBox.add(scrollPane);
//         buttonBox.add(submitButton);
         buttonBox.add(checkRmAvail);
         buttonBox.add(checkCost);
         buttonBox.add(checkReservation);
         buttonBox.add(newCustomer);
         buttonBox.add(newReservation);
         
         buttonBox.add(checkCustomers); // for testing purposes

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
                     
                     // try to recover from invalid user query by executing default query
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
         
         
      // create event listener for checkRmAvail
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
                     
                     // try to recover from invalid user query by executing default query
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
         
         
      // create event listener for checkCost
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
                     
                     // try to recover from invalid user query by executing default query
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
         
      // create event listener for checkReservation
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
                	 System.out.println("User has chosen not to check reservations.");
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query by executing default query
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
         
      // create event listener for checkCustomers
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
                     
                     // try to recover from invalid user query by executing default query
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
         
         
         
         
         
         
         
         // create event listener for newCustomer
         newCustomer.addActionListener( 
                 new ActionListener() 
                 {
                    // pass query to table model
                    public void actionPerformed(ActionEvent event)
                    {
     if (JOptionPane.showConfirmDialog(null, "Are you a new customer?", "Request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
     {	
                 	   String customerFirstName = JOptionPane.showInputDialog("What is your first name?", null);
                 	   if (customerFirstName.matches(".*\\d.*"))
                 	   {
                 		   customerFirstName = JOptionPane.showInputDialog("Please enter a valid first name.", null);
                 	   }
                 	   
                 	   String customerLastName = JOptionPane.showInputDialog("What is your last name?", null);
                 	   if (customerLastName.matches(".*\\d.*"))
                 	   {
                 		   customerLastName = JOptionPane.showInputDialog("Please enter a valid last name.", null);
                 	   }
                 	   
                 	   String customerAddress = JOptionPane.showInputDialog("What is your primary address of residency?", null);
                 	   if (!customerAddress.matches(".*\\d.*"))
                 	   {
                 		   customerAddress = JOptionPane.showInputDialog("Please enter a valid address.", null);
                 	   }
                 	   
                 	   String customerCity = JOptionPane.showInputDialog("Which city is your residency located in?", null);
                 	   if (customerCity.matches(".*\\d.*") || customerCity.length() > 20)
                 	   {
                 		   customerCity = JOptionPane.showInputDialog("Please enter a valid city.", null);
                 	   }
                 	   
                 	   String customerZipCode = JOptionPane.showInputDialog("What is the zip code of your residency? (5 digits)", null);
                 	   if (!customerZipCode.matches("[0-9]*") || customerZipCode.length() != 5)
                 	   {
                 		   customerZipCode = JOptionPane.showInputDialog("Please enter your 5-digit zip code with the following format: #####", null);
                 	   }
                 	   
                 	   String customerPhoneNum = JOptionPane.showInputDialog("What is your main phone number? (10 digits)", null);
                 	   if (!customerPhoneNum.matches("[0-9]*") || customerPhoneNum.length() != 10)
                 	   {
                 		   customerPhoneNum = JOptionPane.showInputDialog("Please enter your 10-digit phone number with the following format: ##########", null);
                        }
                 	   
                 	   String customerCreditCardNum = JOptionPane.showInputDialog("What is your credit card number?", null);
                 	   if (!customerCreditCardNum.matches("[0-9]*") || customerCreditCardNum.length() > 19)
                 	   {
                 		   customerCreditCardNum = JOptionPane.showInputDialog("Please enter a valid credit card number.", null);
                 	   }
                 	   
                 	   String customerUpdatedAt = "NOW()";

                       // perform a new query
                       try 
                       {
                          tableModel.setQuery("INSERT INTO Customer(lastName, firstName, address, city, zipCode, phoneNum, creditCardNum, updatedAt) VALUES(" + "'" + customerLastName + "'" + "," + "'" + customerFirstName + "'" + "," + "'" + customerAddress + "'" + "," + "'" + customerCity + "'" + "," + "'" + customerZipCode + "'" + "," + "'" + customerPhoneNum + "'" + "," + "'" + customerCreditCardNum + "'" + "," + customerUpdatedAt + ")");

/*                          
     Statement tempStatement = null;
     System.out.println("Entering ResultSet tempCustomerID");
     @SuppressWarnings("null")
     ResultSet tempCustomerID = tempStatement.executeQuery("SELECT cID FROM Customer WHERE lastName = '" + customerLastName + "' and firstName = '" + customerFirstName + "';");
     System.out.println("Your Customer ID is" + tempCustomerID);
*/
                          System.out.println("Customer has been entered into the system.");
                          tableModel.setQuery("SELECT * FROM Customer WHERE lastName = '" + customerLastName + "' and firstName = '" + customerFirstName + "';");

                       } // end try
                       catch ( SQLException sqlException ) 
                       {
                          JOptionPane.showMessageDialog( null, 
                             sqlException.getMessage(), "Database error", 
                             JOptionPane.ERROR_MESSAGE );
                          
                          // try to recover from invalid user query by executing default query
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
              
                             System.exit(1); // terminate application
                          } // end inner catch                   
                       } // end outer catch
     }
              	  
                    
                    else
                    {
                    System.out.println("User is not a new customer.");
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

                           System.exit(1); // terminate application
                        }
                    }


                    } // end actionPerformed
                 }  // end ActionListener inner class          
              ); // end call to addActionListener     
         
         
         
         
         
         // create event listener for newReservation
         newReservation.addActionListener( 
            new ActionListener() 
            {
               // pass query to table model
               public void actionPerformed(ActionEvent event)
               {
if (JOptionPane.showConfirmDialog(null, "Are you a new customer?", "Request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
{
/*	
            	   String customerFirstName = JOptionPane.showInputDialog("What is your first name?", null);
            	   if (customerFirstName.matches(".*\\d.*"))
            	   {
            		   customerFirstName = JOptionPane.showInputDialog("Please enter a valid first name.", null);
            	   }
            	   
            	   String customerLastName = JOptionPane.showInputDialog("What is your last name?", null);
            	   if (customerLastName.matches(".*\\d.*"))
            	   {
            		   customerLastName = JOptionPane.showInputDialog("Please enter a valid last name.", null);
            	   }
            	   
            	   String customerAddress = JOptionPane.showInputDialog("What is your primary address of residency?", null);
            	   if (!customerAddress.matches(".*\\d.*"))
            	   {
            		   customerAddress = JOptionPane.showInputDialog("Please enter a valid address.", null);
            	   }
            	   
            	   String customerCity = JOptionPane.showInputDialog("Which city is your residency located in?", null);
            	   if (customerCity.matches(".*\\d.*") || customerCity.length() > 20)
            	   {
            		   customerCity = JOptionPane.showInputDialog("Please enter a valid city.", null);
            	   }
            	   
            	   String customerZipCode = JOptionPane.showInputDialog("What is the zip code of your residency? (5 digits)", null);
            	   if (!customerZipCode.matches("[0-9]*") || customerZipCode.length() != 5)
            	   {
            		   customerZipCode = JOptionPane.showInputDialog("Please enter your 5-digit zip code with the following format: #####", null);
            	   }
            	   
            	   String customerPhoneNum = JOptionPane.showInputDialog("What is your main phone number? (10 digits)", null);
            	   if (!customerPhoneNum.matches("[0-9]*") || customerPhoneNum.length() != 10)
            	   {
            		   customerPhoneNum = JOptionPane.showInputDialog("Please enter your 10-digit phone number with the following format: ##########", null);
                   }
            	   
            	   String customerCreditCardNum = JOptionPane.showInputDialog("What is your credit card number?", null);
            	   if (!customerCreditCardNum.matches("[0-9]*") || customerCreditCardNum.length() > 19)
            	   {
            		   customerCreditCardNum = JOptionPane.showInputDialog("Please enter a valid credit card number.", null);
            	   }
            	               	   
            	   String customerUpdatedAt = "NOW()";
	
                  // perform a new query
                  try 
                  {
                     tableModel.setQuery("INSERT INTO Customer(lastName, firstName, address, city, zipCode, phoneNum, creditCardNum, updatedAt) VALUES(" + "'" + customerLastName + "'" + "," + "'" + customerFirstName + "'" + "," + "'" + customerAddress + "'" + "," + "'" + customerCity + "'" + "," + "'" + customerZipCode + "'" + "," + "'" + customerPhoneNum + "'" + "," + "'" + customerCreditCardNum + "'" + "," + customerUpdatedAt + ")");

                     
Statement tempStatement = null;
System.out.println("Entering ResultSet tempCustomerID");
@SuppressWarnings("null")
ResultSet tempCustomerID = tempStatement.executeQuery("SELECT cID FROM Customer WHERE lastName = '" + customerLastName + "' and firstName = '" + customerFirstName + "';");
System.out.println("Your Customer ID is" + tempCustomerID);


                  } // end try
                  catch ( SQLException sqlException ) 
                  {
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query by executing default query
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
         
                        System.exit(1); // terminate application
                     } // end inner catch                   
                  } // end outer catch
*/
	System.out.println("Please click 'New Customer' button to add yourself to the system");
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

       System.exit(1); // terminate application
    }
}
         	  
/*
resID	lastName	cID		checkIn			checkOut		rmID		pay
*/

else
{

String customerID = JOptionPane.showInputDialog("What is your customer ID?", null);
String reservationLastName = JOptionPane.showInputDialog("What is your last name?", null);
// BEGIN test that the user exists in Customer
try {
tableModel.setQuery("SELECT * FROM Customer WHERE cID = '" + customerID + "' and lastName = '" + reservationLastName + "';");
}
catch ( SQLException sqlException2 ) 
{
   JOptionPane.showMessageDialog( null, 
      sqlException2.getMessage(), "Database error", 
      JOptionPane.ERROR_MESSAGE );

   // ensure database connection is closed
   tableModel.disconnectFromDatabase();

   System.exit(1); // terminate application
}
// END test that user exists in Customer


String roomTypeDesired = JOptionPane.showInputDialog("What room type would you like? (Standard, Deluxe, Suite)", null);
String checkInDate = JOptionPane.showInputDialog("What day would you like to check in? (YYYY-MM-DD)", null);
String checkOutDate = JOptionPane.showInputDialog("What day would you like to check out? (YYYY-MM-DD)", null);
// BEGIN test that smallest roomID for desired room type is chosen
try {
	tableModel.setQuery("SELECT MIN(Room.rmID), Room.roomType, price FROM Room, Cost WHERE Room.roomType = Cost.roomType and Room.roomType = '" + roomTypeDesired + "' and occupied = false;");
}
catch ( SQLException sqlException2 ) 
{
   JOptionPane.showMessageDialog( null, 
      sqlException2.getMessage(), "Database error", 
      JOptionPane.ERROR_MESSAGE );

   // ensure database connection is closed
   tableModel.disconnectFromDatabase();

   System.exit(1); // terminate application
}
// END test that smallest roomID for desired room type is chosen


// dialogue to confirm placing reservation
if (JOptionPane.showConfirmDialog(null, "Place reservation?", "Request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
{	
System.out.println("User has chosen to place reservation.");
System.out.println("Entering final try-catch block");
                  // perform a new query
                  try 
                  {
System.out.println("Entered TRY of final try-catch block");

// Get integer value of customer.
int intCustomerID = Integer.parseInt(customerID);

Statement tempStatement = null;
@SuppressWarnings("null")

// Get integer value of room ID.
ResultSet reservationRmID = tempStatement.executeQuery("SELECT MIN(rmID) FROM Room WHERE roomType = '" + roomTypeDesired + "';");
int intReservationRmID = reservationRmID.getInt("MIN(rmID)");
System.out.println("Successfully passed through ResultSet reservationRmID");

// Get integer value of room price.
ResultSet reservationPay = tempStatement.executeQuery("SELECT price FROM Cost WHERE roomType = '" + roomTypeDesired + "';");
int intReservationPay = reservationPay.getInt("price");
System.out.println("Successfully passed through ResultSet reservationPay");

// Insert the data into Reservation.
tableModel.setQuery("INSERT INTO Reservation(cID, checkIn, checkOut, rmID, pay) VALUES(" + intCustomerID + ",'" + checkInDate + "','" + checkOutDate + "'," + intReservationRmID + "," + intReservationPay + ")");
                  } // end try
                  catch ( SQLException sqlException ) 
                  {
System.out.println("Reservation: Entered CATCH of final try-catch block");
                     JOptionPane.showMessageDialog( null, 
                        sqlException.getMessage(), "Database error", 
                        JOptionPane.ERROR_MESSAGE );
                     
                     // try to recover from invalid user query by executing default query
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
         
                        System.exit(1); // terminate application
                     } // end inner catch                   
                  } // end outer catch
}
else
{
System.out.println("User has chosen not to place reservation.");
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

       System.exit(1); // terminate application
    }
}
}                  


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
            	System.exit(0);
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
   
