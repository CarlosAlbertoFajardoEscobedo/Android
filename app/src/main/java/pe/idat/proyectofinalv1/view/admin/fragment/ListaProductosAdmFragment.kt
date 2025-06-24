package pe.idat.proyectofinalv1.view.admin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pe.idat.proyectofinalv1.R
import pe.idat.proyectofinalv1.databinding.FragmentListaProductosAdmBinding
import pe.idat.proyectofinalv1.databinding.FragmentOrdenListAdmBinding
import pe.idat.proyectofinalv1.databinding.FragmentProductosBinding
import pe.idat.proyectofinalv1.retrofit.response.ResponseCrudProducto
import pe.idat.proyectofinalv1.utilitarios.AppMensaje
import pe.idat.proyectofinalv1.utilitarios.TipoMensaje
import pe.idat.proyectofinalv1.view.admin.adapter.ProductoAdmAdapter
import pe.idat.proyectofinalv1.viewmodel.OrdenViewModel
import pe.idat.proyectofinalv1.viewmodel.ProductoViewModel

class ListaProductosAdmFragment : Fragment(),View.OnClickListener {

    private lateinit var productosAdmViewModel: ProductoViewModel
    private lateinit var adapter: ProductoAdmAdapter
    private lateinit var binding: FragmentListaProductosAdmBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista_productos_adm, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentListaProductosAdmBinding.bind(view)
        productosAdmViewModel=ViewModelProvider(this)[ProductoViewModel::class.java]
        binding.btnNuevoProdAdm.setOnClickListener(this)
        adapter= ProductoAdmAdapter()

        initRecycler()
        productosAdmViewModel.responseCrudProducto.observe(viewLifecycleOwner, Observer {
            response->obtenerResultadoElim(response)
        })
        adapter.setOnItemClickListener {
            val action=ListaProductosAdmFragmentDirections.actionListToEdit(it)
            findNavController().navigate(action)
        }
        adapter.deleteById {
            eliminarProducto(it.idProducto)
        }

    }


    fun initRecycler(){
        productosAdmViewModel.listarProducto().observe(viewLifecycleOwner,
            Observer {
                adapter.differ.submitList(it)
            })
        binding.rvListaProdAdmin.layoutManager=
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.rvListaProdAdmin.adapter=adapter
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnNuevoProdAdm->irNuevoProducto()
        }
    }
    private fun irNuevoProducto() {
        val action= ListaProductosAdmFragmentDirections.actionListToNewprod()
        findNavController().navigate(action)
    }
    private fun obtenerResultadoElim(response: ResponseCrudProducto){
        if(response.respuesta){
            AppMensaje.enviarMensaje(binding.root,response.mensaje, TipoMensaje.EXITO)

        }
    }

    private fun eliminarProducto(id:Int){
        productosAdmViewModel.eliminarProductoById(id)
        val action=ListaProductosAdmFragmentDirections.actionListToList()
        findNavController().navigate(action)
    }



}