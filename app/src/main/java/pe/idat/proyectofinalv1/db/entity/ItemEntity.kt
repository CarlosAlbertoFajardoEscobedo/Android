package pe.idat.proyectofinalv1.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class ItemEntity (
    @PrimaryKey
    var idproducto:Int,
    var nombre:String,
    var descripcion:String,
    var urlImagen:String,
    var precio:Double,
    var quantity:Int
        )
