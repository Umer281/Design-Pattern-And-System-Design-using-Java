package com.DesignPatterns;

public class Main {
    public static void main(String[] args) {
       User user = new User("Umer Farooq", 10);
       user.sayHello();
       var account = new Account();
       account.setBalance(10);
       System.out.println(account.getBalance());
    }
}