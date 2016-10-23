package businessmonk.schoolsapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import businessmonk.schoolsapp.Models.Parent;
import businessmonk.schoolsapp.Models.Student;
import businessmonk.schoolsapp.Volly.JsonRequest;
import businessmonk.schoolsapp.fragment.MessagesFragment;
import businessmonk.schoolsapp.fragment.NotificationFragment;
import businessmonk.schoolsapp.gcm_push_notification.RegistrationService;

public class MainActivity extends AppCompatActivity {
	public static String webServiceUrl = "http://192.168.1.5:9090/";

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a
	 * {@link FragmentPagerAdapter} derivative, which will keep every
	 * loaded fragment in memory. If this becomes too memory intensive, it
	 * may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;
	SharedPreferences sp;
	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	int sons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = PreferenceManager.getDefaultSharedPreferences(this);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.container);

		// start regestration service
		Intent i = new Intent(this, RegistrationService.class);
		startService(i);
		//end


		JsonRequest.getDataWithIDArray(this, "parent/student", String.valueOf(Parent.id), new JsonRequest.VolleyCallback() {
			@Override
			public void onSuccess(String result) throws JSONException {
				Log.v("my sons",result);
				JSONArray mySons = new JSONArray(result);
				Log.v("my sons", String.valueOf(mySons.length()));
				sons = mySons.length();
				Log.e("length", String.valueOf(mySons.length()));
				for(int i = 0;i<mySons.length();i++){
					JSONObject sonObj = mySons.getJSONObject(i);
					Student son = new Student();
					son.id = sonObj.getInt("id");
					son.name =  sonObj.getString("name");
					son.avatar =webServiceUrl+ sonObj.getString("avatar");
					son.class_id = sonObj.getInt("classroom_id");
					son.class_name = sonObj.getString("classname");
					Parent.mySons.add(son);
				}
				mViewPager.setAdapter(mSectionsPagerAdapter);
				TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
				tabLayout.setupWithViewPager(mViewPager);
			}
		});



	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			if(position==2) {
				return  new NotificationFragment();
			}else if(position==1){
				return  new MessagesFragment();
			}else {
					return PlaceholderFragment.newInstance(sons);
			}
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			switch (position) {
				case 0:
					return "Home";
				case 1:
					return "Messages";
				case 2:
					return "Notifications";
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}
		SharedPreferences sp;
		/**
		 * Returns a new instance of this fragment for the given section
		 * number.
		 */
		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
		                         Bundle savedInstanceState) {
			Bundle bundle =getArguments();
			int sonsNum = bundle.getInt(ARG_SECTION_NUMBER);
			getSchedule();
			View rootView;
			if(sonsNum==1) {
				rootView = inflater.inflate(R.layout.fragment_main, container, false);
				ImageView kidPic = (ImageView) rootView.findViewById(R.id.kid_pic);
				ImageView kidSchudule = (ImageView) rootView.findViewById(R.id.kid_schedule);
				TextView kidName = (TextView) rootView.findViewById(R.id.kid_name);
				TextView kidClass = (TextView) rootView.findViewById(R.id.kid_class);
				ArrayList<Student> sons =Parent.mySons;
				Student kid = sons.get(0);
				Glide.with(getContext()).load(kid.avatar).into(kidPic);
				kidName.setText(kid.name);
				kidClass.setText(kid.class_name);
				kidSchudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(Parent.mySons.get(0).Schedule.equals("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",0));
						}
					}
				});
				ImageButton Send = (ImageButton) rootView.findViewById(R.id.send_note);
				Send.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						startActivity(new Intent(getActivity(), NewMessage.class));
					}
				});
				return rootView;
			}else if(sonsNum ==2){
				rootView = inflater.inflate(R.layout.fragment_main2, container, false);
				ImageView kidPic = (ImageView) rootView.findViewById(R.id.kid_pic);
				ImageView kidPic2 = (ImageView) rootView.findViewById(R.id.kid2_pic);
				ImageView kidSchudule = (ImageView) rootView.findViewById(R.id.kid_schedule);
				ImageView kid2Schudule = (ImageView) rootView.findViewById(R.id.kid2_schedule);
				TextView kidName = (TextView) rootView.findViewById(R.id.kid_name);
				TextView kidName2 = (TextView) rootView.findViewById(R.id.kid2_name);
				TextView kidClass = (TextView) rootView.findViewById(R.id.kid_class);
				TextView kidClass2 = (TextView) rootView.findViewById(R.id.kid2_class);
				ArrayList<Student> sons =Parent.mySons;
				Student kid = sons.get(0);
				Glide.with(getContext()).load(kid.avatar).into(kidPic);
				kidName.setText(kid.name);
				kidClass.setText(kid.class_name);
				kid = sons.get(1);
				Glide.with(getContext()).load(kid.avatar).into(kidPic2);
				Log.e("avatar",kid.avatar);
				kidName2.setText(kid.name);
				kidClass2.setText(kid.class_name);
				kidSchudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Log.e("sec",Parent.mySons.get(0).Schedule);
						if(Parent.mySons.get(0).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
							Log.e("mySons ooo",Parent.mySons.get(0).Schedule);
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",0));
						}
					}
				});
				kid2Schudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						Log.e("sec",Parent.mySons.get(1).Schedule);
						if(Parent.mySons.get(1).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",1));
						}
					}
				});
				ImageButton Send = (ImageButton) rootView.findViewById(R.id.send_note);
				Send.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						startActivity(new Intent(getActivity(), NewMessage.class));
					}
				});
				return rootView;
			}else if(sonsNum ==3){
				rootView = inflater.inflate(R.layout.fragment_main3, container, false);
				ImageView kidPic = (ImageView) rootView.findViewById(R.id.kid_pic);
				ImageView kidPic2 = (ImageView) rootView.findViewById(R.id.kid2_pic);
				ImageView kidPic3 = (ImageView) rootView.findViewById(R.id.kid3_pic);
				ImageView kidSchudule = (ImageView) rootView.findViewById(R.id.kid_schedule);
				ImageView kid2Schudule = (ImageView) rootView.findViewById(R.id.kid2_schedule);
				ImageView kid3Schudule = (ImageView) rootView.findViewById(R.id.kid3_schedule);
				TextView kidName = (TextView) rootView.findViewById(R.id.kid_name);
				TextView kidName2 = (TextView) rootView.findViewById(R.id.kid2_name);
				TextView kidName3 = (TextView) rootView.findViewById(R.id.kid3_name);
				TextView kidClass = (TextView) rootView.findViewById(R.id.kid_class);
				TextView kidClass2 = (TextView) rootView.findViewById(R.id.kid2_class);
				TextView kidClass3 = (TextView) rootView.findViewById(R.id.kid3_class);
				ArrayList<Student> sons =Parent.mySons;
				Student kid = sons.get(0);
				Glide.with(getContext()).load(kid.avatar).into(kidPic);
				kidName.setText(kid.name);
				kidClass.setText(kid.class_name);
				kid = sons.get(1);
				Glide.with(getContext()).load(kid.avatar).into(kidPic2);
				kidName2.setText(kid.name);
				kidClass2.setText(kid.class_name);
				kid = sons.get(2);
				Glide.with(getContext()).load(kid.avatar).into(kidPic3);
				kidName3.setText(kid.name);
				kidClass3.setText(kid.class_name);
				kidSchudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(Parent.mySons.get(0).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",0));
						}
					}
				});
				kid2Schudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(Parent.mySons.get(1).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",1));
						}
					}
				});
				kid3Schudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(Parent.mySons.get(2).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",2));
						}
					}
				});
				ImageButton Send = (ImageButton) rootView.findViewById(R.id.send_note);
				Send.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						startActivity(new Intent(getActivity(), NewMessage.class));
					}
				});
				return rootView;
			}else if(sonsNum ==4){
				rootView = inflater.inflate(R.layout.fragment_main4, container, false);
				ImageView kidPic = (ImageView) rootView.findViewById(R.id.kid_pic);
				ImageView kidPic2 = (ImageView) rootView.findViewById(R.id.kid2_pic);
				ImageView kidPic3 = (ImageView) rootView.findViewById(R.id.kid3_pic);
				ImageView kidPic4 = (ImageView) rootView.findViewById(R.id.kid4_pic);
				ImageView kidSchudule = (ImageView) rootView.findViewById(R.id.kid_schedule);
				ImageView kid2Schudule = (ImageView) rootView.findViewById(R.id.kid2_schedule);
				ImageView kid3Schudule = (ImageView) rootView.findViewById(R.id.kid3_schedule);
				ImageView kid4Schudule = (ImageView) rootView.findViewById(R.id.kid4_schedule);
				TextView kidName = (TextView) rootView.findViewById(R.id.kid_name);
				TextView kidName2 = (TextView) rootView.findViewById(R.id.kid2_name);;
				TextView kidName3 = (TextView) rootView.findViewById(R.id.kid3_name);;
				TextView kidName4 = (TextView) rootView.findViewById(R.id.kid4_name);;
				TextView kidClass = (TextView) rootView.findViewById(R.id.kid_class);
				TextView kidClass2 = (TextView) rootView.findViewById(R.id.kid2_class);
				TextView kidClass3 = (TextView) rootView.findViewById(R.id.kid3_class);
				TextView kidClass4 = (TextView) rootView.findViewById(R.id.kid4_class);
				ArrayList<Student> sons =Parent.mySons;
				Student kid = sons.get(0);
				Glide.with(getContext()).load(kid.avatar).into(kidPic);
				kidName.setText(kid.name);
				kidClass.setText(kid.class_name);
				kid = sons.get(1);
				Glide.with(getContext()).load(kid.avatar).into(kidPic2);
				Log.e("avatar",kid.avatar);
				kidName2.setText(kid.name);
				kidClass2.setText(kid.class_name);
				kid = sons.get(2);
				Glide.with(getContext()).load(kid.avatar).into(kidPic3);
				kidName3.setText(kid.name);
				kidClass3.setText(kid.class_name);
				kid = sons.get(3);
				Glide.with(getContext()).load(kid.avatar).into(kidPic4);
				kidName4.setText(kid.name);
				kidClass4.setText(kid.class_name);
				kidSchudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(Parent.mySons.get(0).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",0));
						}
					}
				});
				kid2Schudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(Parent.mySons.get(1).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",1));
						}
					}
				});
				kid3Schudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(Parent.mySons.get(2).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",2));
						}
					}
				});
				kid4Schudule.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						if(Parent.mySons.get(3).Schedule.contains("no_schedule")) {
							Toast.makeText(getContext(), "No schedule available!", Toast.LENGTH_SHORT).show();
						}else {
							startActivity(new Intent(getActivity(), Schedule.class).putExtra("son",3));
						}
					}
				});
				ImageButton Send = (ImageButton) rootView.findViewById(R.id.send_note);
				Send.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View view) {
						startActivity(new Intent(getActivity(), NewMessage.class));
					}
				});
				return rootView;
			}
			return null;
		}
		public void getSchedule(){
			JsonRequest.getDataWithIDArray(getContext(), "parent/student/schedule", String.valueOf(Parent.id), new JsonRequest.VolleyCallback() {
				@Override
				public void onSuccess(String result) throws JSONException {
					JSONArray array = new JSONArray(result);
					for(int i = 0 ; i <array.length();i++) {
						JSONObject son = array.getJSONObject(i);
						String sc = son.getString("schedule");
						String schedule;
						if(sc.equals("no_schedule")){
							schedule = sc;
						}else {
							schedule = webServiceUrl +sc;
						}
						Parent.mySons.get(i).Schedule = schedule;
						Log.e("mySons json",schedule);
						Log.e("mySons schedule",Parent.mySons.get(i).Schedule );

					}
				}
			});
		}
	}
}
