package com.example.appfarmsplus

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.widget.SearchView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var productos: List<Producto>  // Lista de productos
    private lateinit var btnBuscarMedicamento: Button
    private lateinit var btnMisOrdenes: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurar los botones
        btnBuscarMedicamento = findViewById(R.id.btnBuscarMedicamento)
        btnMisOrdenes = findViewById(R.id.btnMisOrdenes)

        // Configurar las acciones de los botones
        btnBuscarMedicamento.setOnClickListener {
            val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.selectedItemId = R.id.nav_search
        }

        btnMisOrdenes.setOnClickListener {
            Toast.makeText(this, "Accediendo a Mis Órdenes", Toast.LENGTH_SHORT).show()
            // Aquí puedes agregar la lógica para ir a la pantalla de "Mis Órdenes"
        }

        // Lista de productos
        productos = listOf(
            Producto("Desmaquillante Bifasico Clean 300Ml", "$10.92", R.drawable.desmaquillante),
            Producto("Mascarilla De Noche Hidratante Kiwi Caracol 120Grs", "$12.40", R.drawable.cremacara),
            Producto("Ibuprofeno Mk 600Mg X 1 Tableta", "$1.00", R.drawable.ibuprofeno),
            Producto("Hilo Dental Oral B Superfloss X50 Unidades", "$3.60", R.drawable.hilodental),
            Producto("Tinte Koleston Deity New Kit 71 Rubio Cenizo Media", "$4.99", R.drawable.hilodental),
            Producto("Pepto Bismol Suspensión Frasco", "$4.18", R.drawable.pepto),
            Producto("Crema Liquida Johnson 200 Ml", "$4.11", R.drawable.cremabebe),
            Producto("Loratadina Ecomed 10Mg X 1 Tableta", "$1.02", R.drawable.loratadina),
            Producto("Condones Vive Colors Fresa 1 de 8 Unidades", "$2.22", R.drawable.condones),
            Producto("Cepillo Kin Ortodoncia Bimaterial", "$3.68", R.drawable.cepillo)
        )

        // Configurar el RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Configurar el SearchView
        searchView = findViewById(R.id.searchView)

        // Ocultar tanto el RecyclerView como el SearchView inicialmente
        recyclerView.visibility = RecyclerView.GONE
        searchView.visibility = SearchView.GONE

        // Configurar el BottomNavigationView
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_search -> {
                    // Mostrar SearchView y RecyclerView, ocultar los botones
                    recyclerView.adapter = ProductoAdapter(this, productos, supportFragmentManager)  // Cambiado: pasando el FragmentManager
                    recyclerView.visibility = RecyclerView.VISIBLE
                    searchView.visibility = SearchView.VISIBLE
                    btnBuscarMedicamento.visibility = View.GONE
                    btnMisOrdenes.visibility = View.GONE
                    true
                }
                R.id.nav_home -> {
                    // Mostrar los botones y ocultar el RecyclerView y SearchView
                    recyclerView.visibility = RecyclerView.GONE
                    searchView.visibility = SearchView.GONE
                    btnBuscarMedicamento.visibility = View.VISIBLE
                    btnMisOrdenes.visibility = View.VISIBLE
                    true
                }
                R.id.nav_cart -> {
                    recyclerView.visibility = RecyclerView.VISIBLE
                    searchView.visibility = SearchView.VISIBLE
                    btnBuscarMedicamento.visibility = View.GONE
                    btnMisOrdenes.visibility = View.GONE
                    true
                }
                else -> {
                    // Ocultar RecyclerView y SearchView para otras pestañas
                    recyclerView.adapter = null
                    recyclerView.visibility = RecyclerView.GONE
                    searchView.visibility = SearchView.GONE
                    true
                }
            }
        }
    }

    fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_sign_out -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
