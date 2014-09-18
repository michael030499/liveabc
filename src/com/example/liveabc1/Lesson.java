package com.example.liveabc1;

//import tw.android.MainActivity;
//import tw.android.R;




import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

public class Lesson extends Activity {
	private ListView mListLesson;
	private Button btnAudioLesson;
	private CheckBox chkboxVideo;
	private boolean baudio;//video or audio
	public  static int m_index=100;//index to column slection,100 mean not been selected


	LessonAdapter adapter;

	
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	    Context myContext=getApplicationContext();
		Help.helpMenuHander(featureId,item, myContext );

		return super.onMenuItemSelected(featureId, item);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_help, menu);
        MenuItem itemFont=menu.getItem(0);
		if(AudioClass.bfontSizeBig){
			itemFont.setTitle (getString(R.string.menu_change_font_small));
		}//end if m_bfontSizeBig	
		else{
			itemFont.setTitle (getString(R.string.menu_change_font_big));
		}
			
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);
		//String str[]={"Lesson1","Lesson2","Lesson3"};
		mListLesson = (ListView) findViewById(R.id.listView1);
		btnAudioLesson=(Button)findViewById(R.id.butStart); 
		chkboxVideo=(CheckBox)findViewById(R.id.checkBoxVideo);		
        adapter=new LessonAdapter(this);

		mListLesson.setAdapter(adapter);
		mListLesson.setOnItemClickListener (listViewLessonOnItemClick );
	}
	

	
	private AdapterView.OnItemClickListener listViewLessonOnItemClick=new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			m_index=position+1;
			Log.i("liveabc","you sel"+ new Integer(position).toString());
			mListLesson.setItemChecked(position, true);
			mListLesson.setSelection(position);
			baudio=!(chkboxVideo.isChecked());
			Bundle data=new Bundle();
			Intent intent=new Intent(Lesson.this,AudioClass.class);
			data.putInt("index", m_index);//list start with 0;
			data.putBoolean("bAudio", baudio);
			intent.putExtras(data);
			Log.i("liveabc","Try start audio class");
			startActivity(intent);
			
		}
	
	
	};
}
