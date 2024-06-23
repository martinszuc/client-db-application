package com.matos.app.ui.component.service.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matos.app.databinding.ServicesCardViewBinding
import com.matos.app.data.entity.Client
import com.matos.app.data.entity.Service
import java.text.SimpleDateFormat
import java.util.Locale

class ServicesAdapter(
    private val services: List<Service>,
    private val clients: List<Client>
) : RecyclerView.Adapter<ServicesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ServicesCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int = services.size

    inner class ViewHolder(private val binding: ServicesCardViewBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            val client = clients.find { it.id == service.client_id }
            val dateFormat = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())

            binding.textViewClientName.text = client?.name ?: ""
            binding.textViewServiceDescription.text = service.description
            binding.textViewServiceDate.text = service.date.let { dateFormat.format(it) }
            binding.textViewServicePrice.text = service.price.toString()
        }

    }
}
