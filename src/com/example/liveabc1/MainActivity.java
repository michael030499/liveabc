package com.example.liveabc1;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
//import android.view.View.
import android.widget.VideoView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		final VideoView video = (VideoView) findViewById(R.id.videoView1);
        Uri video1 = Uri.parse("android.resource://" + getPackageName() + "/" +com.example.liveabc1.R.raw.live3gp );
		final  Intent intent =new Intent(MainActivity.this,Lesson.class);

		video.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.i("liveabc","stop play");
				video.stopPlayback();
				startActivity(intent);
				return false;
			}
		});
        video.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				startActivity(intent);
				// TODO Auto-generated method stub
				
			}
		});
	    
	    video.setVideoURI(video1);
		Log.i("liveabc","video start");
        video.start();
	
	}

}
