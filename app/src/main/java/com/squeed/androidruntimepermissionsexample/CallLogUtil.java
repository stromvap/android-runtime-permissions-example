package com.squeed.androidruntimepermissionsexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;

public class CallLogUtil {
    public static void addCallLogEvent(Context context, String number) {
        ContentValues values = new ContentValues();
        values.put(CallLog.Calls.NUMBER, number);
        values.put(CallLog.Calls.DATE, System.currentTimeMillis());
        values.put(CallLog.Calls.DURATION, 0);
        values.put(CallLog.Calls.TYPE, CallLog.Calls.OUTGOING_TYPE);
        values.put(CallLog.Calls.NEW, 1);
        values.put(CallLog.Calls.CACHED_NAME, "");
        values.put(CallLog.Calls.CACHED_NUMBER_TYPE, 0);
        values.put(CallLog.Calls.CACHED_NUMBER_LABEL, "");
        context.getContentResolver().insert(CallLog.Calls.CONTENT_URI, values);
    }

    public static String getLatestCallLogEventNumber(Context context) {
        String number = "";

        Cursor calls = context.getContentResolver().query(CallLog.Calls.CONTENT_URI, null, null, null, CallLog.Calls.DEFAULT_SORT_ORDER);
        if (calls.moveToNext()) {
            number = calls.getString(calls.getColumnIndex(CallLog.Calls.NUMBER));
        }

        calls.close();

        return number;
    }
}
