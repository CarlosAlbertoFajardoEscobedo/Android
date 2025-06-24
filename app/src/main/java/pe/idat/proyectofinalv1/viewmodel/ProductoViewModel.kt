package pe.idat.proyectofinalv1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pe.idat.proyectofinalv1.repositorio.ProductoRepository
import pe.idat.proyectofinalv1.retrofit.model.Producto
import pe.idat.proyectofinalv1.retrofit.request.RequestNewProducto
import pe.idat.proyectofinalv1.retrofit.response.ResponseCrudProducto

class ProductoViewModel:ViewModel() {
    var responseCrudProducto:LiveData<ResponseCrudProducto>

    private var repository=ProductoRepository()
    init {
        responseCrudProducto=repository.productoCrudResponse
    }

    fun editarProducto(
        idProducto:Int, nombre:String, descripcion:String,
        stock: Int, precio:Double, urlImagen:String, estado:Boolean){

        responseCrudProducto=repository.editarProducto(Producto(idProducto,nombre,descripcion,stock,precio,urlImagen,estado))

    }

    fun nuevoProducto(nombre:String,  descripcion:String,
                      stock:Int,precio:Double, urlImagen:String,estado:Boolean){
        responseCrudProducto=repository.nuevoProducto(RequestNewProducto(nombre,descripcion, stock ,  precio, urlImagen, estado))
    }

    fun eliminarProductoById(idProducto: Int): LiveData<ResponseCrudProducto>{
        return repository.eliminarProductoById(idProducto)
    }

    fun listarProducto():LiveData<List<Producto>>{
        return repository.listarProductos()
    }
    fun listarByNombre(nomb:String):LiveData<List<Producto>>{
        return repository.listarByNombre(nomb)
    }


}