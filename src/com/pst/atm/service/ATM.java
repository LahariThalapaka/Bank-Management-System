package com.pst.atm.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ATM {
	
	
	private static final String DriverName = "oracle.jdbc.driver.OracleDriver";
	private static final String HostName = "jdbc:oracle:thin:@localhost:1521:XE";
	private static final String UserName = "online_bank";
	private static final String password = "online_bank";
	
	
	public void createNewAccount(int account_number,String user_name,String gender,long mobile_number,double amount,int pin) {
		
					
		try {
			 				//calling JDBC Connectivity logic
			Connection con = getDbConnection();
			PreparedStatement ps = con.prepareStatement("insert into atm_user values(?,?,?,?,?,?)");
			ps.setInt(1, account_number);
			ps.setString(2, user_name);
			ps.setString(3, gender);
			ps.setLong(4, mobile_number);
			ps.setDouble(5, amount);
			ps.setInt(6, pin);
			
			
			int i = ps.executeUpdate();
			if(i>0) {
				System.out.println("Account Created Successfully");
			}else {
				System.out.println("Account Creation Failed");
			}
			con.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public boolean verifyPin(int account_number, int pin) {

	    boolean status = false;

	    try {
	        Connection con = getDbConnection();
	        PreparedStatement ps = con.prepareStatement(
	            "select * from atm_user where account_number=? and pin=?");

	        ps.setInt(1, account_number);
	        ps.setInt(2, pin);

	        ResultSet rs = ps.executeQuery();

	        if(rs.next()) {
	            status = true;
	        }

	        con.close();

	    } catch(Exception e) {
	        e.printStackTrace();
	    }

	    return status;
	}

	
	
	
	
	private static final String GET_CURRENT_AMOUNT = "select amount from atm_user where account_number =?";
	
	public void depositAmount(int account_number,double depositAmount) {
		
		//get current amount from user
		//add deposit amount and current amount
		//update total amount to table
		
		double currentAmount = 0.0;
		Connection con = getDbConnection();
		PreparedStatement ps;
		
		
		//get current amount from user
		try {
			ps = con.prepareStatement(GET_CURRENT_AMOUNT);
			ps.setInt(1, account_number);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				currentAmount = rs.getDouble(1);
			}
			
			
			//add deposit amount and current amount
			double totalAmount = currentAmount+depositAmount;
			
			//update total amount to table
			try {
				ps = con.prepareStatement("update atm_user set amount = ? where account_number=?");
				ps.setDouble(1, totalAmount);
				ps.setInt(2, account_number);
				int i = ps.executeUpdate();
				if(i>0) {
					System.out.println("Amount Deposited Succesfully");
					System.out.println("Transaction: DEPOSIT | Amount: " + depositAmount);
				}else {
					System.out.println("Amount Deposition Failed");
				}
				con.close();
				
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
		}catch(Exception e) {
			
		}
		
	}
	
	public void withdrawAmount(int account_number,double withdraw_amount) {
		
		Connection con = getDbConnection();
		PreparedStatement ps;
		
		double currentAmount = 0.0;
		
		try {
			
			ps = con.prepareStatement(GET_CURRENT_AMOUNT);
			ps.setInt(1, account_number);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				currentAmount = rs.getDouble(1);
			}
			
			if(currentAmount < withdraw_amount) {
		            System.out.println("Insufficient Balance");
		            con.close();
		            return;
		     }
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		double total_amount = currentAmount-withdraw_amount;
		
		try {
			ps = con.prepareStatement("update atm_user set amount=? where account_number=?");
			ps.setDouble(1, total_amount);
			ps.setInt(2, account_number);
			int i = ps.executeUpdate();
			if(i>0) {
				System.out.println("Withdraw Successfully");
				System.out.println("Transaction: WITHDRAW | Amount: "
                        + withdraw_amount);
			}else {
				System.out.println("Withdraw Failed");
			}
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void balanceEnquiry(int account_number) {
		double balance = 0.0;
		try {
			Connection con = getDbConnection();
			PreparedStatement ps = con.prepareStatement(GET_CURRENT_AMOUNT);
			ps.setInt(1, account_number);
		    ResultSet rs = ps.executeQuery();
			while(rs.next()) {
			  balance = rs.getDouble(1);
			}
			System.out.println("Balance is: "+balance);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	
	
//-------------------------JDBC Connectivity Logic ---------------------------	
	
	// return type is Connection because we are returning connection logic
	private Connection getDbConnection() {
		
		//here con is returning value so we want to declare here because con scope is inside only try block
		//but we are returning outside of try and catch block
		Connection con = null;
		try {
			Class.forName(DriverName);
			con = DriverManager.getConnection(HostName,UserName,password);
			// here con wants to return whoever want they can use that
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
		
	}

}
