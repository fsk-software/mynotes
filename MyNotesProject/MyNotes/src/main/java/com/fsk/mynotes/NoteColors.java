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
package com.fsk.mynotes;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.List;

public enum NoteColors {

	YELLOW ("Yellow", R.drawable.yellow_note, 
					  R.drawable.yellow_note_header,
			          R.drawable.yellow_button_toggle),
	BLUE   ("Blue",   R.drawable.blue_note, 
			          R.drawable.blue_note_header,
			          R.drawable.blue_button_toggle),
	GREEN  ("Green",  R.drawable.green_note,
					  R.drawable.green_note_header, 
			          R.drawable.green_button_toggle),
    PINK   ("Pink",   R.drawable.pink_note,
			          R.drawable.pink_note_header,
    		          R.drawable.pink_button_toggle),
	GREY   ("Grey",   R.drawable.grey_note,
			          R.drawable.grey_note_header,
			          R.drawable.grey_button_toggle);
			   
	private int mImageResource;			   
	private int mToggleResource;   
	private int mHeaderResource;
	private String mName;
	private static final EnumSet<NoteColors> sEnum = EnumSet.range(YELLOW, GREY);

	private NoteColors(String name, int imageResource, int headerResource, int toggleResource) {
		mImageResource = imageResource;
		mToggleResource = toggleResource;
		mHeaderResource = headerResource;
		mName = name;
	}
	
	public final int getImageResource() {
		return mImageResource;
	}

	public final int getHeaderResource() {
		return mHeaderResource;
	}

	public final int getToggleResource() {
		return mToggleResource;
	}

	public final String getName() {
		return mName;
	}
	
	static public final int getCount() {
		return 5;
	}
	
	static public final NoteColors getColor(long index) {
		NoteColors[] colors = NoteColors.values();
		
		for (int i = 0; i < colors.length; ++i) {
			if (index == colors[i].ordinal()) {
				return colors[i];
			}
		}
		return null;
	}

	public static Iterator<NoteColors> getIterator() {
		return sEnum.iterator();
	}

	public static final List<NoteColors> getList() {
		return new ArrayList<NoteColors>(sEnum);
	}
}
