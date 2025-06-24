package pe.idat.proyectofinalv1.view.admin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.proyectofinalv1.databinding.ItemsProductoAdmBinding
import pe.idat.proyectofinalv1.retrofit.model.Producto

class ProductoAdmAdapter: RecyclerView.Adapter<ProductoAdmAdapter.ProductoAdmViewHolder>() {

    private val callback=object : DiffUtil.ItemCallback<Producto>(){

        override fun areItemsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem.idProducto==newItem.idProducto
        }

        override fun areContentsTheSame(oldItem: Producto, newItem: Producto): Boolean {
            return oldItem==newItem
        }

    }
    val differ= AsyncListDiffer(this,callback)
    private var onItemClickListener:((Producto)-> Unit)={}

    private var onItemClickListener2:((Producto)-> Unit)={}

    fun setOnItemClickListener(listener:(Producto)->Unit){
        onItemClickListener=listener
    }

    fun deleteById(listener: (Producto) -> Unit){
        onItemClickListener2=listener
    }

    inner class ProductoAdmViewHolder(private val binding: ItemsProductoAdmBinding):
        RecyclerView.ViewHolder(binding.root){
        fun bindData(producto: Producto){
            Glide.with(binding.v2MimageAdm)
                .load(producto.urlImagen)
                .into(binding.v2MimageAdm)

            binding.v2MnameAdm.text=producto.nombre
            binding.v2MpriceAdm.text="S/. ${producto.precio}"

            binding.cardViewAdm.setOnClickListener{
                onItemClickListener(producto)
            }
            binding.btnEliminarProdAdm.setOnClickListener{
                onItemClickListener2(producto)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoAdmViewHolder {
        val binding=ItemsProductoAdmBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProductoAdmViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductoAdmViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bindData(product)
    }

    override fun getItemCount()=differ.currentList.size
}