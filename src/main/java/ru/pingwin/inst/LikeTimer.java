package ru.pingwin.inst;


import java.util.Date;

import static java.lang.Thread.sleep;

public class LikeTimer {

    private Integer intervalBetweenLikeInSeconds = 60;
    private long lastLikeInSeconds = 0;
    Date date;

    private long getCurrentTimeInSeconds(){
        date = new Date();
        return date.getTime() / 1000;
    }

    public void sleepBeforeLikeIfNeed() throws InterruptedException {
        if (lastLikeInSeconds == 0) {
            lastLikeInSeconds = getCurrentTimeInSeconds();
            return;
        } else {
            long currentTimeInSeconds = getCurrentTimeInSeconds();
            if (currentTimeInSeconds - lastLikeInSeconds > intervalBetweenLikeInSeconds) {
                lastLikeInSeconds = getCurrentTimeInSeconds();
                return;
            } else {
                long pause = intervalBetweenLikeInSeconds - (currentTimeInSeconds - lastLikeInSeconds)+1;
                Logger.inLog("Pause between likes " + pause + " seconds", 2);
                sleep(pause*1000);
                lastLikeInSeconds = getCurrentTimeInSeconds();
                return;
            }
        }
    }
}
