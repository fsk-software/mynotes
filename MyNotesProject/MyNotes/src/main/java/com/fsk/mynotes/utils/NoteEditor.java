package com.fsk.mynotes.utils;


import com.fsk.mynotes.constants.NoteColor;
import com.fsk.mynotes.data.Note;

/**
 * An interface that defines the editor behavior on a note.
 */
public interface NoteEditor {

    /**
     * Get the Note.
     *
     * @return the current note.
     */
    Note getNote();

    /**
     * Change the color of the note to the new color.
     * @param color the new color for the note.
     */
    void changeNoteColor(NoteColor color);


    /**
     * Save the note.
     */
    void saveNote();


    /**
     * Delete the note.
     */
    void deleteNote();

    /**
     * Show the color selection picker.
     */
    void showColorPicker();

    /**
     * Show the main edit options for the note.
     */
    void showEditOptions();
}
