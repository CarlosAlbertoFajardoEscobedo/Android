package pe.idat.proyectofinalv1.view.cliente.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_pasarela.*
import pe.idat.proyectofinalv1.utilitarios.AppMensaje
import pe.idat.proyectofinalv1.utilitarios.TipoMensaje
import pe.idat.proyectofinalv1.R
import pe.idat.proyectofinalv1.databinding.FragmentPasarelaBinding
import pe.idat.proyectofinalv1.db.entity.ItemEntity
import pe.idat.proyectofinalv1.retrofit.response.ResponseGuardarOrden
import pe.idat.proyectofinalv1.viewmodel.ClienteViewModel
import pe.idat.proyectofinalv1.viewmodel.ItemViewModel
import pe.idat.proyectofinalv1.viewmodel.OrdenViewModel
import java.text.DecimalFormat


class PasarelaFragment : Fragment(), View.OnClickListener {

    private lateinit var itemViewModel: ItemViewModel
    private lateinit var ordenViewModel: OrdenViewModel
    private lateinit var binding: FragmentPasarelaBinding
    private lateinit var clienteViewModel: ClienteViewModel

    private var idClientex:Int = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pasarela, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding=FragmentPasarelaBinding.bind(view)
        ordenViewModel=ViewModelProvider(this)[OrdenViewModel::class.java]
        itemViewModel=ViewModelProvider(this)[ItemViewModel::class.java]
        clienteViewModel=ViewModelProvider(this)[ClienteViewModel::class.java]

        obtenerIdCliente();
        cargarTotal();
        aplicarDescuento();
        binding.btnpagar.setOnClickListener(this)

        ordenViewModel.responseGuardarOrden.observe(viewLifecycleOwner, Observer {
            response->obtenerResultados(response)
        })

    }

    private fun obtenerResultados(response: ResponseGuardarOrden){
        if (response.respuesta){
            AppMensaje.enviarMensaje(binding.root,response.mensaje, TipoMensaje.EXITO)
            val action=PasarelaFragmentDirections.actionPasrelaToProductos()
            itemViewModel.eliminarTodo()
            findNavController().navigate(action)

        }else{
            AppMensaje.enviarMensaje(binding.root,response.mensaje, TipoMensaje.ERROR)
        }
    }


    fun returnItems(){
        if(validar()){
            val num=tvDescuentoQR.text.toString().toDouble()
            if (num>0.0){
                itemViewModel.obtener().observe(viewLifecycleOwner,
                    Observer {
                        ordenViewModel.guardarOrder(idClientex,num,it)
                    })

            }else{
                itemViewModel.obtener().observe(viewLifecycleOwner,
                    Observer {
                        ordenViewModel.guardarOrder(idClientex,0.0,it)
                    })
            }

        }
    }

    private fun validar(): Boolean {
        var respuesta=true
        var mensaje=""
        when{
            binding.edtnrotarjeta.text.toString().trim().isEmpty()->{
                binding.edtnrotarjeta.isFocusableInTouchMode
                binding.edtnrotarjeta.requestFocus()
                mensaje="Ingrese su numero de tarjeta"
                respuesta=false
            }
            binding.edtnrotarjeta.text.toString().trim().length<16->{
                binding.edtnrotarjeta.isFocusableInTouchMode
                binding.edtnrotarjeta.requestFocus()
                mensaje="Ingrese un numero de tarjeta valido"
                respuesta=false
            }
            binding.edtAnioTarjeta.text.toString().trim().isEmpty()->{
                binding.edtAnioTarjeta.isFocusableInTouchMode
                binding.edtAnioTarjeta.requestFocus()
                mensaje="Ingrese el año de su tarjeta"
                respuesta=false
            }


            Integer.parseInt(binding.edtAnioTarjeta.text.toString().trim())<22->{
                binding.edtAnioTarjeta.isFocusableInTouchMode
                binding.edtAnioTarjeta.requestFocus()
                mensaje="Ingrese una tarjeta que no haya caducado"
                respuesta=false
            }
            binding.edtmestarjeta.text.toString().trim().isEmpty()->{
                binding.edtmestarjeta.isFocusableInTouchMode
                binding.edtmestarjeta.requestFocus()
                mensaje="Ingrese el numero del mes de su tarjeta"
                respuesta=false
            }
            binding.edtclave.text.toString().trim().isEmpty()->{
                binding.edtclave.isFocusableInTouchMode
                binding.edtclave.requestFocus()
                mensaje="Ingrese el cvv de su tarjeta"
                respuesta=false
            }

            binding.edtclave.text.toString().trim().length>3->{
                binding.edtclave.isFocusableInTouchMode
                binding.edtclave.requestFocus()
                mensaje="Ingrese un CVV valido de 3 dígitos"
                respuesta=false

            }
        }
        if (!respuesta) AppMensaje.enviarMensaje(binding.root,mensaje, TipoMensaje.ERROR)
        return respuesta

    }

    fun obtenerIdCliente(){
        clienteViewModel.obtener().observe(viewLifecycleOwner,
            Observer{
                    cliente->cliente?.let {
                idClientex=cliente.idCliente
            }
            })

    }
    ///
    private fun cargarTotal(){
        itemViewModel.obtener().observe(viewLifecycleOwner,
        Observer { response->
            total(response)
        })
    }

    fun total(items:List<ItemEntity>){
        val df = DecimalFormat("#.##")
        var price =0.00
        var quantiy=0
        var totalquantity=0
        var subtotal=0.00
        var total=0.00
        items.forEach{p->
            price=p.precio
            quantiy=p.quantity.toInt()
            subtotal=quantiy*price
            total+=subtotal
            totalquantity+=quantiy
        }

        var igv=total*0.18
        var totalIGV=total+igv
        binding.tvTotalVentPass.text= df.format(totalIGV).toString()

    }

    fun aplicarDescuento(){
        val num=tvDescuentoQR.text.toString().toDouble()
        if (num<0.0){
            val total=binding.tvTotalVentPass.text.toString()
            val descuento=binding.tvDescuentoQR.text.toString()
            val Desc=total.toDouble()*descuento.toDouble()
            binding.tvTotalVentPass.text= "Nuevo Total: $Desc"
        }

    }

    override fun onClick(vista: View) {
        when(vista.id){
            R.id.btnpagar -> returnItems()

        }

    }

}