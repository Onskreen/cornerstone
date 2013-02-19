package com.onskreen.cornerstone.panel;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CSSettingsViewer extends Fragment{

    private ArrayList apps = new ArrayList();
    private ArrayList packages = new ArrayList();
    private ViewStub csTopApp;
    private ViewStub csBottomApp;
    private ViewStub csLaunch;
    private boolean isInitialized = false;
    static final String CS_PREFS = "CSPrefs";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        final PackageManager pm = this.getActivity().getPackageManager();

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

        return inflater.inflate(R.layout.cs_settings_viewer_fragment, container, false);
    }

    public void update(int index) {
		final FragmentActivity fa = this.getActivity();
		final Context ctx = fa.getApplicationContext();
		//Toast.makeText(ctx, "Selected setting is: " + index, Toast.LENGTH_LONG).show();
		if(!isInitialized) {
			final SharedPreferences settingsPref = fa.getSharedPreferences(CS_PREFS, 4);
			final SharedPreferences.Editor editor = settingsPref.edit();

			String panel0 = settingsPref.getString("panel0", null);
			String panel1 = settingsPref.getString("panel1", null);
			boolean startup = settingsPref.getBoolean("startup", fa.getResources().getBoolean(R.bool.startup));

			csTopApp = (ViewStub) fa.findViewById(R.id.cs_top_app);
			csTopApp.inflate();
			csBottomApp = (ViewStub) fa.findViewById(R.id.cs_bottom_app);
			csBottomApp.inflate();
			csLaunch = (ViewStub) fa.findViewById(R.id.cs_launch);
			csLaunch.inflate();

			ArrayAdapter topAd = new ArrayAdapter(ctx, android.R.layout.simple_list_item_single_choice, apps);
			final ListView topList = (ListView) fa.findViewById(R.id.cs_top_app_list);
			topList.setAdapter(topAd);
			topList.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
					//showToast((String)apps.get(arg2));
					editor.putString("panel0", (String)packages.get(arg2));
					// Commit the edits!
					editor.commit();
				}
			});
			topList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

			ArrayAdapter bottomAd = new ArrayAdapter(ctx, android.R.layout.simple_list_item_single_choice, apps);
			final ListView bottomList = (ListView) fa.findViewById(R.id.cs_bottom_app_list);
			bottomList.setAdapter(bottomAd);
			bottomList.setOnItemClickListener(new OnItemClickListener(){
				public void onItemClick(AdapterView arg0, View arg1, int arg2, long arg3) {
					//showToast((String)apps.get(arg2));
					editor.putString("panel1", (String)packages.get(arg2));
					// Commit the edits!
					editor.commit();
				}
			});
			bottomList.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			isInitialized = true;

			int pos = packages.indexOf(panel0);
			if(pos != -1){
				topList.setItemChecked(pos, true);
			}
			pos = packages.indexOf(panel1);
			if(pos != -1){
				bottomList.setItemChecked(pos, true);
			}

			final RadioButton yesRB = (RadioButton) fa.findViewById(R.id.radio0);
			yesRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked) {
						editor.putBoolean("startup", true);
						editor.commit();
					}
				}
			});

			final RadioButton noRB = (RadioButton) fa.findViewById(R.id.radio1);
			noRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
					if(isChecked) {
						editor.putBoolean("startup", false);
						editor.commit();
					}
				}
			});

			if(startup) {				
				yesRB.setChecked(true);
			} else {
				noRB.setChecked(true);
			}
		}

		if(index == 0) {
			csTopApp.setVisibility(View.VISIBLE);
			csBottomApp.setVisibility(View.INVISIBLE);
			csLaunch.setVisibility(View.INVISIBLE);
		} else if(index == 1) {
			csBottomApp.setVisibility(View.VISIBLE);
			csTopApp.setVisibility(View.INVISIBLE);
			csLaunch.setVisibility(View.INVISIBLE);
		}else if(index == 2) {
			csLaunch.setVisibility(View.VISIBLE);
			csTopApp.setVisibility(View.INVISIBLE);
			csBottomApp.setVisibility(View.INVISIBLE);
		}
    }

    void showToast(CharSequence msg) {
        Toast.makeText(this.getActivity().getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
    }
}