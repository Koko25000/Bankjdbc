package bank;

import java.sql.*;

import java.util.Scanner;
public class Accounts {
	
	private Connection connection;
	private Scanner scanner;
	
	
	public Accounts(Connection connection,Scanner scanner)
	{
		this.connection = connection;
		this.scanner =scanner;
	}
	
	
	public long open_account(String email)
	{//if account deos not exist then open account with valid
		if(!account_exist(email))
		{
			String open_account_query ="insert into Accounts(account_number,full_name,email,balance,security_pin) values(?,?,?,?,?)";
			scanner.nextLine();
			System.out.println("enter full name ");
			String full_name= scanner.nextLine();
			System.out.println("Enter initial Amount: ");
			double balance=scanner.nextDouble();
			scanner.nextLine();
			System.out.println("Enter Security Pin: ");
			String security_pin =scanner.nextLine();
			
			try
			{
				long account_number =generateAccountNumber();
				PreparedStatement ps =connection.prepareStatement(open_account_query);
			    ps.setLong(1,account_number);
			    ps.setString(2,full_name);
			    ps.setString(3, email);
                ps.setDouble(4, balance);
                ps.setString(5, security_pin);
                int rowsaffected = ps.executeUpdate();
                if(rowsaffected>0)
                {
                	return account_number;
                }
                else
                {
                	throw new RuntimeException("Account creation failed");
                }
			}
			catch(SQLException e)
			{
               e.printStackTrace();
			}
		}
		throw new RuntimeException("Account Already exist");
	}
     
	
	public long getAccount_number(String email)
	{
		//searching some account number for performing certain transcation or using account no as primary for record
		//so on the basis of email we will be finding the uaccount number as email is unique
		String query="select account_number from Accounts where email =? ";
		try
		{
			PreparedStatement ps=connection.prepareStatement(query);
			ps.setString(1,email);
			ResultSet rs=ps.executeQuery();
			if(rs.next())
			{
				//if found the email then get corresponding accountnumber
				return rs.getLong("account_number");
			}
			
		 }
		 catch(SQLException e)
		 {
			e.printStackTrace();
		 }
	    
	throw new RuntimeException("account umber does not exist!");
   }
	
	
   private long generateAccountNumber()
   {
	   try {
	        Statement statement = connection.createStatement();
	        ResultSet resultSet = statement.executeQuery("SELECT MAX(account_number) AS max_account_number FROM Accounts");

	        if (resultSet.next()) {
	            long lastAccountNumber = resultSet.getLong("max_account_number");
	            // Increment the last account number by 1 to get the next available account number
	            return lastAccountNumber + 1;
	        } else {
	            // If no records exist, return a default starting account number
	            return 10000100;
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return 10000100;
   }
	
   public boolean account_exist(String email)
   {
	   //if email exist then only that account exist
	   String query="select account_number from Accounts Where email =?";
	   try
	   {
		   PreparedStatement preparedStatement = connection.prepareStatement(query);
           preparedStatement.setString(1, email);
           ResultSet resultSet = preparedStatement.executeQuery();
           if(resultSet.next()){
               return true;
           }else{
               return false;
           }
		   
	   }
	   catch(SQLException e)
	   {
		   e.printStackTrace();
	   }
	   return false;
   }
  
}
