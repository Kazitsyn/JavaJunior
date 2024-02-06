package ru.geekbrains.junior.lesson2.task1;

public class Person {

    private String name;
    private int age;

    public Person() {
        name = "Name";
        age = 25;
    }

    public void displayInfo(){
        System.out.printf("Name: %s; Age: %d\n%n", name, age);
    }

}
