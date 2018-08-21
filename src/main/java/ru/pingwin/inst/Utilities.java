package ru.pingwin.inst;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Utilities {

    public static final int DAYS_MAX_AFTER_LAST_PUBLICATION = 45;

    public static Boolean isPublicationNotOld(String date, int daysMaxAfterLastPublication){
        int daysAgo = howManyDaysAgo("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", date);
        //System.out.print("daysAgo = "+daysAgo);
        if(daysAgo < daysMaxAfterLastPublication){
            return true;
        }
        return false;
    }

    public static int howManyDaysAgo(String timeFormat, String stringDate){
        SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date date = null;
        try {
            date = dateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int days = convertSecondsToDays(System.currentTimeMillis()) - convertSecondsToDays(date.getTime());
        return days;
    }

    public static int convertSecondsToDays(Long seconds){
        return Math.round((((seconds/1000)/60)/60)/24);
    }

}
