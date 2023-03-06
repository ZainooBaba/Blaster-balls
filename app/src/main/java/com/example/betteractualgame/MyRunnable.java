package com.example.betteractualgame;

import android.util.Log;

public class MyRunnable implements Runnable {
    public void run(){
        while (true) {
            sleep();
            Log.d("Generic", "Running in the Thread " +
                    Thread.currentThread().getId());
            System.out.println("yoo");
        }
    }

    private void sleep () {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
