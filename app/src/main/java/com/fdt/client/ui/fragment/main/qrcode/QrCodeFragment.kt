package com.fdt.client.ui.fragment.main.qrcode

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.fonts.FontFamily
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.fdt.client.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.fragment_qr_code.*
import org.json.JSONException
import org.json.JSONObject

class QrCodeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle? ): View? {
        val v =  inflater.inflate(R.layout.fragment_qr_code, container, false)
        val qrCardView = v.findViewById<CardView>(R.id.qr_code_card_view)
        val detailText = v.findViewById<TextView>(R.id.qr_code_detail_tv)
        val spannable = SpannableString(detailText.text.toString())
        spannable.setSpan(ForegroundColorSpan(Color.parseColor("#25822E")),0,7,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(StyleSpan(Typeface.BOLD),0,7,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(RelativeSizeSpan(1.1f),0,7,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        detailText.text = spannable

        val qrScan = IntentIntegrator(requireActivity())

        qrCardView.setOnClickListener {
            //scan option
            qrScan.setPrompt("Scanning..")
            //
            qrScan.initiateScan()
        }
        return v
    }
}