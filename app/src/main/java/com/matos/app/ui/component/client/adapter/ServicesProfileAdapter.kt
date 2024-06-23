package com.matos.app.ui.component.client.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matos.app.databinding.ServicesProfileCardViewBinding
import com.matos.app.data.entity.Service

class ServicesProfileAdapter(private val services: List<Service>) : RecyclerView.Adapter<ServicesProfileAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ServicesProfileCardViewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(services[position])
    }

    override fun getItemCount(): Int {
        return services.size
    }
    inner class ViewHolder(private val binding: ServicesProfileCardViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(service: Service) {
            binding.textViewServiceDescription.text = service.description
            binding.textViewServiceDate.text = service.date.toString()
            binding.textViewServicePrice.text = service.price.toString()
        }
    }
}
