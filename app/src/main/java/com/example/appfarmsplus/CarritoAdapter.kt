// CarritoAdapter.kt
package com.example.appfarmsplus

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CarritoAdapter(private val productos: List<Pair<Producto, Int>>) :
    RecyclerView.Adapter<CarritoAdapter.CarritoViewHolder>() {

    inner class CarritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreProductoTextView: TextView = itemView.findViewById(R.id.nombreProductoTextView)
        val cantidadProductoTextView: TextView = itemView.findViewById(R.id.cantidadProductoTextView)
        val precioProductoTextView: TextView = itemView.findViewById(R.id.precioProductoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarritoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_carrito, parent, false)
        return CarritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarritoViewHolder, position: Int) {
        val (producto, cantidad) = productos[position]
        holder.nombreProductoTextView.text = producto.nombre
        holder.cantidadProductoTextView.text = "Cantidad: $cantidad"
        holder.precioProductoTextView.text = "Precio: ${producto.precio}"
    }

    override fun getItemCount(): Int = productos.size
}
