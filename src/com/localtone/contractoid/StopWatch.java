package com.localtone.contractoid;

public class StopWatch {
	   
    private long startTime = 0;
    private long stopTime = 0;
    private long elapsed = 0;
    private boolean running = false;

   
    public void start() {
        this.startTime = System.nanoTime();
        this.running = true;
    }

   
    public void stop() {
        this.stopTime = System.nanoTime();
        this.running = false;
    }

    public void reset() {
        this.startTime = 0;
        this.stopTime = 0;
        this.running = false;
    }
    
    //elaspsed time in milliseconds
    public long getElapsedTimeMilli() {
        if (running) {
            elapsed = ((System.nanoTime() - startTime) / 1000000);
        }
        else {
            elapsed = ((stopTime - startTime) / 1000000);
        }
        return elapsed;
    }
}

