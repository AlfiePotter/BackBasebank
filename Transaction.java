public class Transaction {

	String accountID;
	String accountType;
	String initiatorType;
	String dateTime;
	double transactionValue;

	public Transaction( String accountID, String accountType, String initiatorType, String dateTime, double transactionValue){
		this.accountID = accountID;
		this.accountType = accountType;
		this.initiatorType = initiatorType;
		this.dateTime = dateTime;
		this.transactionValue = transactionValue;

	}

	public String getAccountID(){
		return accountID;
	}

	public String getAccountType(){
		return accountType;
	}

	public String getInitiatorType(){
		return initiatorType;
	}

	public String getDateTime(){
		return dateTime;
	}

	public double getTransactionValue(){
		return transactionValue;
	}
}