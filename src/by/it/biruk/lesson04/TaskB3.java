package by.it.biruk.lesson04;
/*
С клавиатуры вводится некоторое число.
Пусть считается сумма int sum=1+2+3+4+5+... и т.д.
Каждая цифра суммы выводится в консоль через запятую (без пробела).
Остановите вывод до того, как сумма превысит введенное число.

Например:
Ввод:
122
Вывод:
1,3,6,10,15,21,28,36,45,55,66,78,91,105,120,

Ввод:
15
Вывод:
1,3,6,10,15,

Ввод:
14
Вывод:
1,3,6,10,


 */


import java.util.Scanner;

public class TaskB3 {
    public static void main (String[] args) {
        Scanner sc=new Scanner(System.in);
        int number=sc.nextInt();
        int sum=1;
        int a=1;
        int b=2;
              while (sum<=number) {

            System.out.print(sum +",");
            sum=sum+b;
            b++;
        }
    }

}
