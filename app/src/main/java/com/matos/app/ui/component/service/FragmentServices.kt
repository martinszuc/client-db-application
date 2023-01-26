package com.matos.app.ui.component.service

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.matos.app.ActivityMain
import com.matos.app.R
import com.matos.app.ui.component.service.adapter.ServicesAdapter
import com.matos.app.databinding.FragmentServicesBinding
import com.matos.app.data.database.DbContext

class FragmentServices : Fragment() {

    private lateinit var recyclerViewServices: RecyclerView
    private lateinit var dbContext: DbContext
    private lateinit var recyclerView: RecyclerView
    private var _binding: FragmentServicesBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentServicesBinding.inflate(inflater, container, false)
        recyclerViewServices = binding.recyclerViewServices
        dbContext = DbContext(requireContext(), null)
        return binding.root
    }

    @SuppressLint("Range")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            val activity = requireActivity() as? ActivityMain
            activity?.loadFragment(FragmentAddService())
        }

        val dBHelper = DbContext(requireContext(), null)

        val clients = dBHelper.getClients()
        val services = dBHelper.getServices()

        if (services.isNotEmpty()) {

            recyclerView = view.findViewById(R.id.recyclerViewServices)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = ServicesAdapter(services,clients)
        } else {
            val activity = requireActivity() as? ActivityMain
            activity?.loadFragment(FragmentServicesEmpty())
        }

        dBHelper.close()
    }

}






