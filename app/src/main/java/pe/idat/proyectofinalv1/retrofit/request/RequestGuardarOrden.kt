package pe.idat.proyectofinalv1.retrofit.request

import pe.idat.proyectofinalv1.db.entity.ItemEntity

data class RequestGuardarOrden( var id_Cliente:Int,
                           var descuento:Double,
                           var productoRequest: List<ItemEntity>)