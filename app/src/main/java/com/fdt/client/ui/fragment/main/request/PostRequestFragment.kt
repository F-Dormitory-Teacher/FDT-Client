package com.fdt.client.ui.fragment.main.request

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.Navigation
import com.fdt.client.R
import com.fdt.client.data.local.SharedPref
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.Article
import com.fdt.client.entity.Lost
import com.fdt.client.entity.response.Data
import com.fdt.client.entity.response.ResponseFile
import com.fdt.client.ui.activity.MainActivity
import com.fdt.client.ui.util.FileUtil
import kotlinx.android.synthetic.main.fragment_post_lost.*
import kotlinx.android.synthetic.main.fragment_post_request.*
import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import splitties.toast.toast

class PostRequestFragment : Fragment() {

    lateinit var sharedPref: SharedPref
    lateinit var imageUri: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedPref = SharedPref(requireContext())
        return inflater.inflate(R.layout.fragment_post_request, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        post_request_card_view.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
            startActivityForResult(intent, 205)
        }

        post_request_complete_btn.setOnClickListener {
            var file: String? = null
            imageUri = FileUtil.uriToFile(
                (requireActivity() as MainActivity).returnUri(),
                requireContext()
            )

            val filePath: MultipartBody.Part = FileUtil.createMultiPart(imageUri)

            val uploadResponse: Call<Data> =
                NetRetrofit.getService()!!.uploadImage(sharedPref.getToken(true), filePath)

            uploadResponse.enqueue(object : Callback<Data> {
                override fun onResponse(
                    call: Call<Data>,
                    response: Response<Data>
                ) {
                    Log.d("code", response.code().toString())
                    if (response.code() == 200) {
                        file = response.body()?.data?.files.toString()
                        Log.d("file", file!!)

                        val article = Article(
                            post_request_title_et.text.toString(),
                            post_request_content_et.text.toString(),
                            post_request_place_et.text.toString(),
                            file.toString()
                        )

                        val response: Call<Void> =
                            NetRetrofit.getService()!!
                                .postArticle(sharedPref.getToken(true), article)

                        response.enqueue(object : Callback<Void> {
                            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                                if (response.code() == 200) {
                                    toast("신청 성공")
                                    Navigation.findNavController(requireView()).popBackStack()
                                } else toast("알 수 없는 오류 발생")
                            }

                            override fun onFailure(call: Call<Void>, t: Throwable) {
                                toast(t.message.toString())
                            }
                        })
                    }
                }

                override fun onFailure(call: Call<Data>, t: Throwable) {
                    Log.e("error", t.message.toString())
                }

            })
        }
    }
}