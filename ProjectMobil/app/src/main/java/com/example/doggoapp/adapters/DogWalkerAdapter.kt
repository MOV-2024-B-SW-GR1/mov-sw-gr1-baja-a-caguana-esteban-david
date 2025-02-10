package com.example.doggoapp.adapters
import android.media.Image
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.doggoapp.R
import com.example.doggoapp.data.DogWalker

class DogWalkerAdapter(
    private val dogWalkers: List<DogWalker>,
    private val onScheduleClick: (DogWalker) -> Unit
) : RecyclerView.Adapter<DogWalkerAdapter.DogWalkerViewHolder>() {

    inner class DogWalkerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.dogWalker_name)
        private val phoneTextView: TextView = itemView.findViewById(R.id.dogWalker_phone)
        private val avatar: ImageView = itemView.findViewById(R.id.avatar)
        private val scheduleButton: Button = itemView.findViewById(R.id.btg_agendar)


        fun bind(dogWalker: DogWalker) {
            nameTextView.text = dogWalker.nombre
            phoneTextView.text = dogWalker.numeroTelefono
            val imageUri = Uri.parse(dogWalker.foto)
            try {
                avatar.setImageURI(imageUri)
            } catch (e: Exception) {
                // Si hay un error, establece una imagen por defecto
                avatar.setImageResource(R.drawable.logo)
            }
            scheduleButton.setOnClickListener { onScheduleClick(dogWalker) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogWalkerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dogwalkeritem, parent, false)
        return DogWalkerViewHolder(view)
    }

    override fun onBindViewHolder(holder: DogWalkerViewHolder, position: Int) {
        holder.bind(dogWalkers[position])
    }

    override fun getItemCount(): Int = dogWalkers.size
}
