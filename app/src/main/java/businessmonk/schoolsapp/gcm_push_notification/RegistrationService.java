package businessmonk.schoolsapp.gcm_push_notification;

import android.app.IntentService;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import org.json.JSONException;

import java.io.IOException;

import businessmonk.schoolsapp.R;
import businessmonk.schoolsapp.Volly.JsonRequest;

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
        String registrationToken = "";
        try {
            registrationToken = myID.getToken(
                    getString(R.string.sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);
            JsonRequest.postMessage(this, new JsonRequest.VolleyCallback() {
                @Override
                public void onSuccess(String result) throws JSONException {

                }
            },"_token",new String[]{"id","gcm_token"},new String[]{String.valueOf(PreferenceManager.getDefaultSharedPreferences(this).getInt("id",0)),registrationToken});
            GcmPubSub subscription = GcmPubSub.getInstance(this);

            subscription.subscribe(registrationToken, "/topics/schoolya", null);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
