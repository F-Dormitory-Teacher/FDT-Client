package com.fdt.client.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fdt.client.R
import com.fdt.client.entity.response.DetailRequest
import java.security.AccessController.getContext


class RequestAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val requestItems = ArrayList<DetailRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_request, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(requestItems[position], position)
        }
    }

    override fun getItemCount(): Int = requestItems.size

    fun add(data: DetailRequest) {
        requestItems.add(data)
        notifyDataSetChanged()
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title = itemView.findViewById<TextView>(R.id.item_request_title)
        private var place = itemView.findViewById<TextView>(R.id.item_request_place)
        private var name = itemView.findViewById<TextView>(R.id.item_request_user_name_tv)
        private var state = itemView.findViewById<TextView>(R.id.item_request_state)

        fun bind(item: DetailRequest, position: Int) {
            title.text = item.title
            place.text = "장소 : ${item.location}"
            name.text = "글쓴이 : ${item.userName}"
            if (item.status == "NONE") {
                state.text = "미완료"
                state.setTextColor(Color.parseColor("#F75742"))
            } else {
                state.text = "완료"
                state.setTextColor(Color.parseColor("#25822E"))
            }
        }
    }

}