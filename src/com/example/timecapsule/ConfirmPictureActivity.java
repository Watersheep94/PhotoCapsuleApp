package com.example.timecapsule;

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
	private static byte[] pic_data;
	Bitmap picture;
	private ImageView image;
	private DialogFragment datePicker;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_confirmpicture);
		
		pic_data = getIntent().getByteArrayExtra(PIC_DATA_EXTRA);
		
		picture = BitmapFactory.decodeByteArray(pic_data, 0, pic_data.length);
		
		image = (ImageView) findViewById(R.id.confirm_picture_view);
		image.setImageBitmap(picture);
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		
		DialogFragment datePicker = new DatePickerFragment();
		datePicker.show(getSupportFragmentManager(), "datePicker");
		
		//FileOutputStream outStream = openFileOutput(pictureFileName, Context.MODE_PRIVATE);
		//picture.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
		//outStream.write(data);
		//outStream.close();
	}
}
