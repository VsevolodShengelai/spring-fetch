# Spring Fetch

Библиотека для удобной работы с запросами в Spring приложениях.

## FetchRequestBuilder

`FetchRequestBuilder` - это удобный инструмент для построения запросов с фильтрацией, сортировкой и пагинацией в Spring приложениях.

### Возможности

- Фильтрация данных с различными операторами сравнения
- Логические операторы AND и OR для группировки условий
- Сортировка по одному или нескольким полям
- Пагинация результатов
- Поддержка вложенных фильтров

### Использование

#### Базовые фильтры

```java
FetchRequest request = new FetchRequestBuilder()
    .equals("userStatus", "ACTIVE")           // равно
    .notEquals("deleted", "true")            // не равно
    .greaterThan("userAge", "18")            // больше
    .greaterThanOrEquals("orderAmount", "1000") // больше или равно
    .lessThan("availableQuantity", "10")     // меньше
    .lessThanOrEquals("discountPercent", "50")  // меньше или равно
    .like("userName", "Ivan%")               // поиск по шаблону
    .in("orderStatus", "NEW", "PROCESSING", "COMPLETED")   // вхождение в список
    .notIn("productId", "1001", "1002", "1003")           // не входит в список
    .build();
```

#### Логические операторы

```java
FetchRequest request = new FetchRequestBuilder()
    .equals("userStatus", "ACTIVE")
    // Группа условий с AND
    .and(List.of(
        new FilterRequest("userAge", ">", "18", List.of()),
        new FilterRequest("userCountry", ":", "RU", List.of())
    ))
    // Группа условий с OR
    .or(List.of(
        new FilterRequest("userType", ":", "PREMIUM", List.of()),
        new FilterRequest("isVip", ":", "true", List.of())
    ))
    .build();
```

#### Сортировка

```java
FetchRequest request = new FetchRequestBuilder()
    .equals("userStatus", "ACTIVE")
    .sortAsc("userName")               // сортировка по возрастанию
    .sortDesc("registrationDate")      // сортировка по убыванию
    .build();
```

#### Пагинация

```java
FetchRequest request = new FetchRequestBuilder()
    .equals("userStatus", "ACTIVE")
    .page(1, 20)                          // страница 1, размер страницы 20
    .build();
```

#### Комбинированный пример

```java
FetchRequest request = new FetchRequestBuilder()
    .equals("userStatus", "ACTIVE")
    .and(List.of(
        new FilterRequest("userAge", ">", "18", List.of()),
        new FilterRequest("userCountry", ":", "RU", List.of())
    ))
    .sortAsc("userName")
    .sortDesc("registrationDate")
    .page(1, 20)
    .build();
```

### Поддерживаемые операторы

- `:` - равно
- `!:` - не равно
- `>` - больше
- `>:` - больше или равно
- `<` - меньше
- `<:` - меньше или равно
- `like` - поиск по шаблону
- `in` - вхождение в список
- `not in` - не входит в список
- `AND` - логическое И
- `OR` - логическое ИЛИ

### Особенности

1. Все методы возвращают `this`, что позволяет использовать цепочку вызовов
2. Поддерживаются вложенные фильтры любой глубины
3. По умолчанию пагинация начинается со страницы 1, с размером страницы 100
4. Все параметры фильтрации и сортировки опциональны
5. Поддерживается валидация входных данных

### Зависимости

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```