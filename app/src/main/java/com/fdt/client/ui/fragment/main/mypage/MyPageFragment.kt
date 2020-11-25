package com.fdt.client.ui.fragment.main.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.fdt.client.R
import com.fdt.client.data.local.SharedPref
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.MyPage
import com.fdt.client.entity.response.UserInfo
import com.fdt.client.ui.dialog.CustomDialog
import kotlinx.android.synthetic.main.alert_ask_logout.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Header
import splitties.toast.toast
import java.text.SimpleDateFormat
import java.util.*

class MyPageFragment : Fragment() {
    lateinit var sharedPref: SharedPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        sharedPref = SharedPref(requireContext())
        return inflater.inflate(R.layout.fragment_my_page, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        my_page_log_out_tv.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.action_mainFragment_to_loginFragment)

            Toast.makeText(requireContext(), "로그아웃", Toast.LENGTH_SHORT).show()

        }

        //var date = Date(2020, 11,24)
        var sformat = SimpleDateFormat("yyyy-MM-dd")
        var date = sformat.format(Calendar.getInstance().time)

        Log.d("TestLog dd = ", date)

        // 아침 ,저녁 상태 설정
        setStatus(date, "MORNING", my_page_attend_morning)
        setStatus(date, "NIGHT", my_page_attend_night)

        val responseUserInfo: Call<UserInfo> =
            NetRetrofit.getService()!!.getUserInfo(sharedPref.getToken(true))

        responseUserInfo.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                Log.d("df",response.code().toString())
                if (response.code() == 200) {
                    Log.d("test", response.body()?.data!!.user.name + "ddd")

                    my_page_name_tv.text = response.body()?.data!!.user.studentId

                    val parseSchoolNum = response.body()?.data!!.user.name.substring(
                        0,
                        1
                    ) + "학년 " + response.body()?.data!!.user.name.substring(
                        1,
                        2
                    ) + "반" + response.body()?.data!!.user.name.substring(2, 4) + "번 "
                    my_page_school_number_tv.text = parseSchoolNum
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("error UserInfo", t.message.toString())
            }

        })

    }


    private fun setStatus(date: String, type: String, statusView: TextView) {
        val response: Call<MyPage> =
            NetRetrofit.getService()!!.getMyAttends(sharedPref.getToken(true), date, type)

        response.enqueue(object : Callback<MyPage> {
            override fun onResponse(call: Call<MyPage>, response: Response<MyPage>) {
                if (response.code() == 200) {
                    //Log.d("TestLog", "데이터 불러오기 완료")

                    Log.d("TestLog body", "${response.body()}")
                    if (response.body()?.data?.attendances?.size != 0) {

                        //Log.d ("TestLog status", "${response.body()?.data?.attendances?.get(0)?.status}")

                        when (response.body()?.data?.attendances?.get(0)?.status) {
                            "ATTEND" -> setStatusAttend(statusView)
                            "NONE" -> setStatusNONE(statusView)
                            "TARDY" -> setStatusTARDY(statusView)
                        }

                    } else {
                        setStatusCustom(statusView, "데이터 없음")
                    }

                } else {
                    Log.d("TestLog 실패사유 : ", "${response.code()},")
                }
            }

            override fun onFailure(call: Call<MyPage>, t: Throwable) {
                Log.e("error", t.message.toString())
            }
        })
    }

    //출석
    private fun setStatusAttend(statusView: TextView) {
        statusView.text = "출석"
        statusView.setTextColor(getResources().getColor(R.color.blue_700))
    }

    //지각
    private fun setStatusTARDY(statusView: TextView) {
        statusView.text = "지각"
        statusView.setTextColor(getResources().getColor(R.color.red_700))
    }

    //기본 상태 - 공백
    private fun setStatusNONE(statusView: TextView) {
        statusView.text = "결석"
        statusView.setTextColor(getResources().getColor(R.color.black))
    }

    //데이터 없을 때 사용
    private fun setStatusCustom(statusView: TextView, text: String) {
        statusView.text = text
        statusView.setTextColor(getResources().getColor(R.color.black))
    }


}