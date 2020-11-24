package com.fdt.client.ui.fragment.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fdt.client.R
import kotlinx.android.synthetic.main.fragment_first_register.*

class FirstRegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        first_register_next_btn.setOnClickListener {
            if (first_register_name_et.text != null && first_register_school_number_et.text != null) {
                val bundle = Bundle()
                bundle.putString("userName", first_register_name_et.text.toString())
                bundle.putString("schoolNumber", first_register_school_number_et.text.toString())

                Navigation.findNavController(requireView())
                    .navigate(R.id.action_firstRegisterFragment_to_secondRegisterFragment,bundle)
            }
        }
    }
}