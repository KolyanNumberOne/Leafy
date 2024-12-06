import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.compose.ui.unit.dp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.ImageSearch
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment

@Composable
fun CameraScreen(viewModel: CameraViewModel = viewModel()) {
    val context = LocalContext.current
    val lifecycleOwner = LocalContext.current as androidx.lifecycle.LifecycleOwner
    val imageCapture = remember { ImageCapture.Builder().build() }
    val capturedImageUri by viewModel.capturedImageUri

    var hasCameraPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        hasCameraPermission = granted
    }

    val galleryLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.selectImageFromGallery(it) }
    }

    // Request camera permission on first render
    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.CAMERA)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        if (hasCameraPermission) {
            Box(modifier = Modifier
                .fillMaxSize()
            ) {
                CameraPreview(
                    imageCapture = imageCapture,
                    lifecycleOwner = lifecycleOwner
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(100.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { viewModel.capturePhoto(context, imageCapture) }) {
                        Icon(
                            Icons.Outlined.Camera,
                            contentDescription = "Сделать фото",
                            modifier = Modifier.size(48.dp))
                    }

                    IconButton(onClick = { galleryLauncher.launch("image/*") }) {
                        Icon(
                            Icons.Outlined.ImageSearch,
                            contentDescription = "Выбрать фото из галереи",
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        } else {
            BasicText(
                text = "Отсутствует разрешение на использование камеры",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp)
            )
        }

//        capturedImageUri?.let { uri ->
//            Spacer(modifier = Modifier.height(16.dp))
//            Image(
//                painter = rememberAsyncImagePainter(uri),
//                contentDescription = "Captured Image",
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .aspectRatio(1f)
//            )
//        }
    }
}

@Composable
fun CameraPreview(
    imageCapture: ImageCapture,
    lifecycleOwner: androidx.lifecycle.LifecycleOwner
) {
    val context = LocalContext.current
    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }

    AndroidView(
        factory = { ctx ->
            val previewView = PreviewView(ctx)
            cameraProviderFuture.addListener({
                val cameraProvider = cameraProviderFuture.get()
                val preview = androidx.camera.core.Preview.Builder().build().also {
                    it.setSurfaceProvider(previewView.surfaceProvider)
                }

                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    lifecycleOwner,
                    CameraSelector.DEFAULT_BACK_CAMERA,
                    preview,
                    imageCapture
                )
            }, ContextCompat.getMainExecutor(ctx))
            previewView
        },
        modifier = Modifier.fillMaxSize()
    )
}
