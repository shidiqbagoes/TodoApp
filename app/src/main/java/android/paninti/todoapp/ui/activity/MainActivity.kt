package android.paninti.todoapp.ui.activity

import android.os.Bundle
import android.paninti.todoapp.databinding.ActivityMainBinding
import android.paninti.todoapp.viewModel.MainViewModel
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

     private val mainViewModel by viewModels<MainViewModel>()

     private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel.hasInternetLiveData.observe(this) {
            Timber.DebugTree().e("isConnected, $it")
        }
    }

     override fun onDestroy() {
         super.onDestroy()

         // Todo : Handle Instance Of Fragments
     }

     override fun onBackPressed() {
         super.onBackPressed()

         // Todo : Handle Navigation Back
     }
}