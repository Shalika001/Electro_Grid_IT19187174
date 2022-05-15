package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement; 

public class Item 
{ //A common method to connect to the DB
		private Connection connect(){ 
			
						Connection con = null; 
						
						try{ 
								Class.forName("com.mysql.jdbc.Driver"); 
 
								//Provide the correct details: DBServer/DBName, username, password 
								con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electrogridpowerconsumption", "root", "root"); 
						} 
						catch (Exception e) {
							e.printStackTrace();
							} 
						
						return con; 
			} 
		
		
		public String insertpowerconsumption(String account_number, String name, String type, String date, String usages, String description){ 
			
					String output = ""; 
					
					try
					{ 
						Connection con = connect(); 
						
						if (con == null) 
						{
							return "Error while connecting to the database for inserting."; 
							
						} 
						// create a prepared statement
						
						String query = " insert into powerconsumption (`consumtiopn_id`,`account_number`,`name`,`type`,`date`,`usages`,`description`)"+" values (?, ?, ?, ?, ?, ?, ?)"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						// binding values
						preparedStmt.setInt(1, 0);
						preparedStmt.setString(2, account_number);
						preparedStmt.setString(3, name);
						preparedStmt.setString(4, type);
						preparedStmt.setString(5, date);
						preparedStmt.setString(6, usages);
						preparedStmt.setString(7, description);
						// execute the statement
 
						preparedStmt.execute(); 
						con.close(); 
						
						String newpowerconsumption = readpowerconsumption(); 
						output = "{\"status\":\"success\",\"data\":\""+newpowerconsumption+"\"}"; 
					} 
					
					catch (Exception e) 
					{ 
						output = "{\"status\":\"error\", \"data\":\"Error while inserting the powerconsumption.\"}"; 
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
		
		
		
		public String readpowerconsumption() 
		{ 
			String output = ""; 
			try
			{ 
				Connection con = connect(); 
		 if (con == null) 
		 { 
		 return "Error while connecting to the database for reading."; 
		 } 
		 // Prepare the html table to be displayed
		 output = "<table border=\"1\" class=\"table\"><tr><th>ID</th>"
		 		+ "<th>Account_Number</th>"
		 		+ "<th>Name</th>"
		 		+ "<th>Type</th>"
		 		+ "<th>Date</th>"
		 		+ "<th>Usage</th>"
		 		+ "<th>Description</th>"
		 		+ "<th>Update</th>"
		 		+ "<th>Remove</th></tr>";
		 		
		
		 String query = "select * from powerconsumption"; 
		 Statement stmt = con.createStatement(); 
		 ResultSet rs = stmt.executeQuery(query); 
		 // iterate through the rows in the result set
		 while (rs.next()) 
		 { 
		 String consumtiopn_id = Integer.toString(rs.getInt("consumtiopn_id")); 
		 String account_number = rs.getString("account_number"); 
		 String name = rs.getString("name"); 
		 String type = rs.getString("type");
		 String date = rs.getString("date");
		 String usages = rs.getString("usages");
		 String description = rs.getString("description");
		 // Add into the html table
		 output += "<tr><td><input id='hidconsumtiopn_idUpdate' name='hidconsumtiopn_idUpdate' type='hidden' value='"+consumtiopn_id+"'>"+consumtiopn_id+"</td>";
		 //output += "<tr><td>" + consumtiopn_id + "</td>";
		 output += "<td>" + account_number + "</td>";
		 output += "<td>" + name + "</td>"; 
		 output += "<td>" + type + "</td>";
		 output += "<td>" + date + "</td>";
		 output += "<td>" + usages + "</td>";
		 output += "<td>" + description + "</td>";
		 // buttons
		 output += "<td><input name='btnUpdate' type='button' value='Update' "
				 + "class='btnUpdate btn btn-secondary' data-consumtiopn_id='" + consumtiopn_id + "'></td>"
				 + "<td><input name='btnRemove' type='button' value='Remove' "
				 + "class='btnRemove btn btn-danger' data-consumtiopn_id='" + consumtiopn_id + "'></td></tr>"; 
		 
		 } 
		 con.close(); 
		 // Complete the html table
		 output += "</table>"; 
		 } 
		 
		catch (Exception e) 
		 { 
		 output = "Error while reading the powerconsumption."; 
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}

			
			
			public String updatepowerconsumption(String consumtiopn_id, String account_number, String name, String type, String date, String usages, String description){ 
				
					String output = ""; 
					
					try{ 
							Connection con = connect(); 
							if (con == null){
								return "Error while connecting to the database for updating.";
								} 
							// create a prepared statement
							String query = "UPDATE powerconsumption SET account_number=?,name=?,type=?,date=?,usages=?,description=? WHERE consumtiopn_id=?"; 
							PreparedStatement preparedStmt = con.prepareStatement(query); 
							// binding values
							preparedStmt.setString(1, account_number); 
							preparedStmt.setString(2, name); 
							preparedStmt.setString(3, type);
							preparedStmt.setString(4, date);
							preparedStmt.setString(5, usages);
							preparedStmt.setString(6, description);
							preparedStmt.setInt(7, Integer.parseInt(consumtiopn_id)); 
							// execute the statement
							preparedStmt.execute(); 
							con.close(); 
							String newpowerconsumption = readpowerconsumption(); 
							output = "{\"status\":\"success\",\"data\":\""+newpowerconsumption+"\"}"; 

					} 
					
					catch (Exception e){ 
						
						output = "{\"status\":\"error\",\"data\":\"Error while updating the powerconsumption.\"}"; 

						System.err.println(e.getMessage()); 
						
					} 
					
					return output; 
			} 
			
			
			public String deletepowerconsumption(String consumtiopn_id){ 
				
					String output = ""; 
					
					try{ 
						Connection con = connect(); 
						
						if (con == null){
							return "Error while connecting to the database for deleting."; 
							} 
						// create a prepared statement
						String query = "delete from powerconsumption where consumtiopn_id=?"; 
						PreparedStatement preparedStmt = con.prepareStatement(query); 
						// binding values
						preparedStmt.setInt(1, Integer.parseInt(consumtiopn_id)); 
						// execute the statement
						preparedStmt.execute(); 
						con.close(); 
						String newpowerconsumption = readpowerconsumption(); 
						 output = "{\"status\":\"success\",\"data\":\""+newpowerconsumption+"\"}"; 

					} 
					
					catch (Exception e){ 
						output = "{\"status\":\"error\",\"data\":\"Error while deleting the powerconsumption.\"}";
						System.err.println(e.getMessage()); 
					} 
					return output; 
			} 
			
			
			
			
} 
