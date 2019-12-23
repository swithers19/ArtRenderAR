package com.example.arcanvas

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.ar.core.*
import com.google.ar.sceneform.ux.ArFragment

import java.io.IOException
import com.google.ar.core.AugmentedImageDatabase

import com.google.ar.core.TrackingState
import com.google.ar.core.AugmentedImage
import com.google.ar.sceneform.FrameTime
import android.os.Build
import androidx.annotation.RequiresApi

import android.net.Uri
import android.widget.Toast
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.core.Anchor
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.rendering.Renderable
import com.google.ar.sceneform.AnchorNode


import com.google.ar.sceneform.rendering.ViewRenderable



class MainActivity : AppCompatActivity() {
    //private lateinit var arFragment: ArFragment
    //private lateinit var arFragment: ImageArFragment
    //var shouldAddModel = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //arFragment = supportFragmentManager.findFragmentById(R.id.ar_fragment) as ImageArFragment

//        arFragment.arSceneView.scene.addOnUpdateListener {
//            this.onUpdateFrame(it)
//        }

    }

//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private fun placeObject(arFragment: ArFragment, anchor: Anchor, uri: Uri) {
//        ModelRenderable.builder()
//           .setSource(arFragment.context, uri)
//            .build()
//            .thenAccept { modelRenderable -> addNodeToScene(arFragment, anchor, modelRenderable) }
//            .exceptionally { throwable ->
//                Toast.makeText(arFragment.context, "Error:" + throwable.message, Toast.LENGTH_LONG)
//                    .show()
//                null
//            }
//    }
//
//    @RequiresApi(Build.VERSION_CODES.N)
//    private fun placeImage(arFragment: ArFragment, anchor: Anchor) {
//        ViewRenderable.builder()
//            .setView(arFragment.context, R.layout.image)
//            .build()
//            .thenAccept{ imageRenderable -> addNodeToScene(arFragment, anchor, imageRenderable)}
//            .exceptionally { throwable  ->
//                Toast.makeText(arFragment.context, "Error:" + throwable.message, Toast.LENGTH_LONG)
//                    .show()
//                null
//            }
//    }
//
//
//    @RequiresApi(api = Build.VERSION_CODES.N)
//    private fun onUpdateFrame(frameTime: FrameTime) {
//        val frame = arFragment.arSceneView.arFrame
//
//        val augmentedImages = frame.getUpdatedTrackables(AugmentedImage::class.java)
//        for (augmentedImage in augmentedImages) {
//            if (augmentedImage.trackingState == TrackingState.TRACKING) {
//                if (augmentedImage.name == "bernese" && shouldAddModel) {
//                    Toast.makeText(arFragment.context,"Seen Augmented Image", Toast.LENGTH_LONG).show()
//                    placeImage(arFragment, augmentedImage.createAnchor(augmentedImage.centerPose))
////                    placeObject(
////                        arFragment,
////                        augmentedImage.createAnchor(augmentedImage.centerPose),
////                        Uri.parse("canvas.sfb")
////                    )
//                    shouldAddModel = false
//                }
//            }
//        }
//    }
//
//
//
//
//    fun setupAugmentedImagesDb(config: Config, session: Session): Boolean {
//        val augmentedImageDatabase: AugmentedImageDatabase
//        val bitmap:Bitmap = loadAugmentedImage() ?: return false
//
//        augmentedImageDatabase = AugmentedImageDatabase(session)
//        augmentedImageDatabase.addImage("bernese", bitmap!!)
//        config.augmentedImageDatabase = augmentedImageDatabase
//        return true
//    }
//
//
//    private fun loadAugmentedImage(): Bitmap? {
//        try {
//            val inputStream = assets.open("bernese.jpg")
//            return  BitmapFactory.decodeStream(inputStream)
//        } catch(e: IOException) {
//            Log.d("imageLoad", "io exception while loading", e)
//        }
//        return null
//    }
//
//    private fun addNodeToScene(arFragment: ArFragment, anchor: Anchor, renderable: Renderable) {
//        val anchorNode = AnchorNode(anchor)
//        val node = TransformableNode(arFragment.transformationSystem)
//        node.renderable = renderable
//        node.setParent(anchorNode)
//        arFragment.arSceneView.scene.addChild(anchorNode)
//        node.select()
//    }

}
