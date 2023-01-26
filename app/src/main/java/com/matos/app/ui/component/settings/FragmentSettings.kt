package com.matos.app.ui.component.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.matos.app.databinding.FragmentSettingsBinding

class FragmentSettings : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up the switch listeners
        binding.switchSetting1.setOnCheckedChangeListener { _, isChecked ->
            // TODO Handle the switch state change here
        }

        binding.switchSetting2.setOnCheckedChangeListener { _, isChecked ->
            // TODO Handle the switch state change here
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
