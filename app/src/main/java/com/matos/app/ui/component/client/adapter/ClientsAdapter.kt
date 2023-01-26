package com.matos.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matos.app.databinding.ClientsCardViewBinding
import com.matos.app.data.entity.Client

class ClientsAdapter(private var clients: List<Client>) : RecyclerView.Adapter<ClientsAdapter.ViewHolder>() {

    var onItemClick: ((Client) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ClientsCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clients[position])
    }

    override fun getItemCount(): Int = clients.size

    inner class ViewHolder(private val binding: ClientsCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(client: Client) {
            binding.textViewClientName.text = client.name
            binding.textViewClientEmail.text = client.email
            binding.textViewClientPhone.text = client.phone

            itemView.setOnClickListener {
                onItemClick?.invoke(client)
            }
        }
    }
    fun sortClients(sortBy: String) {
        when (sortBy) {
            "name" -> clients = clients.sortedBy { it.name }
            "date" -> clients = clients.reversed()
        }
        notifyDataSetChanged()
    }
}
