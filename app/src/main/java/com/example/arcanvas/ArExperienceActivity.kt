package com.example.arcanvas

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView



class MainActivity : AppCompatActivity() {
    private var imageId = 0
    override fun onCreate(savedInstanceState: Bundle?) {
<<<<<<< HEAD
<<<<<<< HEAD:app/src/main/java/com/example/arcanvas/MainActivity.kt
=======
        //TODO("Refactor such that we have navhost fragment which loads in ar fragment and others")
>>>>>>> ad4baba... Restructured folder directory of Kotlin files + refactored image picker ftom activty to fragment. The activty now utilises navhots with the ArtPickerFragment as default:app/src/main/java/com/example/arcanvas/ArExperienceActivity.kt
=======
>>>>>>> 23c6b62... Refactor the ArExperience activity to handle navhost navigation rather than hardcoding in ARFragment
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
