package com.fdt.client.ui.fragment.register

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fdt.client.R
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.Auth
import com.fdt.client.entity.Email
import com.fdt.client.entity.User
import kotlinx.android.synthetic.main.fragment_second_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast

class SecondRegisterFragment : Fragment() {
    var isVerified: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_second_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        second_register_request_verify_btn.setOnClickListener {
            val userEmail = Email(second_register_email_et.text.toString())
            val response: Call<Void> = NetRetrofit.getService()!!
                .requestVerifyCode(userEmail)

            response.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        toast("이메일을 발송했습니다.")
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("error", t.message.toString())
                }
            })
        }

        second_register_confirm_verify_btn.setOnClickListener {
            val response: Call<Void> = NetRetrofit.getService()!!
                .confirmVerifyCode(second_register_verify_code_et.text.toString())

            response.enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.code() == 200) {
                        toast("인증 성공")
                        isVerified = true
                        second_register_email_et.isClickable = false
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Log.e("error", t.message.toString())
                }

            })
        }

        second_register_next_btn.setOnClickListener {
            Log.d("dsafsadf", "dfadfas")
            val auth = Auth(
                second_register_email_et.text.toString(),
                second_register_pw_et.text.toString(),
                arguments?.getString("userName").toString(),
                arguments?.getString("schoolNumber").toString()
            )

            if (isVerified) {
                if (second_register_pw_check_et.text.toString() == second_register_pw_et.text.toString()) {
                    val response: Call<Void> = NetRetrofit.getService()!!.postSignUp(auth)

                    response.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.code() == 200) {
                                Navigation.findNavController(requireView())
                                    .navigate(R.id.action_secondRegisterFragment_to_completeRegisterFragment)
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("error", t.message.toString())
                        }
                    })
                } else toast("비밀번호가 일치하지 않습니다.")
            } else Log.e("error", isVerified.toString())
        }
    }

}