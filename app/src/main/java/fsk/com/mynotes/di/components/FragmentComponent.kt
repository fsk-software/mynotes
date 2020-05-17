package fsk.com.mynotes.di.components

import androidx.fragment.app.Fragment
import dagger.BindsInstance
import dagger.Subcomponent
import fsk.com.mynotes.di.scopes.FragmentScope

@FragmentScope
@Subcomponent
interface FragmentComponent {

    @Subcomponent.Builder
    interface Builder {
        @BindsInstance
        fun fragment(fragment: Fragment): Builder

        fun build(): FragmentComponent
    }
}