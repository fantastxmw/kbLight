package com.deskangel.kblight;


import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;


public class BkService extends Service
{
	ScreenEvent mScrEvnt = null;
    @Override
    public IBinder onBind(Intent arg0)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void onCreate()
    {
    	mScrEvnt = new ScreenEvent();
        super.onCreate();
    }

    @Override
    public void onDestroy()
    {
    	if (mScrEvnt != null)
    	{
    		try 
    		{
    			unregisterReceiver(mScrEvnt);
    		}
    		catch (Exception e)
    		{
    			e.printStackTrace();
    		}

    	}
        super.onDestroy();
    }

	// This is the old onStart method that will be called on the pre-2.0
	// platform.  On 2.0 or later we override onStartCommand() so this
	// method will not be called.
    @Override
    public void onStart(Intent intent, int startId)
    {
    	try 
    	{
	        
	        IntentFilter screenOnfilter = new IntentFilter(Intent.ACTION_SCREEN_ON);
	        registerReceiver(mScrEvnt, screenOnfilter);
	        
	        IntentFilter screenOfffilter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
	        registerReceiver(mScrEvnt, screenOfffilter);

    	}
		catch (Exception e)
		{
			e.printStackTrace();
		}


        super.onStart(intent, startId);
    }

}
