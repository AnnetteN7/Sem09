package com.example.sem09.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.sem09.data.LugarDao
import com.example.sem09.model.Lugar

class LugarRepository (private val lugarDao: LugarDao) {
suspend fun saveLugar(lugar: Lugar){
    lugarDao.saveLugar(lugar)
}
    fun deleteLugar(lugar: Lugar){
        lugarDao.deleteLugar(lugar)
    }

    val getLugares : MutableLiveData<List<Lugar>> = lugarDao.getLugares()

}