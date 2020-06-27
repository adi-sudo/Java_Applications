package bank;

import java.util.Scanner;

/**
 * 
 * @author Aditya
 *
 */
public class BankAccount {
	int balance;
	int previousTransaction;
	String customerName;
	String customerId;
	
	/**
	 * Constructor for providing name and id of customer
	 * @param cname provides customer name
	 * @param cid provides customer id
	 */
	BankAccount(String cname,String cid){
		customerName=cname;
		customerId=cid;
		
	}
	
	/**
	 * To deposit funds  
	 * @param amount fund to deposit
	 */
	void deposit(int amount)	{
		if(amount!=0)	{
			balance =balance+amount;
			previousTransaction=amount;
		}
	}
	
	/**
	 * To withdraw funds
	 * @param amount fund to withdraw
	 */
	void withdraw(int amount)	{
		if(amount!=0)	{
			balance=balance-amount;
			previousTransaction=-amount;
		}
	}
	
	/**
	 * The method provides transaction is successful or not
	 */
	void getPreviousTransaction() {
		if(previousTransaction>0)
			System.out.println("Deposited: "+previousTransaction);
		else if(previousTransaction<0)
			System.out.println("Withdrawn: "+Math.abs(previousTransaction));
		else
			System.out.println("No transaction occured");
	}
	
	/**
	 * Shows the menu and provides Options
	 */
	void showMenu()	{
		
		char option='\0';
		Scanner sc=new Scanner(System.in);
		
		System.out.println("Welcome : "+customerName);
		System.out.println("Id is : "+customerId+'\n');
		System.out.println("A. Check Balance");
		System.out.println("B. Deposit");
		System.out.println("C. Widthdraw");
		System.out.println("D. Previous Transaction");
		System.out.println("E. Exit");
		
		do	{
			System.out.println("=====================");
			System.out.println("Enter an option ");
			System.out.println("=====================");
			option=sc.next().charAt(0);
			System.out.println();
			
			switch(option)	{
			case 'A':
				System.out.println("-----------------");
				System.out.println("Balance= "+balance);
				System.out.println("-----------------");
				break;
				
			case 'B':
				System.out.println("-----------------");
				System.out.println("Enter the amount to deposit");
				System.out.println("-----------------");
				int amount=sc.nextInt();
				deposit(amount);
				System.out.println("\n");
				break;
				
			case 'C':
				System.out.println("-----------------");
				System.out.println("Enter the amount to withdraw");
				System.out.println("-----------------");
				int amount2=sc.nextInt();
				withdraw(amount2);
				System.out.println("\n");
				break;
				
			case 'D':
				System.out.println("-----------------");
				getPreviousTransaction();
				System.out.println("-----------------");
				System.out.println("\n");
				break;
				
			case 'E':
				System.out.println("*****************");
				break;
				
				default:
					System.out.println("Invalid Option. Plaase enter Again");
					break;
				
			}
			
		}while(option!='E');
		
		System.out.println("Thankyou for using our services");
	}

}
