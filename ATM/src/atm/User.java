package atm;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {
	/**
	 * The first name of the user
	 */
	private String firstName;
	
	/**
	 * The last name of user
	 */
	private String lastName;
	
	/**
	 * The Id number of user
	 */
	private String uuid;
	
	/**
	 * The MD5 hash of the user's pin number
	 */
	private byte pinHash[];
	
	/**
	 * The list of accounts for this user
	 */
	private ArrayList<Account> account;
	
	/**
	 * Create a new user
	 * @param firstName the user's first name
	 * @param lastName the user's last name
	 * @param pin the user's account pin number
	 * @param theBank the Bank object that user is a customer of 
	 */
	
	public User(String firstName,String lastName,String pin,Bank theBank)	{
		
		//set user's name
		this.firstName=firstName;
		this.lastName=lastName;
		
		//store the pin's MD5 hash
		try {
			MessageDigest md=MessageDigest.getInstance("MD5");
			this.pinHash=md.digest(pin.getBytes());
	
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//get a new,unique universal ID for the User
		this.uuid=theBank.getNewUserUUID();
		
		//create empty list of accounts
		this.account=new ArrayList<Account>();
		
		//print log message
		System.out.printf("New user %s , %s with ID %s created.\n",lastName,firstName,this.uuid);
	}
	
	/**
	 * Add an account for the user
	 * @param anAcct the account to add
	 */
public void addAccount(Account anAcct)	{
	this.account.add(anAcct);	
	} 

/**
 * Return the user's UUID
 * @return
 */
public String getUUID() {
	return this.uuid;
}

/**
 * Check whether a given pin matches the true User pin
 * @param aPin the pin to check
 * @return whether the pin is valid or not
 */
public boolean validatePin(String aPin)	{
	
	try {
		MessageDigest md=MessageDigest.getInstance("MD5");
		return MessageDigest.isEqual(md.digest(aPin.getBytes()), this.pinHash);
	} catch (NoSuchAlgorithmException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return false;
}

/**
 * Return the user's first name
 * @return the first name
 */
public String getFirstName()	{
	return this.firstName;
}

public void printAccountSummary()	{
	
	System.out.printf("\n\n%s's accounts summary\n",this.firstName);
	for(int a=0;a<this.account.size();a++)	{
		System.out.printf("(%d) %s\n",a+1,this.account.get(a).getSummaryLine());
	}
	System.out.println();
}

/**
 * Get the number of account of the user
 * @return the number of accounts
 */
public int numAccounts()	{
	return this.account.size();
}

/**
 * Print transaction history for a particular account
 * @param accIdx the index of the account to use
 */
public void printAcctTransHistory(int accIdx)	{
	this.account.get(accIdx).printTransHistory();
}

/**
 * Get the balance of particular account 
 * @param acctIdx the index of the account used
 * @return the balance of the account
 */
public double getAcctBalance(int acctIdx)	{
	return this.account.get(acctIdx).getBalance();
}

/**
 * Get the UUID of a particular account
 * @param acctIdx the index of the account to use
 * @return the UUID of the account
 */
public String getAcctUUID(int acctIdx)	{
	return this.account.get(acctIdx).getUUID();
}

/**
 * Add a transaction to a particular account
 * @param acctIdx the index of the account
 * @param amount the amount of the transaction
 * @param memo the memo of the transaction 
 */
public void addAcctTransaction(int acctIdx,double amount,String memo)	{
	this.account.get(acctIdx).addTransaction(amount,memo);
}
}
