package fsk.com.koin.mynotes.ui.fragments.note

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.navArgs
import com.jakewharton.rxbinding3.widget.afterTextChangeEvents
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
import fsk.com.koin.mynotes.R
import fsk.com.koin.mynotes.data.NoteColor
import fsk.com.koin.mynotes.ui.extensions.getNoteArgb
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_edit_note.editNoteCardView
import kotlinx.android.synthetic.main.fragment_edit_note.noteContentEditText
import kotlinx.android.synthetic.main.fragment_edit_note.noteTitleEditText
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.concurrent.TimeUnit


/**
 * Fragment to manage editing a single note.
 */
class EditNoteFragment : Fragment() {

    companion object {
        private const val TEXT_ENTRY_DEBOUNCE_MS = 200L
    }

    private val viewModel: EditNoteViewModel by viewModel {
        parametersOf(args.noteId)

    }

    private val args: EditNoteFragmentArgs by navArgs()

    private val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_edit_note, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noteTitleEditText.afterTextChangeEvents()
            .subscribeOn(Schedulers.computation())
            .debounce(TEXT_ENTRY_DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe { textChangeEvent ->
                if (textChangeEvent.view.hasFocus()) {
                    viewModel.updateTitle(textChangeEvent.editable.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .autoDispose(scopeProvider)
                        .subscribe { }
                }
            }

        noteContentEditText.afterTextChangeEvents()
            .subscribeOn(Schedulers.computation())
            .debounce(TEXT_ENTRY_DEBOUNCE_MS, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe { textChangeEvent ->
                if (textChangeEvent.view.hasFocus()) {
                    viewModel.updateText(textChangeEvent.editable.toString())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .autoDispose(scopeProvider)
                        .subscribe { }
                }
            }

        viewModel.loadNote()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe { note ->
                editNoteCardView.setCardBackgroundColor(requireContext().getNoteArgb(note.color))
                noteTitleEditText.setText(note.title)
                noteContentEditText.setText(note.text)
            }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var handled = true

        when (item.itemId) {
            R.id.delete ->
                viewModel.deleteNote()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .autoDispose(scopeProvider)
                    .subscribe { findNavController(this).popBackStack() }

            R.id.yellow -> changeNoteColor(NoteColor.YELLOW)
            R.id.blue -> changeNoteColor(NoteColor.BLUE)
            R.id.green -> changeNoteColor(NoteColor.GREEN)
            R.id.pink -> changeNoteColor(NoteColor.PINK)
            R.id.grey -> changeNoteColor(NoteColor.GREY)
            R.id.purple -> changeNoteColor(NoteColor.PURPLE)

            else -> handled = false
        }
        return if (handled) true else super.onOptionsItemSelected(item)
    }

    private fun changeNoteColor(noteColor: NoteColor) =
        viewModel.changeNoteColor(noteColor)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .autoDispose(scopeProvider)
            .subscribe { editNoteCardView.setBackgroundResource(noteColor.colorResId) }
}