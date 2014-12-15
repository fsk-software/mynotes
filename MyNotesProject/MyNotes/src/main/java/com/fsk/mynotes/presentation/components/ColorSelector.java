/*
 * Copyright (C) 2012 FSK Consulting, Inc.
 * 
 * Licensed under the LGPL License, Version 3 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.fsk.mynotes.presentation.components;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.fsk.mynotes.constants.NoteColors;
import com.fsk.mynotes.R;

import java.util.EnumMap;
import java.util.Iterator;

public class ColorSelector extends LinearLayout {
	
	interface OnSelectListener {
		public void onColorSelectChange(ColorSelector view, NoteColors color);
	}

	protected OnSelectListener mOnSelectListener;
	
	protected EnumMap<NoteColors,ColorButton> mColorButtons = 
		new EnumMap<NoteColors,ColorButton>(NoteColors.class);
	
	private int mSpacing = 0;
	
	private int mMinChildDimX;
	private int mMinChildDimY;
	
	private int mOnWidth;
	private int mOnHeight;
	
	public ColorSelector(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = 
        	context.obtainStyledAttributes(attrs,
        	                               R.styleable.noteAttrs);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
        	int attr = a.getIndex(i);
        	switch (attr) {
        		case R.styleable.noteAttrs_spacing:
        			mSpacing = (a.getInt(attr, mSpacing));
        			break;
        		case R.styleable.noteAttrs_orientation:
        			setOrientation((a.getInt(attr, 1)==1)? VERTICAL : HORIZONTAL);
        			break;
        		case R.styleable.noteAttrs_minChildDimX:
        			mMinChildDimX = (int)a.getDimension(attr, 40.0f);
        			break;
        		case R.styleable.noteAttrs_minChildDimY:
        			mMinChildDimY = (int)a.getDimension(attr, 50.0f);
        			break;
        	}
            a.recycle();
    	}	
		initialize();
	}

	public ColorSelector(Context context) {
		super(context);
		initialize();
	}
	
	protected void initialize() {	
		LinearLayout scrollingLayout = new LinearLayout(getContext()); 
		if (this.getOrientation() == VERTICAL) {
			ScrollView scrollView = new ScrollView(getContext());
			scrollingLayout.setOrientation(VERTICAL);
			addView(scrollView);
			scrollView.addView(scrollingLayout);
			scrollView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		}
		else {
			HorizontalScrollView scrollView = new HorizontalScrollView(getContext());
			scrollingLayout.setOrientation(HORIZONTAL);
			scrollView.addView(scrollingLayout);	
			addView(scrollView);	
			scrollView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
		}
		
        Iterator<NoteColors> colorIter = NoteColors.getIterator();
		while (colorIter.hasNext()) {
			NoteColors next = colorIter.next();
			ColorButton button = new ColorButton(getContext(), next);
			button.setTextOff("");
			button.setTextOn("");

			button.setOnCheckedChangeListener(new OnCheckedChangeListener () {
				public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
					updateSelection((ColorButton) arg0);
				}
			});
			
			button.setOnLongClickListener(new OnLongClickListener(){
				public boolean onLongClick(View v) {
					ColorButton button = (ColorButton) v;
					Toast toast = 
						Toast.makeText(v.getContext(), button.getColor().name(), Toast.LENGTH_SHORT);
					toast.show();
					return false;
				}
				
			});
			
			button.setChecked(false);
			mColorButtons.put(next, button);			
			scrollingLayout.addView(button);
	    }
	}
	
	protected void updateSelection(ColorButton button) {
		Iterator<NoteColors> iter = NoteColors.getIterator();
		while (iter.hasNext()) {
			ColorButton candidateButton = mColorButtons.get(iter.next());
			if ((button.isChecked()) && 
				(button.getColor() != candidateButton.getColor())) {
				candidateButton.setChecked(false);
			}
		}
		
		if (mOnSelectListener != null) {
			mOnSelectListener.onColorSelectChange(this, button.getColor());
		}
	}
	
	public void selectColor(NoteColors color) {
		if (!mColorButtons.get(color).isChecked()) {
			mColorButtons.get(color).setChecked(true);
		}
	}
	
	public NoteColors getSelectedColor() {
		Iterator<NoteColors> colorIter = NoteColors.getIterator();
		while (colorIter.hasNext()) {
			NoteColors color  = colorIter.next();
			if (mColorButtons.get(color).isChecked()) {
				return color; 
			}
		}
		return null;
	}
	
	public void setOnSelectListner(OnSelectListener listener) {
		mOnSelectListener = listener;
	}
	
	public final boolean isColorSelected(NoteColors color) {
		return mColorButtons.get(color).isChecked();
	}
	
	public void updateChildrenSize() {
		int onWidth = 0;
		int onHeight = 0;
		
		if (getOrientation() == VERTICAL) {
			onWidth = getWidth() - mSpacing;
			onWidth = (onWidth < mMinChildDimX) ? mMinChildDimX : onWidth;
			
			onHeight = (getHeight() - mSpacing)/mColorButtons.size();
			onHeight = (onHeight < mMinChildDimY) ? mMinChildDimY : onHeight;
 		}
		else {
			onWidth = (getWidth() - mSpacing)/mColorButtons.size();
			onWidth = (onWidth < mMinChildDimX) ? mMinChildDimX : onWidth;
			
			onHeight = getHeight() - mSpacing;
			onHeight = (onHeight < mMinChildDimY) ? mMinChildDimY : onHeight;
		}

		if (((onWidth != mOnWidth) || (mOnHeight != onHeight)) && 
			((onWidth != 0) || (onHeight != 0))) {
			mOnWidth = onWidth;
			mOnHeight = onHeight;
			
			int offWidth = (int)(onWidth*0.8);
			int offHeight = (int)(onHeight*0.8);
			
			Iterator<NoteColors> iter = NoteColors.getIterator();
			while (iter.hasNext()) {
				ColorButton button = mColorButtons.get(iter.next());
				button.setOnDimensions(onHeight, onWidth);
				button.setOffDimensions(offHeight, offWidth);
			}
		}
		this.requestLayout();
	}

	@Override
	protected void onMeasure(int w, int h) {
		super.onMeasure(w, h);
		updateChildrenSize();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		updateChildrenSize();
		super.onDraw(canvas);
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		updateChildrenSize();
	}
}
