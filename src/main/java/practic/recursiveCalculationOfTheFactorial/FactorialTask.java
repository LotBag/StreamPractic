package practic.recursiveCalculationOfTheFactorial;

import java.util.concurrent.RecursiveTask;

public class FactorialTask extends RecursiveTask<Long> {

    private final int n;

    public FactorialTask(int n) {
        this.n = n;
    }

    @Override
    protected Long compute() {
        if (n == 1 || n == 0) {
            return 1L;
        } else {
            FactorialTask subTask = new FactorialTask(n - 1);
            subTask.fork();
            return n * subTask.join();
        }
    }
}
