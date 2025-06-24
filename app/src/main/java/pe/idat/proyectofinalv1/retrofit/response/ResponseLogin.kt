package pe.idat.proyectofinalv1.retrofit.response

import pe.idat.proyectofinalv1.retrofit.model.Cliente

data class ResponseLogin(var token:String, var cliente:Cliente,var rpta:Boolean,var rol:String)