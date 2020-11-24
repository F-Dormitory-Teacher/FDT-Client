package com.fdt.client.ui.fragment.main.lost

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.fdt.client.R
import com.fdt.client.data.local.SharedPref
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.Lost
import com.fdt.client.ui.activity.MainActivity
import com.fdt.client.ui.util.FileUtil
import kotlinx.android.synthetic.main.fragment_post_lost.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart
import splitties.toast.toast


class PostLostFragment : Fragment() {

    lateinit var sharedPref: SharedPref
    lateinit var imageUri: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = SharedPref(requireContext())

        return inflater.inflate(R.layout.fragment_post_lost, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        post_lost_card_view.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, 205)
        }

        post_lost_complete_btn.setOnClickListener {
            Log.d("inageUri", FileUtil.uriToFile((requireActivity() as MainActivity).returnUri(),requireContext()) + "df")
            imageUri = FileUtil.uriToFile((requireActivity() as MainActivity).returnUri(),requireContext())

            val lost = Lost(post_lost_title_et.text.toString(), post_lost_place_et.text.toString(), post_lost_content_et.text.toString())
            val file: MultipartBody.Part = FileUtil.createMultiPart(imageUri)

            val response : Call<Void> = NetRetrofit.getService()!!.postLostProduct(sharedPref.getToken(true),lost)

            response.enqueue(object: Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.code() == 200 ){
                        toast("신청 성공")
                    }else toast("알 수 없는 오류 발생")
                }
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    toast(t.message.toString())
                }
            })
        }
    }
}