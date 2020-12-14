package android.paninti.todoapp.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.paninti.todoapp.R
import android.paninti.todoapp.databinding.FragmentMainBinding
import android.paninti.todoapp.util.BackButtonBehaviour
import android.paninti.todoapp.util.setupWithNavController
import android.paninti.todoapp.util.viewBinding
import android.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainFragment : Fragment(R.layout.fragment_main) {

    private val bottomNavSelectedItemIdKey = "BOTTOM_NAV_SELECTED_ITEM_ID_KEY"
    private var bottomNavSelectedItemId = R.id.home

    private val binding by viewBinding<FragmentMainBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        savedInstanceState?.let {
            bottomNavSelectedItemId = it.getInt(bottomNavSelectedItemIdKey, bottomNavSelectedItemId)
        }

        setupBottomNavBar()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(bottomNavSelectedItemIdKey, bottomNavSelectedItemId)
        super.onSaveInstanceState(outState)
    }

    private fun setupBottomNavBar() {
        binding.bottomNavigation.selectedItemId = bottomNavSelectedItemId

        val navGraphIds = listOf(R.navigation.home_graph, R.navigation.course_graph, R.navigation.profile_graph)

        val controller = binding.bottomNavigation.setupWithNavController(
                fragmentManager = childFragmentManager,
                navGraphIds = navGraphIds,
                backButtonBehaviour = BackButtonBehaviour.POP_HOST_FRAGMENT,
                containerId = binding.navHostContainer.id,
                firstItemId = R.id.home,
                intent = requireActivity().intent
        )

        controller.observe(viewLifecycleOwner) { navController ->
            NavigationUI.setupWithNavController(binding.mainToolbar, navController)
            bottomNavSelectedItemId = navController.graph.id
        }
    }

    companion object {
        val Fragment?.parentToolbar: MaterialToolbar? get() {
            val fragment = if (this?.parentFragment is NavHostFragment) {
                (parentFragment as? NavHostFragment)?.parentFragment as? MainFragment
            } else null

            return fragment?.binding?.mainToolbar
        }

        val Fragment?.parentNavigation: BottomNavigationView? get() {
            val fragment = if (this?.parentFragment is NavHostFragment) {
                (parentFragment as? NavHostFragment)?.parentFragment as? MainFragment
            } else null

            return fragment?.binding?.bottomNavigation
        }
    }
}