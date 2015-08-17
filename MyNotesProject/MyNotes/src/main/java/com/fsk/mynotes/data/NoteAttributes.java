package com.fsk.mynotes.data;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.fsk.mynotes.constants.NoteColor;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;


/**
 * A data class that contains the Note Attributes of color, text and id.
 */
class NoteAttributes implements Parcelable, Cloneable {

    /**
     * The unknown note id
     */
    public static final int UNKNOWN = -1;


    /**
     * Standard Creator Pattern
     */
    public static final Creator<NoteAttributes> CREATOR = new Creator<NoteAttributes>() {
        @Override
        public NoteAttributes createFromParcel(final Parcel source) {
            return new NoteAttributes(source);
        }


        @Override
        public NoteAttributes[] newArray(final int size) {
            return new NoteAttributes[size];
        }
    };


    /**
     * The text for the note.
     */
    private String mText = "";


    /**
     * The color of the note.
     */
    private NoteColor mColor = NoteColor.YELLOW;


    /**
     * The note id.
     */
    private long mId = UNKNOWN;


    /**
     * Constructor.
     */
    public NoteAttributes() {
    }


    /**
     * Constructor.
     *
     * @param source
     *         the parcel containing the data for the note.
     */
    public NoteAttributes(Parcel source) {
        mId = source.readLong();
        mText = source.readString();
        mColor = NoteColor.getColor(source.readInt());
    }


    /**
     * Get the note color.
     *
     * @return The non-null note color.  By default, this returns {@link NoteColor#YELLOW}.
     */
    public NoteColor getColor() {
        return mColor;
    }


    /**
     * Set the color for the note.
     *
     * @param color
     *         the color for the note.
     */
    public boolean setColor(@NonNull NoteColor color) {
        Preconditions.checkNotNull(color);
        boolean returnValue = (color != mColor);
        mColor = color;

        return returnValue;
    }


    /**
     * Get the text for the note.
     *
     * @return the non-null text for the note.
     */
    public String getText() {
        return Strings.nullToEmpty(mText);
    }


    /**
     * Set the note text.
     *
     * @param text
     *         the text for the note.  If null, then an empty string is set.
     */
    public boolean setText(String text) {
        String candidateText = Strings.nullToEmpty(text);
        boolean returnValue = !candidateText.equals(getText());
        mText = candidateText;

        return returnValue;
    }


    /**
     * Get the id for the note.
     *
     * @return the id for the note.
     */
    public long getId() {
        return mId;
    }


    /**
     * Set the id for the note.
     *
     * @param id
     *         the id for the note. This must be either {@link #UNKNOWN} or a natural value.
     */
    public boolean setId(long id) {
        Preconditions.checkArgument((id == UNKNOWN) || (id >= 0));
        boolean returnValue = (id != mId);
        mId = id;

        return returnValue;
    }


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(final Parcel dest, final int flags) {
        dest.writeLong(mId);
        dest.writeString(mText);
        dest.writeInt(mColor.ordinal());
    }


    @Override
    public boolean equals(final Object o) {
        boolean returnValue = false;
        if ((o != null) && (o instanceof NoteAttributes)) {
            returnValue = (hashCode() == o.hashCode());
        }
        return returnValue;
    }


    @Override
    public int hashCode() {
        int hashCode = 7;
        hashCode += (9 * getColor().hashCode());
        hashCode += (11 * getText().hashCode());
        hashCode += (13 * getId());

        return hashCode;
    }


    @Override
    protected NoteAttributes clone() throws CloneNotSupportedException {
        NoteAttributes returnValue = new NoteAttributes();
        returnValue.setColor(getColor());
        returnValue.setId(getId());
        returnValue.setText(getText());
        return returnValue;
    }
}
