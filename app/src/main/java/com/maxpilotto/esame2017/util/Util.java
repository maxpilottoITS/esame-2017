package com.maxpilotto.esame2017.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {
    public static final DateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm dd/MM/yyyy");

    public static String printDate(Date date) {
        return DATE_FORMAT.format(date);
    }
}
