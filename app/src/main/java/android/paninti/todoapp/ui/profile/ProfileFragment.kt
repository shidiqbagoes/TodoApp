package android.paninti.todoapp.ui.profile

import android.os.Bundle
import android.paninti.todoapp.R
import android.paninti.todoapp.databinding.FragmentProfileBinding
import android.paninti.todoapp.util.viewBinding
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding by viewBinding<FragmentProfileBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvProfile.setOnClickListener {
            activityResult.launch()
        }
    }

    private val activityResult = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { result ->
        // Handle Bitmap From Camera Result
        val bitmapDrawable = result.toDrawable(resources)
    }
}