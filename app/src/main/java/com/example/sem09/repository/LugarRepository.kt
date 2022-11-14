package com.example.sem09.repository

import androidx.lifecycle.LiveData
import com.example.sem09.data.LugarDao
import com.example.sem09.model.Lugar

class LugarRepository (private val lugarDao: LugarDao) {
suspend fun saveLugar(lugar: Lugar){
    if (lugar.id == 0){
        lugarDao.addLugar(lugar)
    }
    else{
        lugarDao.updateLugar(lugar)
    }
}
    suspend fun deleteLugar(lugar: Lugar){
        lugarDao.deleteLugar(lugar)
    }

    val getLugares : LiveData<List<Lugar>> = lugarDao.getLugares()

}