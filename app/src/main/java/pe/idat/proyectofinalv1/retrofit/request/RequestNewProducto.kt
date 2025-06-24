package pe.idat.proyectofinalv1.retrofit.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RequestNewProducto ( var nombre:String,
                          var descripcion:String, var stock:Int,
                          var precio:Double, var urlImagen:String,
                          var estado:Boolean): Parcelable