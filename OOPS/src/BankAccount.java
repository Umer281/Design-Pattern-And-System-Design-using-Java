public class BankAccount {

    private  double balance =0;

    public double  getBalance(){
        return balance;
    }

    public  BankAccount(double balance) {
        this.balance = balance;
    }

    public double withDraw(double amount){
          return  balance -= amount;
    }

    public void deposit(double amount){
         balance += amount;
    }
}



