package businessmonk.schoolsapp.gcm_push_notification;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import businessmonk.schoolsapp.R;

/**
 * Created by om4rezz on 10/3/16.
 */

public class RegistrationService extends IntentService {

    public RegistrationService() {
        super("RegistrationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID myID = InstanceID.getInstance(this);

        Log.d("aaa", "bbbbbbbbbbbbb");

        String registrationToken = "";
        try {
            GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
           String regid = gcm.register("510636624197");
            registrationToken = myID.getToken(
                    getString(R.string.sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);
            Log.d("Registration Token", registrationToken);
            Log.e("tookennnn",regid );
            Log.d("hello from ", "service");

            GcmPubSub subscription = GcmPubSub.getInstance(this);

            subscription.subscribe(registrationToken, "/topics/schoolya", null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
