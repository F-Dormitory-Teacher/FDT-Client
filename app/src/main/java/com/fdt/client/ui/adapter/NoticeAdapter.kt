package com.fdt.client.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fdt.client.R
import com.fdt.client.entity.response.DetailNotice

class NoticeAdapter(private val itemClick: (Int) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val noticeItems = ArrayList<DetailNotice>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notice, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) {
            holder.bind(noticeItems[position], itemClick, noticeItems[position].idx)
        }
    }

    override fun getItemCount(): Int = noticeItems.size

    fun add(data: DetailNotice) {
        noticeItems.add(data)
        notifyDataSetChanged()
    }

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var title = itemView.findViewById<TextView>(R.id.item_notice_title)
        private var date = itemView.findViewById<TextView>(R.id.item_notice_date)
        private var type = itemView.findViewById<TextView>(R.id.item_notice_type)

        fun bind(item: DetailNotice, itemClick: (Int) -> Unit, position: Int) {
            title.text = item.title
            date.text = "날짜 : ${item.createdAt.substring(0, 10)}"
            if (item.type == "MORINING") {
                type.text = "아침"
                type.setTextColor(Color.parseColor("#25822E"))
            } else {
                type.text = "저녁"
                type.setTextColor(Color.parseColor("#25822E"))
            }

            itemView.setOnClickListener { itemClick(position) }
        }
    }

}