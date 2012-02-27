package com.onskreen.cornerstone.panel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CSSettings extends FragmentActivity implements
CSSettingsList.ListItemSelectedListener{
    static final String CS_PREFS = "CSPrefs";
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);
    }

    @Override
    public void onListItemSelected(int index) {
		CSSettingsViewer settingsViewer = (CSSettingsViewer) getSupportFragmentManager()
        .findFragmentById(R.id.cs_settings_viewer_fragment);

		if (settingsViewer == null || !settingsViewer.isInLayout()) {
			Intent svIntent = new Intent(getApplicationContext(),CSSettingsViewerActivity.class);
			svIntent.putExtra("index", index);
			startActivity(svIntent);
		} else {
			settingsViewer.update(index);
		}
    }
}