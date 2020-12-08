package ru.mikhailskiy.intensiv

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.subscribeOnIoAndObserveOnMainThread(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T>Single<T>.subscribeOnIoAndObserveOnMainThread(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}
