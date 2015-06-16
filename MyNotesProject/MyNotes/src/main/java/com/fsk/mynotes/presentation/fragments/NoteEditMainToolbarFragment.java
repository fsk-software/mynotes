package com.fsk.mynotes.presentation.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fsk.mynotes.R;
import com.fsk.mynotes.utils.NoteEditor;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A Fragment that manages the main note edit toolbar.
 */
public class NoteEditMainToolbarFragment extends BaseNoteEditToolbarFragment {

    /**
     * Creates a new instance of the {@link NoteEditMainToolbarFragment}
     * @return a new instance of the {@link NoteEditMainToolbarFragment}
     */
    public static NoteEditMainToolbarFragment newInstance() {

        NoteEditMainToolbarFragment returnValue = new NoteEditMainToolbarFragment();
        return returnValue;
    }


    @OnClick(R.id.fragment_note_edit_header_save)
    public void onSaveClicked(View view) {
        NoteEditor editor = getNoteEditor();
        if (editor != null) {
            editor.saveNote();
        }
    }


    @OnClick(R.id.fragment_note_edit_header_delete)
    public void deleteButtonClicked(View view) {
        NoteEditor editor = getNoteEditor();
        if (editor != null) {
            editor.deleteNote();
        }
    }

    @OnClick(R.id.fragment_note_edit_header_color_palette)
    public void paletteButtonClicked(View view) {

        NoteEditor editor = getNoteEditor();
        if (editor != null) {
            editor.showColorPicker();
        }
    }


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_note_edit_main_toolbar, container, false);
        ButterKnife.inject(this, rootView);

        return rootView;
    }


    @Override
    public void onActivityCreated(final Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setBackgroundResource(getNoteEditor().getNote().getColor().darkColorResourceId);
    }
}
