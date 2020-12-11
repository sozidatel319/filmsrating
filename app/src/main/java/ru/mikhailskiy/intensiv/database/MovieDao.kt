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
    fun insertMovie(movieDTO: MovieDTO): Completable

    @Query("DELETE FROM MovieDTO WHERE id = :id")
    fun deleteMovieById(id: Long): Completable

    @Query("SELECT * FROM MovieDTO")
    fun getAllLikedMovies(): Single<List<MovieDTO>>

    @Query("SELECT * FROM MovieDTO WHERE id =:id")
    fun findLikedFilm(id: Long): Observable<MovieDTO>
}