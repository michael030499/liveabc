package com.example.liveabc1;


import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
			Log.i("liveabc","getview: "+position+"select pos:"+Lesson.m_index);			
			View vi=convertView;
	        if(convertView==null)
	            vi = inflater.inflate(R.layout.listitem, null);
	        int selPosition=position+1;
	        TextView text=(TextView)vi.findViewById(R.id.textView1);
	        ImageView imageView=(ImageView)vi.findViewById(R.id.imageView1);
	        CheckBox  lessonChkBox=(CheckBox)vi.findViewById(R.id.checkbox);
        	//lessonChkBox.setText("Check");//just crash for no reason
	        text.setText("Lesson"+selPosition);//TBC why it will get NULL some time
	        if((convertView!=null)&&(imageView!=null))
	        	loadImage(imageView,convertView, selPosition );
	        return vi;
	}

	/*
	 * Change the image to the size specify by THUMBNAIL_HEIGHT/WIDTH and load to imageView
	 */
	private void loadImage(ImageView imview, View view ,int indexLesson){
		
		final int THUMBNAIL_HEIGHT = 48;
		final int THUMBNAIL_WIDTH = 66;
		Bitmap imageBitmap;
		//Log.i("liveabc","loadimag for lesson"+indexLesson );
		//imageBitmap = BitmapFactory.decodeByteArray(mImageData, 0, mImageData.length);
		switch(indexLesson){
			case 1:
				 imageBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.lesson1);
				
				break;
			case 2:
				 imageBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.lesson2);
				
				break;
				
			case 3:
				 imageBitmap = BitmapFactory.decodeResource(view.getContext().getResources(), R.drawable.lesson3);				
			
				break;
		
			default:
				return;//no support item call. strange
		}

		Float width  = new Float(imageBitmap.getWidth());
		Float height = new Float(imageBitmap.getHeight());
		Float ratio = width/height;
		imageBitmap = Bitmap.createScaledBitmap(imageBitmap, (int)(THUMBNAIL_HEIGHT*ratio), THUMBNAIL_HEIGHT, false);

		int padding = (THUMBNAIL_WIDTH - imageBitmap.getWidth())/2;
		imview.setPadding(padding, 0, padding, 0);
		imview.setImageBitmap(imageBitmap);
		view.setMinimumHeight(120);//tbc
		
	}
	
}
