package com.example.controldegastos

import com.google.firebase.Timestamp


//clase interfaz de los gastos siguiendo las normas dadas

data class Gasto(
    val id: String = "",
    val nombre: String = "",
    val monto: Double = 0.0,
    val categoria: String = "",
    val fecha: String = "",
    val fechaTimestamp: Timestamp? = null
)
