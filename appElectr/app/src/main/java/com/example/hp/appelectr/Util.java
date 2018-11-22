package com.example.hp.appelectr;

import android.content.res.Resources;
import android.text.format.DateFormat;
import android.util.Log;
import android.util.TypedValue;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public final class Util {

    public static int calcTimeToKnowIfPerson(Long lastTimestamp){
        Timestamp nowTimestamp = new Timestamp((System.currentTimeMillis()/1000)-18000);
        long tsTime2 = nowTimestamp.getTime();
        Long difTimeLastNow = tsTime2-lastTimestamp;
        Log.e("ERR", tsTime2+"--"+lastTimestamp+"--"+difTimeLastNow);
        return difTimeLastNow > 60? 0: 1;
    }

    public static int dpToPx(int dp, Resources r) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public static String getPromHoraEntradaS(List<Long> listFechas){
        String hour = doubleToString(promHoraEntrada(listFechas));
        String minutes = doubleToString(promMinutesEntrada(listFechas));
        return hour+":"+minutes;
    }

    public static double promHoraEntrada(List<Long> listFechas){
        double hour = 0;
        for(long fechaLong: listFechas){
            hour += Double.parseDouble(getDateHour((fechaLong+18000)*1000));
        }
        Log.d("TAGHOURS",(hour/listFechas.size())+"----"+listFechas.size());
        return (hour/listFechas.size());
    }

    public static double promMinutesEntrada(List<Long> listFechas){
        double minutes= 0;
        for(long fechaLong: listFechas){
            minutes += Double.parseDouble(getDateMinutes((fechaLong+18000)*1000));
        }
        Log.d("TAGHOURS",(minutes/listFechas.size())+"----"+listFechas.size());
        return (minutes/listFechas.size());
    }

    public static String getDate(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy hh:mm:ss a", cal).toString();
        return date;
    }

    public static String getDateHour(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format(" HH", cal).toString();
        return date;
    }

    public static String getDateMinutes(long time) {
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("mm", cal).toString();
        return date;
    }

    public static String doubleToString(double doubleToString){
        String str = new Double(doubleToString).toString();
        return str.substring(0,str.indexOf('.'));
    }


}
