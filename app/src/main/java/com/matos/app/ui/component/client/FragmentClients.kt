package com.matos.app.ui.component.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.matos.app.ClientsAdapter
import com.matos.app.databinding.FragmentClientsBinding
import com.matos.app.ui.base.BaseFragment
import com.matos.app.ui.component.client.dialog.AddClientDialog
import com.matos.app.ui.viewmodel.SharedClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentClients : BaseFragment() {

    private var _binding: FragmentClientsBinding? = null
    private val binding get() = _binding!!
    private val sharedClientViewModel: SharedClientViewModel by activityViewModels()
    private lateinit var clientsAdapter: ClientsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            AddClientDialog().show(childFragmentManager, "AddClientDialog")
        }

        val recyclerView = binding.recyclerViewClients
        clientsAdapter = ClientsAdapter(emptyList()) // Pass empty list initially
        setupRecyclerView(recyclerView, clientsAdapter)

        sharedClientViewModel.clients.observe(viewLifecycleOwner) { clients ->
            clientsAdapter.updateClients(clients)
        }

        sharedClientViewModel.loadClients()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
