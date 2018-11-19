package com.example.hp.appelectr;

import android.content.res.Resources;
import android.util.Log;
import android.util.TypedValue;

import java.sql.Timestamp;

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
}
