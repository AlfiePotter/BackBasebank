import java.io.*;
import java.util.*;
import java.lang.Math;


public class BankMain {

	private static String file = "";
	private static List<Transaction> transactions = new ArrayList<Transaction>();
	private static List<Transaction> newTransactions = new ArrayList<Transaction>();
	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main( String[] ans ) throws Exception{

		System.out.println("Enter file name");
		String file = br.readLine();
		file = file +".csv"; // Adds the .csv extension to the file name inputed

		loadTransactions(file);

		Account currentAccount = new Account(); // Creating the two types of accounts 
		Account savingsAccount = new Account();
		Transaction newTransaction;

		for(Transaction currentTransaction : transactions){

			String transactionAccount = currentTransaction.getAccountType();
			double transactionValue = currentTransaction.getTransactionValue();

			// For when they are despositing
			if(transactionValue > 0){
				if(transactionAccount.contains("CURRENT"))
					currentAccount.deposit(transactionValue);
				else if( transactionAccount.contains("SAVINGS"))
					savingsAccount.deposit(transactionValue);

				newTransactions.add(currentTransaction);
			}

			// For when they are withdrawing
			if(transactionValue < 0){
				if(transactionAccount.contains("CURRENT")){

					currentAccount.withdraw(transactionValue);
					newTransactions.add(new Transaction(currentTransaction.getAccountID(), "CURRENT", currentTransaction.getInitiatorType(), currentTransaction.getDateTime(), transactionValue));
				}
				if(transactionAccount.contains("SAVINGS")){
					if(savingsAccount.getBalance() >= transactionValue){
						savingsAccount.withdraw(transactionValue);
						newTransactions.add(new Transaction(currentTransaction.getAccountID(), "SAVINGS", currentTransaction.getInitiatorType(), currentTransaction.getDateTime(), transactionValue));
					}
					else
						System.out.println("Transaction failed no funds");
				}
				if(currentAccount.getBalance() < 0){ //To instantly reduce the overdraft to avoid fees
					double overdraft = currentAccount.getBalance();
					if(Math.abs(overdraft) <= savingsAccount.getBalance()){
						double roundedvalue = Math.round(overdraft * 100.0) / 100.0; // To round the value for display in transactions
						savingsAccount.withdraw(overdraft);
						newTransactions.add(new Transaction(currentTransaction.getAccountID(), "SAVINGS", "SYSTEM", currentTransaction.getDateTime(), roundedvalue));
						currentAccount.deposit(Math.abs(overdraft));
						newTransactions.add(new Transaction(currentTransaction.getAccountID(), "CURRENT", "SYSTEM", currentTransaction.getDateTime(), Math.abs(roundedvalue)));
					}else{
						double savings = savingsAccount.getBalance(); //Allocating the remaining money in the savings account, so it can be deducted
						savingsAccount.withdrawpositive(savings);
						double displaysavings = savings - (savings*2); //To making the remainings savings a negative number for the output
						double roundedvalue2 = Math.round(displaysavings * 100.0) / 100.0;// To round the value for display in transactions
						double roundedvalue3 = Math.round(savings * 100.0) / 100.0;
						newTransactions.add(new Transaction(currentTransaction.getAccountID(), "SAVINGS", "SYSTEM", currentTransaction.getDateTime(), roundedvalue2));
						currentAccount.deposit(savings);
						newTransactions.add(new Transaction(currentTransaction.getAccountID(), "CURRENT", "SYSTEM", currentTransaction.getDateTime(), roundedvalue3));
					}
				}
			}
		}

		System.out.println("Enter file name for export");
		file = br.readLine();
		file = file +".csv";
		exportTransaction(file);
	}
	public static void exportTransaction(String file){
		String details = "AccountID,AccountType,InitiatorType,DateTime,TransactionValue"; // The column names
		for(Transaction i : newTransactions){
			details = i.getAccountID() +","+ i.getAccountType() +","+ i.getInitiatorType() +","+ i.getDateTime() +","+ i.getTransactionValue();
			addToFile(file, details);
		}
	}

	public static void addToFile(String file, String input){
		try {
			//output the details string to a file
			FileWriter write = new FileWriter(file, true);
			PrintWriter out = new PrintWriter(write);
			out.println(input);
			out.close();
 		} catch(IOException e){
				//throws an error if the file cannot be written too
			e.printStackTrace();
		}
	}


	public static void loadTransactions(String file){
		try{
			Scanner readFile = new Scanner(new File(file));
			boolean flag = false;
			while(readFile.hasNextLine()) {
				//Splits the current transaction into a commar seperated list

				//initalises a new transaction with values from the text file
				if(flag == true){
					String[] currentTransaction = readFile.nextLine().split(",");
					Transaction t = new Transaction(currentTransaction[0], currentTransaction[1], currentTransaction[2], currentTransaction[3], Double.parseDouble(currentTransaction[4]));
					transactions.add(t);
				}else{
					flag = true;
					readFile.nextLine();
				}
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}