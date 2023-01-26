package com.matos.app.ui.component.client

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.matos.app.ActivityMain
import com.matos.app.databinding.FragmentAddClientBinding
import com.matos.app.data.database.DbContext


class FragmentAddClient : Fragment() {

    private lateinit var binding: FragmentAddClientBinding
    private lateinit var dbContext: DbContext

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddClientBinding.inflate(inflater, container, false)


        dbContext = DbContext(requireContext(), null)

        binding.buttonSave.setOnClickListener {
            addClient();
            val activity = requireActivity() as? ActivityMain
            activity?.loadFragment(FragmentClients())

        }

        return binding.root
    }

    private fun addClient() {
        val name = binding.editTextName.text.toString()
        val phone = binding.editTextPhone.text.toString()
        val email = binding.editTextEmail.text.toString()


        val clientId = dbContext.addClient(name, phone, email)
        if (clientId<0) {
            Toast.makeText(requireContext(), "Client doesnt exist!", Toast.LENGTH_SHORT).show()
        }else Toast.makeText(requireContext(), "Client added!", Toast.LENGTH_SHORT).show()

    }
}