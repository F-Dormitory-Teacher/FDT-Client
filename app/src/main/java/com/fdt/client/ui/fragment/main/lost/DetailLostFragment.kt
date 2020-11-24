package com.fdt.client.ui.fragment.main.lost

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.fdt.client.R
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.response.LostData
import kotlinx.android.synthetic.main.fragment_detail_lost.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailLostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        return inflater.inflate(R.layout.fragment_detail_lost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val response: Call<LostData> =
            NetRetrofit.getService()!!.getDetailLost(requireArguments().getString("idx")!!.toInt())

        response.enqueue(object : Callback<LostData> {
            override fun onResponse(call: Call<LostData>, response: Response<LostData>) {
                if (response.code() == 200) {
                    detail_lost_title_tv.text = response.body()?.lostProduct?.title
                    detail_lost_date_tv.text =
                        "날짜 : ${response.body()?.lostProduct?.createdAt?.substring(0, 10)}"
                    detail_lost_place_tv.text = "장소 : ${response.body()?.lostProduct?.location}"
                    detail_lost_state_tv.text =
                        "진행 상태 : ${response.body()?.lostProduct?.lostStatus}"
                    detail_lost_writer_tv.text = "글쓴이 : ${response.body()?.lostProduct?.userName}"

                    Log.d("image", response.body()?.lostProduct?.imageUrl.toString())
                    Glide.with(requireContext())
                        .load("http://192.168.43.111:8080/public/" + response.body()?.lostProduct?.imageUrl.toString())
                        .into(detail_lost_image)
                }
            }

            override fun onFailure(call: Call<LostData>, t: Throwable) {
                Log.e("errorFFF", t.message.toString())
            }

        })

        detail_lost_back_image.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).popBackStack()
        }
    }
}