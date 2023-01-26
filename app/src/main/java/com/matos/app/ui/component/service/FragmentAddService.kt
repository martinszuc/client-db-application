package com.matos.app.ui.component.service

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.matos.app.ActivityMain
import com.matos.app.databinding.FragmentAddServiceBinding
import com.matos.app.data.entity.Client
import com.matos.app.data.entity.Service
import com.matos.app.ui.component.client.FragmentAddClient
import com.matos.app.data.database.DbContext
import java.util.Calendar

class FragmentAddService : Fragment() {

    private lateinit var binding: FragmentAddServiceBinding
    private lateinit var dbContext: DbContext
    private lateinit var clientsList: MutableList<Client>
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dbContext = DbContext(requireContext(), null)

        clientsList = dbContext.getClients().toMutableList()

        // Create a list of client names to display in the dropdown
        val clientNames = clientsList.map { it.name }
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, clientNames)
        binding.clientSpinner.adapter = adapter

        binding.buttonSave.setOnClickListener {
            addService()
            val activity = requireActivity() as? ActivityMain
            activity?.loadFragment(FragmentServices())
        }

        binding.buttonAddClient.setOnClickListener {
            val activity = requireActivity() as? ActivityMain
            activity?.loadFragment(FragmentAddClient())
        }

        binding.buttonDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        binding.buttonTime.setOnClickListener {
            val timePickerDialog = TimePickerDialog(
                requireContext(),
                { _, hourOfDay, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    calendar.set(Calendar.MINUTE, minute)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }
    }

    private fun addService() {
        val clientId = clientsList[binding.clientSpinner.selectedItemPosition].id
        val description = binding.editTextDescription.text.toString()
        val price = binding.editTextPrice.text.toString().toDoubleOrNull()
        if (clientId == -1 || description.isEmpty() || price == null) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }
        val date = calendar.time
        val service = Service(clientId, description, date, price)
        dbContext.addService(service)
        Toast.makeText(requireContext(), "Service added successfully", Toast.LENGTH_SHORT).show()
        binding.editTextDescription.text = null
        binding.editTextPrice.text = null
    }


    override fun onDestroy() {
        super.onDestroy()
        dbContext.close()
    }
}
