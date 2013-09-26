package com.deskangel.kblight;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.deskangel.lights.*;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context arg0, Intent arg1) {
		LightsController lc = new LightsController();
		lc.lockOnButtonBkLight();
	}

}
