# Java Junior
***
## Домашняя работа 2:  Reflection API
***
### Задача 1:
Создайте абстрактный класс "Animal" с полями "name" и "age".
Реализуйте два класса-наследника от "Animal" (например, 
"Dog" и "Cat") с уникальными полями и методами.
Создайте массив объектов типа "Animal" и с использованием 
Reflection API выполните следующие действия:
Выведите на экран информацию о каждом объекте.
Вызовите метод "makeSound()" у каждого объекта, 
если такой метод присутствует.

```java
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
```
***
### Дополнительная задача:

Доработайте метод генерации запроса на удаление объекта из таблицы БД (DELETE FROM <Table> WHERE ID = '<id>')
В классе QueryBuilder который мы разработали на семинаре.

```java
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.UUID;

public class QueryBuilder {


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
```
