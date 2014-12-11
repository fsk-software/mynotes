package com.fsk.mynotes;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NoteView extends RelativeLayout {

	private TextView mTextView;
	private TextView mMoreTextView;
	private ImageButton mDeleteButton;
	private View mHeaderView;
	private NoteEventListener mListener;
	private Note mNote;
	
	public NoteView(Context context, AttributeSet attrs) {
		super(context, attrs);	
		initialize();
    }
	
	public NoteView(Context context, Note note, NoteEventListener listener) {
		super(context);	
		mListener = listener;
		initialize();
		setNote(note);
	}
	
	private void initialize() {		
        LayoutInflater inflater = 
        	(LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.grid_note, this);                 

		Typeface face =
			Typeface.createFromAsset(getContext().getAssets(), 
					                 "fonts/memo2self.ttf"); 

		mHeaderView = findViewById(R.id.grid_note_header);
		
		mMoreTextView = (TextView) findViewById(R.id.grid_note_more_text);
		mMoreTextView.setTypeface(face);
		mMoreTextView.setIncludeFontPadding(false);
		
		mTextView = (TextView) findViewById(R.id.grid_note_text);
		mTextView.setTypeface(face);
		mTextView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onNoteClick(NoteView.this, mNote.getRow());
				}
			}
		});
		
		mDeleteButton = (ImageButton) findViewById(R.id.grid_note_delete_button);
		mDeleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener.onNoteDeleteClick(NoteView.this, mNote.getRow());
				}
			}
		});
	}
	
	public void setNote(Note note) {
		mNote = note;
    	NoteColors color = NoteColors.getColor(note.getColor());
		mTextView.setText(note.getText());

		mTextView.setBackgroundResource(color.getImageResource());
		mHeaderView.setBackgroundResource(color.getHeaderResource());

		invalidate();
	}

	public void setNoteEventListener(NoteEventListener listener) {
		mListener = listener;
	}
    
    public NoteColors getColor() {
    	return NoteColors.getColor(mNote.getColor());
    }
    
    @Override
    public void onLayout(boolean changed, int left, int top, int right, int bottom) {
    	super.onLayout(changed, left, top, right, bottom);
    	if (mTextView.getLineCount() > 3) {
    		mMoreTextView.setVisibility(View.VISIBLE);
    	}
    	else {
    		mMoreTextView.setVisibility(View.INVISIBLE);
    	}
    }
    
    public Note getNote() {
    	return mNote;
    }
}
