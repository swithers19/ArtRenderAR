package com.example.arcanvas.ar


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Layout
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
<<<<<<< HEAD:app/src/main/java/com/example/arcanvas/ImageArFragment.kt
import androidx.core.graphics.get
import androidx.core.graphics.scale
=======
import com.example.arcanvas.R
>>>>>>> c7385b4... Restructured folder directory of Kotlin files + refactored image picker ftom activty to fragment. The activty now utilises navhots with the ArtPickerFragment as default:app/src/main/java/com/example/arcanvas/ar/ImageArFragment.kt
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.FrameTime
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ViewRenderable
import com.google.ar.sceneform.ux.ArFragment
import java.io.IOException

class ImageArFragment:ArFragment() {
    // to keep tracking which trackable that we have created AnchorNode with it or not.
    private val trackableMap = mutableMapOf<String, AnchorNode>()
    private var artRotation:Float = 0f
    private var imageUri:Uri? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
<<<<<<< HEAD
<<<<<<< HEAD:app/src/main/java/com/example/arcanvas/ImageArFragment.kt
=======
        //TODO("Bind AR Fragment to ImageArFragment")
>>>>>>> ad4baba... Restructured folder directory of Kotlin files + refactored image picker ftom activty to fragment. The activty now utilises navhots with the ArtPickerFragment as default:app/src/main/java/com/example/arcanvas/ar/ImageArFragment.kt
=======
>>>>>>> 23c6b62... Refactor the ArExperience activity to handle navhost navigation rather than hardcoding in ARFragment
        val view = super.onCreateView(inflater, container, savedInstanceState)
        imageUri = activity!!.intent.extras["ImageUri"] as Uri
        artRotation = activity!!.intent.extras["Rotation"] as Float
        Log.d("onCreateArFragment", imageUri.toString())

        // Turn off the plane discovery since we're only looking for ArImages
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
        arSceneView.planeRenderer.isEnabled = false

        // add frame update listener
        arSceneView.scene.addOnUpdateListener(::onUpdateFrame)

        return view
    }

    override fun getSessionConfiguration(session: Session): Config {
        val markerFile = "bernese.jpg"
        val config = super.getSessionConfiguration(session)
        config.focusMode = Config.FocusMode.AUTO // make camera auto focus


        if (setupAugmentedImagesDb(config, session, markerFile)) {
            Log.d("SetupAugImgDb", "Success")
        } else {
            Log.e("SetupAugImgDb", "Faliure setting up db")
        }

        return config
    }

    fun setupAugmentedImagesDb(config: Config, session: Session, markerFile:String): Boolean {
        val augmentedImageDatabase: AugmentedImageDatabase
        val bitmap: Bitmap = loadAugmentedImage(markerFile) ?: return false

        augmentedImageDatabase = AugmentedImageDatabase(session)
        augmentedImageDatabase.addImage("bernese", bitmap!!)
        config.augmentedImageDatabase = augmentedImageDatabase
        return true
    }


    private fun loadAugmentedImage(markerFile:String): Bitmap? {
        try {
            val inputStream = activity?.assets?.open(markerFile)
            return  BitmapFactory.decodeStream(inputStream)
        } catch(e: IOException) {
            Log.d("imageLoad", "io exception while loading", e)
        }
        return null
    }

    private fun onUpdateFrame(frameTime: FrameTime?){
        // we will add anchor here later
        val frame = arSceneView.arFrame

        // If there is no frame or ARCore is not tracking yet, just return.
        if (frame == null || frame.camera.trackingState != TrackingState.TRACKING) {
            return
        }

        // get detected AugmentedImages
        // there are three types of trackables, they are AugmentedImage, Plane and Point.
        frame.getUpdatedTrackables(AugmentedImage::class.java).forEach { image ->
            when (image.trackingState) {
                // if it is in tracking state and we didn't add AnchorNode, then add one
                TrackingState.TRACKING -> if (!trackableMap.contains(image.name)) {
                    createAnchorNode(image)
                }
                TrackingState.STOPPED -> {
                    // remove it
                    trackableMap.remove(image.name)
                }
                else -> {
                }
            }
        }

    }
    private fun createAnchorNode(image: AugmentedImage) {
        val an = createArt(image)
        if (an != null){
            // add the AnchorNode to the scene
            arSceneView.scene.addChild(an)
            // keep the node
            trackableMap[image.name] = an
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun createArt(image: AugmentedImage): AnchorNode {
        val anchorNode = AnchorNode()

        // make anchor in the center of the images
        anchorNode.anchor = image.createAnchor(image.centerPose)
        anchorNode.localPosition

        Log.d("CreateArt", image.centerPose.toString())

        var arWidth = image.extentX // extentX is estimated width
        var arHeight = image.extentZ // extentZ is estimated height
        Log.d("ImageScale", arWidth.toString())



        var scaledWidth = arWidth/1f
        var scaledHeight = arHeight/0.66f

        // add view
        val viewA = Node()
        viewA.setParent(anchorNode)
        // scale to the right size
        viewA.localRotation = Quaternion(Vector3(1f, 0f, 0f), -90f)
        viewA.localScale = Vector3(5*scaledWidth, 5*scaledHeight, 5*scaledWidth)

        // load the model


        ViewRenderable.builder().setView(this.context, setImageView(imageUri))
            .setVerticalAlignment(ViewRenderable.VerticalAlignment.CENTER)
            .setHorizontalAlignment(ViewRenderable.HorizontalAlignment.CENTER)
            .build()
            .thenAccept { renderable ->
                viewA.renderable = renderable
        }
        return anchorNode
    }

    private fun setImageView(artImage: Uri?):View{
        val layout = layoutInflater.inflate(R.layout.image, null)
        layout.width
        val image = layout.findViewById<ImageView>(R.id.image_card)
        val imageStream = activity?.contentResolver?.openInputStream(artImage)
        val selectedImage = BitmapFactory.decodeStream(imageStream)
        image?.rotation = artRotation
        image?.scaleType = ImageView.ScaleType.CENTER_CROP
        image?.setImageBitmap(selectedImage)
        return layout
    }
}