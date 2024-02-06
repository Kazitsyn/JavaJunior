package ru.geekbrains.junior.lesson2.task2;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class QueryBuilder {

    /**
     * Построить запрос на добавление данных в БД
     *
     * @param obj
     * @return
     */
    public String buildInsertQuery(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Table.class)) {
            return "";
        }

        StringBuilder query = new StringBuilder("INSERT INTO ");
        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query
                .append(tableAnnotation.name())
                .append(" (");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                query
                        .append(columnAnnotation.name())
                        .append(", ");
            }
        }

        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
        }
        query.append(") VALUES (");

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);

                //Class<?> fieldType = field.getType();
                //String n = fieldType.getSimpleName();
                query.append("'").append(field.get(obj)).append("', ");
            }
        }

        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
        }

        query.append(")");

        return query.toString();
    }

    /**
     * Построить запрос на получение данных из БД
     *
     * @param clazz
     * @param primaryKey
     * @return
     */
    public String buildSelectQuery(Class<?> clazz, UUID primaryKey) {

        if (!clazz.isAnnotationPresent(Table.class)) {
            return "";
        }

        StringBuilder query = new StringBuilder("SELECT * FROM ");

        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query.append(tableAnnotation.name()).append(" WHERE ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey()) {
                    query.append(columnAnnotation.name())
                            .append(" = ")
                            .append("'")
                            .append(primaryKey)
                            .append("'");
                    break;
                }
            }
        }

        return query.toString();
    }

    /**
     * Построить запрос на удаление данных из бд
     *
     * @param obj
     * @return
     */
    public String buildUpdateQuery(Object obj) {
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Table.class)) {
            return "";
        }

        StringBuilder query = new StringBuilder("UPDATE ");


        Table tableAnnotation = clazz.getAnnotation(Table.class);
        query.append(tableAnnotation.name()).append(" SET ");

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                field.setAccessible(true);

                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey())
                    continue;
                try {
                    query.append(columnAnnotation.name()).append(" = '").append(field.get(obj)).append("', ");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
        }

        query.append(" WHERE ");

        for (Field field : fields) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (columnAnnotation.primaryKey()) {
                    try {
                        query.append(columnAnnotation.name()).append(" = '").append(field.get(obj)).append("'");
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }

        return query.toString();
    }

    /**
     * Метод генерации SQL запроса на удаление
     * @return
     */
    public String buildDeleteQuery(Class<?> clazz, UUID primaryKey){
        if (!clazz.isAnnotationPresent(Table.class)) return "";
        StringBuilder query = new StringBuilder("DELETE FROM ")
            .append(clazz.getAnnotation(Table.class).name()).append(" WHERE ");
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .filter(field -> field.getAnnotation(Column.class).primaryKey())
                .forEach(field -> query.append(field.getName()).append(" = '").append(primaryKey).append("'"));
        return query.toString();
    }

    /**
     * Метод генерации SQL запроса на удаление
     *
     * @param clazz класс
     * @param fieldValue строковое значение
     *                   Метод обрабатывает строковое значение и определяет email или name
     *
     * @return
     */
    public String buildDeleteQuery(Class<?> clazz, String fieldValue)
    {
        if (!clazz.isAnnotationPresent(Table.class)) return "";
        StringBuilder query = new StringBuilder("DELETE FROM ")
                .append(clazz.getAnnotation(Table.class).name()).append(" WHERE ");
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Column.class)) {
                Column columnAnnotation = field.getAnnotation(Column.class);
                if (fieldValue.contains("@") && columnAnnotation.emailKey()) {
                    query.append(columnAnnotation.name())
                            .append(" = '")
                            .append(fieldValue)
                            .append("'");
                    break;
                } else if (columnAnnotation.primaryKey()) {
                    continue;
                }
                else if (!fieldValue.contains("@")){
                    query.append(columnAnnotation.name())
                            .append(" = '")
                            .append(fieldValue)
                            .append("'");
                    break;
                }
            }
        }
        return query.toString();
    }


    public String buildDeleteQuery(Object obj){
        Class<?> clazz = obj.getClass();
        if (!clazz.isAnnotationPresent(Table.class)) return "";
        StringBuilder query = new StringBuilder("DELETE FROM ")
                .append(clazz.getAnnotation(Table.class).name()).append(" WHERE ");
        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class))
                .forEach(field -> {
                    field.setAccessible(true);
                    Column columnAnnotation = field.getAnnotation(Column.class);
                    try {
                        query.append(columnAnnotation.name()).append(" = '").append(field.get(obj)).append("', ");
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
        if (query.charAt(query.length() - 2) == ',') {
            query.delete(query.length() - 2, query.length());
        }
        return query.toString();
    }
}
