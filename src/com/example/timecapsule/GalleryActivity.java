package com.example.timecapsule;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

public class GalleryActivity extends Activity {
	
	public static final String TAG = "GALLERY";
	
	static GridView mGallery;
	public static String EXTRA_PICTURE_NAMES = "picture_names";
	private static ArrayList<String> mPictureNames;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		
		Log.d(TAG, "In Gallery onCreate()");
		
		mPictureNames = getIntent().getStringArrayListExtra(EXTRA_PICTURE_NAMES);
		
		
		Log.d(TAG, mPictureNames.size() + " PICTURES IN GALLERY!");
		
		mGallery = (GridView) findViewById(R.id.gridview);
		mGallery.setAdapter(new ImageAdapter(this, mPictureNames));
		
		mGallery.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				return;
			}
		});
	}
	

}