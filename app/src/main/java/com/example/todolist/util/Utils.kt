package com.example.todolist.util

import android.graphics.Bitmap
import com.example.todolist.domain.util.Resource
import java.io.ByteArrayOutputStream
import java.io.IOException

val <T> T.exhaustive: T
    get() = this

fun Bitmap.toByteArray() : Resource<ByteArray, Throwable> {
    ByteArrayOutputStream().use { stream ->
        if (!this.compress(Bitmap.CompressFormat.JPEG, 100, stream)) {
            return Resource.Failure(IOException("Couldn't convert Bitmap to ByteArray."))
        }
        return Resource.Success(stream.toByteArray())
    }
}