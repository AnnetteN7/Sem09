package com.example.sem09.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.sem09.data.LugarDao
import com.example.sem09.model.Lugar
import com.example.sem09.repository.LugarRepository
import kotlinx.coroutines.launch

class LugarViewModel(application: Application) : AndroidViewModel(application) {
    val getLugares: MutableLiveData<List<Lugar>>
    private val repository: LugarRepository = LugarRepository(LugarDao())

    init {
        getLugares = repository.getLugares
    }

     fun saveLugar(lugar: Lugar) {
        repository.saveLugar(lugar)
    }

    fun deleteLugar(lugar: Lugar) {
        repository.deleteLugar(lugar)
    }
}