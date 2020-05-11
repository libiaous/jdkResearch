package com.ruijin.thread.futureMain;

import java.io.IOException;
import java.util.Random;

public class ThreadTask {
    public String doTask(String url) throws IOException {
        System.out.println("doTask");
        for (int i = 1; i <= 3; ++i) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) { /* do nothing*/ }
            System.out.println("delayed " + i + " seconds in thread id: " + Thread.currentThread().getId());
        }
        String ret = url + ":" + new Random().nextFloat();
        System.out.println("doTask printed: " + ret);
        return ret;
    }

    public String doTask2(String url) throws IOException {
        System.out.println("doTask2");
        for (int i = 1; i <= 3; ++i) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) { /* do nothing*/ }
            System.out.println("delayed " + i + " seconds in thread id: " + Thread.currentThread().getId());
        }
        String ret = url + ":" + new Random().nextFloat();
        System.out.println("doTask2 printed: " + ret);
        return ret;
    }
}