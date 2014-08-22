
package com.example.liveabc1;
//import com.example.liveabc1.AudioClass;
import com.example.liveabc1.R;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public  class Help {
    
	
	/*
	 * Hanld the onCreateOptionsMenu for help menu
	 */
	
	public static void  createMenu(Menu menu ,Context myContext ){
        MenuItem itemFont=menu.getItem(0);
		if(AudioClass.bfontSizeBig){
			itemFont.setTitle (myContext.getString(R.string.menu_change_font_small));
		}//end if m_bfontSizeBig	
		else{
			itemFont.setTitle (myContext.getString(R.string.menu_change_font_big));
		}

		
	}
	
	
	/*
	 * Hanlde the onMenuItemSelected for help menu 
	 */
	public  static void helpMenuHander(int featureId, MenuItem item ,Context myContext){
  		switch (item.getItemId ()) { 
		case R.id.menuItemFont: 
			if(AudioClass.bfontSizeBig){
				try {
					item.setTitle (myContext.getString(R.string.menu_change_font_big));
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					Log.i("liveabc"," help access resrouce fail");
					e.printStackTrace();
				}
				AudioClass.bfontSizeBig=false;
			}//end if m_bfontSizeBig	
			else{
				try {
					item.setTitle (myContext.getString(R.string.menu_change_font_small));
				} catch (NotFoundException e) {
					// TODO Auto-generated catch block
					Log.i("liveabc"," help access resrouce fail");
					e.printStackTrace();
				}
				AudioClass.bfontSizeBig=true;									
			}
				
			break;
	}		  
    	  
      }
}
