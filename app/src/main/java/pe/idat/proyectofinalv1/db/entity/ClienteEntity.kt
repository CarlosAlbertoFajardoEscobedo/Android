package pe.idat.proyectofinalv1.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cliente")
data class ClienteEntity(
    @PrimaryKey
    val idCliente:Int,
    val nombres: String,
    val apellidos: String,
    val edad:Int,
    val nroCelular:String,
    val direccion: String,
    val dni: String,
    val correo :String
)