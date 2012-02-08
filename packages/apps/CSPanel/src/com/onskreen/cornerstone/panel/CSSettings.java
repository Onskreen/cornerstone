package com.onskreen.cornerstone.panel;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.LayoutInflater;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.widget.AdapterView.OnItemSelectedListener;


import java.util.ArrayList;
import java.util.List;

public class CSSettings extends Activity {

    static final String CS_PREFS = "CSPrefs";
    private ArrayList apps = new ArrayList();
    private ArrayList packages = new ArrayList();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_main);

        final Context context = getApplicationContext();
        final PackageManager pm = this.getPackageManager();

        final SharedPreferences settings = getSharedPreferences(CS_PREFS, 4);
        final SharedPreferences.Editor editor = settings.edit();

        String panel0 = settings.getString("panel0", null);
        String panel1 = settings.getString("panel1", null);

		Intent intent = new Intent(Intent.ACTION_MAIN, null);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);

		List<ResolveInfo> list = pm.queryIntentActivities(intent, PackageManager.PERMISSION_GRANTED);
		for (ResolveInfo rInfo : list) {
		   CharSequence app = rInfo.activityInfo.loadLabel(pm);
		   if(!app.toString().equals("Cornerstone")){
				apps.add(rInfo.activityInfo.loadLabel(pm)); //.applicationInfo.loadLabel(pm).toString());
				packages.add(rInfo.activityInfo.applicationInfo.packageName);
		   }
		}

		RelativeLayout rl1 = (RelativeLayout) findViewById(R.id.layout_header_root);
		ImageView header = (ImageView) findViewById(R.id.cs_settings_header);
		TextView csTitle = (TextView) findViewById(R.id.cssettings);

		RelativeLayout rl2 = (RelativeLayout) findViewById(R.id.layout_title_root);
		ImageView titleHeader = (ImageView) findViewById(R.id.panel_title_header);
		TextView title = (TextView) findViewById(R.id.panelTitle);

		Spinner spinner1 = (Spinner) findViewById(R.id.viewSpin1);
		ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
			    android.R.layout.simple_spinner_item, apps);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner1.setAdapter(adapter1);
		spinner1.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //showToast("Spinner1: position=" + position + " id=" + id);
                editor.putString("panel0", (String)packages.get(position));
                // Commit the edits!
                editor.commit();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //showToast("Spinner1: unselected");
            }
        });

		TextView text1 = (TextView) findViewById(R.id.text1);
		View separator1 = (View) findViewById(R.id.separator1);

		Spinner spinner2 = (Spinner) findViewById(R.id.viewSpin2);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,
			    android.R.layout.simple_spinner_item, apps);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner2.setAdapter(adapter2);
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
	        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //showToast("Spinner2: position=" + position + " id=" + id);
                editor.putString("panel1", (String)packages.get(position));
                // Commit the edits!
                editor.commit();
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //showToast("Spinner2: unselected");
            }
        });

		TextView text2 = (TextView) findViewById(R.id.text2);
		View separator2 = (View) findViewById(R.id.separator2);

		int index = packages.indexOf(panel0);
		if(index != -1){
			spinner1.setSelection(index);
		}
		index = packages.indexOf(panel1);
		if(index != -1){
			spinner2.setSelection(index);
		}
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}