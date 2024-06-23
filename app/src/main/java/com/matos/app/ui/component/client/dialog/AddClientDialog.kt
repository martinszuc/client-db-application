package com.matos.app.ui.component.client.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.matos.app.data.entity.Client
import com.matos.app.databinding.DialogAddClientBinding
import com.matos.app.ui.viewmodel.SharedClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddClientDialog : DialogFragment() {

    private var _binding: DialogAddClientBinding? = null
    private val binding get() = _binding!!
    private val sharedClientViewModel: SharedClientViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddClientBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextName.text.toString()
            val phone = binding.editTextPhone.text.toString()
            val email = binding.editTextEmail.text.toString()
            val client = Client(0, name, phone, email)
            sharedClientViewModel.addClient(client)
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}