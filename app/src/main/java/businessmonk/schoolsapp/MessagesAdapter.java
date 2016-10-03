package businessmonk.schoolsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import businessmonk.schoolsapp.Models.Message;

/**
 * Created by ahmed on 25/09/16.
 */
public class MessagesAdapter extends BaseAdapter {
	LayoutInflater inflater;
	List<Message> messageList;
	Context mContext;
	public MessagesAdapter(Context context, List<Message> messageList){
		this.inflater = LayoutInflater.from(context);
		this.mContext = context;
		this.messageList = messageList;
	}
	@Override
	public int getCount() {
		return messageList.size();
	}

	@Override
	public Object getItem(int i) {
		return null;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(int i, View view, ViewGroup viewGroup) {
		View v = inflater.inflate(R.layout.messages_item,viewGroup,false);
		TextView header = (TextView)v.findViewById(R.id.header);
		TextView content = (TextView)v.findViewById(R.id.content);
		header.setText(messageList.get(i).title);
		content.setText(messageList.get(i).content);
		Log.e("count", String.valueOf(getCount()));
		return v;
	}
}
