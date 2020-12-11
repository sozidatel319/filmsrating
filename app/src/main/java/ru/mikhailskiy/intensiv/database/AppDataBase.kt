package ru.mikhailskiy.intensiv.database

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [MovieDTO::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract val likedFilmsDao: MovieDao
}