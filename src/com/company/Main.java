package com.company;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;

/*
Результаты выполнения с помощью однопоточного метода
Сумма = 49014735. Среднее арифметрическое = 0. Время выполнения = 313 мс.
---------------------------------------------------------------------------
Результаты выполнения с помощью многопоточного метода
Сумма = 49014735. Среднее арифметрическое = 0. Время выполнения = 219 мс.

В результате выполнения программы видно, что чем больше размерность массива,
тем лучше производительность у метода, написанного с помощью рекурсии
При малой размерности массива рекурсия не дает преимущества по сравнению с
однопоточными методами
*/

public class Main {
    private static final int ARRAY_SIZE = 509_000_000;
    private static final int RANGE = 509_000_000;
    public static void main(String[] args) {

        int[] array = getArray(ARRAY_SIZE);
        singleThreadGetSum(array);
        recursiveGetSum(array);
    }

    public static int[] getArray(int arraySize) {
        int[] array = new int[arraySize];
        Random random = new Random();
        for (int i = 0; i < arraySize; i++) {
            array[i] = random.nextInt(RANGE);
        }
        return array;
    }

    public static void singleThreadGetSum(int[] array) {
        long startTime = System.currentTimeMillis();
        int sum = 0;
        int average;
        for (int i = 0; i < array.length; i++) {
            sum += array[i];
        }
        average = sum / array.length;
        System.out.println("Результаты выполнения с помощью однопоточного метода");
        System.out.println("Сумма = " + sum + ". Среднее арифметрическое = " + average +
                ". Время выполнения = " + (System.currentTimeMillis() - startTime) + " мс.");
        System.out.println("---------------------------------------------------------------------------");
    }

    public static void recursiveGetSum(int[] array) {
        long startTime = System.currentTimeMillis();
        int average;
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        int sum = forkJoinPool.invoke(new ForkJoinSum(array, 0, array.length));
        average = sum / array.length;
        System.out.println("Результаты выполнения с помощью многопоточного метода");
        System.out.println("Сумма = " + sum + ". Среднее арифметрическое = " + average +
                ". Время выполнения = " + (System.currentTimeMillis() - startTime) + " мс.");
    }
}
