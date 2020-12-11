package ru.mikhailskiy.intensiv

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

fun <T> Observable<T>.addSchedulers(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T>Single<T>.addSchedulers(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun Completable.addSchedulers(): Completable {
    return observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}
