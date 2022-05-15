package com.example.composeblur

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.widget.FrameLayout
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.composeblur.ui.theme.ComposeBlurTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { Render() }
    }

    @Composable
    private fun Render() {
        ComposeBlurTheme {
            Box {
                Text("Hello world")
                AndroidView(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    factory = ::SomeView
                )
            }
        }
    }
}

class SomeView(context: Context) : FrameLayout(context) {
    private val internalCanvas = Canvas(Bitmap.createBitmap(100, 100, Bitmap.Config.ARGB_8888))

    init {
        setWillNotDraw(false)
    }

    override fun onDraw(canvas: Canvas) {
        // Check to avoid recursive drawing
        if (canvas != internalCanvas) {
            super.onDraw(canvas)

            // FIXME: Triggers RenderNodeLayer.updateDisplayList, even though it's a software canvas.
            //  Bug?
            //  java.lang.IllegalStateException: Recording currently in progress - missing #endRecording() call?
            rootView.draw(internalCanvas)
        }
    }
}
