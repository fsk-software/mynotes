package fsk.com.mynotes.ui.colorfilter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding3.view.clicks
import dagger.android.support.DaggerDialogFragment
import fsk.com.mynotes.R
import fsk.com.mynotes.extensions.plusAssign
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.dialog_color_filter.colorFilterFab
import kotlinx.android.synthetic.main.dialog_color_filter.colorFilterRecycler
import javax.inject.Inject


class ColorFilterDialogFragment : DaggerDialogFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    val viewModel: ColorFilterViewModel by viewModels {
        viewModelFactory
    }

    private lateinit var disposable: CompositeDisposable
    private val colorFilterAdapter = ColorFilterAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View =
        inflater.inflate(R.layout.dialog_color_filter, container, true)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)

        colorFilterRecycler.adapter = colorFilterAdapter
        colorFilterRecycler.addItemDecoration(
            DividerItemDecoration(colorFilterRecycler.context, LinearLayoutManager.VERTICAL)
        )

        disposable = CompositeDisposable()

        disposable +=
            viewModel.selectedColorsObservable.subscribe { colorFilterAdapter.setSelectedColors(it) }

        disposable +=
            colorFilterFab.clicks().subscribe {
                dismissAllowingStateLoss()
            }

        disposable += colorFilterAdapter.colorCheckedObservable.subscribe { pair ->
            viewModel.updateSelectedColor(pair.first, pair.second)
        }
        colorFilterAdapter.setSelectedColors(viewModel.selectedColors)
    }

    override fun onDestroyView() {
        disposable.clear()
        super.onDestroyView()
    }
}