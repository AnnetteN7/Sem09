package com.example.sem09.ui.lugar

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.Global.getString
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.sem09.R
import com.example.sem09.R.*
import com.example.sem09.databinding.FragmentAddLugarBinding
import com.example.sem09.model.Lugar
import com.example.sem09.utilidades.AudioUtiles
import com.example.sem09.utilidades.ImagenUtilidades
import com.example.sem09.viewmodel.LugarViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference




class AddLugar : Fragment() {
   private var _binding:FragmentAddLugarBinding? = null
    private val binding get() = _binding!!
    private lateinit var lugarViewModel: LugarViewModel

    private lateinit var audioUtiles: AudioUtiles

    private lateinit var imagenUtiles: ImagenUtilidades
    private lateinit var tomarFotoActivity: ActivityResultLauncher<Intent>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
       lugarViewModel = ViewModelProvider(this).get(lugarViewModel::class.java)
        _binding = FragmentAddLugarBinding.inflate(inflater,container,false)

        binding.btAgregarLugar.setOnClickListener {
            binding.progressBar.visibility = ProgressBar.VISIBLE
            binding.msgMensaje.text = getString(string.msgGuardandoLugar)
            binding.msgMensaje.visibility = TextView.VISIBLE
            subirAudio()
        }

        //Audio
        audioUtiles = AudioUtiles(requireActivity(),requireContext(), binding.btAccion, binding.btPlay,
            binding.btDelete,getString(string.msgInicioNotaAudio),getString(string.msgDetieneNotaAudio))

        //Fotos
        tomarFotoActivity = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if(result.resultCode == Activity.RESULT_OK){
                imagenUtiles.actualizaFoto()
            }
        }

        imagenUtiles = ImagenUtilidades(requireContext(),binding.btPhoto, binding.btRotaL,
            binding.btRotaR, binding.imagen, tomarFotoActivity)

        return binding.root
    }

    private fun subirAudio(){
        val audioFile = audioUtiles.audioFile
        if(audioFile.exists() && audioFile.isFile && audioFile.canRead()){
            val ruta = Uri.fromFile(audioFile)
            val rutaNube = "lugaresM/${Firebase.auth.currentUser?.email}/audios/${audioFile.name}"
            val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)
            referencia.putFile(ruta)
                .addOnSuccessListener {
                    referencia.downloadUrl
                        .addOnSuccessListener {
                            val rutaAudio = it.toString()
                            subirImagen(rutaAudio)
                        }
                }
                .addOnCanceledListener { subirImagen("") }
        }
        else{
            subirImagen("")
        }
    }

    private fun subirImagen(rutaAudio:String){
        val imagenFile = imagenUtiles.imagenFile
        if(imagenFile.exists() && imagenFile.isFile && imagenFile.canRead()){
            val ruta = Uri.fromFile(imagenFile)
            val rutaNube = "lugaresM/${Firebase.auth.currentUser?.email}/images/${imagenFile.name}"
            val referencia: StorageReference = Firebase.storage.reference.child(rutaNube)
            referencia.putFile(ruta)
                .addOnSuccessListener {
                    referencia.downloadUrl
                        .addOnSuccessListener {
                            val rutaImagen = it.toString()
                            addLugar(rutaAudio,rutaImagen)
                        }
                }
                .addOnCanceledListener { addLugar(rutaAudio,"") }
        }
        else{
            addLugar(rutaAudio,"")
        }
    }

    private fun addLugar(rutaAudio: String,rutaImage:String){
        val nombre = binding.etNombre.text.toString()
        val correo = binding.etEmail.text.toString()
        val telefono = binding.etTelefono.text.toString()
        val web = binding.etWeb.text.toString()

        if(nombre.isNotEmpty()){
            val lugar = Lugar("",nombre,correo,telefono,web,rutaAudio,rutaImage)
            lugarViewModel.saveLugar(lugar)
            Toast.makeText(requireContext(),"Lugar agregado..",
                 Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addLugar_to_nav_home)
            //Proceso de agregar a BD

        } else {
            Toast.makeText(requireContext(),getString(string.msg_data),
            Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
