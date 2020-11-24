package com.fdt.client.ui.fragment.main.lost

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.fdt.client.R
import com.fdt.client.data.local.SharedPref
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.response.ArticleData
import com.fdt.client.entity.response.Data
import com.fdt.client.ui.adapter.LostAdapter
import com.fdt.client.ui.adapter.RequestAdapter
import kotlinx.android.synthetic.main.fragment_lost.*
import kotlinx.android.synthetic.main.fragment_post_lost.*
import kotlinx.android.synthetic.main.fragment_request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val lostAdapter = LostAdapter { position ->
            Log.d("asdfasd",position.toString() +"ㅇㄴㅁㄹ")
            val bundle = Bundle()
            bundle.putString("idx", position.toString())
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.action_mainFragment_to_detailLostFragment, bundle)

        }

        val response: Call<Data> = NetRetrofit.getService()!!.getLostList()

        response.enqueue(object : Callback<Data> {
            override fun onResponse(call: Call<Data>, response: Response<Data>) {
                if (response.code() == 200) {

                    for (i in response.body()!!.lostProducts.size - 1 downTo 0) {
                        lostAdapter.add(response.body()!!.lostProducts[i])
                    }

                    lost_recycler_view.adapter = lostAdapter
                    lost_recycler_view.layoutManager = LinearLayoutManager(requireContext())
                }
            }

            override fun onFailure(call: Call<Data>, t: Throwable) {
                Log.e("error", t.message.toString())
            }

        })

        return inflater.inflate(R.layout.fragment_lost, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lost_add_btn.setOnClickListener {
            Navigation.findNavController(requireActivity(), R.id.fragment_container)
                .navigate(R.id.action_mainFragment_to_postLostFragment)
        }
    }
}