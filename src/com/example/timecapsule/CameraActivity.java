package com.example.timecapsule;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

@TargetApi(11)
public class CameraActivity extends Activity {
	
	private static final String TAG = "CAMERA";
	public static final String EXTRA_PICTURE_NAME = "camera_activity_picture_name";
	
	private Camera camera;
	private SurfaceHolder surfaceHolder;
	private SurfaceView surfaceView;
	private PictureCallback rawCallBack;
	private PictureCallback jpegCallBack;
	private ShutterCallback shutterCallBack;
	
	public static String pictureFileName;
	
	@TargetApi(11)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "In onCreate");
		super.onCreate(savedInstanceState);
		Log.d(TAG, "In onCreate after super");
		Log.d(TAG, "Layout created!");
		setContentView(R.layout.activity_camera);
		pictureFileName = null;
		
		surfaceView = (SurfaceView)findViewById(R.id.camera_view);
		try {
			startCamera();
		} catch (IOException e) {
			e.printStackTrace();
		}
		surfaceView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				takePicture();
			}
		});
	}
	
	protected void startCamera() throws IOException {
		try {
			camera = Camera.open();
			camera.setParameters(camera.getParameters());
			surfaceHolder = surfaceView.getHolder();
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
			setCameraDisplayOrientation(CameraActivity.this,CameraInfo.CAMERA_FACING_BACK, camera);
			
		} catch (RuntimeException e) {
			Log.e(TAG, "camera init failed");
			return;
		}
	}
	
	protected void takePicture() {
		
		camera.takePicture(shutterCallBack, rawCallBack, jpegCallBack);
		
		rawCallBack = new PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				Log.d(TAG, "raw picture taken");
			}
		};
		
		jpegCallBack = new PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				FileOutputStream outStream = null;
				try {
					pictureFileName = String.format("pic_%tD%n_%d", Calendar.getInstance(), System.currentTimeMillis());
					outStream = openFileOutput(pictureFileName, Context.MODE_PRIVATE);
					Log.d(TAG, "onPictureTaken - wrote bytes: " + data.length);
					outStream.write(data);
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		stopCamera();
	}
	
	protected void stopCamera() {
		Intent data = new Intent();
		data.putExtra(EXTRA_PICTURE_NAME, pictureFileName);

		camera.stopPreview();
		camera.release();
		setResult(RESULT_OK, data);
	}
	
	@Override
	protected void onStop() {
		camera.stopPreview();
		camera.release();
	}
	
	//method taken from developer reference guide
	 public static void setCameraDisplayOrientation(Activity activity,
	         int cameraId, android.hardware.Camera camera) {
	     android.hardware.Camera.CameraInfo info =
	             new android.hardware.Camera.CameraInfo();
	     android.hardware.Camera.getCameraInfo(cameraId, info);
	     int rotation = activity.getWindowManager().getDefaultDisplay()
	             .getRotation();
	     int degrees = 0;
	     switch (rotation) {
	         case Surface.ROTATION_0: degrees = 0; break;
	         case Surface.ROTATION_90: degrees = 90; break;
	         case Surface.ROTATION_180: degrees = 180; break;
	         case Surface.ROTATION_270: degrees = 270; break;
	     }

	     int result;
	     if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
	         result = (info.orientation + degrees) % 360;
	         result = (360 - result) % 360;  // compensate the mirror
	     } else {  // back-facing
	         result = (info.orientation - degrees + 360) % 360;
	     }
	     camera.setDisplayOrientation(result);
	 }
}
