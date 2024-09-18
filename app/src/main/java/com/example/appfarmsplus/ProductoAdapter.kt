package com.example.appfarmsplus

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

class ProductoAdapter(
    private val context: Context,
    private val productos: List<Producto>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<ProductoAdapter.ProductoViewHolder>() {

    // ViewHolder para los productos
    inner class ProductoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreProductoTextView: TextView = itemView.findViewById(R.id.nombreProductoTextView)
        val precioProductoTextView: TextView = itemView.findViewById(R.id.precioProductoTextView)
        val productoImageView: ImageView = itemView.findViewById(R.id.productoImageView)
        val agregarCarritoButton: ImageButton = itemView.findViewById(R.id.agregarCarritoButton)
    }

    // Inflar la vista del producto
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    // Vincular datos del producto a la vista
    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = productos[position]
        holder.nombreProductoTextView.text = producto.nombre
        holder.precioProductoTextView.text = producto.precio
        holder.productoImageView.setImageResource(producto.imagen)

        holder.agregarCarritoButton.setOnClickListener {
            showAddToCartDialog(producto)
        }
    }

    // Cantidad de productos
    override fun getItemCount(): Int = productos.size

    // Función para mostrar el diálogo de agregar al carrito
    private fun showAddToCartDialog(producto: Producto) {
        // Infla el diseño del diálogo
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_add_to_cart, null)
        val quantitySeekBar: SeekBar = dialogView.findViewById(R.id.quantitySeekBar)
        val quantityValue: TextView = dialogView.findViewById(R.id.quantityValue)
        val addToCartButton: Button = dialogView.findViewById(R.id.addToCartButton)

        // Configura el SeekBar
        quantitySeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                quantityValue.text = progress.toString()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // Configura el botón para agregar al carrito
        addToCartButton.setOnClickListener {
            val cantidad = quantitySeekBar.progress
            if (cantidad > 0) {
                // Agregar producto al carrito con la cantidad seleccionada
                Carrito.addProducto(producto, cantidad)

                // Mostrar un mensaje y redirigir al carrito
                Toast.makeText(context, "Producto agregado al carrito: ${producto.nombre} (Cantidad: $cantidad)", Toast.LENGTH_SHORT).show()

                // Redirigir al fragmento del carrito
                (context as MainActivity).loadFragment(CartFragment())
            } else {
                Toast.makeText(context, "Cantidad debe ser mayor a 0", Toast.LENGTH_SHORT).show()
            }
        }

        // Muestra el diálogo
        AlertDialog.Builder(context)
            .setView(dialogView)
            .setTitle("Agregar al carrito")
            .setNegativeButton("Cancelar", null)
            .show()
    }
}
