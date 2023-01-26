package com.matos.app.ui.component.client

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.matos.app.data.entity.Client
import com.matos.app.ui.component.service.adapter.ServicesProfileAdapter
import com.matos.app.databinding.FragmentClientProfileBinding
import com.matos.app.data.database.DbContext

class FragmentClientProfile : Fragment() {

    private var _binding: FragmentClientProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var dbContext: DbContext
    private lateinit var client: Client

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        client = arguments?.getParcelable(ARG_CLIENT)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentClientProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dbContext = DbContext(requireContext(), null)

        binding.textViewName.text = client.name
        binding.textViewPhone.text = client.phone
        binding.textViewEmail.text = client.email

        // Initialize the RecyclerView with a list of services

        val services = dbContext.getServicesForClient(client.id)
        binding.recyclerViewProfileServices.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerViewProfileServices.adapter = ServicesProfileAdapter(services)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ARG_CLIENT = "client"

        fun newInstance(client: Client): FragmentClientProfile {
            val fragment = FragmentClientProfile()
            val args = Bundle()
            args.putParcelable(ARG_CLIENT, client)
            fragment.arguments = args
            return fragment
        }
    }
}