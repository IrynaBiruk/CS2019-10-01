package by.it.biruk.lesson03;

import org.junit.Test;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.Assert.*;

@SuppressWarnings("all") //море warnings. всех прячем.

//поставьте курсор на следующую строку и нажмите Ctrl+Shift+F10
public class Testing03 {


    //переменные теста
    private String className;
    private Class<?> aClass;
    private PrintStream oldOut = System.out; //исходный поток вывода
    private PrintStream newOut; //поле для перехвата потока вывода
    private StringWriter strOut = new StringWriter(); //накопитель строки вывода

    //логический блок перехвата вывода
    {
        newOut = new PrintStream(new OutputStream() {
            private byte bytes[] = new byte[1];
            private int pos = 0;

            @Override
            public void write (int b) throws IOException {
                if (pos == 0 && b == '\r') //пропуск \r (чтобы win mac и linux одинаково работали
                    return;
                if (pos == 0) { //определим кодировку https://ru.wikipedia.org/wiki/UTF-8
                    if ((b & 0b11110000) == 0b11110000) bytes = new byte[4];
                    else if ((b & 0b11100000) == 0b11100000) bytes = new byte[3];
                    else if ((b & 0b11000000) == 0b11000000) bytes = new byte[2];
                    else bytes = new byte[1];
                }
                bytes[pos++] = (byte) b;
                if (pos == bytes.length) { //символ готов
                    String s = new String(bytes); //соберем весь символ
                    strOut.append(s); //запомним вывод для теста
                    oldOut.append(s); //копию в обычный вывод
                    pos = 0; //готовим новый символ
                }

            }
        });
    }


    //-------------------------------  тест ----------------------------------------------------------
    public Testing03 () {
        //Конструктор тестов
    }

    //Основной конструктор тестов
    private Testing03 (String className, String in, boolean runMain) {
        //this.className = className;
        aClass = null;
        try {
            aClass = Class.forName(className);
            this.className = className;

        } catch (ClassNotFoundException e) {
            fail("ERROR:Не найден класс " + className + "\n");
        }
        InputStream reader = new ByteArrayInputStream(in.getBytes());
        System.setIn(reader);   //перехват стандартного ввода
        System.setOut(newOut);  //перехват стандартного вывода

        if (runMain) //если нужно запускать, то запустим, иначе оставим только вывод
            try {
                Class[] argTypes = new Class[]{String[].class};
                Method main = aClass.getDeclaredMethod("main", argTypes);
                main.invoke(null, (Object) new String[]{});
                System.setOut(oldOut); //возврат вывода, нужен, только если был запуск
            } catch (Exception x) {
                x.printStackTrace();
            }
    }

    //метод находит и создает класс для тестирования
    //по имени вызывающего его метода, testTaskA1 будет работать с TaskA1
    private static Testing03 run (String in) {
        return run(in, true);
    }

    private static Testing03 run (String in, boolean runMain) {
        Throwable t = new Throwable();
        StackTraceElement trace[] = t.getStackTrace();
        StackTraceElement element;
        int i = 0;
        do {
            element = trace[i++];
        }
        while (!element.getMethodName().contains("test"));

        String[] path = element.getClassName().split("\\.");
        String nameTestMethod = element.getMethodName();
        String clName = nameTestMethod.replace("test", "");
        clName = clName.replaceFirst(".+__", "");
        clName = element.getClassName().replace(path[path.length - 1], clName);
        System.out.println("\n---------------------------------------------");
        System.out.println("Старт теста для " + clName);
        if (!in.isEmpty()) System.out.println("input:" + in);
        System.out.println("---------------------------------------------");
        return new Testing03(clName, in, runMain);
    }

    @Test(timeout = 2500)
    public void testTaskA1 () throws Exception {
        run("7 2").include("9 5 14 3 1\n9.0 5.0 14.0 3.5 1.0");
    }

    @Test(timeout = 2500)
    public void testTaskA2 () throws Exception {
        Testing03 testing = run("");
        String[] lines = testing.strOut.toString().trim().split("\\n");
        if (lines.length < 5)
            fail("Недостаточно строк");
        if (!lines[0].trim().equalsIgnoreCase("Мое любимое стихотворение:") &&
                !lines[0].trim().equalsIgnoreCase("Моё любимое стихотворение:"))
            fail("Нет заголовка: Мое любимое стихотворение:");
        String old = "old";
        for (String s : lines) {
            if (s.length() < 10 && s.length() > 1)
                fail("Слишком короткие строки");
            if (old.equals(s))
                fail("Есть одинаковые строки");
            old = s;
        }
    }

