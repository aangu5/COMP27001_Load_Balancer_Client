package com.company;

import java.util.concurrent.TimeUnit;

public class Work extends Thread {
    private int workID;
    private int duration;
    private int timeLeft;
    private boolean complete;
    private Client thisNode;
    private Server mainNode;

    public int getWorkID() { return workID; }
    public int getDuration() { return duration; }
    public int getTimeLeft() { return timeLeft; }
    public boolean getComplete() { return complete; }

    public void setTimeLeft(int inputTimeLeft) { timeLeft = inputTimeLeft; }
    public void setComplete(boolean inputComplete) { complete = inputComplete; }

    public Work(int workID, int inputDuration, Server inputServer){
        try {
            mainNode = inputServer;
            this.workID = workID;
            duration = inputDuration;
            System.out.println("Work created! ID: " + workID + ", duration: " + duration);
        } catch (Exception e) {
            System.out.println("Input not recognised: " + e.getMessage());
        }
    }

    private void workComplete() {
        String messageToSend = "COMPLETE," + workID;
        mainNode.sendMessageToServer(messageToSend);
    }

    @Override
    public void run() {
        for (int i = 0; i < duration; i++){
            System.out.println("Work Thread: Task " + getWorkID() + " progress " + i + "/" + duration);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Work Thread: Task " + getWorkID() + " is complete");
        workComplete();
    }
}
