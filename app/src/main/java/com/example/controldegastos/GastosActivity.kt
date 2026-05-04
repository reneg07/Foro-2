package com.example.controldegastos
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import android.widget.AutoCompleteTextView

class GastosActivity : AppCompatActivity() {

    private lateinit var etNombreGasto: TextInputEditText
    private lateinit var etMontoGasto: TextInputEditText
    private lateinit var actvCategoria: AutoCompleteTextView
    private lateinit var etFechaGasto: TextInputEditText
    private lateinit var btnGuardarGasto: Button
    private lateinit var btnCancelarGasto: Button

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    private val categorias = listOf(
        "Alimentación", "Transporte", "Entretenimiento",
        "Salud", "Educación", "Otros", "Deportes", "recibos"
    )

    private var fechaSeleccionada = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gastos)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        etNombreGasto = findViewById(R.id.etNombreGasto)
        etMontoGasto = findViewById(R.id.etMontoGasto)
        actvCategoria = findViewById(R.id.actvCategoria)
        etFechaGasto = findViewById(R.id.etFechaGasto)
        btnGuardarGasto = findViewById(R.id.btnGuardarGasto)
        btnCancelarGasto = findViewById(R.id.btnCancelarGasto)

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, categorias)
        actvCategoria.setAdapter(adapter)

        val calendar = Calendar.getInstance()
        fechaSeleccionada = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
        etFechaGasto.setText(fechaSeleccionada)

        etFechaGasto.setOnClickListener {
            mostrarDatePicker()
        }

        btnGuardarGasto.setOnClickListener {
            guardarGasto()
        }

        btnCancelarGasto.setOnClickListener {
            finish()
        }
    }

    private fun mostrarDatePicker() {
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            this,
            { _, year, month, dayOfMonth ->
                calendar.set(year, month, dayOfMonth)
                fechaSeleccionada = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
                etFechaGasto.setText(fechaSeleccionada)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.show()
    }

    private fun guardarGasto() {
        val nombre = etNombreGasto.text.toString().trim()
        val montoStr = etMontoGasto.text.toString().trim()
        val categoria = actvCategoria.text.toString().trim()
        val fecha = fechaSeleccionada

        if (nombre.isEmpty()) {
            etNombreGasto.error = "Ingresa el nombre del gasto"
            return
        }

        if (montoStr.isEmpty()) {
            etMontoGasto.error = "Ingresa el monto"
            return
        }

        val monto = montoStr.toDoubleOrNull()
        if (monto == null || monto <= 0) {
            etMontoGasto.error = "Ingresa un monto válido"
            return
        }

        if (categoria.isEmpty()) {
            Toast.makeText(this, "Selecciona una categoría", Toast.LENGTH_SHORT).show()
            return
        }

        btnGuardarGasto.isEnabled = false

        val userId = auth.currentUser?.uid
        if (userId == null) {
            Toast.makeText(this, "Error: usuario no autenticado", Toast.LENGTH_SHORT).show()
            btnGuardarGasto.isEnabled = true
            return
        }

        val gasto = hashMapOf(
            "nombre" to nombre,
            "monto" to monto,
            "categoria" to categoria,
            "fecha" to fecha,
            "fechaTimestamp" to com.google.firebase.Timestamp.now()
        )

        db.collection("users").document(userId).collection("gastos")
            .add(gasto)
            .addOnSuccessListener {
                Toast.makeText(this, "Gasto guardado", Toast.LENGTH_SHORT).show()
                etNombreGasto.text?.clear()
                etMontoGasto.text?.clear()
                actvCategoria.text?.clear()
                val calendar = Calendar.getInstance()
                fechaSeleccionada = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(calendar.time)
                etFechaGasto.setText(fechaSeleccionada)
                btnGuardarGasto.isEnabled = true
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error al guardar: ${e.message}", Toast.LENGTH_SHORT).show()
                btnGuardarGasto.isEnabled = true
            }
    }
}
