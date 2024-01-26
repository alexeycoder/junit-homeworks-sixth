## Unit-тесты 6.

### Задание

Создайте программу на Python или Java, которая принимает два списка чисел и выполняет следующие действия:

a. Рассчитывает среднее значение каждого списка.

b. Сравнивает эти средние значения и выводит соответствующее сообщение:

- "Первый список имеет большее среднее значение", если среднее значение первого списка больше.
- "Второй список имеет большее среднее значение", если среднее значение второго списка больше.
- "Средние значения равны", если средние значения списков равны.

Важно:

Приложение должно быть написано в соответствии с принципами объектно-ориентированного программирования.
Используйте Pytest (для Python) или JUnit (для Java) для написания тестов, которые проверяют правильность работы программы. Тесты должны учитывать различные сценарии использования вашего приложения.
Используйте pylint (для Python) или Checkstyle (для Java) для проверки качества кода.
Сгенерируйте отчет о покрытии кода тестами. Ваша цель&nbsp;&mdash; достичь минимум 90% покрытия.

### Решение

*Код приложения*

* [ListUtils](src/main/java/edu/alexey/junit/homeworks/sixth/ListUtils.java)&nbsp;&mdash; Утилитный класс для работы со списками с методом подсчёта среднего арифметического числового списка.

* [ListByAverageComparator](src/main/java/edu/alexey/junit/homeworks/sixth/ListByAverageComparator.java)&nbsp;&mdash; Функционал сравнения пары числовых списков реализован в виде стандартного компаратора с уточнённым контрактом, который проверяется тестами.

* [ConsoleLifecycle](src/main/java/edu/alexey/junit/homeworks/sixth/ConsoleLifecycle.java)&nbsp;&mdash; Класс, организующий жизненный цикл консольного приложения.

* [App#main](src/main/java/edu/alexey/junit/homeworks/sixth/App.java#main)&nbsp;&mdash; Точка входа приложения, "Main Class".

*Тесты*

* [ListUtilsTest](src/test/java/edu/alexey/junit/homeworks/sixth/ListUtilsTest.java)&nbsp;&mdash; Модульные тесты проверки метода расчёта среднего арифметического списка.

  Проверяются фактическая реакция метода на передачу некорректных аргументов с точки зрения контракта на соответствие данному контракту, а также правильность результатов расчёта при корректных аргументах.


* [ListByAverageComparator](src/main/java/edu/alexey/junit/homeworks/sixth/ListByAverageComparator.java)&nbsp;&mdash; Модульные тесты проверки компаратора для сравнения двух списков по среднему арифметическому.

  Аналогично, проверяются фактическая реакция метода компаратора на передачу некорректных аргументов с точки зрения контракта на соответствие данному контракту, а также верность возвращаемых результатов сравнения в зависимости от корректных входных списков.


* [ConsoleLifecycleTest](src/test/java/edu/alexey/junit/homeworks/sixth/ConsoleLifecycleTest.java)&nbsp;&mdash; Модульные и интеграционные тесты работы методов интерактивного взаимодействия с пользователем через интерфейс командной строки и организации жизненного цикла консольного приложения в целом.

  Большая часть представлена модульными тестами, где зависимости тестируемых участков подменяются Макетом (поток ввода) и Пустышками (потоки вывода и ошибок).

  Как альтернатива чисто модульным тестам, тесты метода запроса у пользователя списка чисел могут рассматриваться как интеграционные, поскольку для корректности его работы, помимо имитационных зависимостей, применяется подменная реальная реализация потока вывода (для регистрации и проверки сообщений потока ошибок).


* [AppTest](src/test/java/edu/alexey/junit/homeworks/sixth/AppTest.java)&nbsp;&mdash; Интеграционные тесты работы приложения в целом.

  Используются подменные реальные реализации потока ввода и вывода для комплексной эмуляции взаимодействия приложения с пользователем для тестирования сценариев, присущих сквозному тестированию.

### Отчёт о покрытии JaCoCo

См. также в [site/jacoco/index.html](https://htmlpreview.github.io/?https://github.com/alexeycoder/junit-homeworks-sixth/blob/site/jacoco/index.html)

![Отчёт JaCoCo 1](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/jacoco-1.png)

![Отчёт JaCoCo 2](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/jacoco-2.png)

![Отчёт JaCoCo 3](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/jacoco-3.png)

![Отчёт JaCoCo 4](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/jacoco-4.png)

![Отчёт JaCoCo 5](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/jacoco-5.png)

![Отчёт JaCoCo 6](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/jacoco-6.png)

![Отчёт JaCoCo 7](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/jacoco-7.png)

### Отчёт о прохождении тестов

![Прохождение тестов](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/tests-run.png)

### Отчёт CheckStyle

См. также в [site/checkstyle/checkstyle.html](https://htmlpreview.github.io/?https://github.com/alexeycoder/junit-homeworks-sixth/blob/site/checkstyle/checkstyle.html)

В качестве опорных использовались соглашения из Google Java Style с модификациями: табуляция для формирования отступов и порядок импортов, применяемый в Eclipse IDE.

![Отчёт ChrckStyle](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/checkstyle-1.png)

### Пример работы приложения:

![Пример работы](https://raw.githubusercontent.com/alexeycoder/illustrations/main/java-junit-hw6/example.png)
