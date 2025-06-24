package pe.idat.proyectofinalv1.view.cliente.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import pe.idat.proyectofinalv1.databinding.FragmentOrdenBinding
import pe.idat.proyectofinalv1.view.cliente.adapter.OrdenAdapter
import pe.idat.proyectofinalv1.viewmodel.ClienteViewModel
import pe.idat.proyectofinalv1.viewmodel.ItemViewModel
import pe.idat.proyectofinalv1.viewmodel.OrdenViewModel

class OrdenFragment : Fragment(),View.OnClickListener {

    private var _binding:FragmentOrdenBinding?=null
    private val binding get() = _binding!!
    private lateinit var ordenViewModel: OrdenViewModel
    private lateinit var adapter: OrdenAdapter

    private lateinit var clienteViewModel: ClienteViewModel
    private lateinit var itemViewModel: ItemViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrdenBinding.inflate(inflater, container, false)
        ordenViewModel= ViewModelProvider(requireActivity()).get(OrdenViewModel::class.java)
        clienteViewModel=ViewModelProvider(requireActivity()).get(ClienteViewModel::class.java)
        itemViewModel=ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
        adapter= OrdenAdapter()
        obtenerIdCliente()


        adapter.setOnItemClickListener {
            val action=OrdenFragmentDirections.actionOrdenFragmnetToDetalleOrdenFragment(it)
            findNavController().navigate(action)
        }


        return binding.root
    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }

    fun obtenerIdCliente(){
        clienteViewModel.obtener().observe(viewLifecycleOwner,
            Observer{
                    cliente->cliente?.let {

                ordenViewModel.listarOrden(cliente.idCliente).observe(viewLifecycleOwner,
                    Observer {
                        if (it.isNullOrEmpty()){
                            binding.rvListOrden.visibility=View.INVISIBLE
                            binding.ordenEmpty.visibility=View.VISIBLE
                        }else{
                            binding.rvListOrden.visibility=View.VISIBLE
                            binding.ordenEmpty.visibility=View.INVISIBLE
                        }
                        adapter.differ.submitList(it)
                    })
                binding.rvListOrden.layoutManager=
                    StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
                binding.rvListOrden.adapter=adapter

            }
            })
    }


}