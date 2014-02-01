package com.example.timecapsule;

import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.ImageView;

public class ConfirmPictureActivity extends FragmentActivity {
	
	public static final String PIC_DATA_EXTRA = "picture_data";
	public static final String PIC_NAME_EXTRA = "picture_name";
	public static final String PIC_TARGET_DATE_EXTRA = "picture_target_date";
	
	private static byte[] pictureData;
	private static String pictureFileName;
	Bitmap picture;
	private ImageView image;
	private DatePickerFragment datePicker;
	private String targetDate;
	Intent i;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmpicture);
		
		pictureData = getIntent().getByteArrayExtra(PIC_DATA_EXTRA);
		pictureFileName = getIntent().getStringExtra(PIC_NAME_EXTRA);
		
		picture = BitmapFactory.decodeByteArray(pictureData, 0, pictureData.length);
		
		image = (ImageView) findViewById(R.id.confirm_picture_view);
		image.setImageBitmap(picture);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		datePicker = new DatePickerFragment();
		datePicker.show(getSupportFragmentManager(), "datePicker");
		
		i = new Intent();
		
		if (datePicker.isDateSet == true) {
			
			try {
				
				FileOutputStream outStream = openFileOutput(pictureFileName, Context.MODE_PRIVATE);
				picture.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
				outStream.close();
				
				targetDate = datePicker.mMonth + " " + datePicker.mDay + ", " + datePicker.mYear;
				i.putExtra(targetDate, PIC_TARGET_DATE_EXTRA);
				setResult(RESULT_OK, i);
				finish();
				
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	protected void sendNullData() {
		
		if (datePicker.isDateSet == false) {
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
}
