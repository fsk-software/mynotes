package fsk.com.mynotes.di.scopes

import javax.inject.Scope

/**
 * A scope that indicates the item is scoped to an application
 */
@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope