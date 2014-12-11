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

import java.util.EnumMap;
import java.util.Iterator;
import android.content.Context;
import android.util.AttributeSet;

public class MultiColorSelector extends ColorSelector {

	public MultiColorSelector(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MultiColorSelector(Context context) {
		super(context);
	}

	@Override
	protected void updateSelection(ColorButton button) {
		if (mOnSelectListener != null) {
			mOnSelectListener.onColorSelectChange(this, button.getColor());
		}
	}

	public final EnumMap<NoteColors, Boolean> getColorsSelected() {
		EnumMap<NoteColors, Boolean> map = new EnumMap<NoteColors, Boolean>(
				NoteColors.class);

		Iterator<NoteColors> colorIter = NoteColors.getIterator();
		while (colorIter.hasNext()) {
			NoteColors color = colorIter.next();
			Boolean selected = mColorButtons.get(color).isChecked();
			map.put(color, selected);
		}
		return map;
	}

	public final void setColorsSelected(EnumMap<NoteColors, Boolean> map) {
		Iterator<NoteColors> colorIter = NoteColors.getIterator();
		while (colorIter.hasNext()) {
			NoteColors color = colorIter.next();
			Boolean selected = mColorButtons.get(color).isChecked();
			map.put(color, selected);
		}
	}
}