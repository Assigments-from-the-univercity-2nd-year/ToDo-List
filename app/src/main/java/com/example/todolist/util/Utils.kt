package com.example.todolist.util

import android.content.res.Resources
import android.graphics.Bitmap
import com.example.todolist.domain.util.Resource
import java.io.ByteArrayOutputStream
import java.io.IOException

val <T> T.exhaustive: T
    get() = this

fun Bitmap.toByteArray() : ByteArray {
    ByteArrayOutputStream().use { stream ->
        if (!this.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
            throw IOException("Couldn't convert Bitmap to ByteArray.")
        }
        return stream.toByteArray()
    }
}

val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
