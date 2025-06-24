package pe.idat.proyectofinalv1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.idat.proyectofinalv1.repositorio.UsuarioRepository
import pe.idat.proyectofinalv1.retrofit.request.RequestLogin
import pe.idat.proyectofinalv1.retrofit.request.RequestRegistro
import pe.idat.proyectofinalv1.retrofit.response.ResponseLogin
import pe.idat.proyectofinalv1.retrofit.response.ResponseRegistro

class AuthViewModel : ViewModel(){
    var responseLogin : LiveData<ResponseLogin>
    var responseRegistro : LiveData<ResponseRegistro>
    private var repository = UsuarioRepository()

    init {
        responseLogin=repository.loginResponse
        responseRegistro=repository.registroResponse
    }
    fun registrarUsuario(nombres:String,apellidos:String,edad:Int,nroCelular:String,
                         direcion:String,dni:String,correo:String,usuario:String,password:String){
        responseRegistro=repository.registrarUsuario(
            RequestRegistro(nombres,apellidos,edad,nroCelular
            ,direcion,dni,correo,usuario,password)
        )
    }

    fun autenticarUsuario(usuario: String, password: String){
        responseLogin = repository.autenticarUsuario(
            RequestLogin(usuario, password)
        )
    }

}