package com.example.deber2.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber2.R
import com.example.deber2.data.Actualizacion

class UpdateAdapter(
    private val updates: List<Actualizacion>,
    private val onEditClick: (Actualizacion) -> Unit,
    private val onDeleteClick: (Actualizacion) -> Unit
) : RecyclerView.Adapter<UpdateAdapter.UpdateViewHolder>() {

    inner class UpdateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val versionTextView: TextView = itemView.findViewById(R.id.versionTextView)
        private val sizeTextView: TextView = itemView.findViewById(R.id.sizeTextView)
        private val editButton: ImageButton = itemView.findViewById(R.id.editUpdateBtn)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteUpdatebtn)

        @SuppressLint("SetTextI18n")
        fun bind(actualizacion: Actualizacion) {
            versionTextView.text = "Versión: ${actualizacion.version}"
            sizeTextView.text = "Tamaño: ${actualizacion.tamaño} GB"

            editButton.setOnClickListener { onEditClick(actualizacion) }
            deleteButton.setOnClickListener { onDeleteClick(actualizacion) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_actualizacion, parent, false)
        return UpdateViewHolder(view)
    }

    override fun onBindViewHolder(holder: UpdateViewHolder, position: Int) {
        holder.bind(updates[position])
    }

    override fun getItemCount(): Int = updates.size
}
