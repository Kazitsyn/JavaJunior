package ru.geekbrains.junior.lesson2.homework2;

public class Cat extends Animal{
    private int lives;
    public Cat() {
        lives = 9;
        setAge(1);
        setName("Kitty");
    }
    public void dead(){
        if (lives > 0){
            lives--;
        }else {
            System.out.println("killed");
        }

    }

    public int getLives() {
        return lives;
    }
    @Override
    public void makeSound(){
        System.out.println("Mau!");
    }
}
