package com.example.timecapsule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

public class GalleryFragment extends Fragment {
	
	private HorizontalScrollView mScroller;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_gallery, container, false);
		mScroller = (HorizontalScrollView)v.findViewById(R.id.scroller);
		
		
		return v;
	}
}
