package ru.bit.espanola

import android.app.Application
import android.arch.persistence.room.Room
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.bind
import org.kodein.di.generic.singleton

class App : Application(), KodeinAware {

    override val kodein = Kodein.lazy {
        bind<WordDatabase>() with singleton {
            Room.databaseBuilder(applicationContext, WordDatabase::class.java, "words-db2").build()
        }
    }

    override fun onCreate() {
        super.onCreate()

    }
}