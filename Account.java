public class Account {

	double balance;

	public Account(){
		balance = 0.0;

	}

	public double getBalance(){
		return balance;
	}

	public void withdraw(double value){
		balance = balance + value; //Plus because negative in filesave, so to actually withdraw you need to add the negative
	}

	public void withdrawpositive(double value){
		balance = balance - value; //This withdraw method is for the purpose of taking away a postive value
	}

	public void deposit(double value){
		balance = balance + value;
	}
}