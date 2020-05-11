package com.ruijin.thread.futureData;

public class Server {
    public FutureData<String> getFutureData() {
        final FutureData<String> data = new FutureData<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                data.setData("world");
            }
        }).start();
        return data;
    }
}
