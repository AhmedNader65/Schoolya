package businessmonk.schoolsapp.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import businessmonk.schoolsapp.MessageBody;
import businessmonk.schoolsapp.MessagesAdapter;
import businessmonk.schoolsapp.Models.Message;
import businessmonk.schoolsapp.NewMessage;
import businessmonk.schoolsapp.R;
import businessmonk.schoolsapp.data.MessagesColumns;
import businessmonk.schoolsapp.data.MessagesProvider;

/**
 * Created by ahmed on 25/09/16.
 */
public class MessagesFragment extends Fragment {
	public static MessagesAdapter adapter;
	public static List<Message> list;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.messages,container,false);
		ListView listView = (ListView) v.findViewById(R.id.messages_list);
		FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				startActivity(new Intent(getActivity(), NewMessage.class));
			}
		});
		list = new ArrayList<>();
		 adapter = new MessagesAdapter(getContext(),list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				startActivity(new Intent(getContext(), MessageBody.class).putExtra("body",list.get(i).content).putExtra("title",list.get(i).title));
			}
		});
		Cursor c = getActivity().getContentResolver().query(MessagesProvider.Messages.CONTENT_URI,null, MessagesColumns.TYPE+" != ?",new
		String[]{"public"},null);
		if(c.moveToFirst()){
			c.moveToFirst();
			do{
				Message m = new Message();
				m.content= c.getString(c.getColumnIndexOrThrow(MessagesColumns.CONTENT));
				m.title= c.getString(c.getColumnIndexOrThrow(MessagesColumns.SUBJECT));
				long x = Long.parseLong(c.getString(c.getColumnIndexOrThrow(MessagesColumns.DATE)));
				m.date= getDayName(getContext(),x);
				m.inbox= c.getInt(c.getColumnIndexOrThrow(MessagesColumns.INBOX));
				list.add(m);
				adapter.notifyDataSetChanged();
			}while (c.moveToNext());
		}

		return v;
	}
	public static String getDayName(Context context, long dateInMillis) {
		// If the date is today, return the localized version of "Today" instead of the actual
		// day name.

		Time t = new Time();
		t.setToNow();
		int julianDay = Time.getJulianDay(dateInMillis, t.gmtoff);
		int currentJulianDay = Time.getJulianDay(System.currentTimeMillis(), t.gmtoff);
		if (julianDay == currentJulianDay) {
			return context.getString(R.string.today);
		} else if ( julianDay == currentJulianDay +1 ) {
			return context.getString(R.string.tomorrow);
		} else {
			Time time = new Time();
			time.setToNow();
			// Otherwise, the format is just the day of the week (e.g "Wednesday".
			SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE");
			return dayFormat.format(dateInMillis);
		}
	}
}
