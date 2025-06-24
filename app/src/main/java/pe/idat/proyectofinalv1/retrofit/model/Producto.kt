package pe.idat.proyectofinalv1.retrofit.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Producto (var idProducto: Int, var nombre:String,
                var descripcion:String, var stock:Int,
                var precio:Double, var urlImagen:String,
                var estado:Boolean):Parcelable
