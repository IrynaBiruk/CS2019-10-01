package by.it.biruk.lesson02;

/*
Подойдет только 20
В методе main расставь правильно знаки плюс и минус, чтобы значение переменной result получилось равным 20.
Знаки нужно расставить только в строчке, в которой объявляется переменная result.
Перед каждой переменной должен стоять знак либо плюс, либо минус.

Требования:

1. Значения переменных: a, b, c, d не изменять.
2. Перед каждой из переменных: a, b, c, d в строке с объявлением переменной result
    должен стоять один знак плюс либо минус.
3. В результате работы программы на экран нужно вывести число 20.
4. Знаки плюс и минус должны быть расставлены правильно.

 */
class TaskB2 {
    private static int a = 1;
    private static int b = 3;
    private static int c = 9;
    private static int d = 27;

    public static void main (String[] args) {

        int result = -a + b - c + d;

        System.out.println(result);
    }
}
