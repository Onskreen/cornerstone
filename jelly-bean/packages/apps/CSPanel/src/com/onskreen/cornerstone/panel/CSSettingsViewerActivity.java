package com.onskreen.cornerstone.panel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CSSettingsViewerActivity extends FragmentActivity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cs_settings_viewer_activity);
        Intent launchingIntent = getIntent();
        int index = launchingIntent.getIntExtra("index", 0);
        CSSettingsViewer viewer = (CSSettingsViewer) getSupportFragmentManager()
                .findFragmentById(R.id.cs_settings_viewer_fragment);
        viewer.update(index);
    }
}