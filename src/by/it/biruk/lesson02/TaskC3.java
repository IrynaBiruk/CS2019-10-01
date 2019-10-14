package by.it.biruk.lesson02;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Scanner;

/*
Ускорение свободного падения на Земле и Марсе таково:
Марс   3.86
Земля   9.81

С клавиатуры вводится вес человека в килограммах.
Рассчитайте вес человека на Марсе и выведите
округлив его до сотых килограмма (2 знака)

Создайте для этих целей метод getWeight(int weight)

Требования:
1. Метод getWeight(int weight) должен быть статическим.
2. Метод getWeight должен возвращать значение типа double.
3. Метод getWeight должен обязательно (!) округлять до сотых возвращаемое значение типа double.
4. Метод getWeight не должен ничего выводить на экран.
5. Метод getWeight должен правильно переводить вес тела в килограммах на Земле
    в вес этого же тела на Марсе, и возвращать это значение.

Пример:

Ввод:
75

Вывод:
29.51


*/
class TaskC3 {
    public static void main (String[] args) {
        Scanner n = new Scanner(System.in);
        //System.out.println(getWeight(n.nextInt()));
        //System.out.printf(("%.2f%n"), getWeight(n.nextInt()));
        System.out.println(getWeight(n.nextInt()));
    }

    public static double getWeight (int massEarth) {
        double gEarth = 9.81;
        double gMars = 3.86;
        double massMars = (((massEarth) / gEarth * gMars));
        //System.out.println(massMars);
        // int m= (int) (massMars*100);
        //System.out.println(m);
        // double m1= (double) (m/100);
        //System.out.println(m1);
        //return m1;
        //double d = massMars;
        massMars = new BigDecimal(massMars).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
        //System.out.println(massMars);
        //double m = Math.round(massMars*100.00)/100.00;
        return massMars;
        //return m;
    }
}