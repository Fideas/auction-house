package com.nicolascarrasco.www.auctionhouse;

import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Nicolás Carrasco on 23/12/2015.
 */
public class Utilities {

    public static long dateStringToMillis(String dateString, String tag) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        Date date;
        try {
            date = format.parse(dateString);
        } catch (Exception e) {
            //Since the date has already been validated this block should never be reached
            Log.e(tag, e.toString() + ", " + dateString);
            return -1;
        }
        return date.getTime();
    }

    public static boolean isValidPrice(String price){
        /* This expression matches either an integer (ex. 100) or a rational expressed in decimal
         * notation with exactly 2 decimal digits (ex. 99.99)
         */
        return price.matches("\\d+(\\.\\d{2})?");
    }

    public static boolean isValidDate(String date){
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        try {
            format.parse(date);
        } catch (ParseException e) {
//            Log.e(tag, e.toString() + ", " + date);
            return false;
        }
        return true;
    }
}
