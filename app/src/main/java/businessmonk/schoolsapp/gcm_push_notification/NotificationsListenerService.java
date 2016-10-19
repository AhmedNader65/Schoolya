package businessmonk.schoolsapp.gcm_push_notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import businessmonk.schoolsapp.MainActivity;
import businessmonk.schoolsapp.Models.Message;
import businessmonk.schoolsapp.R;
import businessmonk.schoolsapp.data.MessagesColumns;
import businessmonk.schoolsapp.data.MessagesProvider;
import businessmonk.schoolsapp.fragment.MessagesFragment;
import businessmonk.schoolsapp.fragment.NotificationFragment;

import static com.google.android.gms.internal.zzip.runOnUiThread;


/**
 * Created by om4rezz on 10/3/16.
 */

public class NotificationsListenerService extends GcmListenerService {

    private int Teinda_NOTIFICATION_ID = 0;
    Bundle b;
    @Override
    public void onMessageReceived(String s, Bundle bundle) {
//        runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                Toast.makeText(getApplicationContext(), "2received!", Toast.LENGTH_SHORT).show();
//            }
//        });
        b = bundle;
            Set<String> keys = bundle.keySet();

            for(String key : keys){
                Log.w("Hey you", key);
            }

            notify(bundle);
            insertData(bundle.getString("title"), bundle.getString("body"), bundle.getString("type"), String.valueOf(System.currentTimeMillis()));
    }
    public void insertData(String title,String content,String type,String date) {
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date d = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Message m = new Message();
        m.content=content;
        m.title= title;
        m.date=  convertMonthToText(dateFormat.format(d))+","+day;
        m.inbox= 1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(MessagesColumns.CONTENT, content);
        contentValues.put(MessagesColumns.SUBJECT, title);
        contentValues.put(MessagesColumns.TYPE, type);
        try {

            contentValues.put(MessagesColumns.IMAGE,b.getString("image"));
            contentValues.put(MessagesColumns.DATE, convertMonthToText(dateFormat.format(d))+","+day);
            try {
                NotificationFragment.list.add(m);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        NotificationFragment.adapter.notifyDataSetChanged();
                        NotificationFragment.adapter.imm = b.getString("image");
                    }
                });
            }catch (Exception e){
            }
        }catch (Exception e){
            e.printStackTrace();
            contentValues.put(MessagesColumns.DATE, date);
            try {
                MessagesFragment.list.add(m);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MessagesFragment.adapter.notifyDataSetChanged();
                    }
                });
            }catch (Exception ee){

            }
        }
        contentValues.put(MessagesColumns.INBOX, 1);
        getApplicationContext().getContentResolver().insert(MessagesProvider.Messages.CONTENT_URI, contentValues);

    }
    private void notify(Bundle bundle) {
        Context context = getApplicationContext();

        //build your notification here.
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setContentText(bundle.getString("body"));
        notificationBuilder.setContentTitle(bundle.getString("title"));
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.inbox_icon);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setDefaults( Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(Teinda_NOTIFICATION_ID, notificationBuilder.build());
        //refreshing last sync

    }
    private boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }


    public static String convertMonthToText(String num){
        switch (num){
            case "1":
                return "JAN";
            case "2":
                return "FEB";
            case "3":
                return "MAR";
            case "4":
                return "APR";
            case "5":
                return "MAY";
            case "6":
                return "JUN";
            case "7":
                return "JUL";
            case "8":
                return "AUG";
            case "9":
                return "SEP";
            case "10":
                return "OCT";
            case "11":
                return "NOV";
            case "12":
                return "DEC";
            default:
                return "JAN";
        }
    }
}
