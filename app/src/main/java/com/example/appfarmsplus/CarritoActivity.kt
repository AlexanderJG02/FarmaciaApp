package com.example.appfarmsplus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// CarritoActivity.kt
class CarritoActivity : AppCompatActivity() {

    private lateinit var carritoRecyclerView: RecyclerView
    private lateinit var carritoAdapter: CarritoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_carrito)

        carritoRecyclerView = findViewById(R.id.carritoRecyclerView)
        carritoRecyclerView.layoutManager = LinearLayoutManager(this)

        // Asegúrate de que `Carrito.getProductosEnCarrito()` devuelva los productos correctos
        carritoAdapter = CarritoAdapter(Carrito.getProductosEnCarrito())
        carritoRecyclerView.adapter = carritoAdapter
    }
}
