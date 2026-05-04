package com.example.controldegastos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.DecimalFormat


// archivo que contiene la estructura de cada item para que el historial la renderize

class GastoAdapter(private val gastos: List<Gasto>) :
    RecyclerView.Adapter<GastoAdapter.GastoViewHolder>() {

    private val df = DecimalFormat("#,##0.00")

    class GastoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNombre: TextView = view.findViewById(R.id.txtNombreGasto)
        val txtMonto: TextView = view.findViewById(R.id.txtMontoGasto)
        val txtCategoria: TextView = view.findViewById(R.id.txtCategoriaGasto)
        val txtFecha: TextView = view.findViewById(R.id.txtFechaGasto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gasto, parent, false)
        return GastoViewHolder(view)
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val gasto = gastos[position]
        holder.txtNombre.text = gasto.nombre
        holder.txtMonto.text = "$${df.format(gasto.monto)}"
        holder.txtCategoria.text = gasto.categoria
        holder.txtFecha.text = gasto.fecha
    }

    override fun getItemCount(): Int = gastos.size
}
