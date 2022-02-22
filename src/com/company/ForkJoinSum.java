package com.company;

import java.util.concurrent.RecursiveTask;

public class ForkJoinSum extends RecursiveTask<Integer> {
    public final long THRESHOLD = 50000;
    private final int[] array;
    private final int start;
    private final int end;

    public ForkJoinSum(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        int length = end - start;
        if (length <= THRESHOLD) {
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else {
            ForkJoinSum leftTask = new ForkJoinSum(array, start, start + length / 2);
            ForkJoinSum rightTask = new ForkJoinSum(array, start + length / 2, end);
            leftTask.fork();
            Integer rightResult = rightTask.compute();
            Integer leftResult =  leftTask.join();
            return rightResult + leftResult;
        }
    }
}
