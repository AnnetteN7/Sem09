package com.example.sem09.model

import android.os.Parcelable

import kotlinx.parcelize.Parcelize


@Parcelize
data class Lugar (
     val id: String,
     val nombre: String,
     val correo: String?,
     val telefono: String?,
     val web: String?,
     val rutaAudio:String?,
     val rutaImagen:String
):Parcelable{
     constructor():
             this ("","","","","","","")
}

