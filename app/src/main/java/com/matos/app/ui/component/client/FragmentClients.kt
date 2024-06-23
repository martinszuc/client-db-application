package com.matos.app.ui.component.client
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.matos.app.ClientsAdapter
import com.matos.app.databinding.FragmentClientsBinding
import com.matos.app.ui.component.client.dialog.AddClientDialog
import com.matos.app.ui.component.client.dialog.ClientProfileDialog
import com.matos.app.ui.viewmodel.SharedClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentClients : Fragment() {

    private var _binding: FragmentClientsBinding? = null
    private val binding get() = _binding!!
    private val sharedClientViewModel: SharedClientViewModel by activityViewModels()

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
        recyclerView.layoutManager = LinearLayoutManager(activity)

        // Observe the client list
        sharedClientViewModel.clients.observe(viewLifecycleOwner) { clients ->
            val clientsAdapter = ClientsAdapter(clients)
            clientsAdapter.onItemClick = { client ->
                sharedClientViewModel.selectClient(client)
                ClientProfileDialog.newInstance().show(childFragmentManager, "ClientProfileDialog")
            }
            recyclerView.adapter = clientsAdapter
        }

        // Load clients
        sharedClientViewModel.loadClients()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}