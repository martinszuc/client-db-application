package com.matos.app.ui.component.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.matos.app.databinding.FragmentSearchBinding
import com.matos.app.data.database.DbContext


class FragmentSearch : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val _searchResultsAdapter = SearchResultsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return getBinding(inflater, container).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = DbContext(requireContext(), null)
        _searchResultsAdapter.setClients(db.getClients())
        db.close()
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = _searchResultsAdapter
        }



        // Set up the search view listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { performSearch(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { performSearch(it) }
                return true
            }
        })
    }

    private fun performSearch(query: String) {
        val db = DbContext(requireContext(), null)
        val searchResults = mutableListOf<Any>()

        // Search in client names
        val clients = db.searchClients(query)
        searchResults.addAll(clients)

        // Search in service descriptions
        val services = db.searchServices(query)
        searchResults.addAll(services)

        db.close()

        if (searchResults.isEmpty()) {
            // No search results found, show the empty state message and hide the RecyclerView
            binding.recyclerView.visibility = View.GONE
            binding.emptyStateTextView.visibility = View.VISIBLE

            // Clear the search results when there are no matches
            _searchResultsAdapter.clearSearchResults()
        } else {
            // Search results found, show the RecyclerView and hide the empty state message
            binding.recyclerView.visibility = View.VISIBLE
            binding.emptyStateTextView.visibility = View.GONE

            // Update the RecyclerView with the search results
            _searchResultsAdapter.updateSearchResults(searchResults)
        }
    }


    private fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(inflater, container, false).also {
            _binding = it
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
