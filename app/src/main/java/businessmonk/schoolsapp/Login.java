package businessmonk.schoolsapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import businessmonk.schoolsapp.Models.Parent;
import businessmonk.schoolsapp.Volly.JsonRequest;

public class Login extends AppCompatActivity {
	static RelativeLayout container;
	static ProgressDialog dialog;
	SharedPreferences sp ;
	SharedPreferences.Editor edit;
	private ImageView login;
	private EditText user,pass;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		edit = sp.edit();
		login = (ImageView)findViewById(R.id.login);
		container = (RelativeLayout)findViewById(R.id.login_container) ;
		user = (EditText) findViewById(R.id.user_name);
		pass = (EditText)findViewById(R.id.password);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				checkUserName(user.getText().toString(),pass.getText().toString());
			}
		});
	}
	private void checkUserName(String user,String password){
		if(user.length()==11){
			checkPassword(user,password);
		}else{
			Toast.makeText(this,"Mobile number is incorrect!",Toast.LENGTH_LONG).show();
		}
	}
	private void checkPassword(String user,String password){
		if(password.length()>5){
			attempLogin(user, password);
		}else{
			Toast.makeText(this,"password must be at least 6 letters",Toast.LENGTH_LONG).show();
		}
	}
	private void attempLogin(String mob,String password){
		container.setVisibility(View.GONE);
		dialog = ProgressDialog.show(this,"Logging..","Please wait..",true,false);
		JsonRequest.postLogin(Login.this, mob, password, new JsonRequest.VolleyCallback() {
			@Override
			public void onSuccess(String result) throws JSONException {
				JSONObject object = new JSONObject(result);
				Parent.id=object.getInt("id");
				edit.putInt("id",object.getInt("id"));
				edit.putBoolean("logged",true);
				edit.commit();
				dialog.dismiss();
				startActivity(new Intent(Login.this,MainActivity.class));
				Login.this.finish();
			}
		});
	}
	public static void dismiss(){
		try {
			dialog.dismiss();
			container.setVisibility(View.VISIBLE);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
