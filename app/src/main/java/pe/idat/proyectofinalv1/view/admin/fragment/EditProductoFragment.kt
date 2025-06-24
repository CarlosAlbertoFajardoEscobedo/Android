package pe.idat.proyectofinalv1.view.admin.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import pe.idat.proyectofinalv1.R
import pe.idat.proyectofinalv1.databinding.FragmentEditProductoBinding
import pe.idat.proyectofinalv1.retrofit.model.Producto
import pe.idat.proyectofinalv1.retrofit.response.ResponseCrudProducto
import pe.idat.proyectofinalv1.utilitarios.AppMensaje
import pe.idat.proyectofinalv1.utilitarios.TipoMensaje
import pe.idat.proyectofinalv1.viewmodel.ProductoViewModel


class EditProductoFragment : Fragment(),View.OnClickListener {

    private lateinit var binding: FragmentEditProductoBinding
    private lateinit var itemViewModel: ProductoViewModel
    private lateinit var productEdit:Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productEdit=EditProductoFragmentArgs.fromBundle(requireArguments()).productoItemx
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentEditProductoBinding.bind(view)
        itemViewModel=ViewModelProvider(this)[ProductoViewModel::class.java]

        binding.btnActualizarProd.setOnClickListener(this)
        cargarData()
        itemViewModel.responseCrudProducto.observe(viewLifecycleOwner, Observer {
            response->obtenerResultado(response)
        })

    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnActualizarProd ->guardarCambios()
        }

    }

    private fun cargarData(){
        binding.edtCodProdAct.text=productEdit.idProducto.toString()
        binding.tiedtNombreEditProd.setText(productEdit.nombre)
        binding.tiedtDescripcionEditProd.setText(productEdit.descripcion)
        binding.tiedPrecioEditProd.setText(productEdit.precio.toString())
        binding.tiedtStockEditProd.setText(productEdit.stock.toString())
        binding.tiedtUrlImgEditProd.setText(productEdit.urlImagen)
        if (productEdit.estado){
            binding.chkEstadoEditProd.isChecked=true
        }
    }

    private fun obtenerResultado(response:ResponseCrudProducto){
        if(response.respuesta){
            AppMensaje.enviarMensaje(binding.root,response.mensaje, TipoMensaje.EXITO)
            val  action=EditProductoFragmentDirections.actionToListadmin()
            findNavController().navigate(action)
        }

    }

    private fun guardarCambios(){
        itemViewModel.editarProducto(binding.edtCodProdAct.text.toString().toInt(),
        binding.tiedtNombreEditProd.text.toString(),
        binding.tiedtDescripcionEditProd.text.toString(),
        binding.tiedtStockEditProd.text.toString().toInt(),
        binding.tiedPrecioEditProd.text.toString().toDouble(),
        binding.tiedtUrlImgEditProd.text.toString(),
        binding.chkEstadoEditProd.isChecked)
    }


}