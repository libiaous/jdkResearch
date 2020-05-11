package com.ruijin.thread.futureData;

public class FutureData<T> {
    private boolean isReady = false;
    private T data;

    public synchronized void setData(T argData) {
        isReady = true;
        data = argData;
        notifyAll();
    }

    public synchronized T getData() {
        while (!isReady) {
            try {
                wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        return data;
    }
}
