package com.ruijin.thread.futureData;

public class App {
    public static void main(String[] argv) {
        Server server = new Server();
        FutureData<String> futureData = server.getFutureData();
        String hello = "hello";
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println(hello + " " + futureData.getData());
    }
}
