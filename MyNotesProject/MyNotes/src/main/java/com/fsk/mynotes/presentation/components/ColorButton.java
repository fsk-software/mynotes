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
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ToggleButton;

import com.fsk.mynotes.constants.NoteColors;
import com.fsk.mynotes.R;

public class ColorButton extends ToggleButton {
	
	private NoteColors mColor;
	private int mOffWidth = -1;
	private int mOffHeight = -1;
	
	private int mOnWidth = -1;
	private int mOnHeight = -1;
	
	public ColorButton(Context context, AttributeSet attrs) {
		super(context, attrs);	
		
		TypedArray a = 
        	context.obtainStyledAttributes(attrs,
        	                               R.styleable.noteAttrs);

        final int N = a.getIndexCount();
        for (int i = 0; i < N; ++i) {
        	int attr = a.getIndex(i);
        	switch (attr) {
        		case R.styleable.noteAttrs_colorType:
        			setColor(NoteColors.getColor(a.getInt(attr, 
        						            NoteColors.YELLOW.ordinal())));
        			break;
        		case R.styleable.noteAttrs_offHeight:
        			mOffHeight = a.getDimensionPixelSize(attr, 0);
        			break;
        		case R.styleable.noteAttrs_offWidth:
        			mOffWidth = a.getDimensionPixelSize(attr, 0);
        			break;
        		case R.styleable.noteAttrs_onHeight:
        			mOnHeight = a.getDimensionPixelSize(attr, 0);
        			break;
        		case R.styleable.noteAttrs_onWidth:
        			mOnWidth = a.getDimensionPixelSize(attr, 0);
        			break;
        	}
            a.recycle();
    	}	

        setChecked(isChecked());
    }
	

	public ColorButton(Context context, NoteColors color) {
		super(context);	
		mColor = color;
		setBackgroundResource(mColor.getToggleResource());
	}
	
	public ColorButton(Context context, NoteColors color, 
			           int offHeight, int offWidth,
			           int onHeight, int onWidth) {
		super(context);	
		mColor = color;
		
		mOffHeight = offHeight;
		mOffWidth = offWidth;
		
		mOnHeight = onHeight;
		mOnWidth = onWidth;

		setBackgroundResource(mColor.getToggleResource());
	}
	
	public  void setOffDimensions(int height, int width) {
		mOffHeight = height;
		mOffWidth = width;
		resizeNote();
	}

	public  void setOnDimensions(int height, int width) {
		mOnHeight = height;
		mOnWidth = width;
		resizeNote();
	}
	
	public void setColor(NoteColors color) {
		mColor = color;
		setBackgroundResource(mColor.getToggleResource());
	}
    
    public NoteColors getColor() {
    	return mColor;
    }

	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		resizeNote();
	}

    private void resizeNote() {   	   	
    	LinearLayout.LayoutParams params = (LayoutParams) this.getLayoutParams();
    	if ((params != null) && 
    		(mOnHeight > -1) && (mOnWidth > -1) &&
    		(mOffHeight > -1) && (mOffWidth > -1)) {
    		
    		if (isChecked()) {
    			params.height = mOnHeight;
    			params.width = mOnWidth;
    			params.setMargins(0,0,0,0);
    		}
    		else {
    			params.height = mOffHeight;
    			params.width = mOffWidth;
    			int paramsVerticalMargin = (mOnHeight-mOffHeight)/2;
    			int paramsHorizontalMargin = (mOnWidth-mOffWidth)/2;

    			params.setMargins(paramsHorizontalMargin,paramsVerticalMargin,
    					          paramsHorizontalMargin, paramsVerticalMargin);
    		}
        	setLayoutParams(params);
    	}
    }
}
