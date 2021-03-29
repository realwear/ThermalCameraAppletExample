/*
 * RealWear Development Software, Source Code and Object Code.
 * (c) RealWear, Inc. All rights reserved.
 *
 * Contact info@realwear.com for further information about the use of this code.
 */

package com.realwear.thermalcameraexample

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

/**
 * Example on how to launch the Thermal Camera app to capture an image.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * Button callback method.
     */
    fun onLaunchThermalCamera(view: View) {
        //
        // Launch the Thermal Camera.
        //
        val intent = Intent()
        intent.action = INTENT_IMAGE_CAPTURE
        startActivityForResult(intent, INTENT_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && data != null) {
            when (requestCode) {
                INTENT_REQUEST_CODE -> {
                    //
                    // Extract all the returned data.
                    //
                    val photo = data.extras?.getParcelable<Bitmap>(EXTRA_DATA)
                    val temperatureScale = data.extras?.getParcelable<Bitmap>(EXTRA_SCALE)
                    val capturedTemperature = data.extras?.getString(EXTRA_CAPTURED_TEMP)
                    val minTemperature = data.extras?.getString(EXTRA_MIN)
                    val maxTemperature = data.extras?.getString(EXTRA_MAX)

                    //
                    // Update the UI.
                    //
                    findViewById<ImageView>(R.id.captured_image).setImageBitmap(photo)
                    findViewById<ImageView>(R.id.temperature_scale).setImageBitmap(temperatureScale)
                    findViewById<TextView>(R.id.temperature_data).text =
                        "Current: $capturedTemperature Min: $minTemperature Max: $maxTemperature"
                }
            }
        }
    }

    companion object {
        //
        // Intent data for launching the Thermal Camera.
        //
        private const val INTENT_IMAGE_CAPTURE = "com.realwear.thermalcamera.IMAGE_CAPTURE"
        private const val INTENT_REQUEST_CODE = 123

        //
        // Keys for Thermal Camera data.
        //
        private const val EXTRA_DATA = "data"
        private const val EXTRA_SCALE = "scale"
        private const val EXTRA_CAPTURED_TEMP = "captured_temp"
        private const val EXTRA_MIN = "min_temp"
        private const val EXTRA_MAX = "max_temp"
    }
}