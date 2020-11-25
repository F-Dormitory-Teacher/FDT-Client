package com.fdt.client.ui.fragment.main.notice

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fdt.client.R
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.response.Data
import com.fdt.client.entity.response.NoticeData
import com.fdt.client.ui.adapter.NoticeAdapter
import com.fdt.client.ui.dialog.NoticeDialogFragment
import kotlinx.android.synthetic.main.fragment_lost.*
import kotlinx.android.synthetic.main.fragment_notice.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoticeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val noticeAdapter = NoticeAdapter { position ->
            val bundle = Bundle()
            bundle.putString("idx", position.toString())

            val dialog = NoticeDialogFragment()
            dialog.arguments = bundle
            dialog.show(requireActivity().supportFragmentManager, "NoticeDialogFragment")
        }
        val response: Call<NoticeData> = NetRetrofit.getService()!!.getAllNoticeList()

        response.enqueue(object : Callback<NoticeData> {
            override fun onResponse(call: Call<NoticeData>, response: Response<NoticeData>) {
                if (response.code() == 200) {

                    for (i in response.body()!!.data.notices.size - 1 downTo 0) {
                        noticeAdapter.add(response.body()!!.data.notices[i])
                    }

                    notice_recycler_view.adapter = noticeAdapter
                    notice_recycler_view.layoutManager = LinearLayoutManager(requireContext())
                }
            }

            override fun onFailure(call: Call<NoticeData>, t: Throwable) {
                Log.e("error", t.message.toString())
            }

        })

        return inflater.inflate(R.layout.fragment_notice, container, false)
    }
}