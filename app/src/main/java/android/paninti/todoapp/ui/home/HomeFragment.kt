package android.paninti.todoapp.ui.home

import android.graphics.Color
import android.os.Bundle
import android.paninti.todoapp.R
import android.paninti.todoapp.base.BaseViewAdapter
import android.paninti.todoapp.databinding.FragmentHomeBinding
import android.paninti.todoapp.ui.main.MainFragment.Companion.parentToolbar
import android.paninti.todoapp.util.snack
import android.paninti.todoapp.util.viewBinding
import android.paninti.todoapp.viewmodel.MainViewModel
import android.view.View
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import timber.log.Timber

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val mainViewModel by viewModels<MainViewModel>()
    private val binding by viewBinding<FragmentHomeBinding>()

    private val dummyAdapter = BaseViewAdapter.adapterOf<String, TextView>(
            register = BaseViewAdapter.Register(
                    onBindHolder = { position, item, view ->
                        val numberOrder = position + 1
                        view.setTextColor(Color.WHITE)
                        view.text = "$numberOrder. $item"
                    }
            ),
            diff = BaseViewAdapter.Diff(
                    areContentsTheSame = { old, new -> old == new },
                    areItemsTheSame =  { old, new -> old == new }
            ),
            itemList = MutableList(150) { "Position -> $it" }.toList()
    )

    override fun onStart() {
        super.onStart()

        parentToolbar?.isVisible = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.hasInternetLiveData.observe(viewLifecycleOwner) {
            if (!it) {
                binding.root.snack(
                    message = "No internet Connection!",
                    actionText = "Retry",
                    length = Snackbar.LENGTH_INDEFINITE
                ) {
                    Timber.DebugTree().d("Currently Clicking Retry")
                }
            }
        }
    }
}