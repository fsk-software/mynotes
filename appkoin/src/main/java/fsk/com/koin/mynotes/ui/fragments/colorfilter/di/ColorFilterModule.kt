package fsk.com.koin.mynotes.ui.fragments.colorfilter.di

import fsk.com.koin.mynotes.ui.fragments.colorfilter.ColorFilterViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Module to create and define the color filter dependencies.
 */
val colorFilterModule = module {
    viewModel { ColorFilterViewModel(get()) }
}