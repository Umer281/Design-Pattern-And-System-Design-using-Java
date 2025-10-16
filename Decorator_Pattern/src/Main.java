public class Main {
    public static void main(String[] args) {
        Pizza margerattta = new BasePizza();
        System.out.println(margerattta.getDescription());
        System.out.println(margerattta.getCost());
        margerattta = new CheezDecorator(margerattta);
        System.out.println(margerattta.getCost());
        margerattta = new CornDecorator(margerattta);
        margerattta = new PepproniDecorator(margerattta);
        System.out.println(margerattta.getCost());

//        BankAccount bankAccount = new BankAccount(1000.0);
//
//
//
//        bankAccount.withDraw(200);
//        bankAccount.deposit(600);
//
//
//        double bal = bankAccount.getBalance();
//
//        System.out.println(bal);
        Bank myAccount = new SBI();
        myAccount.deposit(20000);
        myAccount.withdraw(5000);
        System.out.println(myAccount.checkBalance());



    }
}