package businessmonk.schoolsapp;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import businessmonk.schoolsapp.Models.Message;
import businessmonk.schoolsapp.Models.Parent;
import businessmonk.schoolsapp.Volly.JsonRequest;
import businessmonk.schoolsapp.data.MessagesColumns;
import businessmonk.schoolsapp.data.MessagesProvider;
import businessmonk.schoolsapp.fragment.MessagesFragment;

public class NewMessage extends AppCompatActivity {

	private Spinner spinner;
	EditText sub , content;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_message);
		ActionBar bar = getSupportActionBar();
		bar.setBackgroundDrawable(getResources().getDrawable(R.color.blue_app));
		sub = (EditText) findViewById(R.id.subject) ;
		content = (EditText) findViewById(R.id.message) ;

		spinner = (Spinner) findViewById(R.id.spinner);
		List<String> list = new ArrayList<String>();
		list.add("Public message");
		for(int i = 0 ; i < Parent.mySons.size();i++){
			list.add(Parent.mySons.get(i).name);
		}
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, list);
		dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.new_message, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		//noinspection SimplifiableIfStatement
		if (id == R.id.send_message) {
			final String subject = sub.getText().toString();
			final String content_text = content.getText().toString();
			if(subject.length()>0) {
				if(content_text.length()>0) {
					if(spinner.getSelectedItemPosition() == 0) {
						JsonRequest.postMessage(this, new JsonRequest.VolleyCallback() {
							@Override
							public void onSuccess(String result) throws JSONException {
								insertData(subject,content_text,"message", String.valueOf(System.currentTimeMillis()));
								NewMessage.this.finish();
							}
						}, "message", new String[]{"parent_id", "subject", "content"}, new String[]{String.valueOf(Parent.id)
								, subject, content_text});
					}else{
						JsonRequest.postMessage(this, new JsonRequest.VolleyCallback() {
							@Override
							public void onSuccess(String result) throws JSONException {
								insertData(subject,content_text,"message", String.valueOf(System.currentTimeMillis()));
								NewMessage.this.finish();
							}
						}, "message", new String[]{"parent_id", "subject", "content", "student_id"}, new String[]{String.valueOf(Parent.id)
								, sub.getText().toString(), content.getText().toString(),
								String.valueOf(Parent.mySons.get(spinner.getSelectedItemPosition()-1).id)});

					}
				}else{
					Toast.makeText(NewMessage.this, "Content can't be empty!", Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(NewMessage.this, "Subject can't be empty!", Toast.LENGTH_SHORT).show();
			}
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	public void insertData(String title,String content,String type,String date) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(MessagesColumns.CONTENT, content);
		contentValues.put(MessagesColumns.SUBJECT, title);
		contentValues.put(MessagesColumns.TYPE, type);
		contentValues.put(MessagesColumns.DATE, date);
		contentValues.put(MessagesColumns.INBOX, 0);
		getApplicationContext().getContentResolver().insert(MessagesProvider.Messages.CONTENT_URI, contentValues);
		Message m = new Message();
		m.content=content;
		m.title= title;
		m.date= MessagesFragment.getDayName(this,System.currentTimeMillis());
		m.inbox= 1;
		MessagesFragment.list.add(m);
		MessagesFragment.adapter.notifyDataSetChanged();

	}
}
