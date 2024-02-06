package ru.geekbrains.junior.lesson2.homework2;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class App {
    public static void main(String[] args) throws ClassNotFoundException,
            InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        Class<?> dog = Class.forName("ru.geekbrains.junior.lesson2.homework2.Dog");
        Class<?> animal = Class.forName("ru.geekbrains.junior.lesson2.homework2.Animal");
        Class<?> cat = Class.forName("ru.geekbrains.junior.lesson2.homework2.Cat");
        Constructor[] constructorsDog = dog.getConstructors();
        Constructor[] constructorsCat = cat.getConstructors();
        Object[] animals = new Object[]{constructorsDog[0].newInstance(null),
                                        constructorsCat[0].newInstance(null)};
        Method makeSound = animal.getDeclaredMethod("makeSound");
        Method displayInfoMethod = animal.getDeclaredMethod("displayInfo");
        Arrays.stream(animals).forEach(a -> {
            try {
                makeSound.invoke(a);
                displayInfoMethod.invoke(a);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
