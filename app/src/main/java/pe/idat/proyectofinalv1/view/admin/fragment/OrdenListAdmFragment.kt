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
import pe.idat.proyectofinalv1.databinding.FragmentOrdenListAdmBinding
import pe.idat.proyectofinalv1.view.admin.adapter.OrdenAdmAdapter
import pe.idat.proyectofinalv1.viewmodel.OrdenViewModel

class OrdenListAdmFragment : Fragment() {

    private var _binding: FragmentOrdenListAdmBinding?=null
    private val binding get()= _binding!!
    private lateinit var ordenViewModel: OrdenViewModel
    private lateinit var adapter:OrdenAdmAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= FragmentOrdenListAdmBinding.inflate(inflater,container,false)
        ordenViewModel=ViewModelProvider(requireActivity())[OrdenViewModel::class.java]
        adapter= OrdenAdmAdapter()
        cargarOrdenes()

        adapter.setOnItemClickListener {
            val action=OrdenListAdmFragmentDirections.actionOrdenAdmDetalleOrdAdm(it)
            findNavController().navigate(action)
        }

        return binding.root
    }

    fun cargarOrdenes(){
        ordenViewModel.listarTodasOrdenes().observe(viewLifecycleOwner,
            Observer {
                if (it.isNullOrEmpty()){
                    binding.rvListOrdenAdmList.visibility=View.INVISIBLE
                    binding.ordenEmptyAdm.visibility=View.VISIBLE
                }else{
                    binding.rvListOrdenAdmList.visibility=View.VISIBLE
                    binding.ordenEmptyAdm.visibility=View.INVISIBLE
                }
                adapter.differ.submitList(it)
            })
        binding.rvListOrdenAdmList.layoutManager=
            StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL)
        binding.rvListOrdenAdmList.adapter=adapter

    }
}