package com.nicolascarrasco.www.auctionhouse;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Nicolás Carrasco on 23/12/2015.
 */
public class Utilities {

    public final static String AUCTION_ID_EXTRA_KEY = "auction_id";
    public final static String USER_EXTRA_KEY = "user";

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

    public static boolean isValidPrice(String price) {
        /* This expression matches either an integer (ex. 100) or a rational expressed in decimal
         * notation with exactly 2 decimal digits (ex. 99.99)
         */
        return price.matches("\\d+(\\.\\d{2})?");
    }

    public static boolean isValidDate(String date) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        try {
            format.parse(date);
        } catch (ParseException e) {
//            Log.e(tag, e.toString() + ", " + date);
            return false;
        }
        return true;
    }

    public static boolean isUserValid(String user){
        return user.length() > 4;
    }

    public static boolean isEmailValid(String email) {
        //TODO: Replace this with real email validation
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        //TODO: Replace this with real password validation
        return password.length() > 4;
    }

    public static String formatPrice(float price) {
        return "$".concat(String.format("%.2f",price)).concat(" USD");
    }

    public static String formatDate(Context context, float dayInMillis) {
        DateFormat format = DateFormat.getDateInstance(DateFormat.SHORT);
        return context.getResources().getString(R.string.label_expires_on)
                .concat(format.format(dayInMillis));
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public static void showProgress(ProgressDialog dialog, final boolean show) {
        if (show) {
            dialog.show();
        } else {
            dialog.dismiss();
        }
    }
}
