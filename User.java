package bank;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class User {
	
	private Connection connection;  //basically getting it from bank class inside constructor as user input
	private Scanner scanner;
	
    //constructor taking user values from bank class
	public User(Connection connection,Scanner scanner)
	{
		this.connection=connection;
		this.scanner=scanner;
	}
	
	public void register()
	{
		scanner.nextLine();
		System.out.println("Full NAME: ");
		String full_name=scanner.nextLine();
		System.out.println("Email: ");
		String email=scanner.nextLine();
		System.out.println("Password: ");
		String password =scanner.nextLine();
		if(user_exist(email))  //i have set the email as unique in sql user table
		{
			System.out.println("User already exists for this email Account!! ");
			return;
		}
		String register_query ="insert into User(full_name,email,password) VALUES(?,?,?)";
		try
		{
			PreparedStatement preparedStatement=connection.prepareStatement(register_query);
			preparedStatement.setString(1,full_name);
			preparedStatement.setString(2,email);
			preparedStatement.setString(3,password);
			int rowsaffected=preparedStatement.executeUpdate();
			if(rowsaffected>0)
			{
				System.out.println("Registration successfull!");
			}
			else
			{
				System.out.println("Registration failed");
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	
	public String login()
	{
		    scanner.nextLine();
	        System.out.print("Email: ");
	        String email = scanner.nextLine();
	        System.out.print("Password: ");
	        String password = scanner.nextLine();
	        String login_query = "SELECT * FROM User WHERE email = ? AND password = ?";
	        try{
	            PreparedStatement preparedStatement = connection.prepareStatement(login_query);
	            preparedStatement.setString(1, email);
	            preparedStatement.setString(2, password);
	            ResultSet resultSet = preparedStatement.executeQuery();
	            if(resultSet.next()){
	                return email;
	            }else{
	                return null;
	            }
	        }catch (SQLException e){
	            e.printStackTrace();
	        }
	        return null;
	}
	
	public boolean user_exist(String email)
	{
		String query="Select * from user Where email=? ";
	    try
	    {
	    	PreparedStatement ps=connection.prepareStatement(query);
	    	ps.setString(1, email);
	    	ResultSet rs=ps.executeQuery();
	    	if(rs.next())
	    	{
	    		return true;
	    	}
	    	else
	    	{
	    		return false;
	    	}
	    }
	    catch(SQLException e )
	    {
	    	e.printStackTrace();
	    }
	    return false;
	}
}
