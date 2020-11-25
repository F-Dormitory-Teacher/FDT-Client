package com.fdt.client.ui.dialog

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.fdt.client.R
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.response.DetailNotice
import com.fdt.client.entity.response.DetailNoticeData
import com.fdt.client.entity.response.NoticeData
import kotlinx.android.synthetic.main.fragment_notice_dialog.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class NoticeDialogFragment : DialogFragment() {

    var mValue: String? = null
    var mType: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mArgs = arguments
        mValue = mArgs!!.getString("idx")
        mType = mArgs!!.getString("type")

        Log.d("arg", mType.toString())

        return inflater.inflate(R.layout.fragment_notice_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (mValue != null && mType == null) {
            val response: Call<DetailNoticeData> =
                NetRetrofit.getService()!!.getDetailNotice(mValue!!.toInt())

            response.enqueue(object : Callback<DetailNoticeData> {
                override fun onResponse(
                    call: Call<DetailNoticeData>,
                    response: Response<DetailNoticeData>
                ) {
                    if (response.code() == 200) {
                        notice_dialog_title_tv.text = response.body()!!.data.notice.title
                        notice_dialog_date_tv.text =
                            response.body()!!.data.notice.date.substring(0, 10)
                        notice_dialog_content_tv.text = response.body()!!.data.notice.content
                    }
                }

                override fun onFailure(call: Call<DetailNoticeData>, t: Throwable) {
                    Log.e("errorDialog", t.message.toString())
                }
            })
        } else {
            Log.d("daily", mType + mValue)
            var sformat = SimpleDateFormat("yyyy-MM-dd")
            var date = sformat.format(Calendar.getInstance().time)

            val response: Call<NoticeData> =
                NetRetrofit.getService()!!.getDailyNotice(date, mType.toString())

            response.enqueue(object : Callback<NoticeData> {
                override fun onResponse(call: Call<NoticeData>, response: Response<NoticeData>) {
                    if (response.code() == 200) {
                        if (response.body()!!.data.notices.isEmpty()) {
                            notice_dialog_title_tv.text = "."
                            notice_dialog_date_tv.text = "."
                            notice_dialog_content_tv.text = "오늘 안내사항은 없습니다."
                        } else {
                            notice_dialog_title_tv.text = response.body()!!.data.notices[0].title
                            notice_dialog_date_tv.text =
                                response.body()!!.data.notices[0].date.substring(0, 10)
                            notice_dialog_content_tv.text =
                                response.body()!!.data.notices[0].content
                        }

                    }
                }

                override fun onFailure(call: Call<NoticeData>, t: Throwable) {
                    Log.e("error Daily", t.message.toString())
                }

            })

        }
    }

}