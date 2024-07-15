// ui/component/service/dialog/AddServiceDialog.kt
package com.matos.app.ui.component.service.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.matos.app.data.entity.Service
import com.matos.app.databinding.DialogAddServiceBinding
import com.matos.app.ui.component.service.ServiceViewModel
import com.matos.app.ui.viewmodel.SharedClientViewModel
import com.matos.app.ui.viewmodel.SharedServiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@AndroidEntryPoint
class AddServiceDialog : DialogFragment() {

    private var _binding: DialogAddServiceBinding? = null
    private val binding get() = _binding!!
    private val serviceSharedViewModel: SharedServiceViewModel by activityViewModels()
    private val sharedClientViewModel: SharedClientViewModel by activityViewModels()
    private val calendar = Calendar.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedClientViewModel.clients.observe(viewLifecycleOwner) { clients ->
            val clientNames = clients.map { it.name }
            val adapter =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, clientNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.clientSpinner.adapter = adapter
        }

        binding.buttonSave.setOnClickListener {
            addService()
            dismiss()
        }

        binding.buttonDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                { _, year, month, dayOfMonth ->
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                    updateDateInView()
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
                    updateTimeInView()
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
            )
            timePickerDialog.show()
        }
    }

    private fun updateDateInView() {
        val myFormat = "dd/MM/yyyy" // In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.buttonDate.text = sdf.format(calendar.time)
    }

    private fun updateTimeInView() {
        val myFormat = "HH:mm" // In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        binding.buttonTime.text = sdf.format(calendar.time)
    }

    private fun addService() {
        val clientId = sharedClientViewModel.clients.value?.get(binding.clientSpinner.selectedItemPosition)?.id ?: -1
        val description = binding.editTextDescription.text.toString()
        val price = binding.editTextPrice.text.toString().toDoubleOrNull()
        if (clientId == -1 || description.isEmpty() || price == null) {
            Toast.makeText(requireContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return
        }
        val date = calendar.time
        val service = Service(0, clientId, description, date, price)
        serviceSharedViewModel.addService(service)
        Toast.makeText(requireContext(), "Service added successfully", Toast.LENGTH_SHORT).show()
        binding.editTextDescription.text = null
        binding.editTextPrice.text = null
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
