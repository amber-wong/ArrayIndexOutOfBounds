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

// Fig. 25.31: DisplayQueryResults.java
// Display the contents of the Authors table in the
// Books database.
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

import javax.swing.*;
import javax.imageio.*;

import java.io.*;

public class DisplayQueryResults extends JFrame {
	// JDBC driver, database URL, username and password
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DATABASE_URL = "jdbc:mysql://localhost/hotel";
	static final String USERNAME= "root";
	static final String PASSWORD= "password";
	
	// default query retrieves all data from authors table
	static final String DEFAULT_QUERY = "SELECT Room.rmID, Room.roomType FROM Room, Cost WHERE Room.roomType = Cost.roomType ORDER BY price, rmID ASC;";
	private ResultSetTableModel tableModel;
	private JTextArea queryArea;
	private JLabel hotelInfo;
	
	// create ResultSetTableModel and GUI
    public DisplayQueryResults() {
		super("Hotel Reservation System");
		
		// create ResultSetTableModel and display database table
		try {
			// create TableModel for results of query SELECT * FROM authors
			tableModel = new ResultSetTableModel(JDBC_DRIVER, DATABASE_URL, USERNAME, PASSWORD, DEFAULT_QUERY);
			
			// set up JTextArea in which user types queries
			queryArea = new JTextArea(DEFAULT_QUERY, 3, 100);
			queryArea.setWrapStyleWord(true);
			queryArea.setLineWrap(true);

			// set up JTextField for hotel information
			hotelInfo = new JLabel("Welcome to the Hotel Reservation System!");
			hotelInfo.setFont(new Font("Arial", Font.BOLD, 16));
			hotelInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
			JPanel hotelInfoBox = new JPanel();
			hotelInfoBox.add(hotelInfo);
			
/*
			// add image to GUI (does not work)
			JLabel hotelImageLabel = new JLabel(new ImageIcon(getClass().getResource("link")));
			hotelInfoBox.add(hotelImageLabel);
*/
			
			// for general users
			JButton checkRmAvail = new JButton("Check Room Availability"); // set up JButton for submitting query to check room availability
			JButton checkCost = new JButton("Check Hotel Prices"); // set up JButton for checking prices
			JButton checkReservation = new JButton("Check Reservations"); // set up JButton for checking reservations
			JButton newReservation = new JButton("Place Reservation"); // set up JButton for checking customers
			JButton newCustomer = new JButton("New Customer"); // set up JButton for adding customer
			
			// for administrator
			JButton addRooms = new JButton("Add room");	// set up JButton for adding new rooms
			JButton checkCustomers = new JButton("Check Customers"); // set up JButton for checking customers
			
			// set up button panels
			final JPanel mainPanel = new JPanel();
			final JPanel userPanel = new JPanel();
			final JPanel adminPanel = new JPanel();
				
			// for main panel
			mainPanel.add(new JButton(new AbstractAction("User") {
				public void actionPerformed(ActionEvent event) {
					remove(adminPanel);
					add(userPanel);
					remove(mainPanel);
//					pack();
					repaint();
					invalidate();
					validate();
					
					try {
						tableModel.setQuery(DEFAULT_QUERY);
						queryArea.setText(DEFAULT_QUERY);
					} // end try
					catch (SQLException sqlException) {
						JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
     						
						// ensure database connection is closed
						tableModel.disconnectFromDatabase();
    
						System.exit(1); // terminate application
					} // end catch                   
				}
			} ) );
			
			mainPanel.add(new JButton(new AbstractAction("Administrator") {
				public void actionPerformed(ActionEvent event) {
					add(adminPanel);
					remove(userPanel);
					remove(mainPanel);
//					pack();
					repaint();
					invalidate();
					validate();
					
					try {
						tableModel.setQuery(DEFAULT_QUERY);
						queryArea.setText(DEFAULT_QUERY);
					} // end try
					catch (SQLException sqlException) {
						JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
     						
						// ensure database connection is closed
						tableModel.disconnectFromDatabase();
    
						System.exit(1); // terminate application
					} // end catch                   
				}
			} ) );

			
			// for general users
			userPanel.add(checkRmAvail);
			userPanel.add(checkCost);
			userPanel.add(checkReservation);
			userPanel.add(newCustomer);
			userPanel.add(newReservation);
			userPanel.add(new JButton(new AbstractAction("Back") {
				public void actionPerformed(ActionEvent event) {
					add(mainPanel);
					remove(userPanel);
					remove(adminPanel);
					repaint();
					invalidate();
					validate();
					
					try {
						tableModel.setQuery(DEFAULT_QUERY);
						queryArea.setText(DEFAULT_QUERY);
					} // end try
					catch (SQLException sqlException) {
						JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
     						
						// ensure database connection is closed
						tableModel.disconnectFromDatabase();
    
						System.exit(1); // terminate application
					} // end catch                   
				}
			} ) );
			
			// for administrator
			adminPanel.add(checkCustomers);
			adminPanel.add(addRooms);
			adminPanel.add(new JButton(new AbstractAction("Back") {
				public void actionPerformed(ActionEvent event) {
					add(mainPanel);
					remove(userPanel);
					remove(adminPanel);
//					pack();
					repaint();
					invalidate();
					validate();
					
					try {
						tableModel.setQuery(DEFAULT_QUERY);
						queryArea.setText(DEFAULT_QUERY);
					} // end try
					catch (SQLException sqlException) {
						JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
     						
						// ensure database connection is closed
						tableModel.disconnectFromDatabase();
    
						System.exit(1); // terminate application
					} // end catch                   
				}
			} ) );
			
			// create JTable delegate for tableModel
			JTable resultTable = new JTable(tableModel);
			
			// place GUI components on content pane
			add(hotelInfoBox, BorderLayout.NORTH);
			add(mainPanel, BorderLayout.CENTER);
			add(new JScrollPane(resultTable), BorderLayout.SOUTH);

			

			// create event listener for checkRmAvail
			checkRmAvail.addActionListener(
				new ActionListener() {
						
					// pass query to table model
					public void actionPerformed(ActionEvent event) {
						
						// perform a new query
						try {
							tableModel.setQuery("SELECT COUNT(Room.roomType) as \"availability\", Room.roomType as \"types\" FROM Room, Cost WHERE Occupied = False AND Room.roomType = Cost.roomType GROUP BY Room.roomType ORDER BY price ASC");
						} // end try
						catch (SQLException sqlException) {
							JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);                 
								
							// try to recover from invalid user query by executing default query
							try {
								tableModel.setQuery(DEFAULT_QUERY);
								queryArea.setText(DEFAULT_QUERY);
							} // end try
							catch (SQLException sqlException2) {
								JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);

								// ensure database connection is closed
								tableModel.disconnectFromDatabase();
								System.exit(1); // terminate application
							} // end inner catch
						} // end outer catch
					} // end actionPerformed
				}  // end ActionListener inner class
			); // end call to addActionListener
         
         
			
