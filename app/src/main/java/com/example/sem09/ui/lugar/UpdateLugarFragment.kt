package com.example.sem09.ui.lugar

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.sem09.R
import com.example.sem09.databinding.FragmentUpdateLugarBinding
import com.example.sem09.model.Lugar
import com.example.sem09.viewmodel.LugarViewModel


class UpdateLugarFragment : Fragment() {
  //Recupera Argumentos
    private val args by navArgs<UpdateLugarFragmentArgs>()

    private var _binding: FragmentUpdateLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        }

override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        lugarViewModel = ViewModelProvider(this).get(LugarViewModel :: class.java)
    _binding = FragmentUpdateLugarBinding.inflate(inflater,container,false)

    //cargar los valores Edit
    binding.etNombre.setText(args.lugar.nombre)
    binding.etTelefono.setText(args.lugar.telefono)
    binding.etEmail.setText(args.lugar.correo)
    binding.etWeb.setText(args.lugar.web)



       //Inflete the layout for this fragment
       return binding.root
    }


    private fun updateLugar() {
        val nombre = binding.etNombre.text.toString()
        val correo = binding.etEmail.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val web = binding.etWeb.text.toString()
        if (nombre.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.msg_data), Toast.LENGTH_LONG).show()
        } else if (correo.isEmpty()) {
            Toast.makeText(requireContext(), getString(R.string.msg_data), Toast.LENGTH_LONG).show()

        }
        //crear objeto
        else {
            val lugar = Lugar(args.lugar.id, nombre, correo, telefono, web)
            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(), getString(R.string.bt_update_lugar), Toast.LENGTH_LONG)
                .show()
            findNavController().navigate(R.id.action_updateLugarFragment_to_nav_home)
        }


        binding.btAgregarLugar.setOnClickListener {
            updateLugar()
        }


    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
       inflater.inflate(R.menu.delete_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId==R.id.menu_delete){
            deleteLugar()
        }
        return super.onContextItemSelected(item)
    }
        private fun deleteLugar(){
            val builder = AlertDialog.Builder(requireContext())
         /*   builder.setPositiveButton(getString(R.string.si)) {_,_ ->*/
                lugarViewModel.deleteLugar(args.lugar)
                Toast.makeText(requireContext(),
                getString(R.string.menu_delete)+ "${args.lugar.nombre}!",
                Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateLugarFragment_to_nav_home)
            }
            /*builder.setNegativeButton(getString(R.string.no)) {_,_ ->}
            builder.setTitle(R.string.menu_delete)
            builder.setMessage(getString(R.string.msg_delete)+"${args.lugar.nombre}?")
            builder.create().show()
        }*/


    }





