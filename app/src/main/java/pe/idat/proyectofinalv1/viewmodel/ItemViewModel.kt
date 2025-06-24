package pe.idat.proyectofinalv1.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pe.idat.proyectofinalv1.db.TiendaProductosRoomDataBase
import pe.idat.proyectofinalv1.db.entity.ItemEntity
import pe.idat.proyectofinalv1.repositorio.ItemRepository

class ItemViewModel (application: Application): AndroidViewModel(application){

    private val repository:ItemRepository
    init {
        val dao = TiendaProductosRoomDataBase.getDatabase(application).itemDao()
        repository = ItemRepository(dao)
    }
        fun insertar(itemEntity: ItemEntity)=viewModelScope.launch(Dispatchers.IO){
            repository.insertar(itemEntity)
        }
        fun actualizar(itemEntity: ItemEntity)=viewModelScope.launch(Dispatchers.IO){
            repository.actualizar(itemEntity)
        }
        fun eliminarTodo()=viewModelScope.launch(Dispatchers.IO){
            repository.eliminarTodo()
        }

        fun obtener(): LiveData<List<ItemEntity>> {
            return repository.obtener()
        }
        fun eliminarById(idMont:Int)=viewModelScope.launch(Dispatchers.IO){
            repository.eliminarById(idMont)
        }

    }
