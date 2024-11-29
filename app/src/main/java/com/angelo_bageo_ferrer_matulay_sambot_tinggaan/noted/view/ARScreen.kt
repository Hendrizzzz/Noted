package com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.view

import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.angelo_bageo_ferrer_matulay_sambot_tinggaan.noted.R
import com.google.ar.core.Pose
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.ArSceneView
import com.google.ar.sceneform.rendering.ViewRenderable


@Composable
fun ARScreen() {
    val context = LocalContext.current
    var noteText by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    var arSceneView: ArSceneView? by remember { mutableStateOf(null) }

    // Set up AR Scene
    LaunchedEffect(context) {
        arSceneView?.let {
            if (it.session == null) {
                Log.e("ARScreen", "AR session is not initialized")
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { showDialog = true }) {
                Text("+")
            }
        }
    ) { padding ->
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(padding)
        ) {
            // AR SceneView
            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { ctx ->
                    ArSceneView(ctx).apply {
                        resume()  // Ensure ArSceneView is resumed when created
                        arSceneView = this
                    }
                },
                update = { sceneView ->
                    // Handle updates to ArSceneView if needed
                }
            )

            // Dialog for Adding Notes
            if (showDialog) {
                NoteDialog(
                    onDismiss = { showDialog = false },
                    onAddNote = { text ->
                        noteText = text
                        addNoteToScene(context, arSceneView, text)
                        showDialog = false
                    }
                )
            }
        }
    }
}

private fun addNoteToScene(context: android.content.Context, arSceneView: ArSceneView?, noteText: String) {
    if (arSceneView == null || arSceneView.session == null) return

    // Create an AR anchor (pose)
    val pose = Pose(floatArrayOf(0f, -0.5f, -1f), floatArrayOf(0f, 0f, 0f, 1f))
    val anchor = arSceneView.session?.createAnchor(pose)
    val anchorNode = AnchorNode(anchor).apply {
        setParent(arSceneView.scene)  // Attach to the scene
    }

    // Build the renderable and attach it to the anchor node
    ViewRenderable.builder()
        .setView(context, R.layout.ar_note_layout)
        .build()
        .thenAccept { renderable ->
            renderable.view.findViewById<TextView>(R.id.noteTitle).text = "Note"
            renderable.view.findViewById<TextView>(R.id.noteDescription).text = noteText
            anchorNode.renderable = renderable
        }
        .exceptionally { throwable ->
            Log.e("ARScreen", "Unable to load renderable", throwable)
            null
        }

}



@Composable
fun NoteDialog(onDismiss: () -> Unit, onAddNote: (String) -> Unit) {
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
        confirmButton = {
            TextButton(onClick = { onAddNote(inputText) }) {
                Text("Add")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}


