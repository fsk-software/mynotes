package fsk.com.mynotes.ui.note

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
import androidx.navigation.fragment.navArgs
import dagger.android.support.DaggerFragment
import fsk.com.mynotes.R
import fsk.com.mynotes.extensions.plusAssign
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_note.noteCardView
import kotlinx.android.synthetic.main.fragment_note.noteColorRecycler
import kotlinx.android.synthetic.main.fragment_note.noteContentEditText
import kotlinx.android.synthetic.main.fragment_note.noteTitleEditText
import javax.inject.Inject


class NoteFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: NoteViewModel by viewModels {
        viewModelFactory
    }

    val args: NoteFragmentArgs by navArgs()

    @NoteId
    val noteId: Long get() = args.noteId

    private val colorAdapter = NoteColorAdapter()

    private lateinit var disposable: CompositeDisposable

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.fragment_note, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable = CompositeDisposable()

        noteColorRecycler.apply {
            this.adapter = colorAdapter
        }

        disposable += viewModel.initialize().subscribe(
            {
                noteCardView.setCardBackgroundColor(viewModel.getNoteArgb())
                noteTitleEditText.setText(it.title)
                noteContentEditText.setText(it.text)
            },
            {

            }
        )
    }


    override fun onDestroyView() {
        disposable.dispose()
        super.onDestroyView()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.delete) {
            disposable += viewModel.deleteNote().subscribe {
                findNavController(this).popBackStack()
            }
            true
        }
        else{
            super.onOptionsItemSelected(item)
        }
    }
}