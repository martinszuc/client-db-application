package com.matos.app.ui.component.service

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.matos.app.ActivityMain
import com.matos.app.databinding.FragmentServicesEmptyBinding

class FragmentServicesEmpty : Fragment() {

    private lateinit var binding: FragmentServicesEmptyBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentServicesEmptyBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addFab.setOnClickListener {
            val activity = requireActivity() as? ActivityMain
            activity?.loadFragment(FragmentAddService())
        }
    }
}
