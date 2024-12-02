package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import android.Manifest
import android.content.Context
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.google.ar.core.*
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.rendering.ViewRenderable
import androidx.compose.ui.Alignment
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.R

class ARView {

    @Composable
    fun DisplayView() {
        Surface(color = MaterialTheme.colorScheme.background) {
            ARScreen()
        }
    }

    @Composable
    private fun ARScreen() {
        val context = LocalContext.current
        val lifecycleOwner = LocalLifecycleOwner.current

        var noteText by remember { mutableStateOf("") }
        var showDialog by remember { mutableStateOf(false) }
        var arSceneView: ArSceneView? by remember { mutableStateOf(null) }

        CheckCameraPermission {
            if (!isARCoreInstalled(context)) {
                Toast.makeText(context, "ARCore is not installed or supported.", Toast.LENGTH_LONG).show()
                return@CheckCameraPermission
            }
        }

        DisposableEffect(lifecycleOwner) {
            val observer = LifecycleEventObserver { _, event ->
                when (event) {
                    Lifecycle.Event.ON_RESUME -> arSceneView?.resume()
                    Lifecycle.Event.ON_PAUSE -> arSceneView?.pause()
                    Lifecycle.Event.ON_DESTROY -> arSceneView?.destroy()
                    else -> {}
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)
            onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
        }

        Scaffold(
            floatingActionButton = {
                FloatingActionButton(onClick = { showDialog = true }) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Add Note")
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { padding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                // AR View Setup
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        ArSceneView(ctx).apply {
                            arSceneView = this
                            setupARSession(context)
                            resume()
                            scene.setOnTouchListener { hitTestResult, motionEvent ->
                                val frame = arSceneView?.arFrame
                                if (frame != null && noteText.isNotEmpty()) {
                                    val hitResults = frame.hitTest(motionEvent)
                                    val validHit = hitResults.firstOrNull { hitResult ->
                                        val trackable = hitResult.trackable
                                        (trackable is Plane && trackable.isPoseInPolygon(hitResult.hitPose)) ||
                                                (trackable is Point && trackable.orientationMode == Point.OrientationMode.ESTIMATED_SURFACE_NORMAL)
                                    }

                                    if (validHit != null) {
                                        val anchor = validHit.createAnchor()
                                        addNoteToScene(context, this, noteText, anchor)
                                        Toast.makeText(context, "Note added successfully!", Toast.LENGTH_SHORT).show()
                                        noteText = ""
                                    } else {
                                        Toast.makeText(context, "No valid surface detected. Move the device slowly.", Toast.LENGTH_SHORT).show()
                                    }
                                }
                                true
                            }
                        }
                    },
                    update = { sceneView -> arSceneView = sceneView }
                )
            }

            if (showDialog) {
                NoteDialog(
                    onDismiss = { showDialog = false },
                    onAddNote = { enteredText ->
                        noteText = enteredText
                        showDialog = false
                        Toast.makeText(context, "Touch a surface in AR to place your note.", Toast.LENGTH_SHORT).show()
                    }
                )
            }
        }
    }

    @Composable
    private fun CheckCameraPermission(onPermissionGranted: () -> Unit) {
        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                onPermissionGranted()
            } else {
                Toast.makeText(context, "Camera permission is required for AR.", Toast.LENGTH_SHORT).show()
            }
        }

        DisposableEffect(Unit) {
            launcher.launch(Manifest.permission.CAMERA)
            onDispose { }
        }
    }

    private fun isARCoreInstalled(context: Context): Boolean {
        val availability = ArCoreApk.getInstance().checkAvailability(context)
        Log.d("ARCore", "ARCore availability: $availability")
        return availability.isSupported && !availability.isTransient
    }

    private fun ArSceneView.setupARSession(context: Context) {
        try {
            val session = Session(context).apply {
                val config = Config(this).apply {
                    planeFindingMode = Config.PlaneFindingMode.HORIZONTAL
                    updateMode = Config.UpdateMode.LATEST_CAMERA_IMAGE
                    if (isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                        depthMode = Config.DepthMode.AUTOMATIC
                    }
                }
                configure(config)
            }
            this.setupSession(session)
        } catch (e: Exception) {
            Log.e("ARScreen", "AR Session setup failed", e)
        }
    }

    private fun addNoteToScene(context: Context, arSceneView: ArSceneView, noteText: String, anchor: Anchor) {
        val anchorNode = AnchorNode(anchor).apply { setParent(arSceneView.scene) }
        ViewRenderable.builder()
            .setView(context, R.layout.ar_note_layout)
            .build()
            .thenAccept { renderable ->
                val noteView = renderable.view
                noteView.findViewById<TextView>(R.id.noteTitle).text = "Note"
                noteView.findViewById<TextView>(R.id.noteDescription).text = noteText
                anchorNode.renderable = renderable
            }
            .exceptionally { throwable ->
                Log.e("ARView", "Renderable failed to load", throwable)
                null
            }
    }

    @Composable
    private fun NoteDialog(onDismiss: () -> Unit, onAddNote: (String) -> Unit) {
        var inputText by remember { mutableStateOf("") }

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("Add Note") },
            text = {
                Column {
                    Text("Enter your note:")
                    BasicTextField(
                        value = inputText,
                        onValueChange = { inputText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }
            },
            confirmButton = { TextButton(onClick = { onAddNote(inputText) }) { Text("Add") } },
            dismissButton = { TextButton(onClick = onDismiss) { Text("Cancel") } }
        )
    }
}
