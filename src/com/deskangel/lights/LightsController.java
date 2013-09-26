package com.deskangel.lights;

import java.io.DataOutputStream;
import java.io.IOException;
import android.os.Looper;
import android.util.Log;

public class LightsController {
    static String LCD_BUTTON_BACKLIGHT = "/sys/class/leds/button-backlight/brightness";
    
    public void lockOnButtonBkLight()
    {
    	String strCommand = null;
    	
		strCommand = "chmod 644 " + LCD_BUTTON_BACKLIGHT + "\n";
		strCommand += "echo 1 > " +  LCD_BUTTON_BACKLIGHT + "\n";
		strCommand += "chmod 444 " + LCD_BUTTON_BACKLIGHT + "\n";
		
		execCmdThread(strCommand);
    }
    
    public void lockOffButtonBkLight()
    {
    	String strCommand = null;
    	
		strCommand = "chmod 644 " + LCD_BUTTON_BACKLIGHT + "\n";
		strCommand += "echo 0 > " +  LCD_BUTTON_BACKLIGHT + "\n";
		strCommand += "chmod 444 " + LCD_BUTTON_BACKLIGHT + "\n";
		
		execCmdThread(strCommand);
    }
    
    public void unlockButtonBkLight()
    {
    	String strCommand = null;
    	
		strCommand = "chmod 644 " + LCD_BUTTON_BACKLIGHT + "\n";
		
		execCmdThread(strCommand);
    }
    
    private void execCmdThread(String strCommand)
    {
    	final String strCmd = strCommand;
    	
        new Thread()
        {
        	public void run()
        	{
        		Looper.prepare();
        		execCmd(strCmd);
        	}
        }.start();

    }

	private boolean execCmd(String strCmd) {
		if (strCmd == null || strCmd.length() <= 0) {
			return false;
		}
		
		boolean bResult = false;

		Runtime r = Runtime.getRuntime();
		DataOutputStream os = null;
		Process p = null;

		try {
			// ask for the root permission
			p = r.exec("su");

			os = new DataOutputStream(p.getOutputStream());

			os.writeBytes(strCmd + "\n");

			os.writeBytes("exit\n");
			os.flush();

			// this is very necessary, otherwise, the command will not execute
			// successfully.
			p.waitFor();

			bResult = true;
		} catch (IOException e) {
			e.printStackTrace();
			Log.v("lights", e.getLocalizedMessage());
		} catch (SecurityException e) {
			e.printStackTrace();
			Log.v("lights", e.getLocalizedMessage());

		} catch (InterruptedException e) {
			e.printStackTrace();
			Log.v("lights", e.getLocalizedMessage());

		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
					Log.v("lights", e.getLocalizedMessage());

				}
			}

			if (p != null) {
				p.destroy();
			}
		}

		return bResult;
	}

}
