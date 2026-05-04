package com.example.controldegastos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var txtWelcome: TextView
    private lateinit var txtTotalMensual: TextView
    private lateinit var btnNuevoGasto: Button
    private lateinit var btnHistorial: Button
    private lateinit var btnLogout: Button

    private val df = DecimalFormat("#,##0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        txtWelcome = findViewById(R.id.txtWelcome)
        txtTotalMensual = findViewById(R.id.txtTotalMensual)
        btnNuevoGasto = findViewById(R.id.btnNuevoGasto)
        btnHistorial = findViewById(R.id.btnHistorial)
        btnLogout = findViewById(R.id.btnLogout)

        val userEmail = auth.currentUser?.email ?: "usuario"
        txtWelcome.text = "Bienvenido, $userEmail"

        btnNuevoGasto.setOnClickListener {
            startActivity(Intent(this, GastosActivity::class.java))
        }

        btnHistorial.setOnClickListener {
            startActivity(Intent(this, HistorialActivity::class.java))
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        calcularTotalMensual()
    }

    private fun calcularTotalMensual() {
        val userId = auth.currentUser?.uid ?: return

        val calendar = Calendar.getInstance()
        val anio = calendar.get(Calendar.YEAR)
        val mes = calendar.get(Calendar.MONTH)

        calendar.set(anio, mes, 1, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val inicioMes = com.google.firebase.Timestamp(calendar.time)

        calendar.set(anio, mes + 1, 1, 0, 0, 0)
        val finMes = com.google.firebase.Timestamp(calendar.time)

        db.collection("users").document(userId).collection("gastos")
            .whereGreaterThanOrEqualTo("fechaTimestamp", inicioMes)
            .whereLessThan("fechaTimestamp", finMes)
            .get()
            .addOnSuccessListener { documents ->
                var total = 0.0
                for (doc in documents) {
                    val monto = doc.getDouble("monto") ?: 0.0
                    total += monto
                }
                txtTotalMensual.text = "$${df.format(total)}"
            }
    }
}
