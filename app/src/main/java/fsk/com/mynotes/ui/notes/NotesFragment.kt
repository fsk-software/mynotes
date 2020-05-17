package fsk.com.mynotes.ui.notes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.support.DaggerFragment
import fsk.com.mynotes.R
import fsk.com.mynotes.extensions.plusAssign
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_notes.notes_add_fab
import kotlinx.android.synthetic.main.fragment_notes.notes_recycler
import javax.inject.Inject


class NotesFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: NotesViewModel by viewModels {
        viewModelFactory
    }

    /**
     * The adapter to manage the display of cards in {@link #cardsRecyclerView}.
     */
    private val cardAdapter = NotesAdapter()

    private lateinit var disposable: CompositeDisposable
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

        disposable = CompositeDisposable()
        //setup color filter

        val layoutManager = FlexboxLayoutManager(context)
        layoutManager.flexDirection = FlexDirection.COLUMN
        layoutManager.justifyContent = JustifyContent.FLEX_END

        notes_recycler.apply {
            this.layoutManager = layoutManager
            this.adapter = cardAdapter
            addItemDecoration(EmptyItemDecoration())
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        disposable += viewModel.selectedColorsObservable.subscribe {
            activity?.invalidateOptionsMenu()
        }

        disposable += viewModel.editNoteObservable.subscribe {
            findNavController(this)
                .navigate(NotesFragmentDirections.actionNavToEditNote(it))
        }

        disposable += notes_add_fab.clicks().subscribe { viewModel.updateNote(null) }

        disposable += cardAdapter.noteClickedObservable.subscribe { note ->
            viewModel.updateNote(note)
        }

        disposable += viewModel.notesFlowable.subscribe { notes ->
            cardAdapter.submitList(notes)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.notes_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.findItem(R.id.color_filter).title = viewModel.getSelectedColorsToolTip()
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
       return if (item.itemId == R.id.color_filter) {
           findNavController(this).navigate(R.id.colorFilterDialog)
           true
        }
        else{
           super.onOptionsItemSelected(item)
       }
    }

    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }
}