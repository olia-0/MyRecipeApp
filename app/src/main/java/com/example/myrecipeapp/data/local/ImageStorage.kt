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



//object ImageStorage {
//    private fun getImageDir(context: Context): File {
//        return File(context.filesDir, "images").apply {
//            if (!exists()) mkdirs()
//        }
//    }
//
//    fun getSavedImagesTotalSizeMB(context: Context): Int {
//        val dir = getImageDir(context)
//        val totalBytes = dir.listFiles()?.sumOf { it.length() } ?: 0L
//        return (totalBytes / (1024 * 1024)).toInt()
//    }
//}

object ImageStorage1 {

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

object ImageStorage {

    private const val MAX_WIDTH = 640
    private const val MAX_HEIGHT = 480
    private const val JPEG_QUALITY = 80

    fun getImageDir(context: Context): File {
        val dir = File(context.filesDir, "recipes_images")
        if (!dir.exists()) dir.mkdirs()
        return dir
    }

    fun resizeBitmap(bitmap: Bitmap, maxWidth: Int = MAX_WIDTH, maxHeight: Int = MAX_HEIGHT): Bitmap {
        val ratio = minOf(maxWidth.toFloat() / bitmap.width, maxHeight.toFloat() / bitmap.height)
        val width = (bitmap.width * ratio).toInt()
        val height = (bitmap.height * ratio).toInt()
        return Bitmap.createScaledBitmap(bitmap, width, height, true)
    }

    suspend fun downloadAndSaveImage(
        context: Context,
        url: String,
        fileName: String
    ): Pair<String, Long>? = withContext(Dispatchers.IO) {
        try {
            val connection = URL(url).openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            val bitmap = BitmapFactory.decodeStream(input)

            val resized = resizeBitmap(bitmap)
            val file = File(getImageDir(context), "$fileName.jpg")

            FileOutputStream(file).use { out ->
                resized.compress(Bitmap.CompressFormat.JPEG, JPEG_QUALITY, out)
            }
            val size = file.length()
            file.absolutePath to size
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}

