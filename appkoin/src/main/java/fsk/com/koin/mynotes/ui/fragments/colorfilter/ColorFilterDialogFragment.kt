package fsk.com.koin.mynotes.ui.fragments.colorfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider
import com.uber.autodispose.autoDispose
import fsk.com.koin.mynotes.R
import fsk.com.koin.mynotes.data.preferences.NoteFilterPreferences
import kotlinx.android.synthetic.main.dialog_color_filter.colorFilterFab
import kotlinx.android.synthetic.main.dialog_color_filter.colorFilterRecycler
import org.koin.android.viewmodel.ext.android.viewModel


/**
 * Dialog to display the color filter options.
 * Color selection changes are saved into persistent memory directory.
 * See [NoteFilterPreferences] to monitor note color selections.
 */
class ColorFilterDialogFragment : DialogFragment() {


    private val viewModel: ColorFilterViewModel by viewModel()

    private val scopeProvider: AndroidLifecycleScopeProvider by lazy {
        AndroidLifecycleScopeProvider.from(this)
    }

    private val colorFilterAdapter = ColorFilterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dialog_color_filter, container, true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        colorFilterRecycler.apply {
            adapter = colorFilterAdapter
            addItemDecoration(
                DividerItemDecoration(
                    colorFilterRecycler.context,
                    LinearLayoutManager.VERTICAL
                )
            )
        }

        viewModel.getSelectedColorUpdates
            .autoDispose(scopeProvider)
            .subscribe { colorFilterAdapter.setSelectedColors(it) }

        colorFilterFab.clicks()
            .autoDispose(scopeProvider)
            .subscribe { dismissAllowingStateLoss() }

        colorFilterAdapter.getOnColorSelectionUpdates
            .autoDispose(scopeProvider)
            .subscribe { pair -> viewModel.updateSelectedColor(pair.first, pair.second) }
    }
}