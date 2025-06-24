package pe.idat.proyectofinalv1.view.cliente.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pe.idat.proyectofinalv1.databinding.ItemsDetalleordBinding
import pe.idat.proyectofinalv1.retrofit.response.ResponseDetalleOrden

class DetalleOrdenAdapter:RecyclerView.Adapter<DetalleOrdenAdapter.DetalleOrdenViewHolder> (){

    private val callback=object :DiffUtil.ItemCallback<ResponseDetalleOrden>(){
        override fun areItemsTheSame(
            oldItem: ResponseDetalleOrden,
            newItem: ResponseDetalleOrden
        ): Boolean {
            return oldItem.idDetalle==newItem.idDetalle
        }

        override fun areContentsTheSame(
            oldItem: ResponseDetalleOrden,
            newItem: ResponseDetalleOrden
        ): Boolean {
            return oldItem==newItem
        }
    }

    val differ=AsyncListDiffer(this,callback)
    private var onItemClickListener:((ResponseDetalleOrden)->Unit)={}

    fun setOnItemClickListener(listener:(ResponseDetalleOrden)->Unit){
        onItemClickListener=listener
    }

    inner class  DetalleOrdenViewHolder(private val binding: ItemsDetalleordBinding):
        RecyclerView.ViewHolder(binding.root){
        fun binData(detalleOrden: ResponseDetalleOrden){
            Glide.with(binding.imageViewDetalleOrd)
                .load(detalleOrden.urlImage)
                .into(binding.imageViewDetalleOrd)
            binding.tvItemDetOrdNombreProd.text=detalleOrden.nombre
            binding.tvItemDetOrdCant.text=detalleOrden.cantidad.toString()
            binding.tvItemDetOrdPrec.text="S/ ${detalleOrden.precio}"
            binding.tvItemDetOrdSubTotal.text="S/ ${detalleOrden.total}"
            binding.ordenCardViewDetalleOrd.setOnClickListener{
                onItemClickListener(detalleOrden)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetalleOrdenViewHolder {
        val binding=ItemsDetalleordBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return DetalleOrdenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetalleOrdenViewHolder, position: Int) {
        val detord=differ.currentList[position]
        holder.binData(detord)
    }

    override fun getItemCount()=differ.currentList.size

}