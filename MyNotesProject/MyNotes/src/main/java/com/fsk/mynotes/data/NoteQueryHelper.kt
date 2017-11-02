package com.fsk.mynotes.data;

import android.content.ContentResolver
import com.fsk.common.database.buildQueryQuestionMarkString
import com.fsk.mynotes.constants.NoteColor
import com.fsk.mynotes.data.database.NotesSQLiteOpenHelper.Companion.NOTE_COLOR_COL
import com.fsk.mynotes.data.providers.NoteContentProvider.Companion.NOTE_CONTENT_URI

/**
 * Created by me on 5/22/2016.
 */


fun ContentResolver.queryForNoteColors(colors: Set<NoteColor>) {

    val selectionTemplate = NOTE_COLOR_COL + " in (%s)";
    val queryQuestionMarks = selectionTemplate.format(buildQueryQuestionMarkString(colors.size));

    val selection = NOTE_COLOR_COL + " in (" + queryQuestionMarks + ")";
    val args = arrayOf<String>();
    var i = 0;
    for (color in colors) {
        args[i++] = Integer.toString(color.ordinal);
    }

    query(NOTE_CONTENT_URI, null, selection, args, null);
}

