package pe.idat.proyectofinalv1.repositorio

import android.util.Log
import androidx.lifecycle.MutableLiveData
import pe.idat.proyectofinalv1.retrofit.TiendaCliente
import pe.idat.proyectofinalv1.retrofit.request.RequestGuardarOrden
import pe.idat.proyectofinalv1.retrofit.response.ResponseGuardarOrden
import pe.idat.proyectofinalv1.retrofit.response.ResponseOrden
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrdenRepository {
    var ordenResponse= MutableLiveData<ResponseGuardarOrden>()
    var listordenResponse= MutableLiveData<List<ResponseOrden>>()



    fun guardarOrden(requestGuardarOrden: RequestGuardarOrden)
            : MutableLiveData<ResponseGuardarOrden> {
        val call: Call<ResponseGuardarOrden> = TiendaCliente.retrofiteService.guardarOrden(requestGuardarOrden)
        call.enqueue(object : Callback<ResponseGuardarOrden> {
            override fun onResponse(
                call: Call<ResponseGuardarOrden>,
                response: Response<ResponseGuardarOrden>
            ) {
                ordenResponse.value=response.body()
            }

            override fun onFailure(call: Call<ResponseGuardarOrden>, t: Throwable) {
                Log.e("ErrorSave", t.message.toString())
            }

        })
        return ordenResponse
    }


    fun listarOrdenByIdClient( idCliente:Int): MutableLiveData<List<ResponseOrden>> {
        val call: Call<List<ResponseOrden>> = TiendaCliente.retrofiteService.listOrdenByClienteId(idCliente)
        call.enqueue(object : Callback<List<ResponseOrden>> {
            override fun onResponse(
                call: Call<List<ResponseOrden>>,
                response: Response<List<ResponseOrden>>
            ) {
                listordenResponse.value=response.body()
            }

            override fun onFailure(call: Call<List<ResponseOrden>>, t: Throwable) {
                Log.e("ErrorListIdCli",  t.message.toString())
            }

        })
        return listordenResponse
    }


    fun listarOrdenes():MutableLiveData<List<ResponseOrden>>{
        val call:Call<List<ResponseOrden>> = TiendaCliente.retrofiteService.listarAllOrden()
        call.enqueue(object : Callback<List<ResponseOrden>>{
            override fun onResponse(
                call: Call<List<ResponseOrden>>,
                response: Response<List<ResponseOrden>>
            ) {
                listordenResponse.value=response.body()
            }

            override fun onFailure(call: Call<List<ResponseOrden>>, t: Throwable) {
                Log.e("ErrorList",  t.message.toString())
            }

        })
        return listordenResponse
    }

}