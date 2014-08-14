package com.example.liveabc1;

//import tw.android.MainActivity;
//import tw.android.R;




import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Lesson extends Activity {
	private ListView mListLesson;
	private Button btnAudioLesson;
	private Button btnVideoLesson;
	private int m_index=100;//index to column slection,100 mean not been selected
	LessonAdapter adapter;

	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_help, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lesson);
		String str[]={"Lesson1","Lesson2","Lesson3"};
		mListLesson = (ListView) findViewById(R.id.listView1);
        adapter=new LessonAdapter(this, str);

//		ArrayAdapter<CharSequence> arrAdapRegion = 
//				ArrayAdapter.createFromResource(Lesson.this, 
//						R.array.lesson_list, 
//						android.R.layout.simple_list_item_single_choice);
//		mListLesson.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		mListLesson.setAdapter(adapter);
		mListLesson.setOnItemClickListener (listViewLessonOnItemClick );
		btnAudioLesson=(Button)findViewById(R.id.butStart); 
		btnVideoLesson=(Button)findViewById(R.id.butPause);
		btnAudioLesson.setOnClickListener(btnAudioOnClick);
		btnVideoLesson.setOnClickListener(btnAudioOnClick);
	}
	
	private View.OnClickListener btnAudioOnClick=new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			String displayString="You press :"; 
			boolean baudio=btnAudioLesson.isPressed();
			displayString=displayString+baudio;
			Log.i("liveabc",displayString);
			Bundle data=new Bundle();
			Intent intent=new Intent(Lesson.this,AudioClass.class);
			data.putInt("index", m_index);//list start with 0;
			data.putBoolean("bAudio", baudio);
			intent.putExtras(data);
			Log.i("liveabc","Try start audio class");
			startActivity(intent);
			
		}
	};
	
	
	private AdapterView.OnItemClickListener listViewLessonOnItemClick=new AdapterView.OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			
			m_index=position+1;
			Log.i("liveabc","you choice:");
			Log.i("liveabc",new Integer(position).toString());
			System.out.println("you press "+ position);
		}
	
	
	};
}
