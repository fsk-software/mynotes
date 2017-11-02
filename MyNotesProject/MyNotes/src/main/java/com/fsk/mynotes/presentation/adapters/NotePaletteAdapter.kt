package com.fsk.mynotes.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import com.fsk.mynotes.R
import com.fsk.mynotes.constants.NoteColor
import com.fsk.mynotes.constants.getNoteColorAt

/**
 * Created by me on 10/31/2017.
 */
class NotePaletteAdapter(context: Context) : ArrayAdapter<NoteColor>(context,
                                                                     R.layout.spinner_palette_item,
                                                                     NoteColor.values()) {
    private var selectedColor = NoteColor.YELLOW;

    fun setSelectedColor(color : NoteColor) {
        if (color != selectedColor) {
            selectedColor = color
            notifyDataSetChanged()
        }
    }

    override fun getView(position: Int,
                         convertView: View?,
                         parent: ViewGroup?): View {
        val view: View;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.spinner_primary_item, parent,
                                                        false);
            view.tag = view;
        }
        else {
            view = convertView.tag as View;
        }

        return view;
    }

    override fun getDropDownView(position: Int,
                                 convertView: View?,
                                 parent: ViewGroup?): View {
        val view: View;
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.spinner_palette_item, parent,
                                                        false);
            view.tag = view;
        }
        else {
            view = convertView.tag as View;
        }

        view.setBackgroundResource(selectedColor.darkColorId)

        val colorBlobResource : Int;
        when (getNoteColorAt(position)) {
            NoteColor.BLUE   ->
                colorBlobResource = R.drawable.blue_color_picker_color_blob

            NoteColor.GREEN  ->
                colorBlobResource= R.drawable.green_color_picker_color_blob

            NoteColor.GREY   ->
                colorBlobResource = R.drawable.gray_color_picker_color_blob

            NoteColor.PINK   ->
                colorBlobResource = R.drawable.pink_color_picker_color_blob

            NoteColor.PURPLE ->
                colorBlobResource = R.drawable.purple_color_picker_color_blob

            NoteColor.YELLOW ->
                colorBlobResource = R.drawable.yellow_color_picker_color_blob
        }
        val colorView = view.findViewById<View>(R.id.spinner_palette_item_color_blob);
        colorView.setBackgroundResource(colorBlobResource);
        return view;
    }
}/**/