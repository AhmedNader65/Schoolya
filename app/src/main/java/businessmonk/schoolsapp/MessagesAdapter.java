package businessmonk.schoolsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
		TextView date = (TextView)v.findViewById(R.id.date);
		ImageView dir = (ImageView) v.findViewById(R.id.direction);
		date.setText(messageList.get(getCount() -i-1).date);
		int inbox = messageList.get(getCount() -i-1).inbox;
		if(inbox==0){
			dir.setImageDrawable(mContext.getResources().getDrawable(R.drawable.sent_icon));
		}else{
			dir.setImageDrawable(mContext.getResources().getDrawable(R.drawable.inbox_icon));
		}
		header.setText(messageList.get(getCount() -i-1).title);
		content.setText(messageList.get(getCount() -i-1).content);
		Log.e("count", String.valueOf(getCount()));

		return v;
	}
}
