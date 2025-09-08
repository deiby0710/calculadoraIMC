package com.example.myapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Modelo de datos
data class Historial(
    val usuario: String,
    val fechaHora: String,
    val peso: String,
    val estatura: String,
    val imc: String
)

class HistorialAdapter(private val listaHistorial: List<Historial>) :
    RecyclerView.Adapter<HistorialAdapter.HistorialViewHolder>() {

    // Clase interna para acceder a las vistas de item_historial.xml
    class HistorialViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvUsuario: TextView = itemView.findViewById(R.id.tvUsuario)
        val tvFechaHora: TextView = itemView.findViewById(R.id.tvFechaHora)
        val tvPeso: TextView = itemView.findViewById(R.id.tvPeso)
        val tvEstatura: TextView = itemView.findViewById(R.id.tvEstatura)
        val tvIMC: TextView = itemView.findViewById(R.id.tvIMC)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_historial, parent, false)
        return HistorialViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialViewHolder, position: Int) {
        val item = listaHistorial[position]
        holder.tvUsuario.text = holder.itemView.context.getString(R.string.txtH_usuario, item.usuario)
        holder.tvFechaHora.text = item.fechaHora
        holder.tvPeso.text = holder.itemView.context.getString(R.string.txtH_Peso, item.peso)
        holder.tvEstatura.text = holder.itemView.context.getString(R.string.txtH_Estatura, item.estatura)
        holder.tvIMC.text = holder.itemView.context.getString(R.string.txtH_IMC, item.imc)
    }

    override fun getItemCount(): Int = listaHistorial.size
}
