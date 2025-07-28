package com.DesignPatterns;

public class User {

    public String name;

    public  User(String name, int age){
        this.name = name;
    }

    public void sayHello(){
        System.out.println("My name is " + name);
    }
}
