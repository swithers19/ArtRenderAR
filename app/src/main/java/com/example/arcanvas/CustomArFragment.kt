package com.example.arcanvas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.ar.core.Config
import com.google.ar.core.Session
import com.google.ar.sceneform.ux.ArFragment



class CustomArFragment : ArFragment() {

    override fun getSessionConfiguration(session: Session): Config {
        planeDiscoveryController.hide()
        planeDiscoveryController.setInstructionView(null)
        arSceneView.planeRenderer.isEnabled = false

        val config = Config(session)
        config.updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
        session?.configure(config)
        arSceneView.setupSession(session)

//        if ((activity as MainActivity).setupAugmentedImagesDb(config, session)) {
//            Log.d("SetupAugImgDb", "Success")
//        } else {
//            Log.e("SetupAugImgDb", "Faliure setting up db")
//        }

        return config
    }

}