package com.example.liveabc1;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
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
    private SurfaceHolder holder ;
    private Button btnPlay,btnPause;
    

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_help, menu);
		return super.onCreateOptionsMenu(menu);
	}
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("liveabc","Audio class startting");
		setContentView(R.layout.activity_audio_class);
 		player = new MediaPlayer();//use mediaplayer for audio and video
		Intent intent=getIntent();
		Bundle b=intent.getExtras();
		m_index=b.getInt("index");
		m_bAudio=b.getBoolean("bAudio");
		Log.i("liveabc",new Integer(m_index).toString());
		getActionBar().setTitle("Lesson"+m_index);
		txtView=(TextView) findViewById(R.id.lessontextView);
		viView= (VideoView)findViewById(R.id.videoView1); 
		btnPlay=(Button)findViewById(R.id.butStart);
		btnPause=(Button)findViewById(R.id.butPause);
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
		
		if(m_bAudio){
			//viView.setVisibility(View.INVISIBLE);
			txtView.setVisibility(View.VISIBLE);
			txtView.append("==========Audio Class: "+m_index+"\n");	
			//float fontsize=txtView.getTextSize();
			//Log.i("liveavc","fontsize"+fontsize);
			if(Lesson.bfontSizeBig)
				txtView.setTextSize(25);
			else
				txtView.setTextSize(22);			
			//get txtfile form asset
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
				 // TODO Auto-generated catch block
				 e.printStackTrace();
				 MyStream = e.toString();
				 txtView.append("Open File error");
			}
			txtView.setText(MyStream);
			playAudio();
		}//if baudio
		else{
			txtView.setVisibility(View.INVISIBLE);
			viView.setVisibility(View.VISIBLE);	
			playVideo();//playvideo
			
		}
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
	     }
	  });
	 
	} catch (Exception e) { e.printStackTrace();}
	
	
}
	
/*
 * Play Video file  from assset folder
 */
	
	private void playVideo(){
		SurfaceHolder holder = viView.getHolder();
		//holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

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
				     public void onPrepared(MediaPlayer mp) {
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
	

	private String fileRead(InputStream inputStream){
		String myStream=null; 		
		return myStream;
	}
	
	
}
