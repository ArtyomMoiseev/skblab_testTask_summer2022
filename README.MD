# SKBLAB тестовое задание java(Spring)
Моисеев Артём tg: @moiseevorg

## Краткое описание 
Форма регистрации с отправкой е-мейла после одобрения внешней системой.

## Реализация
#### Cтек
Spring: 2.7
DBMS - h2
Lombok.
#### Краткое описание реализации
Решение построено на основе событий. В данном случае это позволяет достичь асинхронной отправки формы на одобрение, а также асинхронной отправки сообщений на электронную почту.
По основным моментам в коде имеются комментарии.
## Демонстрация работы
Форма 1:
![image](md_images/form1.png)

Форма 2:
![image](md_images/form2.png)

Результат:
![image](md_images/result.png)
## Запуск
1. Выполните команду: `mvn spring-boot:run`
2. Форма будет доступна по адресу: `127.0.0.1:8080/index.html`
