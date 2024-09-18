// Carrito.kt
package com.example.appfarmsplus

object Carrito {
    private val productosEnCarrito: MutableList<Pair<Producto, Int>> = mutableListOf()

    fun addProducto(producto: Producto, cantidad: Int) {
        // Agrega el producto al carrito con la cantidad especificada
        productosEnCarrito.add(Pair(producto, cantidad))
    }

    fun getProductosEnCarrito(): List<Pair<Producto, Int>> {
        return productosEnCarrito
    }
}
