package fsk.com.mynotes.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import fsk.com.mynotes.di.scopes.FragmentScope
import fsk.com.mynotes.ui.colorfilter.ColorFilterBindsModule
import fsk.com.mynotes.ui.colorfilter.ColorFilterDialogFragment
import fsk.com.mynotes.ui.note.NoteBindsModule
import fsk.com.mynotes.ui.note.NoteFragment
import fsk.com.mynotes.ui.note.NoteProvidesModule
import fsk.com.mynotes.ui.notes.NotesBindsModule
import fsk.com.mynotes.ui.notes.NotesFragment


@Module
abstract class FragmentBindingModule {

    @FragmentScope
    @ContributesAndroidInjector(modules = [NoteBindsModule::class, NoteProvidesModule::class])
    abstract fun bindNoteFragment(): NoteFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [NotesBindsModule::class])
    abstract fun bindNotesFragment(): NotesFragment

    @FragmentScope
    @ContributesAndroidInjector(modules = [ColorFilterBindsModule::class])
    abstract fun bindColorFilterFragment(): ColorFilterDialogFragment

}