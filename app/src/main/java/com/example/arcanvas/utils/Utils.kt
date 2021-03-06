package com.example.arcanvas.utils

import android.util.Log
import com.google.ar.sceneform.math.Vector3

object Utils {
    @JvmStatic
    fun getRotationVector(exifTag:Int):Float{
        var x = 0f
        when (exifTag) {
            in 1..2-> x = 0f
            in 3..4-> x = 180f
            in 5..6-> x = 90f
            in 7..8-> x = 270f
        }
        return x
    }
}
