package com.matos.app.ui.component.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.matos.app.data.entity.Client
import com.matos.app.databinding.FragmentServicesBinding
import com.matos.app.ui.component.service.adapter.ServicesAdapter
import com.matos.app.ui.component.service.dialog.AddServiceDialog
import com.matos.app.ui.viewmodel.SharedServiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentServices : Fragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!
    private val sharedServiceViewModel: SharedServiceViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            AddServiceDialog().show(childFragmentManager, "AddServiceDialog")
        }

        val recyclerView = binding.recyclerViewServices
        recyclerView.layoutManager = LinearLayoutManager(activity)

        sharedServiceViewModel.services.observe(viewLifecycleOwner, Observer { services ->
            val clients = listOf<Client>() // Fetch or pass your clients list here
            val servicesAdapter = ServicesAdapter(services, clients)
            recyclerView.adapter = servicesAdapter
        })

        sharedServiceViewModel.loadServices()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

