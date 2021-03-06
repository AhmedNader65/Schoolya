package businessmonk.schoolsapp.Volly;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

import businessmonk.schoolsapp.Login;

/**
 * Created by ahmed on 26/07/16.
 */
public class JsonRequest {
	private static final String LOG_TAG = JsonRequest.class.getSimpleName();
	public static String webServiceUrl = "http://192.168.1.2:9090/api";

	public interface VolleyCallback{
		void onSuccess(String result) throws JSONException;
	}
	public static void postMessage(final Context context, final VolleyCallback callback,String controller, final String[] queryname, final String[] queryVal){
		RequestQueue queue = Volley.newRequestQueue(context);
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
		Uri.Builder uri  = Uri.parse(webServiceUrl).buildUpon();
		uri.appendPath(controller);
		Log.e("route ",uri.toString());
		StringRequest sr = new StringRequest(Request.Method.POST,uri.toString(), new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					callback.onSuccess(response);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				if (error instanceof TimeoutError || error instanceof NoConnectionError) {
					Toast.makeText(context,
							"No Connection",
							Toast.LENGTH_LONG).show();
				} else if (error instanceof AuthFailureError) {
					Toast.makeText(context,
							"Check username & password",
							Toast.LENGTH_LONG).show();
				} else if (error instanceof ServerError) {
					Toast.makeText(context,
							"SORRY!, We have a problem try again",
							Toast.LENGTH_LONG).show();
				} else if (error instanceof NetworkError) {
					Toast.makeText(context,
							"Network Error try again",
							Toast.LENGTH_LONG).show();
				} else if (error instanceof ParseError) {
					Toast.makeText(context,
							"ooo we have a problem",
							Toast.LENGTH_LONG).show();
				}
			}
		}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> params = new HashMap<String, String>();
				for(int i = 0 ; i<queryname.length;i++){
					params.put(queryname[i], queryVal[i]);
				}
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String,String> params = new HashMap<String, String>();
				params.put("Content-Type","application/x-www-form-urlencoded");
				return params;
			}
		};
		queue.add(sr);
	}
	public static void getDataWithIDArray(Context context,String parameter,String id,final VolleyCallback callback){
		Uri.Builder uri  = Uri.parse(webServiceUrl + "/").buildUpon();
		uri.appendPath(parameter);
		uri.appendPath(id);
		String url = uri.toString();
		Log.v(LOG_TAG,url);
		JsonArrayRequest jsObjRequest = new JsonArrayRequest
				(Request.Method.GET, url.toString(), null, new Response.Listener<JSONArray>() {

					@Override
					public void onResponse(JSONArray response) {
						try {
							callback.onSuccess(response.toString());
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e(LOG_TAG,"error  "+error);

					}
				});
		jsObjRequest.setRetryPolicy(new RetryPolicy() {
			@Override
			public int getCurrentTimeout() {
				return 50000;
			}

			@Override
			public int getCurrentRetryCount() {
				return 50000;
			}

			@Override
			public void retry(VolleyError error) throws VolleyError {

			}
		});
// Access the RequestQueue through your singleton class.
		MySingleton.getInstance(context).addToRequestQueue(jsObjRequest);
	}
	public static void postLogin(final Context context, final String userName, final String password, final VolleyCallback callback){
		RequestQueue queue = Volley.newRequestQueue(context);
		String url = webServiceUrl + "/login";
		Log.v("login url "," ==> "+url);
		StringRequest sr = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					if(response.contains("wrong")){
						Toast.makeText(context, "wrong mobile number", Toast.LENGTH_SHORT).show();
						((Activity) context).finish();
						context.startActivity(new Intent(context,Login.class));
					}else {
						callback.onSuccess(response);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Login.dismiss();
				if (error instanceof TimeoutError || error instanceof NoConnectionError) {
					Toast.makeText(context,
							"No Connection",
							Toast.LENGTH_LONG).show();
				} else if (error instanceof AuthFailureError) {
					Toast.makeText(context,
							"Check username & password",
							Toast.LENGTH_LONG).show();
				} else if (error instanceof ServerError) {
					Toast.makeText(context,
							"SORRY!, We have a problem try again",
							Toast.LENGTH_LONG).show();
				} else if (error instanceof NetworkError) {
					Toast.makeText(context,
							"Network Error try again",
							Toast.LENGTH_LONG).show();
				} else if (error instanceof ParseError) {
					Toast.makeText(context,
							"ooo we have a problem",
							Toast.LENGTH_LONG).show();
				}
			}
		}){
			@Override
			protected Map<String,String> getParams(){
				Map<String,String> params = new HashMap<String, String>();
				params.put("phone_number",userName);
				params.put("password",password);;
				return params;
			}

			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				Map<String,String> params = new HashMap<String, String>();
				params.put("Content-Type","application/x-www-form-urlencoded");
				return params;
			}
		};
		queue.add(sr);
	}
}
