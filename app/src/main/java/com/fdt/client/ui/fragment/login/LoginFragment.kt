package com.fdt.client.ui.fragment.login

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.fdt.client.R
import com.fdt.client.data.local.SharedPref
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.User
import com.fdt.client.entity.response.Token
import kotlinx.android.synthetic.main.fragment_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast

class LoginFragment : Fragment() {
    lateinit var sharedpref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedpref = SharedPref(requireContext())
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_complete_btn.setOnClickListener {
            if (login_id_et.text != null && login_pw_et.text != null) {
                val user = User(login_id_et.text.toString(), login_pw_et.text.toString())

                val response: Call<Token> = NetRetrofit.getService()!!.postSignIn(user)

                response.enqueue(object : Callback<Token> {
                    override fun onResponse(call: Call<Token>, response: Response<Token>) {
                        if (response.code() == 200) {
                            toast("로그인 성공")
                            Navigation.findNavController(requireView())
                                .navigate(R.id.action_loginFragment_to_mainFragment)
                            sharedpref.saveToken(response.body()!!.accessToken, true)
                            Log.d("token",sharedpref.getToken(true))
                        } else if (response.code() == 404) {
                            login_id_et_layout.error = "아이디가 일치하지 않습니다."
                            login_pw_et_layout.error = "비밀번호가 일치하지 않습니다."
                        }
                    }

                    override fun onFailure(call: Call<Token>, t: Throwable) {
                        Log.e("error", t.message.toString())
                    }
                })
            } else toast("빈칸으르 모두 채워주세요")
        }
        login_go_register_tv.setOnClickListener {
            Navigation.findNavController(requireView())
                .navigate(R.id.action_loginFragment_to_firstRegisterFragment)
        }
    }
}