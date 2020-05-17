package fsk.com.mynotes.extensions

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

operator fun CompositeDisposable.plusAssign(disposable: Disposable) {
    add(disposable)
}

fun Disposable.addTo(androidDisposable: CompositeDisposable): Disposable
        = apply { androidDisposable.add(this) }