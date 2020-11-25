package com.fdt.client.ui.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fdt.client.R
import com.fdt.client.data.remote.NetRetrofit
import com.fdt.client.entity.response.DetailLost
import kotlinx.android.synthetic.main.item_lost.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.AccessController.getContext

class LostAdapter(private val itemClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val lostItems = ArrayList<DetailLost>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lost, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(lostItems[position], itemClick, lostItems[position].idx)
        }
    }

    override fun getItemCount(): Int = lostItems.size

    fun add(data: DetailLost) {
        lostItems.add(data)
        notifyDataSetChanged()
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title = itemView.findViewById<TextView>(R.id.item_lost_title)
        private var date = itemView.findViewById<TextView>(R.id.item_lost_date)
        private var place = itemView.findViewById<TextView>(R.id.item_lost_place)
        private var state = itemView.findViewById<TextView>(R.id.item_lost_state)

        fun bind(item: DetailLost, itemClick: (Int) -> Unit, position: Int) {
            title.text = item.title
            place.text = "장소 : ${item.location}"
            date.text = "날짜 : ${item.createdAt.substring(0, 10)}"
            if (item.lostStatus == "NONE") {
                state.text = "보관"
                state.setTextColor(Color.parseColor("#F75742"))
            } else {
                state.text = "미보관"
                state.setTextColor(Color.parseColor("#25822E"))
            }

            itemView.setOnClickListener { itemClick(position) }
        }
    }

}