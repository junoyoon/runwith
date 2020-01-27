package me.ryan.runwith.algo;

public class Fibo {
    public int calculateFibo(int number) {
        return this.fibo(number);
    }

    public int calculateFibo(int number, boolean isFast) {
        if (isFast) {
            return this.fastFibo(number);
        }
        return this.fibo(number);
    }

    private int fastFibo(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            int[] arr = new int[n + 1];
            arr[0] = 0;
            arr[1] = 1;
            for (int i = 2; i <= n; i++) {
                arr[i] = arr[i - 1] + arr[i - 2];
            }
            return arr[n];
        }
    }

    private int fibo(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return fibo(n - 1) + fibo(n - 2);
        }
    }
}
