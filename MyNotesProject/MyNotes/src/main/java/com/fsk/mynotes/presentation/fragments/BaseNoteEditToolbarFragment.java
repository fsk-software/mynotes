package com.fsk.mynotes.presentation.fragments;


import android.app.Activity;
import android.app.Fragment;

import com.fsk.mynotes.utils.NoteEditor;

/**
 * The basic note edit toolbar fragment.
 */
public class BaseNoteEditToolbarFragment extends Fragment {

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);
        if (!(activity instanceof NoteEditor)) {
            throw new IllegalStateException("The activity must be implement the NoteEditor");
        }
    }


    /**
     * Get the note editor.
     * @return the note editor.  This may be null.
     */
    protected NoteEditor getNoteEditor() {
        return (NoteEditor) getActivity();
    }


}
