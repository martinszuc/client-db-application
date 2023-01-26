package com.matos.app.ui.component.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.matos.app.databinding.ClientsCardViewBinding
import com.matos.app.databinding.ServicesCardViewBinding
import com.matos.app.data.entity.Client
import com.matos.app.data.entity.Service
import java.text.SimpleDateFormat
import java.util.Locale

class SearchResultsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val searchResults = mutableListOf<Any>()
    private val originalItems = mutableListOf<Any>()
    private var clients: List<Client> = emptyList()

    fun setClients(clients: List<Client>) {
        this.clients = clients
    }
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
            is ClientViewHolder -> {
                val client = item as Client
                holder.bind(client)
            }
            is ServiceViewHolder -> {
                val service = item as Service
                holder.bind(service)
            }
        }
    }

    override fun getItemCount(): Int {
        return searchResults.size
    }

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

        // Save the original list of items when updating the search results
        if (originalItems.isEmpty()) {
            originalItems.addAll(searchResults)
        }

        notifyDataSetChanged()
    }

    fun clearSearchResults() {
        // Clear the search results and show the original list of items
        searchResults.clear()
        searchResults.addAll(originalItems)
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

            binding.textViewServiceDate.text = service.date?.let { dateFormat.format(it) }
            binding.textViewServicePrice.text = service.price.toString()
            binding.textViewServiceDescription.text = service.description

            val client = clients.find { it.id == service.clientId }
            binding.textViewClientName.text = client?.name ?: ""
        }
    }

    companion object {
        private const val CLIENT_VIEW_TYPE = 1
        private const val SERVICE_VIEW_TYPE = 2
    }
}
