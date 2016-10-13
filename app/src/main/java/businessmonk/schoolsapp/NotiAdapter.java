package businessmonk.schoolsapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import businessmonk.schoolsapp.Models.Message;

/**
 * Created by ahmed on 25/09/16.
 */
public class NotiAdapter extends BaseAdapter {
	LayoutInflater inflater;
	List<Message> messageList;
	public static String imm= null;
	Context mContext;
	public NotiAdapter(Context context, List<Message> messageList){
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
		View v = inflater.inflate(R.layout.notification_item,viewGroup,false);
		TextView header = (TextView)v.findViewById(R.id.header);
		TextView date = (TextView)v.findViewById(R.id.date);
		TextView content = (TextView)v.findViewById(R.id.content);
		ImageView studentPic = (ImageView)v.findViewById(R.id.kid_pic);
		header.setText(messageList.get(getCount()-i-1).title);
		try {
			Log.e("date",messageList.get(getCount()-i-1).date);
			String[] dateArray = messageList.get(getCount()-i-1).date.split(",");
			date.setText(dateArray[1] + "\n" + dateArray[0]);
		}catch (Exception e){
			e.printStackTrace();
			date.setText(messageList.get(getCount()-i-1).date);
		}
		content.setText(messageList.get(getCount()-i-1).content);
		try{
			if(messageList.get(getCount()-i-1).img.length()>1){
				Glide.with(mContext).load(messageList.get(getCount()-i-1).img).into(studentPic);
			}else{
				studentPic.setVisibility(View.GONE);
			}
		}catch (Exception e){
			if(imm!=null){
				Glide.with(mContext).load(imm).into(studentPic);
			}else {
				studentPic.setVisibility(View.GONE);
			}
			}
//		studentPic.setImageDrawable(mContext.getResources().getDrawable(R.drawable.kid));
		Log.e("count", String.valueOf(getCount()));
		return v;
	}
}
