package com.onskreen.cornerstone.panel;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class StartupIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		//Set what activity should launch after boot completes
		Intent startupBootIntent = new Intent(context, CSPanel.class);
		startupBootIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(startupBootIntent);
	}

}
