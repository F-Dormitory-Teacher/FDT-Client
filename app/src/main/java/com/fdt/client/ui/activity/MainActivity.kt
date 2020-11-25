package com.fdt.client.ui.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.fdt.client.R
import com.fdt.client.data.local.SharedPref
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.response.Token
import com.fdt.client.ui.dialog.NoticeDialogFragment
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.fragment_post_lost.*
import kotlinx.android.synthetic.main.fragment_post_request.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast

class MainActivity : AppCompatActivity() {
    lateinit var sharedPref: SharedPref
    lateinit var selectedImageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = SharedPref(applicationContext)

        setContentView(R.layout.activity_main)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("requestCode", requestCode.toString())
        if (requestCode == 131277) {
            selectedImageUri = data?.data!!
            post_request_image.setImageURI(selectedImageUri)
            ic_picture_image.visibility = View.GONE
        } else if (requestCode == 65641) {
            selectedImageUri = data?.data!!
            post_lost_image.setImageURI(selectedImageUri)
            ic_lost_picture.visibility = View.GONE
        } else {
            val result: IntentResult =
                IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

            try {
                //data를 jsonobject로 변경
                val obj = JSONObject(result.contents)

                //qrcode가 있다면
                if (result.contents != null && obj.getString("type") == "NIGHT" || obj.getString("type") == "MORNING") {
                    val response: Call<Void> =
                        NetRetrofit.getService()!!.postAttend(sharedPref.getToken(true))

                    response.enqueue(object : Callback<Void> {
                        override fun onResponse(call: Call<Void>, response: Response<Void>) {
                            if (response.code() == 200) {
                                toast("출석 완료")

                                val bundle = Bundle()
                                bundle.putString("type", obj.getString("type"))

                                val dialog = NoticeDialogFragment()
                                dialog.arguments = bundle
                                dialog.show(this@MainActivity.supportFragmentManager, "NoticeDialogFragment")


                            } else if (response.code() == 409) {
                                toast("이미 출석 체크한 유저입니다.")
                            } else if (response.code() == 403) {
                                toast("출석 체크 가능한 시간이 아닙니다.")
                            }
                        }

                        override fun onFailure(call: Call<Void>, t: Throwable) {
                            Log.e("error", t.message.toString())
                        }
                    })
                    return
                } else Toast.makeText(this, "취소", Toast.LENGTH_SHORT).show()

            } catch (e: JSONException) {
                toast("유효하지 않은 qr code입니다.")
            }
        }

    }

    fun returnUri(): Uri {
        return selectedImageUri
    }


}