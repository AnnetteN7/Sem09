package com.example.sem09.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.sem09.model.Lugar


@Dao
interface LugarDao {
    //Obtener datos


    //Insert
    @Insert( onConflict = OnConflictStrategy.IGNORE)
    suspend fun addLugar(lugar: Lugar)

    //Update
    @Update( onConflict = OnConflictStrategy.IGNORE)
    suspend fun updateLugar(lugar: Lugar)

    //Delete
    @Delete
    suspend fun deleteLugar(lugar: Lugar)

     @Query("SELECT * FROM LUGAR")
   fun getLugares() : LiveData<List<Lugar>>
}