package pe.idat.proyectofinalv1.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import pe.idat.proyectofinalv1.db.dao.ClienteDao
import pe.idat.proyectofinalv1.db.dao.ItemDao
import pe.idat.proyectofinalv1.db.entity.ClienteEntity
import pe.idat.proyectofinalv1.db.entity.ItemEntity

@Database(entities = [ClienteEntity::class,ItemEntity::class], version = 1)
abstract class TiendaProductosRoomDataBase:RoomDatabase() {
    abstract fun clienteDao():ClienteDao
    abstract fun itemDao():ItemDao

    companion object{
        private var INSTANCE : TiendaProductosRoomDataBase?=null

        fun getDatabase(context: Context):TiendaProductosRoomDataBase{
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TiendaProductosRoomDataBase::class.java,
                    "tiendaproductosdb"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}