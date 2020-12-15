package ru.mikhailskiy.intensiv.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface MovieDao {

    @Insert(onConflict = REPLACE)
    fun insertMovie(movieEntity: MovieEntity): Completable

    @Query("DELETE FROM MovieEntity WHERE id = :id")
    fun deleteMovieById(id: Long): Completable

    @Query("SELECT * FROM MovieEntity")
    fun getAllLikedMovies(): Single<List<MovieEntity>>

    @Query("SELECT * FROM MovieEntity WHERE id =:id")
    fun findLikedFilm(id: Long): Observable<MovieEntity>
}