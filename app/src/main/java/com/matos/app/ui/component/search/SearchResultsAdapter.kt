package com.matos.app.ui.component.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matos.app.data.entity.Client
import com.matos.app.data.entity.Service
import com.matos.app.databinding.ClientsCardViewBinding
import com.matos.app.databinding.ServicesCardViewBinding
import java.text.SimpleDateFormat
import java.util.Locale

class SearchResultsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val searchResults = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            CLIENT_VIEW_TYPE -> {
                val binding = ClientsCardViewBinding.inflate(inflater, parent, false)
                ClientViewHolder(binding)
            }
            SERVICE_VIEW_TYPE -> {
                val binding = ServicesCardViewBinding.inflate(inflater, parent, false)
                ServiceViewHolder(binding)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = searchResults[position]
        when (holder) {
            is ClientViewHolder -> holder.bind(item as Client)
            is ServiceViewHolder -> holder.bind(item as Service)
        }
    }

    override fun getItemCount(): Int = searchResults.size

    override fun getItemViewType(position: Int): Int {
        return when (searchResults[position]) {
            is Client -> CLIENT_VIEW_TYPE
            is Service -> SERVICE_VIEW_TYPE
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    fun updateSearchResults(results: List<Any>) {
        searchResults.clear()
        searchResults.addAll(results)
        notifyDataSetChanged()
    }

    fun clearSearchResults() {
        searchResults.clear()
        notifyDataSetChanged()
    }

    inner class ClientViewHolder(private val binding: ClientsCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(client: Client) {
            binding.textViewClientName.text = client.name
            binding.textViewClientEmail.text = client.email
            binding.textViewClientPhone.text = client.phone
        }
    }

    inner class ServiceViewHolder(private val binding: ServicesCardViewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(service: Service) {
            val dateFormat = SimpleDateFormat("HH:mm dd/MM/yyyy", Locale.getDefault())
            binding.textViewServiceDate.text = dateFormat.format(service.date)
            binding.textViewServicePrice.text = service.price.toString()
            binding.textViewServiceDescription.text = service.description
            // Assume clients list is provided to the adapter
//            val client = // Find client based on service.client_id
//                binding.textViewClientName.text = client?.name ?: ""
        }
    }

    companion object {
        private const val CLIENT_VIEW_TYPE = 1
        private const val SERVICE_VIEW_TYPE = 2
    }
}