			// create event listener for checkCost
			checkCost.addActionListener(
				new ActionListener() {
					// pass query to table model
					public void actionPerformed(ActionEvent event) {
						// perform a new query
						try {
							tableModel.setQuery("SELECT * FROM Cost ORDER BY price ASC");
						} // end try
						catch (SQLException sqlException) {
							JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
                    
							// try to recover from invalid user query by executing default query
							try {
								tableModel.setQuery(DEFAULT_QUERY);
								queryArea.setText(DEFAULT_QUERY);
							} // end try
							catch (SQLException sqlException2) {
								JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
         						
								// ensure database connection is closed
								tableModel.disconnectFromDatabase();
        
								System.exit(1); // terminate application
							} // end inner catch                   
						} // end outer catch
					} // end actionPerformed
				}  // end ActionListener inner class    
			); // end call to addActionListener
			
			
			
			// create event listener for checkReservation
			checkReservation.addActionListener( 
				new ActionListener() {
					// pass query to table model
					public void actionPerformed(ActionEvent event) {
						
						JTextField field1 = new JTextField(2), field2 = new JTextField(2);
						JPanel fillablePanel = new JPanel();
						fillablePanel.setLayout(new BoxLayout(fillablePanel, BoxLayout.Y_AXIS));
						fillablePanel.add(new JLabel("Customer ID:"));
						fillablePanel.add(field1);
						fillablePanel.add(Box.createHorizontalStrut(2));
						fillablePanel.add(new JLabel("Last Name:"));
						fillablePanel.add(field2);

						int result = JOptionPane.showConfirmDialog(null, fillablePanel, "Please enter the following:", JOptionPane.OK_CANCEL_OPTION);
						
						if (result == JOptionPane.OK_OPTION) {
							String customerID = field1.getText();
							String reservationLastName = field2.getText();
							
							// perform a new query
							try {
								tableModel.setQuery("SELECT resID as 'Reservation ID', Reservation.cID, lastName, creditCardNum, checkIn, checkOut, rmID, pay FROM Reservation, Customer WHERE Reservation.cID = Customer.cID AND Customer.cID = " + customerID + " AND lastName = '" + reservationLastName + "' ORDER BY resID, checkIn, checkOut ASC;");
/*
								tableModel.renameColumn("Reservation", "lastName", "LastName");
*/
           
/*
resID	cID		lastName	creditCardNum		checkIn			checkOut		rmID		pay
4		3		Spartan		1234567890123456	2013-11-03		2013-11-05		101			75
*/
							} // end try
							catch (SQLException sqlException) {
								System.out.println("User has chosen not to check reservations.");
								JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
								
								// try to recover from invalid user query by executing default query
								try {
									tableModel.setQuery(DEFAULT_QUERY);
									queryArea.setText(DEFAULT_QUERY);
								} // end try
								catch (SQLException sqlException2) {
									JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
								
									// ensure database connection is closed
									tableModel.disconnectFromDatabase();
         
									System.exit(1); // terminate application
								} // end inner catch  
							} // end outer catch
						}
						else {
							// try to recover from invalid user query by executing default query
							try {
								tableModel.setQuery(DEFAULT_QUERY);
								queryArea.setText(DEFAULT_QUERY);
							} // end try
							catch (SQLException sqlException2) {
								JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
								
								// ensure database connection is closed
								tableModel.disconnectFromDatabase();
								
								System.exit(1); // terminate application
							} // end catch
						}
					} // end actionPerformed
				}  // end ActionListener inner class          
			); // end call to addActionListener
         
			
			
