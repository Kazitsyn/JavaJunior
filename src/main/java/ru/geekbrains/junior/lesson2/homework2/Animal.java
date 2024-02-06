package ru.geekbrains.junior.lesson2.homework2;

public abstract class Animal {
    private String name;
    private int age;

    public Animal() {
        this.name = "Test";
        this.age = 2;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
    public void makeSound(){
        System.out.println("Ror!");
    }

    public void displayInfo(){
        System.out.printf("Name: %s; Age: %d\n%n", name, age);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
