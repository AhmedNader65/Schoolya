package businessmonk.schoolsapp.gcm_push_notification;

import android.content.Intent;

import com.google.android.gms.iid.InstanceIDListenerService;

/**
 * Created by om4rezz on 10/3/16.
 */

public class TokenRefreshListenerService extends InstanceIDListenerService {
    @Override
    public void onTokenRefresh() {
        Intent i = new Intent(this, RegistrationService.class);
        startService(i);
    }
}
