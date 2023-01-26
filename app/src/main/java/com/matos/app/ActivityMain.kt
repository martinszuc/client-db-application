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
import com.matos.app.data.database.DbContext

class ActivityMain : AppCompatActivity() {
    internal lateinit var binding: ActivityMainBinding
    internal lateinit var navController: NavController
    internal lateinit var dbContext: DbContext

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dbContext = DbContext(this,null)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        navController.setGraph(R.navigation.nav_graph)

        binding.bottomNavigationView.setupWithNavController(navController)

        loadFragment(FragmentClients())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.fragmentClients -> {
//                    supportActionBar?.title = getString(R.string.label_clients)

                    loadFragment(FragmentClients())
                    true
                }
                R.id.fragmentSearch -> {
//                    supportActionBar?.title = getString(R.string.label_search)

                    loadFragment(FragmentSearch())
                    true
                }
                R.id.fragmentServices -> {
//                    supportActionBar?.title = getString(R.string.label_services)
                    loadFragment(FragmentServices())
                    true
                }
                R.id.fragmentSettings -> {
//                    supportActionBar?.title = getString(R.string.label_settings)
                    loadFragment(FragmentSettings())
                    true
                }
                else -> throw AssertionError()
            }
        }

    }

    fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.nav_host_fragment,fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
