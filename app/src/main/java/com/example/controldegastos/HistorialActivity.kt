package com.example.controldegastos

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HistorialActivity : AppCompatActivity() {

    private lateinit var rvGastos: RecyclerView
    private lateinit var txtSinGastos: TextView
    private lateinit var actvFiltroCategoria: AutoCompleteTextView
    private lateinit var btnVolver: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private val listaGastos = mutableListOf<Gasto>()
    private lateinit var adapter: GastoAdapter

    private val categorias = listOf(
        "Todas", "Alimentación", "Transporte", "Entretenimiento",
        "Salud", "Educación", "Otros", "Deportes", "recibos"
    )

    private var filtroActual = "Todas"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        rvGastos = findViewById(R.id.rvGastos)
        txtSinGastos = findViewById(R.id.txtSinGastos)
        actvFiltroCategoria = findViewById(R.id.actvFiltroCategoria)
        btnVolver = findViewById(R.id.btnVolverHistorial)

        adapter = GastoAdapter(listaGastos)
        rvGastos.layoutManager = LinearLayoutManager(this)
        rvGastos.adapter = adapter

        val adapterFiltro = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        actvFiltroCategoria.setAdapter(adapterFiltro)

        actvFiltroCategoria.setText("Todas", false)

        actvFiltroCategoria.setOnItemClickListener { _, _, position, _ ->
            filtroActual = categorias[position]
            cargarGastos()
        }

        btnVolver.setOnClickListener {
            finish()
        }

        cargarGastos()
    }

    private fun cargarGastos() {
        val userId = auth.currentUser?.uid ?: return

        var query: Query = db.collection("users").document(userId).collection("gastos")
            .orderBy("fechaTimestamp", Query.Direction.DESCENDING)

        if (filtroActual != "Todas") {
            query = query.whereEqualTo("categoria", filtroActual)
        }

        query.get()
            .addOnSuccessListener { documents ->
                listaGastos.clear()
                for (doc in documents) {
                    val gasto = doc.toObject(Gasto::class.java).copy(id = doc.id)
                    listaGastos.add(gasto)
                }
                adapter = GastoAdapter(listaGastos)
                rvGastos.adapter = adapter

                if (listaGastos.isEmpty()) {
                    rvGastos.visibility = android.view.View.GONE
                    txtSinGastos.visibility = android.view.View.VISIBLE
                } else {
                    rvGastos.visibility = android.view.View.VISIBLE
                    txtSinGastos.visibility = android.view.View.GONE
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al cargar: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
