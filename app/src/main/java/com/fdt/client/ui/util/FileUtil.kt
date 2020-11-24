package com.fdt.client.ui.util

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


object FileUtil {
    @RequiresApi(api = Build.VERSION_CODES.O)
    fun uriToFile(uri: Uri?, context: Context): String {
        val cursor: Cursor = context.contentResolver.query(uri!!, null, null, null)!!
        cursor.moveToNext()
        val path: String = cursor.getString(cursor.getColumnIndex("_data"))
        cursor.close()
        return path
    }

    fun createMultiPart(filePath: String?): MultipartBody.Part {
        val file = File(filePath)
        val requestBody: RequestBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("files", file.name, requestBody)
    }
}