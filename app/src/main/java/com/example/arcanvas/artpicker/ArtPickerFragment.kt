package com.example.arcanvas.artpicker

import android.annotation.SuppressLint
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
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.arcanvas.ArExperienceActivity
import com.example.arcanvas.R
import com.example.arcanvas.databinding.FragmentArtPickerBinding
import com.example.arcanvas.utils.Utils

class ArtPickerFragment : Fragment() {
    private val SELPHOTO = 1

    private lateinit var viewModel: ArtPickerViewModel
    private lateinit var binding:FragmentArtPickerBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_art_picker, container, false)

        // Get the viewmodel and bind to view
        viewModel = ViewModelProviders.of(this).get(ArtPickerViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        binding.btnSelectImage.setOnClickListener {
            selectImageOnClick()
        }

        viewModel.startAct.observe(viewLifecycleOwner, Observer { isStarted ->
            val lnchIntent:Intent
            view?.let { v ->
                lnchIntent = viewModel.setArIntent(v)
                startActivity(lnchIntent)
            }
        })
        Log.i("ArtPickerFragment","Completed set-up and binding")
        return binding.root

    }


    private fun selectImageOnClick(){
        val photoPicker = Intent(Intent.ACTION_PICK)
        photoPicker.setType("image/*")
        startActivityForResult(photoPicker, SELPHOTO)
    }


    override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == SELPHOTO) {
            // Make sure the request was successful
            if (resultCode == AppCompatActivity.RESULT_OK) {
                Log.i("SelectImageOnClick", "Image URI request successful")
                context?.let { viewModel.getOrientation(it, data?.data) }
            }
        }
    }

}