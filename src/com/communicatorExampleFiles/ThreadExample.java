package com.communicatorExampleFiles;

/**
 * Modifying demo located at here:
 * @source https://www.infoworld.com/article/2074217
 */
public class ThreadExample extends Thread {
    private int N;

    public ThreadExample(int N) {
        this.N = N;
    }

    public void run() {
        for (int i = 0; i < N; i++) {
            System.out.println("i is: " + i + " and id is: " + getName());
        }
    }

    public static void main (String[] args) {
        int N = 10;
        ThreadExample te = new ThreadExample(N);
        te.start();
        ThreadExample t2 = new ThreadExample(N);
        t2.start();
        for (int j = 0; j < N; j++) {
            System.out.println("j is: " + j);
        }
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            System.out.println(e);
//        }
        for (int k = 0; k < N; k++) {
            System.out.println("k is: " + k);
        }
    }
}
