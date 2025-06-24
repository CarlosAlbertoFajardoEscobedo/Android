package pe.idat.proyectofinalv1.repositorio

import android.util.Log
import androidx.lifecycle.MutableLiveData
import pe.idat.proyectofinalv1.retrofit.TiendaCliente
import pe.idat.proyectofinalv1.retrofit.model.Producto
import pe.idat.proyectofinalv1.retrofit.request.RequestNewProducto
import pe.idat.proyectofinalv1.retrofit.response.ResponseCrudProducto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductoRepository {

    var monturaResponse= MutableLiveData<List<Producto>>()
    var monturaNomResponse= MutableLiveData<List<Producto>>()
    var productoCrudResponse=MutableLiveData<ResponseCrudProducto>()


    fun nuevoProducto(requestNewProducto: RequestNewProducto):MutableLiveData<ResponseCrudProducto>{
        val call:Call<ResponseCrudProducto> = TiendaCliente.retrofiteService.nuevoProducto(requestNewProducto)
        call.enqueue(object :Callback<ResponseCrudProducto>{
            override fun onResponse(
                call: Call<ResponseCrudProducto>,
                response: Response<ResponseCrudProducto>
            ) {
                productoCrudResponse.value=response.body()
            }

            override fun onFailure(call: Call<ResponseCrudProducto>, t: Throwable) {
                Log.e("ErrorEdit",  t.message.toString())
            }

        })
        return productoCrudResponse
    }

    fun editarProducto(requestProductox: Producto):MutableLiveData<ResponseCrudProducto>{
        val call: Call<ResponseCrudProducto> = TiendaCliente.retrofiteService.editarProducto(requestProductox)

        call.enqueue(object : Callback<ResponseCrudProducto>{
            override fun onResponse(
                call: Call<ResponseCrudProducto>,
                response: Response<ResponseCrudProducto>
            ) {
                productoCrudResponse.value=response.body()
            }

            override fun onFailure(call: Call<ResponseCrudProducto>, t: Throwable) {
                Log.e("ErrorEdit",  t.message.toString())
            }

        })

        return productoCrudResponse
    }

    fun eliminarProductoById(idProducto:Int):MutableLiveData<ResponseCrudProducto>{
        val call : Call <ResponseCrudProducto> = TiendaCliente.retrofiteService.eliminarProducto(idProducto)
        call.enqueue(object : Callback<ResponseCrudProducto>{
            override fun onResponse(
                call: Call<ResponseCrudProducto>,
                response: Response<ResponseCrudProducto>
            ) {
                productoCrudResponse.value=response.body()
            }

            override fun onFailure(call: Call<ResponseCrudProducto>, t: Throwable) {
                Log.e("ErrorEdit",  t.message.toString())
            }

        })
        return productoCrudResponse
    }


    fun listarProductos():MutableLiveData<List<Producto>>{
        val call: Call<List<Producto>> = TiendaCliente.retrofiteService.listarProductos()
        call.enqueue(object : Callback<List<Producto>> {
            override fun onResponse(
                call: Call<List<Producto>>,
                response: Response<List<Producto>>
            ) {
                monturaResponse.value=response.body()
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("ErrorLISTADOMONTURAS",  t.message.toString())
            }

        })
        return monturaResponse
    }

    fun listarByNombre(nomb:String):MutableLiveData<List<Producto>>{
        val call:Call<List<Producto>> = TiendaCliente.retrofiteService.productosByName(nomb)
        call.enqueue(object :Callback<List<Producto>>{
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {
                monturaNomResponse.value=response.body()
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
        return monturaNomResponse
    }


}