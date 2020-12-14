package android.paninti.todoapp.ui.main

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.paninti.todoapp.databinding.ActivityMainBinding
import android.paninti.todoapp.util.snack
import android.paninti.todoapp.util.viewBinding
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by viewBinding<ActivityMainBinding>()
    private val permissionRequest = arrayOf(Manifest.permission.CAMERA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Dummy Call to Activate Binding

        binding.mainNavHostContainer.setOnClickListener {
            activityResult.launch(permissionRequest)
        }
    }

    private val activityResult = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
        val hasCameraPermission = result[permissionRequest[0]]

        binding.root.snack("Now You Have Camera Permission Set to $hasCameraPermission")
    }
}