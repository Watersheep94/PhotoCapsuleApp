package com.example.timecapsule;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	public static int mYear;
	public static int mMonth;
	public static int mDay;
	public static String TAG = "DatePickerFragment";
	
	public boolean isDateSet;
	
	public DatePickerFragment() {
		Log.d(TAG, "DatePickerFragment created!");
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		isDateSet = savedInstanceState.getBoolean("date_set");
		return new DatePickerDialog(getActivity(), (ConfirmPictureActivity)getActivity(), mYear, mMonth, mDay);
		
	}
	
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		Log.d(TAG, "In dateSet!");
	}
	
	public int getMonth() {
		return mMonth;
	}
	
	public int getYear() {
		return mMonth;
	}
	
	public int getDay() {
		return mDay;
	}
	
}
