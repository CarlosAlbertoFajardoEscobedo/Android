package pe.idat.proyectofinalv1.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import pe.idat.proyectofinalv1.db.entity.ItemEntity

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertar( vararg item:ItemEntity)

    @Update
    fun actualizar( vararg item:ItemEntity)

    @Query("DELETE FROM item")
    fun eliminarTodo()

    @Query("DELETE FROM item WHERE idproducto= :idProd")
    fun eliminarByID(idProd:Int)

    @Query("SELECT * FROM item ")
    fun obtener(): LiveData<List<ItemEntity>>

    @Query("SELECT * FROM item WHERE nombre == :nom ")
    fun obtenerByName(nom:String): LiveData<ItemEntity>
}