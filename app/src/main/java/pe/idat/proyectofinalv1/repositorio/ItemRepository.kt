package pe.idat.proyectofinalv1.repositorio

import androidx.lifecycle.LiveData
import pe.idat.proyectofinalv1.db.dao.ItemDao
import pe.idat.proyectofinalv1.db.entity.ItemEntity

class ItemRepository(private val itemDao: ItemDao) {

    suspend fun insertar(itemEntity: ItemEntity){
        itemDao.insertar(itemEntity)
    }

    suspend fun actualizar(itemEntity: ItemEntity){
        itemDao.actualizar(itemEntity)
    }

    suspend fun eliminarTodo(){
        itemDao.eliminarTodo()
    }

    suspend fun eliminarById(idMont:Int){
        itemDao.eliminarByID(idMont)
    }

    fun obtener(): LiveData<List<ItemEntity>> {
        return  itemDao.obtener()
    }
}