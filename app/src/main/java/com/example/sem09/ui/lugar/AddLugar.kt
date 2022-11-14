package com.example.sem09.ui.lugar

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.sem09.R
import com.example.sem09.databinding.FragmentAddLugarBinding
import com.example.sem09.model.Lugar
import com.example.sem09.viewmodel.LugarViewModel


class AddLugar : Fragment() {
   private var _binding:FragmentAddLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
       lugarViewModel = ViewModelProvider(this).get(lugarViewModel::class.java)
        _binding = FragmentAddLugarBinding.inflate(inflater,container,false)

        binding.btAgregarLugar.setOnClickListener{ addLugar()}


        return binding.root
    }

    private fun addLugar(){
        val nombre = binding.etNombre.text.toString()
        val correo = binding.etEmail.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val web = binding.etWeb.text.toString()

        if(nombre.isNotEmpty()){
            val lugar = Lugar(0,nombre,correo,telefono,web)
            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(),getString(R.string.msg_lugar_added),
            Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addLugar_to_nav_home)
            //Proceso de agregar a BD

        } else {
            Toast.makeText(requireContext(),getString(R.string.msg_data),
            Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
