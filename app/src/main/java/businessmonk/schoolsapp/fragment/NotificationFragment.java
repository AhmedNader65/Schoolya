package businessmonk.schoolsapp.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import businessmonk.schoolsapp.Models.Message;
import businessmonk.schoolsapp.NotiAdapter;
import businessmonk.schoolsapp.R;
import businessmonk.schoolsapp.data.MessagesColumns;
import businessmonk.schoolsapp.data.MessagesProvider;

import static businessmonk.schoolsapp.fragment.MessagesFragment.getDayName;

/**
 * Created by ahmed on 25/09/16.
 */
public class NotificationFragment extends Fragment {
	public static ArrayList<Message> list;
	public static NotiAdapter adapter;
	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.notifications, container, false);
		ListView listView = (ListView) v.findViewById(R.id.noti_list);
		list = new ArrayList<>();
		adapter = new NotiAdapter(getContext(), list);
		listView.setAdapter(adapter);
		Cursor c = getActivity().getContentResolver().query(MessagesProvider.Messages.CONTENT_URI, null, MessagesColumns.TYPE + " = ?", new
				String[]{"public"}, null);
		if (c.moveToFirst()) {
			c.moveToFirst();
			do {
				Message m = new Message();
				m.content = c.getString(c.getColumnIndexOrThrow(MessagesColumns.CONTENT));
				m.title = c.getString(c.getColumnIndexOrThrow(MessagesColumns.SUBJECT));
				long x = Long.parseLong(c.getString(c.getColumnIndexOrThrow(MessagesColumns.DATE)));
				m.date = getDayName(getContext(), x);
				m.inbox = c.getInt(c.getColumnIndexOrThrow(MessagesColumns.INBOX));
				m.img = c.getString(c.getColumnIndexOrThrow(MessagesColumns.IMAGE));
				list.add(m);
				adapter.notifyDataSetChanged();
			} while (c.moveToNext());
		}
		return v;
	}

}
