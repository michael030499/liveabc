package com.example.liveabc1;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class LessonAdapter  extends BaseAdapter {
	   private Activity activity;//list view activity
       private String[] m_listItemString;
	   private static LayoutInflater inflater=null;
	    
	    public LessonAdapter (Activity a, String[] lessonString) {
	        activity = a;
	        m_listItemString=lessonString;
	        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    }

	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return m_listItemString.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
			Log.i("liveabc","getview"+position+"selpos:"+Lesson.m_index);			
			View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.listitem, null);
	        int selPosition=position++;
	        TextView text=(TextView)vi.findViewById(R.id.textView1);
	        ImageView image=(ImageView)vi.findViewById(R.id.image);
	        CheckBox  lessonChkBox=(CheckBox)vi.findViewById(R.id.checkbox);
        	//lessonChkBox.setText("Check");//just crash for no reason
	        text.setText("Lesson"+selPosition);
	        //imageLoader.DisplayImage(data[position], image);
	        return vi;
	}

}
