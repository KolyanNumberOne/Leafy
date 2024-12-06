import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor
import java.io.File

class CameraViewModel : ViewModel() {
    private val _capturedImageUri = mutableStateOf<Uri?>(null)
    val capturedImageUri: State<Uri?> = _capturedImageUri

    fun capturePhoto(context: Context, imageCapture: ImageCapture) {
        val outputDirectory = context.filesDir
        val photoFile = File(outputDirectory, "${System.currentTimeMillis()}.jpg")

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(outputOptions, Dispatchers.IO.asExecutor(), object : ImageCapture.OnImageSavedCallback {
            override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                _capturedImageUri.value = Uri.fromFile(photoFile)
            }

            override fun onError(exception: ImageCaptureException) {
                exception.printStackTrace()
            }
        })
    }

    fun selectImageFromGallery(uri: Uri) {
        _capturedImageUri.value = uri
    }
}
