package id.binar.fp.secondhand.ui.base

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import id.binar.fp.secondhand.R

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: ViewBinding? = null
    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    open val isNavigationVisible: Boolean
        get() = true

    open val isLightStatusBar: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    override fun onResume() {
        super.onResume()
        checkAuth()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    open fun setup() {
        setupStatusBar()
        setupToolbar()
        setupBottomNavigation()
    }

    private fun setupStatusBar() {
        ViewCompat.getWindowInsetsController(requireActivity().window.decorView)?.isAppearanceLightStatusBars =
            isLightStatusBar
    }

    private fun setupBottomNavigation() {
        requireActivity().findViewById<BottomNavigationView>(R.id.bottom_navigation_view).isVisible =
            isNavigationVisible
    }

    open fun setupToolbar() {}

    open fun checkAuth() {}

    open fun checkPermissions(initBottomSheet: () -> Unit) {
        if (checkPermissionsIsGranted(
                requireActivity(),
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION
            )
        ) {
            initBottomSheet.invoke()
        }
    }

    private fun checkPermissionsIsGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        requestCode: Int
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, requestCode)
            }
            false
        } else {
            true
        }
    }

    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, please allow permissions from App Settings.")
            .setPositiveButton("Pengaturan") { _, _ -> openAppSettings() }
            .setNegativeButton("Batal") { dialog, _ -> dialog.cancel() }
            .show()
    }

    private fun openAppSettings() {
        val intent = Intent()
        val uri = Uri.fromParts("package", requireActivity().packageName, null)

        intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
        intent.data = uri

        startActivity(intent)
    }

    companion object {
        private const val REQUEST_CODE_PERMISSION = 100
    }
}