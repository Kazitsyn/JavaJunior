package ru.geekbrains.junior.lesson1.hw1;

import java.util.ArrayList;
import java.util.List;

public class hw1 {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>();
        for (int i = 1; i < 100; i++) integerList.add((int)(Math.random() * 10));

        System.out.println(integerList.stream()
                .filter(list -> list%2 == 0)
                .mapToInt(e -> e)
                .average()
                .getAsDouble());

    }
}
