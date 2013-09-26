package com.deskangel.kblight;

import com.deskangel.lights.LightsController;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.PreferenceActivity;

public class kbLight extends PreferenceActivity implements OnPreferenceChangeListener {
    /** Called when the activity is first created. */
	static Intent mBkService = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.options);

        registerCheckBoxChangeListener(R.string.pref_lock_on_key);
        registerCheckBoxChangeListener(R.string.pref_lock_on_with_screen_on_key);
        registerCheckBoxChangeListener(R.string.pref_lock_off_key);
    }
    
    private void registerCheckBoxChangeListener(int resId)
    {
        CheckBoxPreference cbPref = (CheckBoxPreference) findPreference(getString(resId));
        if (cbPref != null)
        {
            cbPref.setOnPreferenceChangeListener(this);
        }
    }

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
        String strPrefKey = preference.getKey();
        if (strPrefKey.equals(getString(R.string.pref_lock_on_key)))
        {
    		LightsController lc = new LightsController();
    		if ((Boolean)newValue)
    		{
    			lc.lockOnButtonBkLight();
    			
    			// make the mutex with the lock off option
                CheckBoxPreference cbPref = (CheckBoxPreference) findPreference(getString(R.string.pref_lock_off_key));
                cbPref.setChecked(false);
                
                // start the service
                CheckBoxPreference cbScreenOnPref = (CheckBoxPreference) findPreference(getString(R.string.pref_lock_on_with_screen_on_key));
                if (cbScreenOnPref.isChecked())
                {
                	startBkService(this);
                }
    		}
    		else
    		{
    			lc.unlockButtonBkLight();
    			stopBkService(this);
    		}
    		
    		return true;
        }
        else if (strPrefKey.equals(getString(R.string.pref_lock_on_with_screen_on_key)))
        {
        	if ((Boolean)newValue)
        	{
        		// start the service to listen the screen_on/screen_off event
        		startBkService(this);
        	}
        	else
        	{
        		// stop the service
        		stopBkService(this);
        	}
        	
        	return true;
        }
        else if (strPrefKey.equals(getString(R.string.pref_lock_off_key)))
        {
        	LightsController lc = new LightsController();
        	if ((Boolean)newValue)
        	{
        		lc.lockOffButtonBkLight();
        		
        		// make the mutex with the lock on option
                CheckBoxPreference cbPref = (CheckBoxPreference) findPreference(getString(R.string.pref_lock_on_key));
                cbPref.setChecked(false);

                // stop the service
                stopBkService(this);
        	}
        	else
        	{
        		lc.unlockButtonBkLight();
        	}
        	
        	return true;
        }
		return false;
	}
	
    private void startBkService(Context cntxt)
    {
        if (mBkService == null)
        {
            mBkService = new Intent(cntxt.getApplicationContext(), BkService.class);
            cntxt.startService(mBkService);
        }
    }
    
    private void stopBkService(Context cntxt)
    {
        if (mBkService != null)
        {
            cntxt.stopService(mBkService);
            mBkService = null;
        }
    }
}