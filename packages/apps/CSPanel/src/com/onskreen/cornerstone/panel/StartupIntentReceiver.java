package com.onskreen.cornerstone.panel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class StartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//Check that Cornerstone should start on boot
		SharedPreferences settings = context.getSharedPreferences(CSSettings.CS_PREFS, Context.MODE_MULTI_PROCESS);
		if (settings.getBoolean("startup", context.getResources().getBoolean(R.bool.startup))) {
			//Set what activity should launch after boot completes
			Intent startupBootIntent = new Intent(context, CSPanel.class);
			startupBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(startupBootIntent);
		}
	}
}
