package fsk.com.koin.mynotes.ui.fragments.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.jakewharton.rxbinding3.view.clicks
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
import fsk.com.koin.mynotes.R
import fsk.com.koin.mynotes.data.NoteColor
import fsk.com.koin.mynotes.data.database.note.Note
import fsk.com.koin.mynotes.ui.common.GridMiddleDividerItemDecoration
import fsk.com.koin.mynotes.ui.extensions.getNoteColorName
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_notes.notesAddFab
import kotlinx.android.synthetic.main.fragment_notes.notesRecycler
import org.koin.android.viewmodel.ext.android.viewModel

/**
 * Fragment to manage the display of notes.
 * Interactions include filtering by colors and selecting notes for editing.
 */
class NotesFragment : Fragment() {

    private val viewModel: NotesViewModel by viewModel()

    private val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }
    private val cardAdapter = NotesAdapter()

    private var colorsTooltip: String = ""

    private var notesDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_notes, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesRecycler.apply {
            adapter = cardAdapter.apply {
                getOnNoteClickedUpdates
                    .autoDispose(scopeProvider)
                    .subscribe { position -> gotoEditNote(cardAdapter.getNote(position)) }
            }

            addItemDecoration(EmptyItemDecoration())
            addItemDecoration(
                GridMiddleDividerItemDecoration().apply {
                    dividerDrawable =
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.divider_invisible
                        )
                }
            )
        }

        notesAddFab.clicks()
            .autoDispose(scopeProvider)
            .subscribe { gotoEditNote(null) }

        viewModel.getSelectedColorsUpdates
            .autoDispose(scopeProvider)
            .subscribe {
                colorsTooltip = createToolTipForColors(it)
                activity?.invalidateOptionsMenu()
                refreshNotes()
            }

        refreshNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.color_filter).title = colorsTooltip
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.color_filter) {
            findNavController(this).navigate(R.id.colorFilterDialog)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun refreshNotes() {
        notesDisposable?.dispose()
        notesDisposable = viewModel.getNoteUpdates
            .autoDispose(scopeProvider)
            .subscribe { notes -> cardAdapter.submitList(notes) }

    }

    private fun gotoEditNote(note: Note?) {
        findNavController(this)
            .navigate(NotesFragmentDirections.actionNavToEditNote(note?.id ?: -1))
    }

    private fun createToolTipForColors(selectedColors: Set<NoteColor>): String {
        val colorString = if (selectedColors.size == NoteColor.values().size) {
            requireContext().getString(R.string.all_colors_selected)
        } else {
            val builder = StringBuilder()
            selectedColors.forEach { builder.appendLine(requireContext().getNoteColorName(it)) }
            builder.toString()
        }

        return requireContext().getString(R.string.color_filter_tooltip, colorString)
    }
}