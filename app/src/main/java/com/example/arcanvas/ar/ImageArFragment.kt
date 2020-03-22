package com.example.arcanvas.ar


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProviders
import com.example.arcanvas.R
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.PinchGesture
import com.google.ar.sceneform.ux.TransformableNode
import java.io.IOException

class ImageArFragment:ArFragment() {
    // to keep tracking which trackable that we have created AnchorNode with it or not.
    private val trackableMap = mutableMapOf<String, AnchorNode>()
    private var artRotation:Float = 0f
    private lateinit var imageUri:Uri

    private lateinit var viewModel:ImageArViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        imageUri = activity!!.intent.extras["ImageUri"] as Uri
        artRotation = activity!!.intent.extras["Rotation"] as Float

        //Initialisation of viewmodel
        val viewModelFactory = ImageArViewModelFactory(context!!, imageUri, artRotation, this.transformationSystem)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ImageArViewModel::class.java)

        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
        arSceneView.planeRenderer.isEnabled = false
        // add frame update listener
        arSceneView.scene.addOnUpdateListener(::onUpdateFrame)

        return view
    }

    override fun getSessionConfiguration(session: Session): Config {
        val config = super.getSessionConfiguration(session)
        config.focusMode = Config.FocusMode.AUTO // make camera auto focus
        Log.i("getSessionConfig", "Session Called")

        if (viewModel.setupAugmentedImagesDb(config, session)) {
            Log.d("SetupAugImgDb", "Success")
        } else {
            Log.e("SetupAugImgDb", "Faliure setting up db")
        }
        return config
    }

    private fun onUpdateFrame(frameTime: FrameTime?){
        // we will add anchor here later
        val frame = arSceneView.arFrame
        Log.i("OnUpdateFrame", "Another Frame")

        // If there is no frame or ARCore is not tracking yet, just return.
        if (frame == null || frame.camera.trackingState != TrackingState.TRACKING) {
            return
        }

        val anchor  = viewModel.getDetectedImages(frame)
        anchor?.let { arSceneView.scene.addChild(anchor) }
        // get detected AugmentedImages

    }



}