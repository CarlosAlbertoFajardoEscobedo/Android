package pe.idat.proyectofinalv1.repositorio

import android.util.Log
import androidx.lifecycle.MutableLiveData
import pe.idat.proyectofinalv1.retrofit.TiendaCliente
import pe.idat.proyectofinalv1.retrofit.response.ResponseDetalleOrden
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class DetalleOrdenRepository {

    var detalleOrdenResponse= MutableLiveData<List<ResponseDetalleOrden>>()

    fun listarDetalleOrdByIdOrd(idOrden: Int): MutableLiveData<List<ResponseDetalleOrden>> {
        val call: Call<List<ResponseDetalleOrden>> = TiendaCliente.retrofiteService.listDetalleByOrdenId(idOrden)
        call.enqueue(object : Callback<List<ResponseDetalleOrden>> {
            override fun onResponse(
                call: Call<List<ResponseDetalleOrden>>,
                response: Response<List<ResponseDetalleOrden>>
            ) {
                detalleOrdenResponse.value=response.body()
            }

            override fun onFailure(call: Call<List<ResponseDetalleOrden>>, t: Throwable) {
                Log.e("ErrorDetallOrd",  t.message.toString())
            }

        })
        return detalleOrdenResponse
    }
}