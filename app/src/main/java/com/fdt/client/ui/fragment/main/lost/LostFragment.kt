package com.fdt.client.ui.fragment.main.lost

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fdt.client.R
import kotlinx.android.synthetic.main.fragment_lost.*
import kotlinx.android.synthetic.main.fragment_post_lost.*

class LostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lost_add_btn.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.fragment_container).navigate(R.id.action_mainFragment_to_postLostFragment)
        }
    }
}