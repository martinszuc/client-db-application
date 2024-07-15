package com.matos.app.ui.component.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.matos.app.databinding.FragmentServicesBinding
import com.matos.app.ui.base.BaseFragment
import com.matos.app.ui.component.service.adapter.ServicesAdapter
import com.matos.app.ui.component.service.dialog.AddServiceDialog
import com.matos.app.ui.viewmodel.SharedServiceViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FragmentServices : BaseFragment() {

    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!
    private val sharedServiceViewModel: SharedServiceViewModel by activityViewModels()
    private lateinit var servicesAdapter: ServicesAdapter

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
        servicesAdapter = ServicesAdapter(emptyList(), listOf()) // Pass empty list initially
        setupRecyclerView(recyclerView, servicesAdapter)

        sharedServiceViewModel.services.observe(viewLifecycleOwner, Observer { services ->
            servicesAdapter.updateServices(services)
        })

        sharedServiceViewModel.loadServices()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
