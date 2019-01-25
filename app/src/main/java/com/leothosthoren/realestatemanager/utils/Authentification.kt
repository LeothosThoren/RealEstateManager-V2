package com.leothosthoren.realestatemanager.utils

import android.util.Patterns
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.Single

//If the app encounters an error, then try again//

inline fun retryWhenError(crossinline onError: (ex: Throwable) -> Unit): ObservableTransformer<String, String> =
        ObservableTransformer { observable ->
            observable.retryWhen { errors ->

                //Use the flatmap() operator to flatten all emissions into a single Observable//

                errors.flatMap {
                    onError(it)
                    Observable.just("")
                }

            }
        }

//Define an ObservableTransformer, where we’ll perform the email validation//

val validateEmailAddress = ObservableTransformer<String, String> { observable ->
    observable.flatMap {
        Observable.just(it).map { it.trim() }

//Check whether the user input matches Android’s email pattern//

                .filter {
                    Patterns.EMAIL_ADDRESS.matcher(it).matches()

                }

//If the user’s input doesn’t match the email pattern, then throw an error//

                .singleOrError()
                .onErrorResumeNext {
                    if (it is NoSuchElementException) {
                        Single.error(Exception("Please enter a valid email address"))
                    } else {
                        Single.error(it)

                    }
                }
                .toObservable()
    }

}

val validatePassword = ObservableTransformer<String, String> { observable ->
    observable.flatMap {
        Observable.just(it).map { it.trim() }

//Check whether the user input matches Android’s email pattern//

                .filter {
                    it.length > 7

                }

//If the user’s input doesn’t match the email pattern, then throw an error//

                .singleOrError()
                .onErrorResumeNext {
                    if (it is NoSuchElementException) {
                        Single.error(Exception("Your password must be 7 characters or more"))
                    } else {
                        Single.error(it)

                    }
                }
                .toObservable()
    }
}