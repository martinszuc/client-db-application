package com.matos.app.ui.component.client

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matos.app.ActivityMain
import com.matos.app.ClientsAdapter
import com.matos.app.databinding.FragmentClientsBinding
import com.matos.app.data.database.DbContext


class FragmentClients : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentClientsBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientsBinding.inflate(inflater, container, false)

        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            val activity = requireActivity() as? ActivityMain
            activity?.loadFragment(FragmentAddClient())
        }

        val db = DbContext(requireContext(), null)

        val clients = db.getClients()

        if (clients.isNotEmpty()) {
            recyclerView = binding.recyclerViewClients
            recyclerView.layoutManager = LinearLayoutManager(activity)
            val clientsAdapter = ClientsAdapter(clients)
            clientsAdapter.onItemClick = { client ->
                val activity = requireActivity() as? ActivityMain
                activity?.loadFragment(FragmentClientProfile.newInstance(client))
            }
            recyclerView.adapter = clientsAdapter
        } else {
            val activity = requireActivity() as? ActivityMain
            activity?.loadFragment(FragmentClientsEmpty())
        }

        db.close()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}