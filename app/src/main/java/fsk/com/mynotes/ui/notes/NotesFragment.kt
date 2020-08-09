package fsk.com.mynotes.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.jakewharton.rxbinding3.view.clicks
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
import dagger.android.support.DaggerFragment
import fsk.com.mynotes.R
import fsk.com.mynotes.data.database.note.Note
import fsk.com.mynotes.ui.common.GridMiddleDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_notes.notes_add_fab
import kotlinx.android.synthetic.main.fragment_notes.notes_recycler
import javax.inject.Inject

/**
 * Fragment to manage the display of notes.
 * Interactions include filtering by colors and selecting notes for editing.
 */
class NotesFragment : DaggerFragment() {

    /**
     * This is only public to allow injection. DO NOT USE outside of this class.
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: NotesViewModel by viewModels {
        viewModelFactory
    }

    private val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }
    private val cardAdapter = NotesAdapter()

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

        notes_recycler.apply {
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

        notes_add_fab.clicks()
            .autoDispose(scopeProvider)
            .subscribe { gotoEditNote(null) }

        viewModel.getNoteUpdates
            .autoDispose(scopeProvider)
            .subscribe { notes -> cardAdapter.submitList(notes) }

        viewModel.getSelectedColorsUpdates
            .autoDispose(scopeProvider)
            .subscribe { activity?.invalidateOptionsMenu() }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.color_filter).title = viewModel.selectedColorsToolTip
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

    private fun gotoEditNote(note: Note?) {
        findNavController(this)
            .navigate(NotesFragmentDirections.actionNavToEditNote(note?.id ?: -1))
    }
}