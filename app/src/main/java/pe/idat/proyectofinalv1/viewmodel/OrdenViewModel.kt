package pe.idat.proyectofinalv1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.idat.proyectofinalv1.db.entity.ItemEntity
import pe.idat.proyectofinalv1.repositorio.OrdenRepository
import pe.idat.proyectofinalv1.retrofit.request.RequestGuardarOrden
import pe.idat.proyectofinalv1.retrofit.response.ResponseGuardarOrden
import pe.idat.proyectofinalv1.retrofit.response.ResponseOrden

class OrdenViewModel : ViewModel(){

    var responseGuardarOrden: LiveData<ResponseGuardarOrden>
    private  var repository=OrdenRepository()
    init {
        responseGuardarOrden=repository.ordenResponse
    }

    fun listarOrden(idCliente:Int): LiveData<List<ResponseOrden>> {
        return repository.listarOrdenByIdClient(idCliente)
    }

    fun guardarOrder(idCliente: Int,descuento:Double,monturaRequest:List<ItemEntity>){
        responseGuardarOrden=repository.guardarOrden(RequestGuardarOrden(idCliente,descuento,monturaRequest))
    }

    fun listarTodasOrdenes():LiveData<List<ResponseOrden>>{
        return repository.listarOrdenes()
    }
}