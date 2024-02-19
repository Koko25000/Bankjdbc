package bank;
import java.util.Scanner;
import static java.lang.Class.forName;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
public class BankingApp {
     //url
	private static final String url="jdbc:mysql://localhost:3306/banking_system";
	
	private static final String username="root";
	
	private static final String password="root";
	//private secuirty point of out class any one should not use it and finalbecause database must not be changed


	
	
	public static void main(String args[])throws ClassNotFoundException,SQLException
	{
		try
		{
			Class.forName("com.sql.jdbc.Driver");
			
		}
		catch(ClassNotFoundException e)
		{
			System.out.println(e.getMessage());
		}
		
	   try
	  {
	  	//here objects are also made ad via constructor values are also passed
		Connection connection= DriverManager.getConnection(url,username,password);
		Scanner scanner=new Scanner(System.in);
		User user=new User(connection,scanner);
	    Accounts accounts=new Accounts(connection,scanner);
	    AccountManager accountManager=new AccountManager(connection,scanner);

	
	    String email;
	    long account_number;
	    
	    while(true)
	    {
	    	System.out.println("***WELCOME TO BANKING SYSTEM");
	    	System.out.println();
	    	System.out.println("1. Register");
	    	System.out.println("2. Login");
	    	System.out.println("3. Exit");
	    	System.out.println("enter your choice ");
	        int choice1= scanner.nextInt();
	        
	        switch(choice1)
	        {
	          //all methods related to userr class
	        case 1:
	        	user.register();
	            break;
	        case 2:
	        	//email is made unique  basic logiif account no and emailmatches
	        	email=user.login();
	        	if(email != null)
	        	{
	        		System.out.println();
	        		System.out.println("user Logged in !");
	        		if(!accounts.account_exist(email))
	        		{
	        			//if email deos not exist then give option either make new account or exit the banking app
	        			System.out.println();
	        			System.out.println("1.Open a new Bank Account");
	        			System.out.println("2. Exit");
	        			
	        		
	        		      if(scanner.nextInt()==1)
	        		      {
	        		    	   account_number = accounts.open_account(email);
	        		           System.out.println("account created successfully");   	  
	        		           System.out.println("Your Account Number is: " + account_number);
	        		      }
	        	 
	        		       else
	        		      {
	        			    //if account already exist then break
	        			     break;
	        		      }
	        		}
	        	
	             	//if account already exist then simply get the account number from accounts classs
	                account_number=accounts.getAccount_number(email);
	        	
	             	int choice2=0;
	        	   while(choice2 != 5)
	        	   {
	        		 System.out.println();
	        		 System.out.println("1. Debit Money");
	        		 System.out.println("2. Credit Money");
	        		 System.out.println("3. Transfer Money");
	        	     System.out.println("4. Check Balance");
	        		 System.out.println("5. Log Out");
	        		 System.out.println("Enter your choice: ");
	        		 choice2 =scanner.nextInt();
	        		 switch(choice2)
	        		 {
	        		 
	        		 case 1:
                         accountManager.debit_money(account_number);
                         break;
                     case 2:
                         accountManager.credit_money(account_number);
                         break;
                     case 3:
                         accountManager.transfer_money(account_number);
                         break;
                     case 4:
                         accountManager.getBalance(account_number);
                         break;
                     case 5:
                         break;
                     default:
                         System.out.println("Enter Valid Choice!");
                         break;
	        	
	        		 }
	        	   }
	        	}
	        	else
	        	{
	        		System.out.println("Incorrect Email or Password");
	        	}
	        case 3:
	        	System.out.println("THANK YOU FOR USING BANKING SYSTEM");
	        	System.out.println("Exiting System!");
	        	return;
	        default:
	        	System.out.println("Enter valid choice");
	        }//switch
	        
	        	
	    }//while
	}//try
	catch(SQLException e)
	{
	   e.printStackTrace();
	}
 }
}
