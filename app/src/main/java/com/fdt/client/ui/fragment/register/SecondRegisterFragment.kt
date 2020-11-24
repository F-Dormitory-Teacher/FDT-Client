package com.fdt.client.ui.fragment.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fdt.client.R
import kotlinx.android.synthetic.main.fragment_second_register.*

class SecondRegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        second_register_next_btn.setOnClickListener {
            Navigation.findNavController(requireView()).navigate(R.id.action_secondRegisterFragment_to_completeRegisterFragment)
        }
    }

}