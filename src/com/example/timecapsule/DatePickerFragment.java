package com.example.timecapsule;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	
	public static int mYear;
	public static int mMonth;
	public static int mDay;
	public static boolean isDateSet;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);
		isDateSet = false;
		
		return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
		
	}
	
	public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
		mYear = year;
		mMonth = monthOfYear;
		mDay = dayOfMonth;
		isDateSet = true;
	}

}
