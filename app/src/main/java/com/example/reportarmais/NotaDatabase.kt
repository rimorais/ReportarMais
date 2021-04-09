package com.example.reportarmais

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Nota::class],
    version = 1,
    exportSchema = false
)
abstract class NotaDatabase : RoomDatabase() {

    abstract fun notaDao(): NotaDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDatabase? = null

        fun getDatabase(context: Context): NotaDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance as NotaDatabase
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotaDatabase::class.java,
                    "nota_database"
                ).fallbackToDestructiveMigration()          // TODO: migration
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}