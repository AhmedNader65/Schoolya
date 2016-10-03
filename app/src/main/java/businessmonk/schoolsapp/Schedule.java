package businessmonk.schoolsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

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
}
