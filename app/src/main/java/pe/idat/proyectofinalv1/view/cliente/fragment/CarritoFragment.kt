package pe.idat.proyectofinalv1.view.cliente.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import pe.idat.proyectofinalv1.utilitarios.AppMensaje
import pe.idat.proyectofinalv1.utilitarios.TipoMensaje
import pe.idat.proyectofinalv1.R
import pe.idat.proyectofinalv1.databinding.FragmentCarritoBinding
import pe.idat.proyectofinalv1.db.entity.ItemEntity
import pe.idat.proyectofinalv1.view.cliente.adapter.CarritoAdapter
import pe.idat.proyectofinalv1.viewmodel.ItemViewModel
import java.text.DecimalFormat


class CarritoFragment : Fragment(), View.OnClickListener {


    private lateinit var itemViewModel: ItemViewModel
    private lateinit var adapter: CarritoAdapter
    private lateinit var binding: FragmentCarritoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_carrito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding=FragmentCarritoBinding.bind(view)
        itemViewModel= ViewModelProvider(this)[ItemViewModel::class.java]

        cargar();
        adapter.notifyDataSetChanged()

        adapter.setOnRemoveClickListener {
            itemViewModel.eliminarById(it.idproducto)
            adapter.notifyDataSetChanged()

            val action=CarritoFragmentDirections.actionDetalleProductoFragmentToCarritoFragment()
            findNavController().navigate(action)
        }
        adapter.incrementClickListener {
                var sum=it.quantity+1
                itemViewModel.actualizar(ItemEntity(it.idproducto,it.nombre,it.descripcion,it.urlImagen
                    ,it.precio,sum))
                adapter.notifyDataSetChanged()
                val action=CarritoFragmentDirections.actionDetalleProductoFragmentToCarritoFragment()
                findNavController().navigate(action)

        }
        adapter.decrementClickListener {

            if (it.quantity>1){
                var rest=it.quantity-1
                itemViewModel.actualizar(ItemEntity(it.idproducto,it.nombre,it.descripcion,it.urlImagen
                    ,it.precio,rest))
                adapter.notifyDataSetChanged()
                val action=CarritoFragmentDirections.actionDetalleProductoFragmentToCarritoFragment()
                findNavController().navigate(action)
            }else{
                AppMensaje.enviarMensaje(binding.root,"La cantidad minima es 1", TipoMensaje.ERROR)
            }
        }

        binding.cartLimpiar.setOnClickListener(this)
        binding.cartCheckout.setOnClickListener(this)
    }

    private fun cargar(){

        adapter= CarritoAdapter()
        itemViewModel.obtener().observe(viewLifecycleOwner,
            Observer {response->
                adapter.differ.submitList(response)
                total(response)
            })

        binding.rvCarrito.adapter=adapter

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
        binding.cartItemsPrecio.text = (df.format(totalIGV)).toString()

        binding.cartItemsInfo.text="IGV: ${df.format(igv)}, Nr° Productos: $totalquantity"
        if (binding.cartItemsPrecio.text.toString() == "0"){
            binding.cartLimpiar.visibility=View.INVISIBLE
            binding.cartItemsInfo.visibility=View.INVISIBLE
            binding.cartEmpty.visibility=View.VISIBLE

        }
        else{

            binding.cartLimpiar.visibility=View.VISIBLE
            binding.cartItemsInfo.visibility=View.VISIBLE
            binding.cartEmpty.visibility=View.INVISIBLE

        }

    }

    override fun onClick(vista: View) {
       when(vista.id){
           R.id.cart_limpiar-> limpiarCarrito()

           R.id.cart_checkout-> irPagarOrden()
       }
    }

    private fun irPagarOrden() {
        if(adapter.differ.currentList.size==0){
            AppMensaje.enviarMensaje(binding.root,"Añada productos a su carrito", TipoMensaje.ERROR)
        }else{

            val action=CarritoFragmentDirections.actionCarritoToPasarella()
            findNavController().navigate(action)
        }

    }
    private fun limpiarCarrito() {
        itemViewModel.eliminarTodo()
        adapter.notifyDataSetChanged()
        val action=CarritoFragmentDirections.actionDetalleProductoFragmentToCarritoFragment()
        findNavController().navigate(action)
    }

}