// ActivityMain.kt
package com.matos.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.matos.app.databinding.ActivityMainBinding
import com.matos.app.ui.component.client.FragmentClients
import com.matos.app.ui.component.search.FragmentSearch
import com.matos.app.ui.component.settings.FragmentSettings
import com.matos.app.ui.component.service.FragmentServices
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActivityMain : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)

        binding.bottomNavigationView.setupWithNavController(navController)

        loadFragment(FragmentClients())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentClients -> {
                    loadFragment(FragmentClients())
                    true
                }
                R.id.fragmentSearch -> {
                    loadFragment(FragmentSearch())
                    true
                }
                R.id.fragmentServices -> {
                    loadFragment(FragmentServices())
                    true
                }
                R.id.fragmentSettings -> {
                    loadFragment(FragmentSettings())
                    true
                }
                else -> throw AssertionError()
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
