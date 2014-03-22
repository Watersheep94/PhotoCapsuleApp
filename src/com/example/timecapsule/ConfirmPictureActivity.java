package com.example.timecapsule;

import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.app.DatePickerDialog;

public class ConfirmPictureActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {
	
	public static final String PIC_DATA_EXTRA = "picture_data";
	public static final String PIC_NAME_EXTRA = "picture_name";
	public static final String PIC_TARGET_DATE_EXTRA = "picture_target_date";
	public static final String TAG = "ConfirmPictureActivity";
	
	private static byte[] pictureData;
	private static String pictureFileName;
	Bitmap picture;
	private ImageView image;
	private DatePickerFragment datePicker;
	private String targetDate;
	Intent i;
	public static boolean isDateSet = false;
	int mYear;
	int mMonth;
	int mDay;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmpicture);
		Log.d(TAG, "Created confirm picture activity");
		pictureData = getIntent().getByteArrayExtra(PIC_DATA_EXTRA);
		pictureFileName = getIntent().getStringExtra(PIC_NAME_EXTRA);
		Log.d(TAG, "Got intents for ConfirmPicture");
		picture = BitmapFactory.decodeByteArray(pictureData, 0, pictureData.length);
		Log.d(TAG, "decoded ByteArray!");
		image = (ImageView) findViewById(R.id.confirm_picture_view);
		image.setImageBitmap(picture);
		Log.d(TAG, "Set Image Bitmap!");
		
		FragmentManager fragmentManager = getSupportFragmentManager();
	
		datePicker = newInstance();
	
		datePicker.show(getSupportFragmentManager(), "datePicker");
		Log.d(TAG, "datePicker shown!");
		
		i = new Intent();
		
		if (isDateSet == true) {
			
			try {
				Log.d(TAG, "Picture confirmed!");
				FileOutputStream outStream = openFileOutput(pictureFileName, Context.MODE_PRIVATE);
				picture.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				outStream.close();
				
				targetDate = datePicker.getMonth() + " " + datePicker.getDay() + ", " + datePicker.getYear();
				i.putExtra(targetDate, PIC_TARGET_DATE_EXTRA);
				setResult(RESULT_OK, i);
				finish();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	@Override
	public View onCreateView(String name, Context context, AttributeSet attrs) {
		return null;
	}
	
	protected void sendNullData() {
		
		if (isDateSet == false) {
			targetDate = null;
			i.putExtra(targetDate, PIC_TARGET_DATE_EXTRA);
			setResult(RESULT_OK, i);
		}
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		sendNullData();
		finish();
	}
	
	protected void onStop() {
		super.onStop();
		sendNullData();
		finish();
	}
	
	public static final DatePickerFragment newInstance(){
		
		DatePickerFragment datePicker = new DatePickerFragment();
		Bundle bundle = new Bundle(1);
		bundle.putBoolean("date_set", isDateSet);
		datePicker.setArguments(bundle);
		return datePicker;
	}
	
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		isDateSet = true;
		Log.d(TAG, "In dateSet!");
	}
	
}
