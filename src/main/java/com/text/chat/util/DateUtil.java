package com.text.chat.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    public static long diffInMin(Date d1, Date d2) {
        long duration = Math.abs(d1.getTime() - d2.getTime());
        return TimeUnit.MILLISECONDS.toMinutes(duration);
    }

    public static long diffInSec(Date d1, Date d2) {
        long duration = Math.abs(d1.getTime() - d2.getTime());
        return TimeUnit.MILLISECONDS.toSeconds(duration);
    }

    public static long diffInHr(Date d1, Date d2) {
        long duration = Math.abs(d1.getTime() - d2.getTime());
        return TimeUnit.MILLISECONDS.toHours(duration);
    }

    public static long diffInDay(Date d1, Date d2) {
        long duration = Math.abs(d1.getTime() - d2.getTime());
        return TimeUnit.MILLISECONDS.toDays(duration);
    }
}
