package pe.idat.proyectofinalv1.view.cliente.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import pe.idat.proyectofinalv1.utilitarios.AppMensaje
import pe.idat.proyectofinalv1.utilitarios.TipoMensaje
import pe.idat.proyectofinalv1.R
import pe.idat.proyectofinalv1.databinding.FragmentDetalleProductoBinding
import pe.idat.proyectofinalv1.db.entity.ItemEntity
import pe.idat.proyectofinalv1.retrofit.model.Producto
import pe.idat.proyectofinalv1.viewmodel.ItemViewModel

class DetalleProductoFragment : Fragment(),View.OnClickListener {

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var binding: FragmentDetalleProductoBinding
    private lateinit var productItem:Producto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        productItem=DetalleProductoFragmentArgs.fromBundle(requireArguments()).productItem
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle_producto, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentDetalleProductoBinding.bind(view)
        itemViewModel=ViewModelProvider(this)[ItemViewModel::class.java]

        binding.btnDetalleAgregar.setOnClickListener(this)
        updateVista()
    }

    private fun updateVista(){
        binding.tvDetalleNomb.text=productItem.nombre
        binding.tvDetallePrecio.text="S/. ${productItem.precio}"
        binding.edtDescripcion.setText(productItem.descripcion)

        Glide.with(binding.ivDetalleProd)
            .load(productItem.urlImagen)
            .into(binding.ivDetalleProd)

    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnDetalleAgregar -> agregarItem()
        }
    }

    private fun agregarItem(){
        val tex=binding.edtDetalleCantidad.text.toString()

        if(tex.isNullOrEmpty()||tex==""){
            AppMensaje.enviarMensaje(binding.root,"Ingrese minimo 1", TipoMensaje.ERROR);
        }
        else{
            itemViewModel.insertar(ItemEntity(productItem.idProducto,productItem.nombre,
                productItem.descripcion,productItem.urlImagen,productItem.precio,
                Integer.parseInt(binding.edtDetalleCantidad.text.toString())))

            AppMensaje.enviarMensaje(binding.root,"El producto ah sido agregado", TipoMensaje.EXITO)
        }

    }


}