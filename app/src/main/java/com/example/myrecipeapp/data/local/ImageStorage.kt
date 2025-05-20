package com.example.myrecipeapp.data.local

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import com.google.gson.internal.bind.TypeAdapters.URL
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageStorage {

    fun getImageDir(context: Context): File {
        val dir = File(context.filesDir, "recipes_images")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun resizeBitmap(bitmap: Bitmap, maxWidth: Int, maxHeight: Int): Bitmap {
        return Bitmap.createScaledBitmap(bitmap, maxWidth, maxHeight, true)
    }

    suspend fun downloadAndSaveImage(
        context: Context,
        url: String,
        fileName: String
    ): String? = withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)

            val resized = resizeBitmap(bitmap, 640, 480)
            val file = File(getImageDir(context), "$fileName.jpg")

            val out = FileOutputStream(file)
            resized.compress(Bitmap.CompressFormat.JPEG, 85, out)
            out.flush()
            out.close()

            file.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
