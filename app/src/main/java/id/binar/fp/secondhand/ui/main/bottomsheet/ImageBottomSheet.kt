package id.binar.fp.secondhand.ui.main.bottomsheet

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import id.binar.fp.secondhand.databinding.BottomSheetImageBinding
import id.binar.fp.secondhand.ui.base.BaseBottomSheet
import id.binar.fp.secondhand.util.bitmapToUri
import id.binar.fp.secondhand.util.uriToFile
import java.io.File

class ImageBottomSheet : BaseBottomSheet<BottomSheetImageBinding>() {

    private lateinit var currentPhotoPath: String

    var bottomSheetCallback: BottomSheetCallback? = null

    private val launcherCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK && result.data != null) {
                val bitmap = result.data?.extras?.get("data") as Bitmap
                val uri = bitmapToUri(requireContext(), bitmap)
                val file = uriToFile(uri, requireContext())

                bottomSheetCallback?.onSelectImage(bitmap, file)
            }
        }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data as Uri
                val file = uriToFile(uri, requireContext())
                bottomSheetCallback?.onSelectImage(uri, file)
            }
        }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> BottomSheetImageBinding
        get() = BottomSheetImageBinding::inflate

    override fun setup() {
        onCameraClicked()
        onGalleryClicked()
    }

    private fun onCameraClicked() {
        binding.btnCamera.setOnClickListener {
            openCamera()
        }
    }

    private fun onGalleryClicked() {
        binding.btnGallery.setOnClickListener {
            openGallery()
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        launcherCamera.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"

        val chooser = Intent.createChooser(intent, "Pilih gambar")
        launcherGallery.launch(chooser)
    }

    interface BottomSheetCallback {
        fun onSelectImage(bitmap: Bitmap, file: File)
        fun onSelectImage(uri: Uri, file: File)
    }

    companion object {
        const val TAG = "ImageBottomSheet"
    }
}