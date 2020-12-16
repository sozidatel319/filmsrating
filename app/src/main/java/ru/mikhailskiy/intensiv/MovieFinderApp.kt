package ru.mikhailskiy.intensiv

import android.app.Application
import androidx.room.Room
import ru.mikhailskiy.intensiv.Constants.DATABASE_NAME
import ru.mikhailskiy.intensiv.database.AppDatabase
import timber.log.Timber

class MovieFinderApp : Application() {
    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, DATABASE_NAME
        ).build()
        initDebugTools()
    }

    private fun initDebugTools() {
        if (BuildConfig.DEBUG) {
            initTimber()
        }
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        lateinit var instance: MovieFinderApp
        private set
    }
}