package com.onskreen.cornerstone.panel;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;

public class CSSettingsList  extends ListFragment {
    private int index = 0;
    private ListItemSelectedListener selectedListener;
    private String TAG = "CSSettings";

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
		getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		getListView().setItemChecked(position, true);

        index = position;
        selectedListener.onListItemSelected(position);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Context ctx = this.getActivity().getApplicationContext();
        Log.v(TAG, "onActivityCreated called");
		Resources res = this.getResources();

		String[] options = res.getStringArray(R.array.cs_settings_names);
		TypedArray icons = res.obtainTypedArray(R.array.cs_settings_icons);
		setListAdapter(new ImageAndTextAdapter(ctx, R.layout.cs_settings_list_item, options, icons));

        if (savedInstanceState != null) {
            index = savedInstanceState.getInt("index", 0);
            selectedListener.onListItemSelected(index);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("index", index);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            selectedListener = (ListItemSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement ListItemSelectedListener in Activity");
        }
    }

    public interface ListItemSelectedListener {
        public void onListItemSelected(int index);
    }

    public class ImageAndTextAdapter extends ArrayAdapter<String> {

		private LayoutInflater mInflater;

		private String[] mStrings;
		private TypedArray mIcons;

		private int mViewResourceId;

		public ImageAndTextAdapter(Context ctx, int viewResourceId,
				String[] strings, TypedArray icons) {
			super(ctx, viewResourceId, strings);

			mInflater = (LayoutInflater)ctx.getSystemService(
					Context.LAYOUT_INFLATER_SERVICE);

			mStrings = strings;
			mIcons = icons;

			mViewResourceId = viewResourceId;

		}

		@Override
		public int getCount() {
			return mStrings.length;
		}

		@Override
		public String getItem(int position) {
			return mStrings[position];
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = mInflater.inflate(mViewResourceId, null);

			ImageView iv = (ImageView)convertView.findViewById(R.id.option_icon);
			iv.setImageDrawable(mIcons.getDrawable(position));

			TextView tv = (TextView)convertView.findViewById(R.id.option_text);
			tv.setText(mStrings[position]);
			return convertView;
		}
	}
}