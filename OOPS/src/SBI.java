public class SBI  extends  Bank{
    private double balance =0;
    @Override
    void withdraw(double amount) {
       if(amount <= balance){
           balance -= amount;
       }else{
           System.out.println("Not enough balance in account");
       }
    }

    @Override
    void deposit(double amount) {
      balance += amount;
    }

    @Override
    public double checkBalance() {
        return balance;
    }
}
