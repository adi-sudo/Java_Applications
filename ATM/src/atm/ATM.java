package atm;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		
		//init Scanner
		Scanner sc=new Scanner(System.in);
		
		//init Bank
		Bank theBank=new Bank("Bank of Aditya");
		
		//add a user,which also creates a savings account
		User aUser=theBank.addUser("Aditya", "Gupta", "1234");
		
		//add a checking account for our user
		Account newAccount =new Account("Checking",aUser,theBank);
		aUser.addAccount(newAccount);
		theBank.addAccount(newAccount);
		
		User curUser;
		while(true)	{
			//stay in login prompt until successful login
			curUser=ATM.mainMenuPrompt(theBank,sc);
			
			//stay in main menu until user quit
			ATM.printUserMenu(curUser,sc);
		}

	}
	
	/**
	 * Print the ATM's login menu
	 * @param theBank the bank object whose accounts to use
	 * @param sc the Scanner object to use for user input
	 * @return the authenticated User object
	 */
	public static User mainMenuPrompt(Bank theBank,Scanner sc)	{
		
		//inits
		String userID;
		String pin;
		User authUser;
		
		//prompt the user IO/pin combo until a correct one is reached
		do	{
			System.out.printf("\n\nWelcome to %s\n\n",theBank.getName());
			System.out.print("Enter the user ID: ");
			userID=sc.nextLine();
			System.out.print("Enter the pin: ");
			pin=sc.nextLine();
			
			//try to get the user object corresponding to the ID and pin combo
			authUser=theBank.userLogin(userID, pin);
			if(authUser==null)	{
				System.out.println("Incorrect user I/O pin combination "+" Please try Again.");
				
			}
		} while(authUser==null);// continue looping until successful login
		
		return authUser;
	}
	
	public static void printUserMenu(User theUser,Scanner sc)	{
		
		//print a summary of the use's accounts
		theUser.printAccountSummary();
		
		//init 
		int choice;
		
		//user menu
		do	{
			System.out.printf("Welcome %s,what would you like to do?\n",theUser.getFirstName());
			System.out.println(" 1) Show account transaction history");
			System.out.println(" 2) Widthdraw");
			System.out.println(" 3) Deposit");
			System.out.println(" 4) Transfer");
			System.out.println(" 5) Quit");
			System.out.println();
			System.out.println("Enter choice: ");
			choice=sc.nextInt();
			
			if(choice<1||choice>5)	{
				System.out.println("Invalid choice.Please chose 1-5");
			}
		}while(choice<1||choice>5);
		
		//process the choice
		switch(choice)	{
		case 1:
			ATM.showTransferHistory(theUser,sc);
			break;
		case 2:
			ATM.withdrawFunds(theUser,sc);
			break;
		case 3:
			ATM.depositFunds(theUser,sc);
			break;
		case 4:
			ATM.transferFunds(theUser,sc);
			break;
		case 5:
			//gobble up rest of previous input
			sc.nextLine();
			break;
		}
		
		//redisplay this menu unless the user wants to quit
		if(choice!=5)
			ATM.printUserMenu(theUser, sc);
}
	

/**
 * Show the transaction history for an account
 * @param theUser the logged-in user object
 * @param sc the scanner object used for user input
 */
public static void showTransferHistory(User theUser,Scanner sc)	{
	
	int theAcct;
	
	//get account whose transaction history to look at
	do	{
		System.out.printf("Enter the number (1-%d) of the account\n"+ 
							"whose transactions you want to see",theUser.numAccounts());
		theAcct=sc.nextInt()-1;
		if(theAcct<0||theAcct>=theUser.numAccounts())	{
			System.out.println("Invalid account.Please try again");
		}
	}while(theAcct<0||theAcct>=theUser.numAccounts());
	
	//print the transaction history
	theUser.printAcctTransHistory(theAcct);
	
}

/**
 * Process transferring funds from one account to another
 * @param theUser the logged-in User object
 * @param sc the Scanner object used for user input
 */
