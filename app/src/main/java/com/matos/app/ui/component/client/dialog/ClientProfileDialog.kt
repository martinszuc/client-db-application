package com.matos.app.ui.component.client.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.matos.app.databinding.DialogClientProfileBinding
import com.matos.app.ui.viewmodel.SharedClientViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClientProfileDialog : BottomSheetDialogFragment() {

    private var _binding: DialogClientProfileBinding? = null
    private val binding get() = _binding!!
    private val sharedClientViewModel: SharedClientViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogClientProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sharedClientViewModel.selectedClient.observe(viewLifecycleOwner) { client ->
            binding.textViewName.text = client.name
            binding.textViewPhone.text = client.phone
            binding.textViewEmail.text = client.email
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(): ClientProfileDialog {
            return ClientProfileDialog()
        }
    }
}