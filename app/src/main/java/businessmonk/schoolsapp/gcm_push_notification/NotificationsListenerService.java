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
        super.onMessageReceived(s, bundle);

        try {
            insertData(bundle.getString("title"), bundle.getString("body"), bundle.getString("type"), String.valueOf(System.currentTimeMillis()));
        }catch (Exception e){
            e.printStackTrace();
            b = bundle.getBundle("notification");
            insertData(b.getString("title"), b.getString("body"), b.getString("type"), String.valueOf(System.currentTimeMillis()));
            for (String key: b.keySet())
            {
                Log.d ("imaggggge", key );
            }
            try{
                Log.e("image",b.getString("image"));
            }catch (Exception er){
                er.printStackTrace();
            }
        }
        boolean foregroud = isAppIsInBackground(getApplicationContext());
        if(!foregroud){
            Log.e("Hey","I am foreground "+foregroud);
            notify(bundle);
        }else{
            notify(bundle);
            Log.e("Hey","I am background "+foregroud);
        }

//        Log.e("Hey you", bundle.getal.toString());

        Set<String> keys = bundle.keySet();

        for(String key : keys){
            Log.w("Hey you", key);
        }
    }
    public void insertData(String title,String content,String type,String date) {
        Message m = new Message();
        m.content=content;
        m.title= title;
        m.date= MessagesFragment.getDayName(this,System.currentTimeMillis());
        m.inbox= 1;

        ContentValues contentValues = new ContentValues();
        contentValues.put(MessagesColumns.CONTENT, content);
        contentValues.put(MessagesColumns.SUBJECT, title);
        contentValues.put(MessagesColumns.TYPE, type);
//        Log.e("tyyype",type);

        contentValues.put(MessagesColumns.DATE, date);
        try {

            contentValues.put(MessagesColumns.IMAGE,b.getString("image"));
            DateFormat dateFormat = new SimpleDateFormat("MM");
            Date d = new Date();
            Log.d("Month",dateFormat.format(d));
            NotificationFragment.list.add(m);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    NotificationFragment.adapter.notifyDataSetChanged();
                    NotificationFragment.adapter.imm =b.getString("image");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
            MessagesFragment.list.add(m);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    MessagesFragment.adapter.notifyDataSetChanged();
                }
            });
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

}
