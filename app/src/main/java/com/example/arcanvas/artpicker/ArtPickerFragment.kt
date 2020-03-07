package com.example.arcanvas.artpicker

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.arcanvas.ArExperienceActivity
import com.example.arcanvas.R
import com.example.arcanvas.databinding.FragmentArtPickerBinding
import com.example.arcanvas.utils.Utils

class ArtPickerFragment : Fragment() {
    private val SELECT_PHOTO = 1
    private var imageRotation:Float = 0f
    private var uri: Uri? = null

    private lateinit var binding:FragmentArtPickerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_art_picker, container, false)

        binding.btnAr.isEnabled = false
        binding.btnSelectImage.setOnClickListener {
            selectImageOnClick()
        }

            binding.btnAr.setOnClickListener {
                startArExperience()
                Log.i("btn display art", "clicked")
            }

        Log.i("ArtPickerFragment","Completed binding")
        return binding.root

    }

    private fun startArExperience(){
        val arIntent = Intent(activity, ArExperienceActivity::class.java)
        arIntent.putExtra("ImageUri",uri)
        arIntent.putExtra("Rotation", imageRotation)
        startActivity(arIntent)
    }

    private fun selectImageOnClick(){
        val photoPicker = Intent(Intent.ACTION_PICK)
        photoPicker.setType("image/*")
        startActivityForResult(photoPicker, SELECT_PHOTO)
    }


    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        //TODO("Move data processing and refactor set thumbnail")

        if (requestCode == SELECT_PHOTO) {
            // Make sure the request was successful
            Log.i("PhotoIntent", "Select Intent successful")
            if (resultCode == AppCompatActivity.RESULT_OK) {
                Log.i("PhotoIntent", "Got an image")
                uri = data?.data
                getOrientation(context, uri)
                setThumb(uri)
                binding.btnAr.isEnabled = true

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun getOrientation(context: Context?, uri: Uri?) {
        val inputStream = context?.contentResolver?.openInputStream(uri)
        val exif = ExifInterface(inputStream)
        val exifInt = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        imageRotation = Utils.getRotationVector(exifInt)
    }


    fun setThumb(uri: Uri?){
        val image = binding.imgThumbnail
        val imageStream = activity?.contentResolver?.openInputStream(uri)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        image.scaleType = ImageView.ScaleType.CENTER_CROP
        image.rotation = imageRotation
        image?.setImageBitmap(selectedImage)
    }


}