package com.gec.hrm.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatStringAsDate {
    public static Date formart(String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = df.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
