package com.example.arcanvas

import android.content.Context
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arcanvas.utils.Utils

class ImagePickViewModel : ViewModel(){
    private val SELECT_PHOTO = 1

    private val _imageUri = MutableLiveData<Uri>()
    val imageUri: LiveData<Uri>
        get() = _imageUri

    private val _imageRotation = MutableLiveData<Float>()
    val imageRotation: LiveData<Float>
            get() = _imageRotation

    private val _imageSet = MutableLiveData<Boolean>()
    val imageSet: LiveData<Boolean>
        get()= _imageSet


    init {

    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getOrientation(context: Context, uri: Uri?) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val exif = ExifInterface(inputStream)
        val exifInt = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        _imageRotation.value = getRotationVector(exifInt)
    }

    private fun getRotationVector(exifTag:Int):Float{
        var x = 0f
        when (exifTag) {
            in 1..2-> x = 0f
            in 3..4-> x = 180f
            in 5..6-> x = 90f
            in 7..8-> x = 270f
        }
        return x
    }
}