package pe.idat.proyectofinalv1.view.cliente.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import pe.idat.proyectofinalv1.utilitarios.AppMensaje
import pe.idat.proyectofinalv1.utilitarios.TipoMensaje
import pe.idat.proyectofinalv1.MenuUsuario
import pe.idat.proyectofinalv1.R
import pe.idat.proyectofinalv1.databinding.ActivityLoginBinding
import pe.idat.proyectofinalv1.db.entity.ClienteEntity
import pe.idat.proyectofinalv1.retrofit.response.ResponseLogin
import pe.idat.proyectofinalv1.view.admin.activity.MenuAdmin
import pe.idat.proyectofinalv1.viewmodel.AuthViewModel
import pe.idat.proyectofinalv1.viewmodel.ClienteViewModel

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var authViewModel: AuthViewModel
    private lateinit var clienteViewModel: ClienteViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel= ViewModelProvider(this)[AuthViewModel::class.java]
        clienteViewModel= ViewModelProvider(this)[ClienteViewModel::class.java]

        limpiarDatos()
        binding.btnlogin.setOnClickListener(this)
        binding.btnregistrar.setOnClickListener(this)
        binding.chkrecordar.setOnClickListener(this)
        authViewModel.responseLogin.observe(this, Observer {
            response->obtenerDatosLogin(response)
        })
    }

    private fun obtenerDatosLogin(response: ResponseLogin){

        if (response.rpta && response.rol=="ROLE_USER"){

            val clienteEntity = ClienteEntity(response.cliente.idCliente,
                response.cliente.nombres,
                response.cliente.apellidos,
                response.cliente.edad,
                response.cliente.nroCelular,
                response.cliente.direccion,
                response.cliente.dni,
                response.cliente.correo)
            clienteViewModel.insertar(clienteEntity)
            startActivity(Intent(applicationContext, MenuUsuario::class.java))
            finish()
        }else if(response.rol=="ROLE_ADMIN"){
            val clienteEntity = ClienteEntity(response.cliente.idCliente,
                response.cliente.nombres,
                response.cliente.apellidos,
                response.cliente.edad,
                response.cliente.nroCelular,
                response.cliente.direccion,
                response.cliente.dni,
                response.cliente.correo)
            clienteViewModel.insertar(clienteEntity)
            startActivity(Intent(applicationContext, MenuAdmin::class.java))
            finish()

        }

        else{
            AppMensaje.enviarMensaje(binding.root,
            "USUARIO INCORRECTO",
            TipoMensaje.ERROR)
        }
        binding.btnregistrar.isEnabled=true
        binding.btnlogin.isEnabled=true
    }

    fun validarUsuarioPassword() : Boolean{
        var okValidacion = true
        if(binding.etusuario.text.toString().trim().isEmpty()){
            binding.etusuario.isFocusableInTouchMode = true
            binding.etusuario.requestFocus()
            okValidacion = false
        } else if(binding.etpassword.text.toString().trim().isEmpty()){
            binding.etpassword.isFocusableInTouchMode = true
            binding.etpassword.requestFocus()
            okValidacion = false
        }
        return okValidacion
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnlogin -> autenticarUsuario()
            R.id.btnregistrar->startActivity(Intent(applicationContext, RegistroActivity::class.java))

        }
    }

    private fun limpiarDatos(){
        clienteViewModel.eliminarTodo();
    }

    private fun autenticarUsuario() {
        binding.btnregistrar.isEnabled = false
        binding.btnlogin.isEnabled = false
        if(validarUsuarioPassword()){
            authViewModel.autenticarUsuario(binding.etusuario.text.toString(),
                binding.etpassword.text.toString())
        }else{
            AppMensaje.enviarMensaje(binding.root,
                "Usuario y Password son necesarios",
                TipoMensaje.ERROR)
            binding.btnregistrar.isEnabled = true
            binding.btnlogin.isEnabled = true
        }
    }
}