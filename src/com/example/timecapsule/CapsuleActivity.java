/*
 * Time Capsule Application
 * Created By Yang (Michael) Liu 
 * Copyright (c) 2013
 */


package com.example.timecapsule;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class CapsuleActivity extends Activity {
	
	private static final String TAG = "CapsuleActivity";
	private static final String LIMIT = "limit_reached";
	private static final String PICS_TAKEN = "pictures_taken_today";
	private static final String TARGET_DATE = "target_date";
	
	private static boolean limitReached = false;
	private static boolean targetDateReached = false;
	private static int numOfPicturesTakenToday;
	
	private ImageButton mCameraButton;
	private ImageButton mGalleryButton;
	private EditText mJournalButton;
	private Button mDateButton;
	private ImageButton mScrapbookButton;

	private SimpleDateFormat mDate;
	private SimpleDateFormat mDateWithoutYear;
	private Date mDateValue;
	
	private String targetDate = "Jan 1";
	
	private String[] mPictureGallery; //stores all the picture names
	private String currentPicture;    //stores current picture name
	
	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_capsule);
		ActionBar bar = getActionBar();
		bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8B008B"))); //sets the action bar to purple
	
		mDateButton = (Button)findViewById(R.id.current_date);
		mDate = new SimpleDateFormat("MMM d, yyyy");
		mDateWithoutYear = new SimpleDateFormat("MMM d");
		mDateValue = new Date();
		String date = mDateWithoutYear.format(mDateValue);
		mDateButton.setText(mDate.format(mDateValue));
		
		mScrapbookButton = (ImageButton)findViewById(R.id.scrapbook_button);
		if (date.equals(targetDate)) {
			
			mScrapbookButton.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
				}
			});
			
		} else {
			mScrapbookButton.setVisibility(View.GONE);
		}
	
		mCameraButton = (ImageButton)findViewById(R.id.camera_button);
		mCameraButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (!limitReached) {
					Intent intent = new Intent(CapsuleActivity.this, CameraActivity.class);
					startActivityForResult(intent, 0);
				}
				else {
					
					Toast.makeText(getApplicationContext(), R.string.limit_reached, Toast.LENGTH_SHORT).show();
				}
				
			}
			
		});
		
		mGalleryButton = (ImageButton)findViewById(R.id.gallery_button);
		mGalleryButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
    	boolean pictureTaken = data.getBooleanExtra(CameraActivity.EXTRA_PICTURE_TAKEN, false);
    	
    	if (pictureTaken) {
    		numOfPicturesTakenToday++;
    		if (numOfPicturesTakenToday >= 3) {
    			limitReached = true;
    		}
    	}
    }

    @Override
	public void onResume(){
    	super.onResume();
    	
    	Calendar c = Calendar.getInstance();
		mDate = new SimpleDateFormat("MMM d, yyyy");
		mDateValue = new Date();
		mDateButton.setText(mDate.format(mDateValue));
    }
    
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
    	
    	String previousDate = mDate.format(mDateValue);
    	//recognize that the day has passed and reset the numOfPicturesTakenToday (DO LATER)
    	
    	savedInstanceState.putBoolean(TARGET_DATE, targetDateReached);
    	savedInstanceState.putInt(PICS_TAKEN, numOfPicturesTakenToday);
    	savedInstanceState.putBoolean(LIMIT, limitReached);
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.capsule, menu);
		return true;
	}
}
