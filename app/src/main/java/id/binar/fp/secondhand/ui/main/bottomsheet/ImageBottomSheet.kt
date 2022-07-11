package id.binar.fp.secondhand.ui.main.bottomsheet

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.binar.fp.secondhand.databinding.BottomSheetImageBinding
import id.binar.fp.secondhand.util.Constants
import id.binar.fp.secondhand.util.createTempFile
import id.binar.fp.secondhand.util.uriToFile
import java.io.File

class ImageBottomSheet : BottomSheetDialogFragment() {

    private var _binding: BottomSheetImageBinding? = null
    private val binding get() = _binding!!

    private lateinit var currentPhotoPath: String

    var bottomSheetCallback: BottomSheetCallback? = null

    private val launcherCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val file = File(currentPhotoPath)
                val bitmap = BitmapFactory.decodeFile(file.path)
                bottomSheetCallback?.onSelectImage(bitmap, file)
            }
        }

    private val launcherGallery =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                val uri = result.data?.data as Uri
                val file = uriToFile(uri, requireContext())
                bottomSheetCallback?.onSelectImage(uri, file)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onCameraClicked()
        onGalleryClicked()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        intent.resolveActivity(requireActivity().packageManager)

        createTempFile(requireContext()).also {
            val photoURI: Uri = FileProvider.getUriForFile(
                requireContext(),
                Constants.APP_ID,
                it,
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            launcherCamera.launch(intent)
        }
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