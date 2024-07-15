package com.matos.app.ui.component.service.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.activityViewModels
import com.matos.app.data.entity.Service
import com.matos.app.databinding.DialogAddServiceBinding
import com.matos.app.ui.base.BaseDialogFragment
import com.matos.app.ui.viewmodel.SharedClientViewModel
import com.matos.app.ui.viewmodel.SharedServiceViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class AddServiceDialog : BaseDialogFragment() {

    private var _binding: DialogAddServiceBinding? = null
    private val binding get() = _binding!!
    private val serviceSharedViewModel: SharedServiceViewModel by activityViewModels()
    private val sharedClientViewModel: SharedClientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddServiceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClientSpinner()
        setupDateAndTimePickers()
        setupSaveButton()
    }

    private fun setupClientSpinner() {
        sharedClientViewModel.clients.observe(viewLifecycleOwner) { clients ->
            val clientNames = clients.map { it.name }
            val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, clientNames)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.clientSpinner.adapter = adapter
        }
    }

    private fun setupDateAndTimePickers() {
        binding.buttonDate.setOnClickListener {
            showDatePicker { year, month, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                binding.buttonDate.text = updateDateInView("dd/MM/yyyy")
            }
        }

        binding.buttonTime.setOnClickListener {
            showTimePicker { hourOfDay, minute ->
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
                calendar.set(Calendar.MINUTE, minute)
                binding.buttonTime.text = updateTimeInView("HH:mm")
            }
        }
    }

    private fun setupSaveButton() {
        binding.buttonSave.setOnClickListener {
            addService()
        }
    }

    private fun addService() {
        val clientId = sharedClientViewModel.clients.value?.get(binding.clientSpinner.selectedItemPosition)?.id ?: -1
        val description = binding.editTextDescription.text.toString()
        val price = binding.editTextPrice.text.toString().toDoubleOrNull()
        if (clientId == -1 || description.isEmpty() || price == null) {
            showToast("Please fill in all fields")
            return
        }
        val date = calendar.time
        val service = Service(0, clientId, description, date, price)
        serviceSharedViewModel.addService(service)
        showToast("Service added successfully")
        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
