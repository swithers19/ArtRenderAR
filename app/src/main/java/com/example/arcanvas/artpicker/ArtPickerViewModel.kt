package com.example.arcanvas.artpicker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arcanvas.ArExperienceActivity

class ArtPickerViewModel() : ViewModel(){
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

    private val _startAct = MutableLiveData<Boolean>()
    val startAct: LiveData<Boolean>
        get() =_startAct

    private val _imageThumbnail = MutableLiveData<Drawable>()
    val imageThumbnail: LiveData<Drawable>
        get() = _imageThumbnail

    init {
        _imageSet.value = false
    }

    fun startNewActivity() {
        _startAct.value = true
        _imageSet.value = false
    }

    fun setArIntent(view: View):Intent {
        val context:Context = view.context
        val arIntent = Intent(context, ArExperienceActivity::class.java)
        arIntent.putExtra("ImageUri",_imageUri.value)
        arIntent.putExtra("Rotation", _imageRotation.value)
        return arIntent
    }

    @SuppressLint("NewApi")
    fun getOrientation(context: Context, uri: Uri?) {
        _imageUri.value = uri
        val inputStream = context.contentResolver.openInputStream(imageUri.value)
        val exif = ExifInterface(inputStream)
        val exifInt = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        _imageRotation.value = getRotationVector(exifInt)
        setThumbnail(context)
        _imageSet.value = true
    }

    fun setThumbnail(context: Context) {
        val imageStream = context?.contentResolver?.openInputStream(imageUri.value)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        val drawableThumb:Drawable = BitmapDrawable(context.resources, selectedImage)
        _imageThumbnail.value = drawableThumb
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