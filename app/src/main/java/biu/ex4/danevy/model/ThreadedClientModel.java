package biu.ex4.danevy.model;


import android.util.Log;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ThreadedClientModel implements IClientModel {

    private volatile boolean isRunning;
    private IClientModel decorated;
    private BlockingQueue<Runnable> blockingQueue;

    public ThreadedClientModel(IClientModel client) {
        this.decorated = client;
        this.blockingQueue = new LinkedBlockingQueue<>();
        this.isRunning = true;
        openThread();
    }

    private void openThread() {
        new Thread(() -> {
            while(isRunning) {
                try {
                    this.blockingQueue.take().run();
                } catch (InterruptedException e) {
                    Log.e("TCP", "Interrupted: Error", e);
                }
            }
        }).start();
    }

    @Override
    public void connect(String ip, int port) {
        addTask(()->this.decorated.connect(ip, port));
    }

    @Override
    public void disconnect() {
        addTask(()-> {
            this.decorated.disconnect();
            isRunning = false;
        });
    }

    @Override
    public void sendCommand(String cmd) {
        addTask(()->this.decorated.sendCommand(cmd));
    }

    private void addTask(Runnable r) {
        this.blockingQueue.add(r);
    }
}
