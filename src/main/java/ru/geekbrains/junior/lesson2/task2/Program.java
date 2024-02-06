package ru.geekbrains.junior.lesson2.task2;

import java.util.UUID;

public class Program {

    /*
    Задача 2: Реализовать простой фреймворк для создания SQL-запросов на основе Java объектов

    Фреймворк должен позволять аннотировать классы и поля для связывания их
    с таблицами и столбцами в базе данных.

    1. Аннотации для маппинга:
        Создайте аннотации, такие как @Table, @Column для маппинга классов,
        таблиц и полей в базе данных.

    2. Механизм генерации SQL-запросов:
        Реализуйте класс QueryBuilder, который может принимать объект и генерировать
        SQL-запросы для выполнения операций CRUD (Create, Read, Update, Delete) на основе аннотаций.
        Используйте Reflection для получения метаданных класса,
        аннотированного объекта, чтобы построить соответствующий SQL-запрос.

    3. Пример использования:
        Создайте простой класс, аннотированный для маппинга с базой данных.
        Используйте ваш фреймворк для генерации SQL-запросов для различных операций,
        таких как вставка, выборка, обновление и удаление.
    */
    public static void main(String[] args) throws IllegalAccessException {
        Employee user = new Employee("Sergey", "sample@gmail.com");

        QueryBuilder queryBuilder = new QueryBuilder();

        // Генерация SQL-запроса для вставки
        String insertQuery = queryBuilder.buildInsertQuery(user);
        System.out.println("Insert Query: " + insertQuery);

        // Генерация SQL-запроса для выборки
        UUID pk = UUID.randomUUID();
        String selectQuery = queryBuilder.buildSelectQuery(Employee.class, pk);
        System.out.println("Select Query: " + selectQuery);

        // Генерация SQL-запроса для обновления
        String updateQuery = queryBuilder.buildUpdateQuery(user);
        System.out.println("Update Query: " + updateQuery);

        // Генерация SQL-запроса для удаления
//        UUID pk = UUID.randomUUID();
        String deleteQuery = queryBuilder.buildDeleteQuery(Employee.class, pk);
        System.out.println("Delete Query: " + deleteQuery);

        String deleteQuery1 = queryBuilder.buildDeleteQuery(Employee.class, "Sergey");
        System.out.println("Delete Query: " + deleteQuery1);
        String deleteQuery2 = queryBuilder.buildDeleteQuery(Employee.class, "sample@gmail.com");
        System.out.println("Delete Query: " + deleteQuery2);
        String deleteQuery3 = queryBuilder.buildDeleteQuery(user);
        System.out.println("Delete Query: " + deleteQuery3);
    }

}
