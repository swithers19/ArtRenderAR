package com.example.arcanvas.ar

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.arcanvas.R
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.TransformableNode
import com.google.ar.sceneform.ux.TransformationSystem
import java.io.IOException

class ImageArViewModel(context: Context, imageUri:Uri, imageRotation:Float, transSystem: TransformationSystem) : ViewModel(){
    private val context:Context = context
    private val trackableMap = mutableMapOf<String, AnchorNode>()
    private var uri:Uri = imageUri
    private var imageRotation:Float = imageRotation
    private var transformationSystem = transSystem

    fun getDetectedImages(frame: Frame): AnchorNode? {
        var anchor:AnchorNode? = null
        frame.getUpdatedTrackables(AugmentedImage::class.java).forEach { image ->
            when (image.trackingState) {
                // if it is in tracking state and we didn't add AnchorNode, then add one
                TrackingState.TRACKING -> if (!trackableMap.contains(image.name)) {
                    anchor = createAnchorNode(image, transformationSystem)
                }
                TrackingState.STOPPED -> {
                    trackableMap.remove(image.name)
                }
            }
        }
        return anchor
    }

    fun createAnchorNode(img:AugmentedImage, transSystem: TransformationSystem): AnchorNode {
        var anchorNode = AnchorNode()

        // make anchor in the center of the images
        anchorNode.anchor = img.createAnchor(img.centerPose)

        var arWidth = img.extentX // extentX is estimated width
        var scaledWidth = arWidth/1f

        val transNode = TransformableNode(transSystem)
        transNode.scaleController.minScale = scaledWidth
        transNode.scaleController.maxScale = 15*scaledWidth
        transNode.scaleController.isEnabled = true
        transNode.rotationController.isEnabled = false

        transNode.setParent(anchorNode)
        transNode.localRotation = Quaternion(Vector3(1f, 0f, 0f), -90f)

        ViewRenderable.builder().setView(this.context, altSetImageView(uri))
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.CENTER)
            .setHorizontalAlignment(ViewRenderable.HorizontalAlignment.CENTER)
            .build()
            .thenAccept { renderable ->
                transNode.renderable = renderable
            }

        updateTrackables(img, anchorNode)
        return anchorNode
    }

    private fun updateTrackables(img: AugmentedImage, anchor:AnchorNode) {
        trackableMap[img.name] = anchor
    }

    private fun altSetImageView(artImage: Uri?):View {
        val imageView = ImageView(context)
        val imageStream = context.contentResolver?.openInputStream(artImage)
        val selectedImage = BitmapFactory.decodeStream(imageStream)

        //Set view features
        imageView.rotation = imageRotation
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        imageView.adjustViewBounds = true

        imageView.setImageBitmap(selectedImage)
        return imageView
    }

    fun setupAugmentedImagesDb(config: Config, session: Session): Boolean {
        val markerFile = "bernese.jpg"
        val bitmap: Bitmap = loadAugmentedImage(markerFile) ?: return false

        val augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("bernese", bitmap!!)
        config.augmentedImageDatabase = augmentedImageDatabase
        return true
    }

    private fun loadAugmentedImage(markerFile:String): Bitmap? {
        try {
            val inputStream = context.assets?.open(markerFile)
            return  BitmapFactory.decodeStream(inputStream)
        } catch(e: IOException) {
            Log.d("imageLoad", "io exception while loading", e)
        }
        return null
    }
}