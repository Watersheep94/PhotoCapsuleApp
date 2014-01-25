package com.example.timecapsule;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
	
	private static final String TAG = "CAMERA";
	private static final int mID = 5;
	private SurfaceHolder mHolder;
	private static Camera mCamera;
	
	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;
		
		// Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
		mHolder = getHolder();
		mHolder.addCallback(this);
	
	}
	
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.setDisplayOrientation(90);
			//setCameraDisplayOrientation( (Activity)getContext(), mID, mCamera);
			mCamera.startPreview();
		} catch (IOException e) {
			 Log.d(TAG, "Error setting camera preview: " + e.getMessage());
		}
	}
	
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d(TAG, "Surface destroyed!");
		 // release the Camera preview in activity.
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		
		if (mHolder.getSurface() == null){
	          // preview surface does not exist
			Log.d(TAG, "derp");
	          return;
	        }

	        // stop preview before making changes
	        try {
	        	Log.d(TAG, "before stop preview!");
	            mCamera.stopPreview();
	            Log.d(TAG, "passed stop preview!");
	        } catch (Exception e){
	          // ignore: tried to stop a non-existent preview
	        }

	        // set preview size and make any resize, rotate or
	        // reformatting changes here

	        // start preview with new settings
	        try {
	            mCamera.setPreviewDisplay(mHolder);
	            //mCamera.setDisplayOrientation(90);
	            //setCameraDisplayOrientation( (Activity)getContext(), mID, mCamera);
	            mCamera.startPreview();
	            Log.d(TAG, "passed restart preview!");

	        } catch (Exception e){
	            Log.d(TAG, "Error starting camera preview: " + e.getMessage());
	        }
	}
	
	//method taken from developer reference guide
		 public static void setCameraDisplayOrientation(Activity activity,
		         int cameraId, Camera camera) {
			 
		    Camera.CameraInfo info = new Camera.CameraInfo();
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
		     mCamera.setDisplayOrientation(result);
		 }
	
}
