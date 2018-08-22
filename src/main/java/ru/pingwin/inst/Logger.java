package ru.pingwin.inst;

import java.util.ArrayList;

public class Logger {
    public static final int HI_DETAILS = 2;
    public static final int MEDIUM_DETAILS = 1;
    public static final int ONLY_IMPORTANT_DETAILS = 0;
    private static int level = HI_DETAILS;
    public static void inLog(String str, int detailsLevel){
        if(detailsLevel <= level){
            System.out.println(str);
        }
    }

    public void setLevel(int level){
        this.level = level;
    }

}
