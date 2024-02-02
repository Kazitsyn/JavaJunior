## Домашняя работа 1

```Java
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
```
***
Напишите программу, которая использует Stream API для обработки списка чисел. Программа должна вывести на экран среднее значение всех четных чисел в списке.

```Java
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
```