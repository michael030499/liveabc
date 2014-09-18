package com.example.liveabc1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;


public class AudioClass extends Activity {
	private TextView txtView;
	private VideoView viView;
	private int m_index;//lesson  index 100 mean no choice;
	private AssetManager m_asManger;
	private InputStream inputStream=null ;
    private String MyStream;//File data container
    private boolean m_bAudio;//play audio or video
    private MediaPlayer player;
    //private SurfaceHolder holder;
    private Button btnPlay,btnPause;
    private Thread m_monitorThread;
	private SpannableString m_spString;//for update textString
	private int m_textLength;//textLength
	public static boolean bfontSizeBig=false;//decide the font to be big or small size

 
//==============================for handle audio player progress	
	
	private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            StyleSpan[] styleSpans;//for remove hight line text
            Bundle data = msg.getData();
            int duration=data.getInt("duration");
            int time=data.getInt("time");
			Log.i("liveabc","Get Message"+"duration"+time );  
			int timeZone=0;
			int timeZonePre=0;
			if(time <= (duration/3) ){
				timeZone=1;
			}//if time< 	
			else if ((time<duration/3*2)){
				timeZone=2;
			}//else if time<
			else 
			{
				timeZone=3;
			}
			if(timeZone!=timeZonePre){//update textView while move to diff time zone		

				timeZonePre=timeZone;
				switch (timeZone)
				{
					case 1:
						m_spString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),1, m_textLength/3, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
												
						break;
					case 2:
					    styleSpans = m_spString.getSpans(0, m_spString.length(), StyleSpan.class);
					    for (StyleSpan us : styleSpans) 
					    	m_spString.removeSpan(us);	
						m_spString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),m_textLength/3, m_textLength/3*2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
						
						break;
						
					case 3:
					   styleSpans = m_spString.getSpans(0, m_spString.length(), StyleSpan.class);
					    for (StyleSpan us : styleSpans) 
					    	m_spString.removeSpan(us);	

						m_spString.setSpan(new StyleSpan(android.graphics.Typeface.BOLD),m_textLength/3*2, m_textLength, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);		
						
						break;
						
				}//switch time zone		
				txtView.setText(m_spString);
			}
			
        }
    };

	
	class MonitorThread implements Runnable {

	
		@Override
		public void run() {
			try {
				Bundle timeBundle = new Bundle();
				int time=player.getCurrentPosition();
				int during=player.getDuration();
				timeBundle.putInt("duration",during);
				while((time+9000) <= during){//time is not procisely 
					time=player.getCurrentPosition();
					timeBundle.putInt("time",time );
					Message msg = new Message();
					msg.setData(timeBundle);
					mHandler.sendMessage(msg);//send message to  update UI in  thread
					Thread.sleep(3000);
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}


	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_help, menu);
	    Context myContext=getApplicationContext();
        Help.createMenu(menu, myContext);
		return super.onCreateOptionsMenu(menu);
	}
	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onMenuItemSelected(int, android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
	    Context myContext=getApplicationContext();
		Help.helpMenuHander(featureId,item, myContext );
		if(bfontSizeBig)
			txtView.setTextSize(25);
		else
			txtView.setTextSize(22);
		return super.onMenuItemSelected(featureId, item);


	}


	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("liveabc","Audio class startting");
		setContentView(R.layout.activity_audio_class);
		txtView=(TextView) findViewById(R.id.lessontextView);
		viView= (VideoView)findViewById(R.id.videoView1); 
		btnPlay=(Button)findViewById(R.id.butStart);
		btnPause=(Button)findViewById(R.id.butPause);
		player = new MediaPlayer();//use mediaplayer for audio and video
		Intent intent=getIntent();//get what the lesson select from user
		Bundle b=intent.getExtras();
		m_index=b.getInt("index");
		m_bAudio=b.getBoolean("bAudio");
		Log.i("liveabc",new Integer(m_index).toString());
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB){
			getActionBar().setTitle("Lesson"+m_index);
		}//make sure older version of android 
		
		
		if(m_bAudio){
			txtView.setVisibility(View.VISIBLE);
			if(bfontSizeBig)
				txtView.setTextSize(15);
			else
				txtView.setTextSize(12);			
			m_asManger=getAssets();	
			try
			{
			inputStream=m_asManger.open("Lesson"+m_index+"/"+"English.txt");
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			byte[] bytes = new byte[4096];
			int len;
			while ((len = inputStream.read(bytes)) > 0){
			  byteArrayOutputStream.write(bytes, 0, len);
			}		 
			 MyStream = new String(byteArrayOutputStream.toByteArray(), "UTF8");
			}catch (IOException e) {
				 e.printStackTrace();
				 MyStream = e.toString();
				 txtView.append("Open File error");
			}
			txtView.setText(MyStream);
	        m_monitorThread = new Thread(new MonitorThread() );//thread to monitor audio progress
			m_spString= new SpannableString(txtView.getText()) ;//string to update base on 	audio progress	
			m_textLength=m_spString.length();
			playAudio();
		}//if baudio
		else{
			txtView.setVisibility(View.INVISIBLE);
			viView.setVisibility(View.VISIBLE);			
			playVideo();//playvideo
			
		}
		
		//===========Event Handling
		btnPlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try {
					if(!player.isPlaying()){
						player.start();
					}
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					Log.i("liveabc","Player error start error");
					e.printStackTrace();
				}
			}
		});
		btnPause.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(player.isPlaying()){
					player.pause();
				}
			}
		});
		
		
	}
	

	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.i("liveabc","Audioclass onPause");

	      if(player != null){
	            player.stop();//stop play while not visible
	        }  
	      if (m_monitorThread != null) {
	          if (!m_monitorThread.isInterrupted()) {
	        	  m_monitorThread.interrupt();
	          }
	      }
 
	}




	/* (non-Javadoc)
	 * @see android.app.Activity#onStop()
	 */
	@Override
	protected void onStop() {
		Log.i("liveabc","Audioclass onStop");		
		super.onStop();
	      if(player != null){
	            //player.release();//remove to fix the issue switch App
	        }        
	}

	
private void playAudio(){

	AssetFileDescriptor afd;
	try {
		afd = getAssets().openFd("Lesson"+m_index+"/"+"audio.mp3");
	    player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
	    player.prepareAsync();
	    player.setOnPreparedListener(new OnPreparedListener() {
	 
	     @Override
	     public void onPrepared(MediaPlayer mp) {
	    	Log.i("liveabc","start to play"); 
	        mp.start();
	        m_monitorThread.start();//start a thread to monitor media player progress
	        //mHandler.post(m_monitorThread);
	     }
	  });
	 
	} catch (Exception e) { e.printStackTrace();}
	
	
}
	
/*
 * Play Video file  from assset folder
 */
	
	private void playVideo(){
		SurfaceHolder holder = viView.getHolder();
		holder.addCallback(new SurfaceHolder.Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				player.setDisplay(holder);
				AssetFileDescriptor afd;
				try {
					afd = getAssets().openFd("Lesson"+m_index+"/"+"video.3gp");
				    player.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(), afd.getLength());
				    player.prepareAsync();
				    player.setOnPreparedListener(new OnPreparedListener() {
				 
				     @Override
				     public void onPrepared(MediaPlayer mp) {//make sure we ready to play
				    	Log.i("liveabc","start to play"); 
				        mp.start();
				     }
				  });
				 
				} catch (Exception e) { e.printStackTrace();}
				
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				// TODO Auto-generated method stub
				
			}
		}	);
        

		
		
	}
	


	
	
}
