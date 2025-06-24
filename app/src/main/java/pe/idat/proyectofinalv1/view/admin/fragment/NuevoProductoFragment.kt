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
import pe.idat.proyectofinalv1.databinding.FragmentNuevoBinding

import pe.idat.proyectofinalv1.retrofit.response.ResponseCrudProducto
import pe.idat.proyectofinalv1.utilitarios.AppMensaje
import pe.idat.proyectofinalv1.utilitarios.TipoMensaje
import pe.idat.proyectofinalv1.viewmodel.ItemViewModel
import pe.idat.proyectofinalv1.viewmodel.ProductoViewModel


class NuevoProductoFragment : Fragment(), View.OnClickListener {

    private lateinit var binding : FragmentNuevoBinding
    private lateinit var itemViewModel: ProductoViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nuevo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentNuevoBinding.bind(view)
        itemViewModel=ViewModelProvider(this)[ProductoViewModel::class.java]

        binding.btnGuardarNewProd.setOnClickListener(this)
        itemViewModel.responseCrudProducto.observe(viewLifecycleOwner, Observer {
            response->obtenerResultadoNew(response)
        })
    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnGuardarNewProd->guardarProducto()
        }

    }

    private fun obtenerResultadoNew(response:ResponseCrudProducto){
        if (response.respuesta){
            AppMensaje.enviarMensaje(binding.root,response.mensaje, TipoMensaje.EXITO)
            val action=NuevoProductoFragmentDirections.actionNewprodToListprod()
            findNavController().navigate(action)
        }
    }

    fun validarCampos():Boolean{
        var respuesta=true
        var mensaje=""
        when{
            binding.tiedtNombreNewProd.text.toString().trim().isEmpty()->{
                binding.tiedtNombreNewProd.isFocusableInTouchMode=true
                binding.tiedtNombreNewProd.requestFocus()
                mensaje="Ingrese el nombre del producto"
                respuesta=false

            }
            binding.tiedDescripNewProd.text.toString().trim().isEmpty()->{
                binding.tiedDescripNewProd.isFocusableInTouchMode=true
                binding.tiedDescripNewProd.requestFocus()
                mensaje="Ingrese la descripcion del producto"
                respuesta=false

            }

            binding.tiedtStockNewProd.text.toString().trim().isEmpty()->{
                binding.tiedtStockNewProd.isFocusableInTouchMode=true
                binding.tiedtStockNewProd.requestFocus()
                mensaje="Ingrese el stock del producto"
                respuesta=false

            }
            binding.tiedPrecioNewProd.text.toString().trim().isEmpty()->{
                binding.tiedPrecioNewProd.isFocusableInTouchMode=true
                binding.tiedPrecioNewProd.requestFocus()
                mensaje="Ingrese el precio del producto"
                respuesta=false

            }
            binding.tiedtUrlImagenNewProd.text.toString().trim().isEmpty()->{
                binding.tiedtUrlImagenNewProd.isFocusableInTouchMode=true
                binding.tiedtUrlImagenNewProd.requestFocus()
                mensaje="Ingrese el UrlImagen del producto"
                respuesta=false

            }
        }
        if(!respuesta) AppMensaje.enviarMensaje(binding.root,mensaje,TipoMensaje.ERROR)
        return respuesta

    }

    private fun guardarProducto(){
        if (validarCampos()){
            itemViewModel.nuevoProducto(
                binding.tiedtNombreNewProd.text.toString(),
                binding.tiedDescripNewProd.text.toString(),
                binding.tiedtStockNewProd.text.toString().toInt(),
                binding.tiedPrecioNewProd.text.toString().toDouble(),
                binding.tiedtUrlImagenNewProd.text.toString(),
                binding.chkEstadoNewProd.isChecked
            )
        }else{
            AppMensaje.enviarMensaje(binding.root, "Hubo un error al guardar el Producto",TipoMensaje.ERROR)

        }
    }
}