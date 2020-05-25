package com.ruijin.thread;

import org.junit.Test;

import java.util.concurrent.*;

import static org.junit.Assert.*;

public class CompletedFutureExample {

    @Test
    public void testCompletedFuture() {
        CompletableFuture future = CompletableFuture.completedFuture("Hello World");
        assertTrue(future.isDone());
        assertEquals("Hello World", future.getNow(null));
    }

    @Test
    public void testRunAsync() throws InterruptedException {
        CompletableFuture future = CompletableFuture.runAsync(() -> {
            assertTrue(Thread.currentThread().isDaemon());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        assertFalse(future.isDone()); // future is not done because the daemon thread is sleeping
        Thread.sleep(1500);
        assertTrue(future.isDone());
    }

    @Test
    public void testThenApply() throws InterruptedException, ExecutionException {
        CompletableFuture future = CompletableFuture.completedFuture("hello world").thenApply(s -> {
                    //this is not async, it will block the main thread
                    assertFalse(Thread.currentThread().isDaemon()); // this is main thread not daemon.
                    try {
                        System.out.println("The current thread name is : " + Thread.currentThread().getName());
                        Thread.sleep(30000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                }
        );
        System.out.println("main Thread name is : " + Thread.currentThread().getName());
        assertTrue(future.isDone()); //future is on main thread, main thread is blocked until thenApply finished
        assertEquals("HELLO WORLD", future.getNow(null));
    }

    @Test
    public void testApplyAsync() throws InterruptedException, ExecutionException {
        CompletableFuture future = CompletableFuture.completedFuture("hello world").thenApplyAsync(s -> {
                    assertTrue(Thread.currentThread().isDaemon());
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                }
        );
        assertFalse(future.isDone()); //future is on daemon thread, main thread is not blocked
        assertEquals(null, future.getNow(null)); // getNow will be null as future has not completed yet
        Thread.sleep(3100);
        assertTrue(future.isDone());
        assertEquals("HELLO WORLD", future.join()); // future.join will not be null as future has already completed already
    }

    @Test
    public void testApplyAsyncWithExecutor() throws InterruptedException, ExecutionException {
        ExecutorService executor = Executors.newFixedThreadPool(5, new ThreadFactory() {
            int count = 1;

            @Override
            public Thread newThread(Runnable runnable) {
                return new Thread(runnable, "custom-executor-" + count++);
            }
        });

        CompletableFuture future = CompletableFuture.completedFuture("hello world").thenApplyAsync(s -> {
                    assertTrue(Thread.currentThread().getName().startsWith("custom-executor-"));
                    System.out.println(Thread.currentThread().getName());
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    return s.toUpperCase();
                }, executor
        );
        assertFalse(future.isDone()); //future is on daemon thread, main thread is not blocked
        assertEquals(null, future.getNow(null)); // getNow will be null as future has not completed yet
        Thread.sleep(505);
        assertTrue(future.isDone());
        assertEquals("HELLO WORLD", future.join()); // future.join will not be null as future has already completed already
    }


    @Test
    public void testApplyAccept() throws InterruptedException, ExecutionException {
        CompletableFuture future = CompletableFuture.completedFuture("hello world").thenAccept(s -> {
                    // this is not async, the main thread will execute here rather than a daemon
                    assertFalse(Thread.currentThread().isDaemon());
                    try {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("The passed in is " + s + "converted it to " + s.toUpperCase());
                }
        );
        System.out.println("testApply: " + Thread.currentThread().getName());
        assertTrue(future.isDone());
    }

    @Test
    public void testApplyAcceptAsync() throws InterruptedException, ExecutionException {
        CompletableFuture future = CompletableFuture.completedFuture("hello world").thenAcceptAsync(s -> {
                    assertTrue(Thread.currentThread().isDaemon());
                    try {
                        System.out.println(Thread.currentThread().getName());
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("The passed in is " + s + " converted it to " + s.toUpperCase());
                }
        );
        System.out.println("testApplyAsync: " + Thread.currentThread().getName());
        assertFalse(future.isDone());
        Thread.sleep(3001);
        assertTrue(future.isDone());
    }

    @Test
    public void testApplyAcceptAsyncExceptionally() throws InterruptedException, ExecutionException {
        CompletableFuture future = CompletableFuture.completedFuture("hello world").thenAcceptAsync(String::toUpperCase);
        CompletableFuture exceptionHandler = future.handle((s, th) -> {
            return (th != null) ? "message upon cancel" : "";
        });
        System.out.println("testApplyAsync: " + Thread.currentThread().getName());
        assertFalse(future.isDone());
        Thread.sleep(3001);
        assertTrue(future.isDone());
    }


}
