package com.example.timecapsule;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

@TargetApi(11)
public class CameraActivity extends Activity {
	
	public static final String TAG = "CAMERA";
	public static final String EXTRA_PICTURE_NAME = "camera_activity_picture_name";
	public static final String EXTRA_PICTURE_DATE = "camera_activity_picture_date";
	public static final String EXTRA_PICTURE_TARGET_DATE = "camera_activity_picture_date";
	
	private static Camera mCamera;
	private CameraPreview mPreview;
	private ImageButton mCapturePictureButton;
	private ImageButton mCancelButton;
	
	private LinearLayout preview;
	
	private Camera.ShutterCallback shutterCallback;
	private Camera.PictureCallback rawCallback;
	private Camera.PictureCallback jpegCallback;
	
	public String pictureFileName;
	public String pictureFileDate;
	public String pictureTargetDate;
	
	Intent data;
	
	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
	
		pictureFileName = "";
		pictureFileDate = "";
		
		mCamera = getCameraInstance();
		Camera.Parameters parameter = mCamera.getParameters();
		parameter.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		mCamera.setParameters(parameter);
		
		mPreview = new CameraPreview(this, mCamera);
		preview = (LinearLayout) findViewById(R.id.camera_preview);
		preview.addView(mPreview);
		
		rawCallback = new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				Log.d(TAG, "raw picture taken");
			}
		};
		
		jpegCallback = new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] data, Camera camera) {
				
					Bitmap picture = BitmapFactory.decodeByteArray(data, 0, data.length);
					pictureFileName = String.format("pic%tF_%d", Calendar.getInstance(), System.currentTimeMillis());
					//pictureFileDate = String.format("%tF", Calendar.getInstance());
				
					Log.d(TAG, "onPictureTaken name: " + pictureFileName);
					Log.d(TAG, "pictureFileDate: " + pictureFileDate);
		
					
						
					//File directory = getDir(pictureFileDate, Context.MODE_PRIVATE);
					//File path = new File(directory, pictureFileName);
					//FileOutputStream outStream = new FileOutputStream(path);
						
					Intent intent = new Intent(CameraActivity.this, ConfirmPictureActivity.class);
					intent.putExtra(ConfirmPictureActivity.PIC_DATA_EXTRA, data);
					intent.putExtra(ConfirmPictureActivity.PIC_NAME_EXTRA, pictureFileName);
					startActivityForResult(intent, 0);
						
				
					//sendData();
					//mCamera.startPreview(); //restart preview since preview display is destroyed after taking pic
			}
		};
		
		mCapturePictureButton = (ImageButton) findViewById(R.id.picture_capture_button);
		Log.d(TAG, "Made capture button!");
		mCapturePictureButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCamera.takePicture(null, null, jpegCallback);
			}
		});
	}
	
	public static Camera getCameraInstance() {
		
		Camera c = null;
		
		try {
			c = Camera.open();
		} catch (Exception e) {
			Log.e(TAG, "Camera not availible!");
		}
		return c;
	}
	
	protected void sendData(String pictureTargetDate) {
		
		if (pictureTargetDate != null) {
			data = new Intent();
			data.putExtra(EXTRA_PICTURE_NAME, pictureFileName);
			data.putExtra(EXTRA_PICTURE_DATE, pictureFileDate);
			data.putExtra(EXTRA_PICTURE_TARGET_DATE, pictureTargetDate);
			Log.d(TAG, "pictureFileName:" + pictureFileName);
			setResult(RESULT_OK, data);
			mCamera.stopPreview();
			stopCamera();
			finish();
		}
	}
	
	protected void stopCamera() {
		
		if (mCamera != null) {
			mCamera.release();
			mCamera = null;
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		pictureTargetDate = data.getStringExtra(ConfirmPictureActivity.PIC_TARGET_DATE_EXTRA);
		
		sendData(pictureTargetDate);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		stopCamera();
	}
	@Override
	protected void onStop() {
		super.onStop();
		stopCamera();
	}
	
}
