package com.milind.chatapp.utils;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by Milind Amrutkar on 5/8/2019.
 */
public class Utility {

    // for getting the current time
    public static String getCurrentTime() {
        Date currentTime = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("HH:mm ");
        String localTime = dateFormat.format(currentTime);
        return localTime;
    }

    public static int generatedThreeDigitRandomNumber() {
        Random random = new Random();
        return random.nextInt(900) + 100;
    }

}
