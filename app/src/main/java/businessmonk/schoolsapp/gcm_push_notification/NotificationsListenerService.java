package businessmonk.schoolsapp.gcm_push_notification;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;

import java.util.List;
import java.util.Set;

import businessmonk.schoolsapp.MainActivity;
import businessmonk.schoolsapp.R;


/**
 * Created by om4rezz on 10/3/16.
 */

public class NotificationsListenerService extends GcmListenerService {

    private int Teinda_NOTIFICATION_ID = 0;

    @Override
    public void onMessageReceived(String s, Bundle bundle) {
        super.onMessageReceived(s, bundle);
        boolean foregroud = isAppIsInBackground(getApplicationContext());
        if(!foregroud){
            Log.e("Hey","I am foreground "+foregroud);
            notify(bundle);
        }else{
            notify(bundle);
            Log.e("Hey","I am background "+foregroud);
        }
        for (String key: bundle.keySet())
        {
            Log.d ("myApplication", key + " is a key in the bundle");
        }
//        Log.e("Hey you", bundle.getal.toString());

        Set<String> keys = bundle.keySet();

        for(String key : keys){
            Log.w("Hey you", key);
        }
    }

    private void notify(Bundle bundle) {
        Context context = getApplicationContext();

        //build your notification here.
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(resultIntent);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setContentText(bundle.getString("body"));
        notificationBuilder.setContentTitle("built noti"+bundle.getString("title"));
        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        notificationBuilder.setContentIntent(pendingIntent);
        notificationBuilder.setSmallIcon(R.drawable.inbox_icon);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setSound( Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.notifysnd));
        notificationBuilder.setDefaults( Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE);
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
