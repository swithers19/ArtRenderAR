package com.example.arcanvas.ar

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.ar.sceneform.ux.TransformationSystem

class ImageArViewModelFactory(private val context: Context,
                              private val imageUri: Uri,
                              private val imageRotation:Float,
                              private val transformationSystem: TransformationSystem)
    : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageArViewModel::class.java)) {
            return ImageArViewModel(context, imageUri, imageRotation, transformationSystem) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}