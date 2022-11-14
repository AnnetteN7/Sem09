package com.example.sem09.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.sem09.model.Lugar


@Database(entities = [Lugar::class],version= 1, exportSchema =  false)
abstract class LugarDatabase :RoomDatabase() {
    abstract fun  lugarDao(): LugarDao

    companion object {
        @Volatile
        private var Instancia: LugarDatabase? = null

        fun getDatabase(context: android.content.Context) : LugarDatabase{
            val bd = Instancia
            if (bd !=null){
                return bd

            }
            synchronized(this){
                val  instancia = Room.databaseBuilder(
                    context.applicationContext,
                    LugarDatabase::class.java,
                    "lugar_database"
                ).build()
                Instancia = instancia
                return instancia
            }
        }


    }

}