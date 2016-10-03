package businessmonk.schoolsapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import businessmonk.schoolsapp.Models.Message;
import businessmonk.schoolsapp.NotiAdapter;
import businessmonk.schoolsapp.R;

/**
 * Created by ahmed on 25/09/16.
 */
public class NotificationFragment extends Fragment {

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.notifications,container,false);
		ListView listView = (ListView) v.findViewById(R.id.noti_list);
		List<Message> list = new ArrayList<>();
		Message newMessage = new Message();
		newMessage.content = "Waiting for you";
		newMessage.title = "New event";
		newMessage.date = "NOV\n3";
		list.add(newMessage);
		newMessage = new Message();
		newMessage.content = "Waiting for you2";
		newMessage.title = "New event";
		newMessage.date = "NOV\n3";
		list.add(newMessage);
		newMessage = new Message();
		newMessage.content = "Waiting for you3";
		newMessage.title = "New event";
		newMessage.date = "NOV\n3";
		list.add(newMessage);
		newMessage = new Message();
		newMessage.content = "Waiting for you4";
		newMessage.title = "New event";
		newMessage.date = "NOV\n3";
		list.add(newMessage);
		newMessage = new Message();
		newMessage.content = "Waiting for you5";
		newMessage.title = "New event";
		newMessage.date = "NOV\n3";
		list.add(newMessage);
		newMessage = new Message();
		newMessage.content = "Waiting for you6";
		newMessage.title = "New event";
		newMessage.date = "NOV\n3";
		list.add(newMessage);
		NotiAdapter adapter = new NotiAdapter(getContext(),list);
		listView.setAdapter(adapter);
		return v;
	}

}
