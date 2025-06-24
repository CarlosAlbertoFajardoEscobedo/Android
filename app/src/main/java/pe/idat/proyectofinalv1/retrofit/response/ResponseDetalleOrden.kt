package pe.idat.proyectofinalv1.retrofit.response

data class ResponseDetalleOrden (var idDetalle:Int,var nombre:String,
                                 var urlImage:String,var cantidad:Integer,
                                 var precio:Double,var total:Double)