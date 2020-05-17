package fsk.com.mynotes.data.preferences

import android.content.SharedPreferences
import com.google.gson.Gson
import fsk.com.mynotes.data.NoteColor
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class AppPreferences(
    val sharedPreferences: SharedPreferences,
    val gson: Gson
) {

    companion object {
        const val SHARED_PREFERENCE_NAME ="app_preferences.xml"
        private const val SELECTED_COLORS_KEY = "selected_colors_key"
    }

    private val selectedColorsSubject: BehaviorSubject<Set<NoteColor>> = BehaviorSubject.create()
    val selectedColorsObservable: Observable<Set<NoteColor>> get() = selectedColorsSubject

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener { _, key ->
            when (key) {
                SELECTED_COLORS_KEY -> selectedColorsSubject.onNext(getSelectedColors())
            }
        }
    }

    fun getSelectedColors(): Set<NoteColor> {
        val json = sharedPreferences.getString(SELECTED_COLORS_KEY, gson.toJson(NoteColor.values()))
        val colors = gson.fromJson(json, Array<NoteColor>::class.java).toMutableSet()
        autoFillEmptyColors(colors)
        return colors
    }

    private fun setSelectedColors(colors: MutableSet<NoteColor>) {
        autoFillEmptyColors(colors)
       sharedPreferences.edit().apply {
           putString(SELECTED_COLORS_KEY, gson.toJson(colors.toTypedArray()))
           apply()
        }
    }

    fun removeSelectedColor(color: NoteColor) {
        val selectedColors = getSelectedColors().toMutableSet()
        if ( selectedColors.remove(color)) {
            setSelectedColors(selectedColors)

        }
    }

    fun addSelectedColor(color: NoteColor) {
        val selectedColors = getSelectedColors().toMutableSet()
        if ( selectedColors.add(color)) {
            setSelectedColors(selectedColors)
        }
    }

    private fun autoFillEmptyColors(colors: MutableSet<NoteColor>) {
        if (colors.isEmpty()) {
            colors.add(NoteColor.YELLOW)
        }
    }
}