			// create event listener for newCustomer
			newCustomer.addActionListener(
				new ActionListener() {
					// pass query to table model
					public void actionPerformed(ActionEvent event) {
						JTextField field1 = new JTextField(2), field2 = new JTextField(2), field3 = new JTextField(2),
								field4 = new JTextField(2), field5 = new JTextField(2), field6 = new JTextField(2),
								field7 = new JTextField(2);
						JPanel fillablePanel = new JPanel();
						fillablePanel.setLayout(new BoxLayout(fillablePanel, BoxLayout.Y_AXIS));
						fillablePanel.add(new JLabel("First Name:"));
						fillablePanel.add(field1);
						fillablePanel.add(Box.createHorizontalStrut(2));
						fillablePanel.add(new JLabel("Last Name:"));
						fillablePanel.add(field2);
						fillablePanel.add(Box.createHorizontalStrut(2));
						fillablePanel.add(new JLabel("Address:"));
						fillablePanel.add(field3);
						fillablePanel.add(Box.createHorizontalStrut(2));
						fillablePanel.add(new JLabel("City:"));
						fillablePanel.add(field4);
						fillablePanel.add(Box.createHorizontalStrut(2));
						fillablePanel.add(new JLabel("Zip Code:"));
						fillablePanel.add(field5);
						fillablePanel.add(Box.createHorizontalStrut(2));
						fillablePanel.add(new JLabel("Phone Number (##########):"));
						fillablePanel.add(field6);
						fillablePanel.add(Box.createHorizontalStrut(2));
						fillablePanel.add(new JLabel("Credit Card Number (################):"));
						fillablePanel.add(field7);
                   		
						int result = JOptionPane.showConfirmDialog(null, fillablePanel, "Please enter the following:", JOptionPane.OK_CANCEL_OPTION);
						
						if (result == JOptionPane.YES_OPTION) {
							String customerFirstName = field1.getText();
							String customerLastName = field2.getText();
							String customerAddress = field3.getText();
							String customerCity = field4.getText();
							String customerZipCode = field5.getText();
							String customerPhoneNum = field6.getText();
							String customerCreditCardNum = field7.getText();
							String customerUpdatedAt = "NOW()";
							
							// perform a new query
							try {
								tableModel.setQuery("INSERT INTO Customer(lastName, firstName, address, city, zipCode, phoneNum, creditCardNum, updatedAt) VALUES(" + "'" + customerLastName + "'" + "," + "'" + customerFirstName + "'" + "," + "'" + customerAddress + "'" + "," + "'" + customerCity + "'" + "," + "'" + customerZipCode + "'" + "," + "'" + customerPhoneNum + "'" + "," + "'" + customerCreditCardNum + "'" + "," + customerUpdatedAt + ")");
								
								System.out.println("Customer has been entered into the system.");
								tableModel.setQuery("SELECT * FROM Customer WHERE lastName = '" + customerLastName + "' and firstName = '" + customerFirstName + "';");
							} // end try
							catch (SQLException sqlException) {
								JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
                          
								// try to recover from invalid user query by executing default query
								try {
									tableModel.setQuery(DEFAULT_QUERY);
									queryArea.setText(DEFAULT_QUERY);
								} // end try
								catch (SQLException sqlException2) {
									JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
              
									// ensure database connection is closed
									tableModel.disconnectFromDatabase();
              
									System.exit(1); // terminate application
								} // end inner catch                   
							} // end outer catch
						}
						else {
							System.out.println("New customer has not been added to the system.");
							try {
								tableModel.setQuery(DEFAULT_QUERY);
								queryArea.setText(DEFAULT_QUERY);
							} // end try
							catch (SQLException sqlException2) {
								JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);

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
				new ActionListener() {
					// pass query to table model
					public void actionPerformed(ActionEvent event) {
						if (JOptionPane.showConfirmDialog(null, "Are you a new customer?", "Request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
        				
							// is a new customer
							JOptionPane.showMessageDialog(null, "Please click 'New Customer' button to add yourself to the system.", "Alert", JOptionPane.QUESTION_MESSAGE);
							try {
								tableModel.setQuery(DEFAULT_QUERY);
								queryArea.setText(DEFAULT_QUERY);
							} // end try
							catch (SQLException sqlException2) {
								JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
								
								// ensure database connection is closed
								tableModel.disconnectFromDatabase();
								System.exit(1); // terminate application
							}
						}
        			
/*
resID	lastName	cID		checkIn			checkOut		rmID		pay
*/
						else {
							JTextField field1 = new JTextField(2), field2 = new JTextField(2), field3 = new JTextField(2),
									field4 = new JTextField(2), field5 = new JTextField(2);
							JPanel fillablePanel = new JPanel();
							fillablePanel.setLayout(new BoxLayout(fillablePanel, BoxLayout.Y_AXIS));
							fillablePanel.add(new JLabel("Customer ID:"));
							fillablePanel.add(field1);
							fillablePanel.add(Box.createHorizontalStrut(2));
        					fillablePanel.add(new JLabel("Last Name:"));
        					fillablePanel.add(field2);
        					fillablePanel.add(Box.createHorizontalStrut(2));
        					fillablePanel.add(new JLabel("Room Type (Standard, Deluxe, Suite):"));
        					fillablePanel.add(field3);
        					fillablePanel.add(Box.createHorizontalStrut(2));
        					fillablePanel.add(new JLabel("Check-in Date (YYYY-MM-DD):"));
        					fillablePanel.add(field4);
        					fillablePanel.add(Box.createHorizontalStrut(2));
        					fillablePanel.add(new JLabel("Check-out Date (YYYY-MM-DD):"));
        					fillablePanel.add(field5);

        					int result = JOptionPane.showConfirmDialog(null, fillablePanel, "Please enter the following:", JOptionPane.OK_CANCEL_OPTION);

        					if (result == JOptionPane.OK_OPTION) {
        						String customerID = field1.getText();
        						String reservationLastName = field2.getText();
        						String roomTypeDesired = field3.getText();
        						String checkInDate = field4.getText();
        						String checkOutDate = field5.getText();
        						
        						// dialogue to confirm placing reservation
        						if (JOptionPane.showConfirmDialog(null, "Place reservation?", "Request", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
        						System.out.println("User has chosen to place reservation.");
System.out.println("Reservation: Entering final try-catch block");   
        						
        							// perform a new query
        							try {
System.out.println("Reservation: Entered TRY of final try-catch block");

        								// Get integer value of customer.
        								int intCustomerID = Integer.parseInt(customerID);
        								System.out.println("Customer ID is " + intCustomerID);
            					   
/*
reservationRoomID = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD).prepareCall("SELECT MIN(rmID) FROM Room WHERE roomType = '" + roomTypeDesired + "';");
reservationRoomID.execute();
System.out.println("Reservation: callable statement executed, now converting the selected room number to an integer.");
int intReservationRmID = reservationRoomID.getInt("MIN(rmID)");
System.out.println("Room ID is " + intReservationRmID);
            					   
Statement tempStatement = null;
@SuppressWarnings("null")
        					   
// Get integer value of room ID.
ResultSet reservationRmID = tempStatement.executeQuery("SELECT MIN(rmID) FROM Room WHERE roomType = '" + roomTypeDesired + "';");
//int intReservationRmID = reservationRmID.getInt("MIN(rmID)");
System.out.println("Successfully passed through ResultSet reservationRmID");
            					   
// Get integer value of room price.
ResultSet reservationPay = tempStatement.executeQuery("SELECT price FROM Cost WHERE roomType = '" + roomTypeDesired + "';");
int intReservationPay = reservationPay.getInt("price");
System.out.println("Successfully passed through ResultSet reservationPay");

// Insert the data into Reservation.
            					   tableModel.setQuery("INSERT INTO Reservation(cID, checkIn, checkOut, rmID, pay) VALUES(" + intCustomerID + ",'" + checkInDate + "','" + checkOutDate + "'," + intReservationRmID + "," + intReservationPay + ")");
*/
            					   
String storedProc =
"DELIMITER //" +
"CREATE PROCEDURE makeRes(IN minRmID INT(11), IN rmPrice FLOAT)" + 
"BEGIN" +
"SELECT MIN(rmID) FROM Room WHERE roomType = '" + roomTypeDesired + "' as minRmID;" +
"SELECT price FROM Cost WHERE roomType = '" + roomTypeDesired + "' as rmPrice;" +
"INSERT INTO Reservation(cID, checkIn, checkOut, rmID, pay) VALUES(" + intCustomerID + ",'" + checkInDate + "','" + checkOutDate + "', minRmID, rmPrice)" +
"END //" +
"DELIMITER ;" +
"CALL makeRes('" + roomTypeDesired + "', " + roomTypeDesired + "');";
            					    	// Insert the data into Reservation.
            					    	tableModel.setQuery(storedProc);
        							} // end try
        							catch (SQLException sqlException) {
        								System.out.println("Reservation: Entered CATCH of final try-catch block");
        								JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        								
        								// try to recover from invalid user query by executing default query
        								try {
        									tableModel.setQuery(DEFAULT_QUERY);
        									queryArea.setText(DEFAULT_QUERY);
        								} // end try
        								catch (SQLException sqlException2) {
        									JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        								
        									// ensure database connection is closed
        									tableModel.disconnectFromDatabase();
        									
        									System.exit(1); // terminate application
        								} // end inner catch
        							} // end outer catch
        						}
        						else {
        							System.out.println("User has chosen not to place reservation.");
        							try {
        								tableModel.setQuery(DEFAULT_QUERY);
        								queryArea.setText(DEFAULT_QUERY);
        							} // end try
        							catch (SQLException sqlException2) {
        								JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
        							
        								// ensure database connection is closed
        								tableModel.disconnectFromDatabase();
        							
        								System.exit(1); // terminate application
        							}
        						}
        					}
        					else {
        						try {
        							tableModel.setQuery(DEFAULT_QUERY);
        							queryArea.setText(DEFAULT_QUERY);
        						} // end try
        						catch (SQLException sqlException2) {
        							JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
            				   
        							// ensure database connection is closed
        							tableModel.disconnectFromDatabase();
        						
        							System.exit(1); // terminate application
        						} // end inner catch
        					}
						}
					} // end actionPerformed
				}  // end ActionListener inner class
			); // end call to addActionListener    
         
         
			
			// create event listener for checkCustomers
			checkCustomers.addActionListener(
				new ActionListener() {
					// pass query to table model
					public void actionPerformed(ActionEvent event) {
						// perform a new query
						try {
							tableModel.setQuery("SELECT * FROM Customer;");
/*
cID		lastName	firstName	address					city		zipCode		phoneNum		creditCardNum			updatedAt
3		Spartan		Sammy		1 Washington Square		San Jose	95112		4089241000		1234567890123456		2013-11-03
 */
						} // end try
						catch (SQLException sqlException) {
							JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
                     
							// try to recover from invalid user query by executing default query
							try {
								tableModel.setQuery(DEFAULT_QUERY);
								queryArea.setText(DEFAULT_QUERY);
							} // end try
							catch (SQLException sqlException2) {
								JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
									// ensure database connection is closed
								tableModel.disconnectFromDatabase();
         
								System.exit(1); // terminate application
							} // end inner catch                   
						} // end outer catch
					} // end actionPerformed
				}  // end ActionListener inner class          
			); // end call to addActionListener
			
			
			// create event listener for addRooms
			addRooms.addActionListener(
				new ActionListener() {
					// pass query to table model
					public void actionPerformed(ActionEvent event) {
						JTextField field1 = new JTextField(2), field2 = new JTextField(2);
						JPanel fillablePanel = new JPanel();
						fillablePanel.setLayout(new BoxLayout(fillablePanel, BoxLayout.Y_AXIS));
						fillablePanel.add(new JLabel("Room Number:"));
						fillablePanel.add(field1);
						fillablePanel.add(Box.createHorizontalStrut(2));
						fillablePanel.add(new JLabel("Room Type (Standard, Deluxe, Suite):"));
						fillablePanel.add(field2);
                   		
						int result = JOptionPane.showConfirmDialog(null, fillablePanel, "Please enter the following:", JOptionPane.OK_CANCEL_OPTION);
						
						if (result == JOptionPane.YES_OPTION) {
							String insertRoomID = field1.getText();
							String insertRoomType = field2.getText();
							
							// perform a new query
							try {
								tableModel.setQuery("INSERT INTO Room(rmID, roomType) VALUES(" + "'" + insertRoomID + "'" + "," + "'" + insertRoomType + "');");
								
								System.out.println("Room has been entered into the system.");
								tableModel.setQuery("SELECT * FROM Room WHERE rmID = '" + insertRoomID + "' and roomType = '" + insertRoomType + "';");
							} // end try
							catch (SQLException sqlException) {
								JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE );
                          
								// try to recover from invalid user query by executing default query
								try {
									tableModel.setQuery(DEFAULT_QUERY);
									queryArea.setText(DEFAULT_QUERY);
								} // end try
								catch (SQLException sqlException2) {
									JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
              
									// ensure database connection is closed
									tableModel.disconnectFromDatabase();
              
									System.exit(1); // terminate application
								} // end inner catch                   
							} // end outer catch
						}
						else {
							System.out.println("New room has not been added to the system.");
							try {
								tableModel.setQuery(DEFAULT_QUERY);
								queryArea.setText(DEFAULT_QUERY);
							} // end try
							catch (SQLException sqlException2) {
								JOptionPane.showMessageDialog(null, sqlException2.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);

								// ensure database connection is closed
								tableModel.disconnectFromDatabase();

								System.exit(1); // terminate application
							}
						}
					} // end actionPerformed
				}  // end ActionListener inner class
			); // end call to addActionListener
			         
			         

         	setSize(605, 565); // set window size
//         	pack();
         	setVisible(true); // display window
    	} // end try
		catch (ClassNotFoundException classNotFound) {
			JOptionPane.showMessageDialog(null, "MySQL driver not found", "Driver not found", JOptionPane.ERROR_MESSAGE);
			
			System.exit(1); // terminate application
		} // end catch
		catch (SQLException sqlException) {
			JOptionPane.showMessageDialog(null, sqlException.getMessage(), "Database error", JOptionPane.ERROR_MESSAGE);
               
			// ensure database connection is closed
			tableModel.disconnectFromDatabase();
			
			System.exit(1);   // terminate application
		} // end catch
      
		// dispose of window when user quits application (this overrides the default of HIDE_ON_CLOSE)
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
      
		// ensure database connection is closed when user quits application
		addWindowListener(
			new WindowAdapter() {
				// disconnect from database and exit when window has closed
				public void windowClosed(WindowEvent event) {
					tableModel.disconnectFromDatabase();
					System.exit(0);
				} // end method windowClosed
			} // end WindowAdapter inner class
		); // end call to addWindowListener
	} // end DisplayQueryResults constructor
   
    // execute application
    public static void main(String args[]) {
    	new DisplayQueryResults();
    } // end main
} // end class DisplayQueryResults
   