public static void transferFunds(User theUser,Scanner sc)	{
	
	//inits
	int fromAcct;
	int toAcct;
	double amount;
	double acctBal;
	
	//get the account to transfer from
	
	do {
		System.out.printf("Enter the number (1-%d) of the account\n to transfer from: ",theUser.numAccounts());
		fromAcct=sc.nextInt()-1;
		if(fromAcct<0||fromAcct>=theUser.numAccounts())	{
			System.out.println("Invalid account.Please try again");
			
		}
	}while(fromAcct<0||fromAcct>=theUser.numAccounts());
	acctBal=theUser.getAcctBalance(fromAcct);
	
	//get the account to transfer to 
	do {
		System.out.printf("Enter the number (1-%d) of the account\n to transfer to: ",theUser.numAccounts());
		toAcct=sc.nextInt()-1;
		if(toAcct<0||toAcct>=theUser.numAccounts())	{
			System.out.println("Invalid account.Please try again");
			
		}
	}while(toAcct<0||toAcct>=theUser.numAccounts());
	
	//get the amount to transfer
	do	{
		System.out.printf("Enter the amount to transfer (max $%.02f): $",acctBal);
		amount=sc.nextDouble();
		if(amount<0)	{
			System.out.println("Amount must be gerater than zero");
		} else if(amount>acctBal)	{
			System.out.printf("Amount must not be grater than\n balance of $%.02f.\n",acctBal);
		}
	} while(amount<0|| amount >acctBal);
	
	
	//finally,do the transfer
	theUser.addAcctTransaction(fromAcct,-1*amount,String.format("Transfer to account %s",theUser.getAcctUUID(toAcct)));
	
	theUser.addAcctTransaction(toAcct,+1*amount,String.format("Transfer to account %s",theUser.getAcctUUID(fromAcct)));
}

/**
 * Process a fund withraw from an account
 * @param theUser the logged-in User object
 * @param sc the Scanner object user for user input
 */
public static void withdrawFunds(User theUser,Scanner sc)	{
	
	//inits
		int fromAcct;
		double amount;
		double acctBal;
		String memo;
		
		//get the account to transfer from
		
		do {
			System.out.printf("Enter the number (1-%d) of the account\n to withdraw from: ",theUser.numAccounts());
			fromAcct=sc.nextInt()-1;
			if(fromAcct<0||fromAcct>=theUser.numAccounts())	{
				System.out.println("Invalid account.Please try again");
				
			}
		}while(fromAcct<0||fromAcct>=theUser.numAccounts());
		acctBal=theUser.getAcctBalance(fromAcct);
		
		
		
		//get the amount to transfer
		do	{
			System.out.printf("Enter the amount to withdraw (max $%.02f): $",acctBal);
			amount=sc.nextDouble();
			if(amount<0)	{
				System.out.println("Amount must be gerater than zero");
				
				
			} else if(amount>acctBal)	{
				System.out.printf("Amount must not be grater than\n balance of $%.02f.\n",acctBal);
				
			}
		} while(amount<0|| amount >acctBal);
		
		//gobble up rest of previous input
		sc.nextLine();
		
		//get a memo
		System.out.print("Enter a memo:");
		memo =sc.nextLine();
		
		//do the withdraw
		theUser.addAcctTransaction(fromAcct, -1*amount, memo);
		
}

public static void depositFunds(User theUser,Scanner sc)	{

	//inits
			int toAcct;
			double amount;
			double acctBal;
			String memo;
			
			//get the account to transfer from
			
			do {
				System.out.printf("Enter the number (1-%d) of the account\n to deposit in: ",theUser.numAccounts());
				toAcct=sc.nextInt()-1;
				if(toAcct<0||toAcct>=theUser.numAccounts())	{
					System.out.println("Invalid account.Please try again");
					
				}
			}while(toAcct<0||toAcct>=theUser.numAccounts());
			acctBal=theUser.getAcctBalance(toAcct);
			
			//get the amount to transfer
			do	{
				System.out.printf("Enter the amount to deposit (max $%.02f): $",acctBal);
				amount=sc.nextDouble();
				if(amount<0)	{
					System.out.println("Amount must be gerater than zero");
				} 
			} while(amount<0);
			
			//gobble up rest of previous input
			sc.nextLine();
			
			//get a memo
			System.out.print("Enter a memo:");
			memo =sc.nextLine();
			
			//do the deposit
			theUser.addAcctTransaction(toAcct, amount, memo);
}
}
