package com.fdt.client.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.fdt.client.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v:View = inflater.inflate(R.layout.fragment_main, container, false)

        val navController: NavController = Navigation.findNavController(v.findViewById(R.id.main_container))
        val mainNavigation:BottomNavigationView = v.findViewById(R.id.main_navigation)

        NavigationUI.setupWithNavController(mainNavigation,navController)

        return v
    }
}