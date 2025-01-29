package com.example.deber2.adapters
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.deber2.R
import com.example.deber2.data.VideoGame

class VideoGameAdapter(
    private val videoGames: List<VideoGame>,
    private val onEditClick: (VideoGame) -> Unit,
    private val onDeleteClick: (VideoGame) -> Unit,
    private val onDetailsClick: (VideoGame) -> Unit
) : RecyclerView.Adapter<VideoGameAdapter.VideoGameViewHolder>() {

    // ViewHolder interno que enlaza los datos al diseño del ítem
    inner class VideoGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val priceTextView: TextView = itemView.findViewById(R.id.priceTextView)
        private val editButton: ImageButton = itemView.findViewById(R.id.editVideoGameButton)
        private val deleteButton: ImageButton = itemView.findViewById(R.id.deleteVideoGameButton)
        private val detailsButton: ImageButton = itemView.findViewById(R.id.viewVideoGameButton)

        fun bind(videoGame: VideoGame) {
            titleTextView.text = videoGame.titulo
            priceTextView.text = "Precio: ${videoGame.precio} USD"

            // Configurar listeners para los botones
            editButton.setOnClickListener { onEditClick(videoGame) }
            deleteButton.setOnClickListener { onDeleteClick(videoGame) }
            detailsButton.setOnClickListener { onDetailsClick(videoGame) }
        }
    }

    // Inflar la vista del ítem y crear el ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoGameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_videogame, parent, false)
        return VideoGameViewHolder(view)
    }

    // Enlazar los datos al ViewHolder en una posición específica
    override fun onBindViewHolder(holder: VideoGameViewHolder, position: Int) {
        holder.bind(videoGames[position])
    }

    // Retornar el tamaño de la lista
    override fun getItemCount(): Int = videoGames.size
}
