package pe.idat.proyectofinalv1.view.cliente.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.idat.proyectofinalv1.utilitarios.AppMensaje
import pe.idat.proyectofinalv1.utilitarios.TipoMensaje
import pe.idat.proyectofinalv1.R
import pe.idat.proyectofinalv1.databinding.ActivityRegistroBinding
import pe.idat.proyectofinalv1.retrofit.response.ResponseRegistro
import pe.idat.proyectofinalv1.viewmodel.AuthViewModel

class RegistroActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegistroBinding
    private lateinit var authViewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        authViewModel=ViewModelProvider(this)[AuthViewModel::class.java]

        binding.btnRegistrarCliente.setOnClickListener(this)
        binding.btnVolverLogin.setOnClickListener(this)
        authViewModel.responseRegistro.observe(this, Observer {
            response->obtenerResultadoRegistro(response)
        })


    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnRegistrarCliente->registrarUsuario()
            R.id.btnVolverLogin->startActivity(Intent(applicationContext, LoginActivity::class.java))
        }
    }
    private fun obtenerResultadoRegistro(response: ResponseRegistro) {
        if(response.respuesta){
            seterControles()
            AppMensaje.enviarMensaje(binding.root,
                response.mensaje, TipoMensaje.EXITO)
            binding.btnRegistrarCliente.isEnabled=true
            binding.btnVolverLogin.isEnabled=true
        }else{
            AppMensaje.enviarMensaje(binding.root,response.mensaje, TipoMensaje.ERROR)
        }


    }
    private fun seterControles(){
        binding.tiedtNombre.setText("")
        binding.tiedtApellido.setText("")
        binding.tiedEdad.setText("")
        binding.tiedtNroCel.setText("")
        binding.tiedtDireccion.setText("")
        binding.tiedtDni.setText("")
        binding.tiedtCorreo.setText("")
        binding.tiedtUsuario.setText("")
        binding.tiedtPassword.setText("")
        binding.tiedtConfirmPassword.setText("")
    }


    private fun registrarUsuario(){
        binding.btnRegistrarCliente.isEnabled=false
        binding.btnVolverLogin.isEnabled=false
        if(validarFormulario()){
            authViewModel.registrarUsuario(binding.tiedtNombre.text.toString(),
                binding.tiedtApellido.text.toString(),Integer.parseInt(binding.tiedEdad.text.toString()),
                binding.tiedtNroCel.text.toString(),binding.tiedtDireccion.text.toString(),
                binding.tiedtDni.text.toString(),binding.tiedtCorreo.text.toString(),
                binding.tiedtUsuario.text.toString(),binding.tiedtPassword.text.toString())}
        else{
            binding.btnRegistrarCliente.isEnabled=true
            binding.btnVolverLogin.isEnabled=true
        }
    }

    private fun validarFormulario():Boolean{
        var respuesta=true
        var mensaje=""
        when{
            binding.tiedtNombre.text.toString().trim().isEmpty()->{
                binding.tiedtNombre.isFocusableInTouchMode=true
                binding.tiedtNombre.requestFocus()
                mensaje="Ingrese sus nombres"
                respuesta=false
            }
            binding.tiedtApellido.text.toString().trim().isEmpty()->{
                binding.tiedtApellido.isFocusableInTouchMode=true
                binding.tiedtApellido.requestFocus()
                mensaje="Ingrese sus apellidos"
                respuesta=false
            }
            binding.tiedEdad.text.toString().trim().isEmpty()->{
                binding.tiedEdad.isFocusableInTouchMode=true
                binding.tiedEdad.requestFocus()
                mensaje="Ingrese su edad"
                respuesta=false
            }
            Integer.parseInt( binding.tiedEdad.text.toString().trim())<18->{
                binding.tiedEdad.isFocusableInTouchMode=true
                binding.tiedEdad.requestFocus()
                mensaje="Debe ser mayor de 17 Años para registrarse"
                respuesta=false

            }
            binding.tiedtNroCel.text.toString().trim().isEmpty()->{
                binding.tiedtNroCel.isFocusableInTouchMode=true
                binding.tiedtNroCel.requestFocus()
                mensaje="Ingrese su número celular"
                respuesta=false
            }
            binding.tiedtDireccion.text.toString().trim().isEmpty()->{
                binding.tiedtDireccion.isFocusableInTouchMode=true
                binding.tiedtDireccion.requestFocus()
                mensaje="Ingrese su dirección"
                respuesta=false
            }
            binding.tiedtDni.text.toString().trim().isEmpty()->{
                binding.tiedtDni.isFocusableInTouchMode=true
                binding.tiedtDni.requestFocus()
                mensaje="Ingrese su DNI"
                respuesta=false
            }
            binding.tiedtDni.text.toString().trim().length<8|| binding.tiedtDni.text.toString().trim().length>8 ->{
                binding.tiedtDni.isFocusableInTouchMode=true
                binding.tiedtDni.requestFocus()
                mensaje="Ingrese un DNI valido"
                respuesta=false

            }
            binding.tiedtCorreo.text.toString().trim().isEmpty()->{
                binding.tiedtCorreo.isFocusableInTouchMode=true
                binding.tiedtCorreo.requestFocus()
                mensaje="Ingrese su correo"
                respuesta=false
            }
            binding.tiedtUsuario.text.toString().trim().isEmpty()->{
                binding.tiedtUsuario.isFocusableInTouchMode=true
                binding.tiedtUsuario.requestFocus()
                mensaje="Ingrese su Usuario"
                respuesta=false
            }
            binding.tiedtPassword.text.toString().trim().isEmpty()->{
                binding.tiedtPassword.isFocusableInTouchMode=true
                binding.tiedtPassword.requestFocus()
                mensaje="Ingrese su password"
                respuesta=false
            }
            binding.tiedtConfirmPassword.text.toString().trim().isNotEmpty()->{
                if(binding.tiedtPassword.text.toString() !=binding.tiedtConfirmPassword.text.toString()){
                    binding.tiedtConfirmPassword.isFocusableInTouchMode=true
                    mensaje="El password no coincide"
                    respuesta =false
                }
            }

        }
        if(!respuesta) AppMensaje.enviarMensaje(binding.root,mensaje, TipoMensaje.ERROR)
        return respuesta
    }
}