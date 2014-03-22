package com.example.timecapsule;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
	
	public static final String TAG = "IMAGE_ADAPTER";
			
	private Context mContext;
	private static String[] mPictureNames;
	
	
	public ImageAdapter(Context c, ArrayList<HashMap<String,String>> pictureNames){
		mContext = c;
		mPictureNames = c.fileList();
		
		Log.d(TAG, "In ImageAdapter Constructor");
		Log.d(TAG, "mPictureNames size: " + mPictureNames.length);
	
	}
	
	public int getCount() {
		return mPictureNames.length;
	}
	
	public Object getItem(int position) {
		return null;
	}
	
	public long getItemId(int position) {
		return 0;
	}
	
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Log.d(TAG, "In getView");
		
		ImageView imageView;
		
		if (convertView == null) {
			imageView = new ImageView(mContext);
			imageView.setLayoutParams(new GridView.LayoutParams(85,85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		}
		else {
			imageView = (ImageView) convertView;
		}
		Log.d(TAG, "Before setImageDrawable");
		Bitmap picture = BitmapFactory.decodeFile(mContext.getFilesDir() + "/" + mPictureNames[position]);
		imageView.setImageBitmap(picture);
		//imageView.setImageDrawable(Drawable.createFromPath(mPictureNames.get(position)));
		return imageView;
	}
	
}
