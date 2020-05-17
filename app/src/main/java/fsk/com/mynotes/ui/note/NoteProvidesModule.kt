package fsk.com.mynotes.ui.note

import dagger.Module
import dagger.Provides

@Module
class NoteProvidesModule {

    @NoteId
    @Provides
    fun providesNoteId(fragment: NoteFragment): Long = fragment.noteId
}
