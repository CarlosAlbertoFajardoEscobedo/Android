package pe.idat.proyectofinalv1.retrofit

import pe.idat.proyectofinalv1.retrofit.model.Producto
import pe.idat.proyectofinalv1.retrofit.request.RequestGuardarOrden
import pe.idat.proyectofinalv1.retrofit.request.RequestLogin
import pe.idat.proyectofinalv1.retrofit.request.RequestNewProducto
import pe.idat.proyectofinalv1.retrofit.request.RequestRegistro
import pe.idat.proyectofinalv1.retrofit.response.*
import retrofit2.Call
import retrofit2.http.*

interface TiendaServicio {

    @POST("usuarios/v1/crearToken")
    fun login(@Body requestLogin: RequestLogin): Call<ResponseLogin>

    @POST("usuarios/v1/guardar")
    fun registro(@Body requestRegistro: RequestRegistro): Call<ResponseRegistro>


    @GET("productos/v1/listar")
    fun listarProductos():Call<List<Producto>>

    @POST("productos/v1/guardar")
    fun nuevoProducto(@Body requestNewProducto: RequestNewProducto):Call<ResponseCrudProducto>

    @PUT("productos/v1/editar")
    fun editarProducto(@Body requestEditProducto:Producto):Call<ResponseCrudProducto>

    @DELETE("productos/v1/eliminar/{id}")
    fun eliminarProducto(@Path("id") id:Int):Call<ResponseCrudProducto>

    @GET("productos/v1/listarByNombre/{nomb}")
    fun productosByName(@Path("nomb") nomb:String):Call<List<Producto>>

    @GET("ordenes/v1/listar")
    fun listarAllOrden():Call<List<ResponseOrden>>

    @POST("ordenes/v1/guardar")
    fun guardarOrden(@Body requestGuardarOrden: RequestGuardarOrden): Call<ResponseGuardarOrden>

    @GET("ordenes/v1/listarByClienteId/{id}")
    fun listOrdenByClienteId(@Path("id") id:Int):Call<List<ResponseOrden>>

    @GET("detalles/v1/listar/{id}")
    fun listDetalleByOrdenId(@Path("id") id:Int): Call<List<ResponseDetalleOrden>>
}