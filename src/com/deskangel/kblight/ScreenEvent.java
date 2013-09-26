package com.deskangel.kblight;

import com.deskangel.lights.LightsController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.SensorEventListener;
import android.os.Looper;

public class ScreenEvent extends BroadcastReceiver
{
	SensorEventListener sensorEventListener = null;
	Context cntxt = null;
	
	String mStrAction = null;
	
    @Override
    public void onReceive(Context context, Intent intent)
    {
    	mStrAction = intent.getAction();
    	
    	cntxt = context;
    	new Thread()
    	{
    		public void run()
    		{
    			Looper.prepare();
    			changeBkLightOfButtons(cntxt, mStrAction);
    		}
    	}.start();


    }

	private void changeBkLightOfButtons(Context context, String strAction) {
		
		if (strAction.equals(Intent.ACTION_SCREEN_OFF))
		{
			LightsController lc = new LightsController();
			lc.lockOffButtonBkLight();
		}
		else if (strAction.equals(Intent.ACTION_SCREEN_ON))
		{
			LightsController lc = new LightsController();
			lc.lockOnButtonBkLight();
		}
	}

}
