package com.example.doggoapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doggoapp.R
import com.example.doggoapp.data.Paseo

class PaseoAdapter(
    private val paseos: List<Paseo>,
    private val onEditClick: (Paseo) -> Unit,
    private val onDeleteClick: (Paseo) -> Unit
) : RecyclerView.Adapter<PaseoAdapter.PaseoViewHolder>() {

    inner class PaseoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val editButton: ImageButton = itemView.findViewById(R.id.editUpdateBtn)
        private val nameTextView: TextView = itemView.findViewById(R.id.DogWalkerText)
        private val dateTextView: TextView = itemView.findViewById(R.id.DateTimeText)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteUpdatebtn)

        fun bind(paseo: Paseo) {
            nameTextView.text = "Mascota: ${paseo.raza}"
            dateTextView.text = "Fecha: ${paseo.fecha} - Hora: ${paseo.hora}"
            editButton.setOnClickListener { onEditClick(paseo) }
            deleteButton.setOnClickListener { onDeleteClick(paseo) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaseoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.paseo_item, parent, false)
        return PaseoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PaseoViewHolder, position: Int) {
        holder.bind(paseos[position])
    }

    override fun getItemCount(): Int = paseos.size
}
