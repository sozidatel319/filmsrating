package ru.mikhailskiy.intensiv

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navOptions
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.mikhailskiy.intensiv.data.vo.Movie

fun <T> Observable<T>.addSchedulers(): Observable<T> {
    return observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun <T> Single<T>.addSchedulers(): Single<T> {
    return observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun Completable.addSchedulers(): Completable {
    return observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
}

fun View.progressBarVisible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun Fragment.openMovieDetails(movie: Movie, idFragment:Int) {
    val options = navOptions {
        anim {
            enter = R.anim.slide_in_right
            exit = R.anim.slide_out_left
            popEnter = R.anim.slide_in_left
            popExit = R.anim.slide_out_right
        }
    }

    val bundle = Bundle()
    bundle.putParcelable(Constants.MOVIE, movie)
    findNavController().navigate(idFragment, bundle, options)
}

