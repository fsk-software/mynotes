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
package com.fsk.mynotes.constants;

import com.fsk.mynotes.R;

import java.util.EnumSet;

public enum NoteColor {

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
			   
	private final int mImageResource;
	private final int mToggleResource;
	private final int mHeaderResource;
	private final String mName;
	private static final EnumSet<NoteColor> sEnum = EnumSet.range(YELLOW, GREY);


    /**
     * Constructor
     *
     * @param name The fixed name of the note
     * @param imageResource The resource id of the note body.
     * @param headerResource The resource id of the note header.
     * @param toggleResource The resource id of the toggle background.
     */
	private NoteColor(String name, int imageResource, int headerResource, int toggleResource) {
		mImageResource = imageResource;
		mToggleResource = toggleResource;
		mHeaderResource = headerResource;
		mName = name;
	}

    @Deprecated
	public final int getImageResource() {
		return mImageResource;
	}

    @Deprecated
	public final int getHeaderResource() {
		return mHeaderResource;
	}

    @Deprecated
	public final int getToggleResource() {
		return mToggleResource;
	}

    @Deprecated
	public final String getName() {
		return mName;
	}


    /**
     * Get the {@link NoteColor} associated with the supplied ordinal.
     *
     * @param index
     *         the ordinal of the {@link NoteColor} to retrieve.
     *
     * @return the {@link NoteColor} associated with the ordinal or null.
     */
    static public final NoteColor getColor(long index) {
        NoteColor[] colors = NoteColor.values();

        for (int i = 0; i < colors.length; ++i) {
            if (index == colors[i].ordinal()) {
                return colors[i];
            }
        }
        return null;
    }
}
