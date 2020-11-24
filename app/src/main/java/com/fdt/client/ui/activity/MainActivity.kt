package com.fdt.client.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.fdt.client.R
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result: IntentResult = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)

        if(result == null ){
            super.onActivityResult(requestCode, resultCode, data)
        }

        //qrcode가 없다면
        if(result.contents == null){
            Toast.makeText(this,"취소", Toast.LENGTH_SHORT).show()
            return
        }
        //qrcode가 있다면
        Toast.makeText(this,"스캔 성공", Toast.LENGTH_SHORT).show()

    }
}