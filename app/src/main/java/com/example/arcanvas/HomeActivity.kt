package com.example.arcanvas

import android.content.Context
import android.os.Bundle
import android.widget.Button

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.provider.MediaStore

import android.widget.ImageView
import android.media.ExifInterface
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.example.arcanvas.databinding.ActivityImagePickerBinding
import com.example.arcanvas.utils.Utils


class ImagePickActivity : AppCompatActivity() {
    private val SELECT_PHOTO = 1
    private var imageRotation:Float = 0f
    private var uri:Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_picker)

<<<<<<< HEAD:app/src/main/java/com/example/arcanvas/ImagePickActivity.kt
=======
        //val binding = ActivityImagePickerBinding.inflate(layoutInflater)

        //TODO("Bind layout to activity")

>>>>>>> ad4baba... Restructured folder directory of Kotlin files + refactored image picker ftom activty to fragment. The activty now utilises navhots with the ArtPickerFragment as default:app/src/main/java/com/example/arcanvas/HomeActivity.kt
        //Handle select image
        val pickImage = findViewById<Button>(R.id.btn_select_image)
        pickImage.setOnClickListener{v->
            selectImageOnClick()
        }

        val btnNext = findViewById<Button>(R.id.btn_ar)
        btnNext.isEnabled = false
        btnNext.setOnClickListener { y->
            startArExperience()
            Log.i("btn display art", "clicked")
        }
    }

    private fun startArExperience(){
        val arIntent = Intent(this, MainActivity::class.java)
        arIntent.putExtra("ImageUri",uri)
        arIntent.putExtra("Rotation", imageRotation)
        startActivity(arIntent)
    }

    private fun selectImageOnClick(){
        val photoPicker = Intent(Intent.ACTION_PICK)
        photoPicker.setType("image/*")
        startActivityForResult(photoPicker, SELECT_PHOTO)
    }

    override fun onActivityResult(requestCode:Int, resultCode:Int, data:Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

<<<<<<< HEAD:app/src/main/java/com/example/arcanvas/ImagePickActivity.kt
=======
        //TODO("Move data processing and refactor set thumbnail")

>>>>>>> ad4baba... Restructured folder directory of Kotlin files + refactored image picker ftom activty to fragment. The activty now utilises navhots with the ArtPickerFragment as default:app/src/main/java/com/example/arcanvas/HomeActivity.kt
        if (requestCode == SELECT_PHOTO) {
            // Make sure the request was successful
            Log.i("PhotoIntent", "Select Intent successful")
            if (resultCode == RESULT_OK) {
                Log.i("PhotoIntent", "Got an image")
                uri = data?.data
                getOrientation(this, uri)
                setThumb(uri)
               findViewById<Button>(R.id.btn_ar).isEnabled = true

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getOrientation(context: Context, uri: Uri?) {
        val inputStream = context.contentResolver.openInputStream(uri)
        val exif = ExifInterface(inputStream)
        val exifInt = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        imageRotation = Utils.getRotationVector(exifInt)
    }


    fun setThumb(uri: Uri?){
        val image = findViewById<ImageView>(R.id.img_thumbnail)
        val imageStream = contentResolver.openInputStream(uri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        image.rotation = imageRotation
        image?.setImageBitmap(selectedImage)
    }
}