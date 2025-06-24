package pe.idat.proyectofinalv1.view.cliente.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pe.idat.proyectofinalv1.R
import pe.idat.proyectofinalv1.databinding.FragmentProductosBinding
import pe.idat.proyectofinalv1.view.cliente.adapter.ProductosAdapter
import pe.idat.proyectofinalv1.viewmodel.ProductoViewModel


class ProductosFragment : Fragment() {

    private lateinit var productoViewModel: ProductoViewModel
    private lateinit var adapter: ProductosAdapter
    private lateinit var binding:FragmentProductosBinding



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_productos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentProductosBinding.bind(view)
        productoViewModel=ViewModelProvider(this)[ProductoViewModel::class.java]
        adapter= ProductosAdapter()
        initRecycler()

        binding.svBuscar.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()){
                    productoViewModel.listarByNombre(query.uppercase()).observe(viewLifecycleOwner,Observer{
                        adapter.differ.submitList(it)
                    })
                }

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()){
                    initRecycler()
                }else{
                    productoViewModel.listarByNombre(newText.toString()).observe(viewLifecycleOwner,Observer{
                        adapter.differ.submitList(it)
                    })

                }

                return false

            }
        })

        adapter.setOnItemClcickListener {
            val action=ProductosFragmentDirections.actionProductoFragmentToDetalleProductoFragment(it)
            findNavController().navigate(action)

        }
    }

    fun initRecycler(){
        productoViewModel.listarProducto().observe(viewLifecycleOwner,
        Observer {
            adapter.differ.submitList(it)
        })
        binding.rvListaProductos.layoutManager=
            StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        binding.rvListaProductos.adapter=adapter
    }


}