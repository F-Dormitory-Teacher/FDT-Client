package com.fdt.client.ui.fragment.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fdt.client.R
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.User
import com.fdt.client.entity.response.Token
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_complete_btn.setOnClickListener {
            val user = User(login_id_et.text.toString(), login_pw_et.text.toString())

            val response: Call<Token> = NetRetrofit.getService()!!.postSignIn(user)

            response.enqueue(object: Callback<Token>{
                override fun onResponse(call: Call<Token>, response: Response<Token>) {
                    Log.d("hi", response.code().toString())
                }

                override fun onFailure(call: Call<Token>, t: Throwable) {
                    Log.e("error", t.message.toString())
                }

            })
//            Navigation.findNavController(requireView())
//                .navigate(R.id.action_loginFragment_to_mainFragment)
        }
        login_go_register_tv.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_firstRegisterFragment)
        }
    }
}