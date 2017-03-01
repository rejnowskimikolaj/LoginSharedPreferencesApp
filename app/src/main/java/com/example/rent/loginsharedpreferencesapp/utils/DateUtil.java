package com.example.rent.loginsharedpreferencesapp.utils;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 2017-02-16.
 */

public class DateUtil {

    private static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public static String getStringDateFromDatePicker(DatePicker datePicker){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(getDateFromDatePicker(datePicker));
    }


    public static Date getDateFromString(String s) {
        Log.d("DATE", "getDateFromString: s: "+s);
        String[] dateStrings = s.split("-");
        int m = Integer.parseInt(dateStrings[1]);
        int d = Integer.parseInt(dateStrings[2]);
        int y = Integer.parseInt(dateStrings[0]);
        Calendar calendar = Calendar.getInstance();
        calendar.set(y, m-1, d);
        return calendar.getTime();
    }
}
