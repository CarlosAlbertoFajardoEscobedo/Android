package pe.idat.proyectofinalv1.view.cliente.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.proyectofinalv1.databinding.ItemsHomeBinding
import pe.idat.proyectofinalv1.retrofit.model.Producto

class ProductosAdapter : RecyclerView.Adapter<ProductosAdapter.ProductoViewHolder>() {

    private val callback=object :DiffUtil.ItemCallback<Producto>(){
        override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem.idProducto==newItem.idProducto
        }

        override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem==newItem
        }

    }
    val differ=AsyncListDiffer(this,callback)
    private var onItemClickListener:((Producto)->Unit)={}
    fun setOnItemClcickListener(listener:(Producto)->Unit){
        onItemClickListener=listener
    }



    inner class ProductoViewHolder(private val binding:ItemsHomeBinding):
            RecyclerView.ViewHolder(binding.root){
                fun bindData(producto:Producto){
                    Glide.with(binding.v2Mimage)
                        .load(producto.urlImagen)
                        .into(binding.v2Mimage)

                    binding.v2Mname.text=producto.nombre
                    binding.v2Mprice.text="S/. ${producto.precio}"
                    binding.cardView.setOnClickListener{
                        onItemClickListener(producto)
                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val binding=ItemsHomeBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val product= differ.currentList[position]
        holder.bindData(product)
    }

    override fun getItemCount()=differ.currentList.size


}