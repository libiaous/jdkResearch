package com.ruijin.thread.futureMain;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class FutureMain {
    public static void main(String[] args) throws Exception {
        ThreadTask task = new ThreadTask();
        System.out.println("main thread starts in thread id: " + Thread.currentThread().getId());
        CompletableFuture futureNonBlocking = CompletableFuture.supplyAsync(() -> {
            try {
                String ret = task.doTask("google");
                System.out.println("futureNonBlocking returned value : " + ret);
                System.out.println("Thread id: " + Thread.currentThread().getId());
                return ret;
            } catch (Exception e) {
                e.printStackTrace();
                throw new CompletionException(e);
            }
        });

        CompletableFuture futureBlocking = CompletableFuture.supplyAsync(() -> {
            try {
                String ret = task.doTask2("Facebook");
                System.out.println("futureBlocking returned value : " + ret);
                System.out.println("Thread id: " + Thread.currentThread().getId());
                return ret;
            } catch (Exception e) {
                e.printStackTrace();
                throw new CompletionException(e);
            }
        });

        futureNonBlocking.thenAcceptAsync(
                result ->
                {
                    System.out.println("Thread id: " + Thread.currentThread().getId());
                    System.out.println("\nfrom non blocking future:\n" + result + "\n");
                }
        );

        Object ret = futureBlocking.get();

        System.out.println("\nfrom blocking future:\n" + ret + "\n");
        System.out.println("main thread about to sleep");
        Thread.sleep(5000);
        System.out.println("main thread is awake");
        System.out.println("main thread ends");
    }
}