    @Test(timeout = 2500)
    public void testTaskB1 () throws Exception {
        run("").include("575.222")
                .include("111.111 ")
                .include("7 73 273 ")
                .include("111.111");
    }

    @Test(timeout = 2500)
    public void testTaskB2 () throws Exception {
        run("2 5 3").include("-1.0").include("-1.5");
        run("2 4 2").include("-1.0\n");
        run("2 2 2").include("Отрицательный дискриминант");
    }

    @Test(timeout = 2500)
    public void testTaskC1 () throws Exception {
        Testing03 testing = run("");
        Method m = checkMethod(testing.aClass.getSimpleName(), "convertCelsiumToFahrenheit", int.class);
        assertEquals(104.0, (double) m.invoke(null, 40), 1e-22);
        assertEquals(68.0, (double) m.invoke(null, 20), 1e-22);
        assertEquals(32.0, (double) m.invoke(null, 0), 1e-22);
    }

    @Test(timeout = 2500)
    public void testTaskC2 () throws Exception {
        Testing03 testing = run("");
        Method m = checkMethod(testing.aClass.getSimpleName(), "sumDigitsInNumber", int.class);
        assertEquals((int) m.invoke(null, 5467), 22);
        assertEquals((int) m.invoke(null, 5555), 20);
        assertEquals((int) m.invoke(null, 1111), 4);
        assertEquals((int) m.invoke(null, 9993), 30);
    }

    /*
===========================================================================================================
НИЖЕ ВСПОМОГАТЕЛЬНЫЙ КОД ТЕСТОВ. НЕ МЕНЯЙТЕ В ЭТОМ ФАЙЛЕ НИЧЕГО.
Но изучить как он работает - можно, это всегда будет полезно.
===========================================================================================================
 */
    //-------------------------------  методы ----------------------------------------------------------
    private Class findClass (String SimpleName) {
        String full = this.getClass().getName();
        String dogPath = full.replace(this.getClass().getSimpleName(), SimpleName);
        try {
            return Class.forName(dogPath);
        } catch (ClassNotFoundException e) {
            fail("\nERROR:Тест не пройден. Класс " + SimpleName + " не найден.");
        }
        return null;
    }

    private Method checkMethod (String className, String methodName, Class<?>... parameters) throws Exception {
        Class aClass = this.findClass(className);
        try {
            methodName = methodName.trim();
            Method m;
            if (methodName.startsWith("static")) {
                methodName = methodName.replace("static", "").trim();
                m = aClass.getDeclaredMethod(methodName, parameters);
                if ((m.getModifiers() & Modifier.STATIC) != Modifier.STATIC) {
                    fail("\nERROR:Метод " + m.getName() + " должен быть статическим");
                }
            } else
                m = aClass.getDeclaredMethod(methodName, parameters);
            m.setAccessible(true);
            return m;

        } catch (NoSuchMethodException e) {
            System.err.println("\nERROR:Не найден метод " + methodName + " либо у него неверная сигнатура");
            System.err.println("ERROR:Ожидаемый класс: " + className);
            System.err.println("ERROR:Ожидаемый метод: " + methodName);
            return null;
        }
    }

    private Method findMethod (Class<?> cl, String name, Class... param) {
        try {
            return cl.getDeclaredMethod(name, param);
        } catch (NoSuchMethodException e) {
            fail("\nERROR:Тест не пройден. Метод " + cl.getName() + "." + name + " не найден\n");
        }
        return null;
    }

    private Object invoke (Method method, Object o, Object... value) {
        try {
            method.setAccessible(true);
            return method.invoke(o, value);
        } catch (Exception e) {
            System.out.println(e.toString());
            fail("\nERROR:Не удалось вызвать метод " + method.getName() + "\n");
        }
        return null;
    }

    //проверка вывода
    private Testing03 is (String str) {
        assertTrue("ERROR:Ожидается такой вывод:\n<---начало---->\n" + str + "<---конец--->",
                strOut.toString().equals(str));
        return this;
    }

    private Testing03 include (String str) {
        assertTrue("ERROR:Строка не найдена: " + str + "\n", strOut.toString().contains(str));
        return this;
    }

    private Testing03 exclude (String str) {
        assertTrue("ERROR:Лишние данные в выводе: " + str + "\n", !strOut.toString().contains(str));
        return this;
    }
}
