package fsk.com.mynotes.ui.colorfilter

import android.content.Context
import androidx.lifecycle.ViewModel
import fsk.com.mynotes.data.NoteColor
import fsk.com.mynotes.data.preferences.AppPreferences
import io.reactivex.Observable
import javax.inject.Inject

class ColorFilterViewModel @Inject constructor(
    val context: Context,
    val appPreferences: AppPreferences
) : ViewModel() {
    val selectedColorsObservable: Observable<Set<NoteColor>> get() = appPreferences.selectedColorsObservable

    val selectedColors: Set<NoteColor> get() = appPreferences.getSelectedColors()

    private fun removeSelectedColor(noteColor: NoteColor) {
        appPreferences.removeSelectedColor(noteColor)
    }

    private fun addSelectedColor(noteColor: NoteColor) {
        appPreferences.addSelectedColor(noteColor)
    }


    fun updateSelectedColor(noteColor: NoteColor, selected: Boolean) {
        if (selected) {
            addSelectedColor(noteColor)
        }
        else {
            removeSelectedColor(noteColor)
        }
    }
}