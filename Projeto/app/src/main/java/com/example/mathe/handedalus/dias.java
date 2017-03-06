package com.example.mathe.handedalus;

import android.text.format.DateUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.TimeZone;

import static android.R.attr.timeZone;
import static android.R.id.input;
import static android.text.format.DateUtils.FORMAT_NUMERIC_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;

/**
 * Created by Mathe on 05/03/2017.
 */
 public class dias {

    public static String getRelativeTime(final String date) {

        DateTimeZone timeZone = DateTimeZone.forID( "Brazil/East" );

        DateTimeFormatter formatterInput = DateTimeFormat.forPattern( "dd/MM/YY" );
        DateTime future = formatterInput.parseDateTime(date);

        DateTime now = new DateTime( timeZone );

        int days = Days.daysBetween( now, future ).getDays();
        Interval interval = new Interval( now, future );
        Period period = new Period( interval );

        String daysString  = Integer.toString(days);
        return daysString;
    }
}

