package com.fdt.client.ui.fragment.main.request

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
import com.fdt.client.entity.response.DetailRequest
import com.fdt.client.ui.adapter.RequestAdapter
import kotlinx.android.synthetic.main.fragment_request.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestFragment : Fragment() {

//    private var list : ArrayList<DetailRequest> =  ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val requestAdapter = RequestAdapter()
        val response: Call<ArticleData> = NetRetrofit.getService()!!.getArticleList()

        response.enqueue(object: Callback<ArticleData>{
            override fun onResponse(call: Call<ArticleData>, response: Response<ArticleData>) {
                if(response.code() == 200){
                    for(i in response.body()?.data!!.articles.size -1 downTo 0){
                        requestAdapter.add(response.body()?.data!!.articles[i])
                    }

                    request_recycler_view.adapter = requestAdapter
                    request_recycler_view.layoutManager = LinearLayoutManager(requireContext())

                }
            }

            override fun onFailure(call: Call<ArticleData>, t: Throwable) {
                Log.e("dsafsa",t.message.toString())
            }

        })

        return inflater.inflate(R.layout.fragment_request, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        request_floating_btn.setOnClickListener {
            Navigation.findNavController(requireActivity(),R.id.fragment_container).navigate(R.id.action_mainFragment_to_postRequestFragment)
        }
    }

}