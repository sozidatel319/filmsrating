package ru.mikhailskiy.intensiv

import android.app.Application
import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import ru.mikhailskiy.intensiv.Constants.DATABASE_NAME
import ru.mikhailskiy.intensiv.database.AppDatabase
import ru.mikhailskiy.intensiv.di.domainModule
import ru.mikhailskiy.intensiv.di.presentationModule
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

        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@MovieFinderApp)
            modules(domainModule, presentationModule)
        }
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