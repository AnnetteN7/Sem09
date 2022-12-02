package com.example.sem09.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sem09.databinding.FragmentAddLugarBinding
import com.example.sem09.databinding.FragmentLugarFilaBinding
import com.example.sem09.model.Lugar
import com.example.sem09.ui.lugar.LugarFragmentDirections


class LugarAdapter: RecyclerView.Adapter<LugarAdapter.LugarViewHolder>() {

    private var listaLugares = emptyList<Lugar>()

    inner class LugarViewHolder(private val itemBinding: FragmentLugarFilaBinding): RecyclerView.ViewHolder(itemBinding.root){
        fun dibujar (lugar: Lugar){
            itemBinding.tvNombre.text = lugar.nombre
            itemBinding.tvTelefono.text = lugar.telefono
            itemBinding.tvCorreo.text = lugar.correo

            if(lugar.rutaImagen?.isNotEmpty() ==true){
                Glide.with(itemBinding.root.context)
                    .load(lugar.rutaImagen)
                    .into(itemBinding.imagen)
            }

            //Eventp edit
            itemBinding.vistaFila.setOnClickListener{
                val accion = LugarFragmentDirections
                    .actionNavHomeToUpdateLugarFragment(lugar)
                itemView.findNavController().navigate(accion)
            }

        }
    }
    fun setlugares (lugares: List<Lugar>){
        listaLugares = lugares
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LugarViewHolder {
        val itemBinding = FragmentLugarFilaBinding
            .inflate(LayoutInflater.from(parent.context)
            ,parent,false)
        return LugarViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: LugarViewHolder, position: Int) {
       val lugar = listaLugares[position]
        holder.dibujar(lugar)
    }

    override fun getItemCount(): Int {
        return listaLugares.size
    }
}