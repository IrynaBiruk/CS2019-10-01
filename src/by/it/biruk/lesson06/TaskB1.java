package by.it.biruk.lesson06;

/*

    Шарик 1 год, Жучка 2 года, Бобик 3 года, Барбос 4 года, Полкан 5 лет

    Напечатайте их имена с помощью метода printAllNames, ожидается
    Шарик Жучка Бобик Барбос Полкан

    Напечатайте с новой строки их средний возраст,
    рассчитанный через метод averageAge, ожидается:
    3.0

Итого, правильный вывод такой:
Шарик Жучка Бобик Барбос Полкан
3.0

*/

public class TaskB1 {
    public static void main (String[] args) {
        Dog[] dogs = {new Dog("Шарик", 1),
                new Dog("Жучка", 2),
                new Dog("Бобик", 3),
                new Dog("Барбос", 4),
                new Dog("Полкан", 5)
        };
        DogHelper.printAllNames(dogs);
        double AverageAge = DogHelper.averageAge(dogs);
        System.out.println(AverageAge);
    }

}
