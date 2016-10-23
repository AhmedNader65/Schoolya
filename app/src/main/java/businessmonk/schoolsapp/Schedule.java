package businessmonk.schoolsapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import businessmonk.schoolsapp.Helpers.CapturePhotoUtils;
import businessmonk.schoolsapp.Models.Parent;

public class Schedule extends AppCompatActivity {
	ImageView schedule;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		schedule = (ImageView)findViewById(R.id.schedule);
		String sched = Parent.mySons.get(getIntent().getIntExtra("son",0)).Schedule;
		Glide.with(this).load(sched).into(schedule);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.download) {
			saveImageToGallery();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	private void saveImageToGallery(){
		schedule.setDrawingCacheEnabled(true);
		Bitmap b = schedule.getDrawingCache();
		CapturePhotoUtils.insertImage(getContentResolver(), b,"schedule"+getIntent().getIntExtra("son",0), "my son desc");
		Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show();
	}
}
