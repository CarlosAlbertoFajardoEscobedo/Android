package pe.idat.proyectofinalv1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.idat.proyectofinalv1.repositorio.DetalleOrdenRepository
import pe.idat.proyectofinalv1.retrofit.response.ResponseDetalleOrden

class DetalleOrdenViewModel : ViewModel() {

    private var repository=DetalleOrdenRepository()

    fun listarDetalle(idOrden:Int): LiveData<List<ResponseDetalleOrden>> {
        return repository.listarDetalleOrdByIdOrd(idOrden)
    }
}