package ru.geekbrains.junior.lesson2.homework2;

public class Dog extends Animal{
private boolean inPackOfDogs;

    public Dog() {
        inPackOfDogs = false;
        setAge(3);
        setName("Bobik");
    }

    public boolean isInPackOfDogs() {
        return inPackOfDogs;
    }

    public void setInPackOfDogs(boolean inPackOfDogs) {
        this.inPackOfDogs = inPackOfDogs;
    }
    @Override
    public void makeSound(){
        System.out.println("Wof!");
    }
}
