package pe.idat.proyectofinalv1.view.cliente.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pe.idat.proyectofinalv1.databinding.ItemsOrdenBinding
import pe.idat.proyectofinalv1.retrofit.response.ResponseOrden

class OrdenAdapter:RecyclerView.Adapter<OrdenAdapter.OrdenViewHolder>() {

    private val callback=object :DiffUtil.ItemCallback<ResponseOrden>() {
        override fun areItemsTheSame(oldItem: ResponseOrden, newItem: ResponseOrden): Boolean {
            return oldItem.idOrden == newItem.idOrden
        }

        override fun areContentsTheSame(oldItem: ResponseOrden, newItem: ResponseOrden): Boolean {
            return oldItem == newItem
        }
    }
        val differ=AsyncListDiffer(this,callback)
        private var onItemClickListener:((ResponseOrden)->Unit)={}
        fun setOnItemClickListener(listener:(ResponseOrden)->Unit){
            onItemClickListener=listener
        }


        inner class OrdenViewHolder(private val binding:ItemsOrdenBinding):
            RecyclerView.ViewHolder(binding.root){
            fun bindData(orden:ResponseOrden){
                binding.tvItemNroOrden.text=orden.numeroOrden
                binding.tvItemEstdOrd.text=orden.estadoOrden
                binding.tvItemFechaOrd.text= orden.fechaOrden.toString()
                binding.tvItemTotalOrd.text="S/ ${orden.totalOrden} "
                binding.ordenCardView.setOnClickListener{
                    onItemClickListener(orden)
                }
            }

        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdenViewHolder {
        val binding=ItemsOrdenBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return OrdenViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdenViewHolder, position: Int) {
        val orden=differ.currentList[position]
        holder.bindData(orden)
    }

    override fun getItemCount()=differ.currentList.size

}
