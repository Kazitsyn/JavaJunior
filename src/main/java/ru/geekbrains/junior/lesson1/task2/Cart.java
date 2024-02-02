package ru.geekbrains.junior.lesson1.task2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Корзина
 * @param <T> Еда
 */
public class Cart<T extends Food> {

    //region Поля

    /**
     * Товары в магазине
     */
    private final ArrayList<T> foodstuffs;
    private final UMarket market;
    private final Class<T> clazz;

    //endregion

    //region Конструкторы

    /**
     * Создание нового экземпляра корзины
     * @param market принадлежность к магазину
     */
    public Cart(Class<T> clazz, UMarket market)
    {
        this.clazz = clazz;
        this.market = market;
        foodstuffs = new ArrayList<>();
    }

    //endregion

    /**
     * Балансировка корзины
     */
    public void cardBalancing()
    {
        if (this.foodstuffs.stream().anyMatch(Food::getProteins) &&
                this.foodstuffs.stream().anyMatch(Food::getFats) &&
                this.foodstuffs.stream().anyMatch(Food::getCarbohydrates)) {
            System.out.println("Корзина уже сбалансирована по БЖУ.");
        } else {
            if (this.foodstuffs.stream().noneMatch(Food::getProteins)) {
                this.market.getThings(this.clazz)
                        .stream()
                        .filter(Food::getProteins)
                        .findAny()
                        .map(foodstuffs::add);
            }

            if (this.foodstuffs.stream().noneMatch(Food::getFats)) {
                this.market.getThings(this.clazz)
                        .stream()
                        .filter(Food::getFats)
                        .findAny()
                        .map(foodstuffs::add);
            }

            if (this.foodstuffs.stream().noneMatch(Food::getCarbohydrates)) {
                this.market.getThings(this.clazz)
                        .stream()
                        .filter(Food::getCarbohydrates)
                        .findAny()
                        .map(foodstuffs::add);
            }

            if (this.foodstuffs.stream().anyMatch(Food::getProteins) &&
                    this.foodstuffs.stream().anyMatch(Food::getFats) &&
                    this.foodstuffs.stream().anyMatch(Food::getCarbohydrates)) {
                System.out.println("Корзина сбалансирована по БЖУ.");
            } else {
                System.out.println("Невозможно сбалансировать корзину по БЖУ.");
            }

        }
    }

    public Collection<T> getFoodstuffs() {
        return foodstuffs;
    }

    /**
     * Распечатать список продуктов в корзине
     */
    public void printFoodstuffs(){
        /*int index = 1;
        for (var food : foodstuffs)
            System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n", index++, food.getName(), food.getProteins() ? "Да" : "Нет",
                    food.getFats() ? "Да" : "Нет", food.getCarbohydrates() ? "Да" : "Нет");
         */
        AtomicInteger index = new AtomicInteger(1);
        foodstuffs.forEach(food -> System.out.printf("[%d] %s (Белки: %s Жиры: %s Углеводы: %s)\n",
                index.getAndIncrement(), food.getName(),
                food.getProteins() ? "Да" : "Нет",
                food.getFats() ? "Да" : "Нет",
                food.getCarbohydrates() ? "Да" : "Нет"));

    }

}
