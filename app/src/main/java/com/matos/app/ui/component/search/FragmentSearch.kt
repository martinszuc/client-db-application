package com.matos.app.ui.component.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.matos.app.databinding.FragmentSearchBinding
import com.matos.app.ui.base.AbstractFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentSearch : AbstractFragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val searchViewModel: SearchViewModel by viewModels()
    private val _searchResultsAdapter = SearchResultsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView(binding.recyclerView, _searchResultsAdapter)

        searchViewModel.searchResults.observe(viewLifecycleOwner, Observer { searchResults ->
            if (searchResults.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.emptyStateTextView.visibility = View.VISIBLE
                _searchResultsAdapter.clearSearchResults()
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.emptyStateTextView.visibility = View.GONE
                _searchResultsAdapter.updateSearchResults(searchResults)
            }
        })

        // Set up the search view listener
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchViewModel.performSearch(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { searchViewModel.performSearch(it) }
                return true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
