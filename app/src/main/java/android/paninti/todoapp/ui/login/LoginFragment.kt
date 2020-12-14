package android.paninti.todoapp.ui.login

import android.os.Bundle
import android.paninti.todoapp.R
import android.paninti.todoapp.data.preferences.AccessOption
import android.paninti.todoapp.databinding.FragmentLoginBinding
import android.paninti.todoapp.util.navController
import android.paninti.todoapp.util.navigateOrNull
import android.paninti.todoapp.util.viewBinding
import android.paninti.todoapp.viewmodel.LoginViewModel
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModels<LoginViewModel>()
    private val binding by viewBinding<FragmentLoginBinding>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onLoginAccessObserve()

        viewModel.loginFormState.observe(viewLifecycleOwner) { loginFormState ->
            loginFormState ?: return@observe

            binding.login.isEnabled = loginFormState.isDataValid

            loginFormState.usernameError?.let {
                binding.username.error = getString(it)
            }
            loginFormState.passwordError?.let {
                binding.password.error = getString(it)
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner) { loginResult ->
            loginResult ?: return@observe

            binding.loading.isVisible = false
            loginResult.error?.let {
                showLoginFailed(it)
            }
            loginResult.success?.let {
                updateUiWithUser(it)
            }
        }

        binding.username.doOnTextChanged { _, _, _, _ ->
            viewModel.loginDataChanged(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }
        binding.password.doOnTextChanged { _, _, _, _ ->
            viewModel.loginDataChanged(
                binding.username.text.toString(),
                binding.password.text.toString()
            )
        }

        binding.password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.login(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
            false
        }

        binding.login.setOnClickListener(onClick)
    }

    private fun onLoginAccessObserve() {
        viewModel.loginAccess.observe(viewLifecycleOwner) { option ->
            if (option == AccessOption.HAS_ACCESS) {
                navController.navigateOrNull(LoginFragmentDirections.actionMainFragment(), true)
            }
        }
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome) + model.displayName
        // TODO : initiate successful logged in experience
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, welcome, Toast.LENGTH_LONG).show()

        navController.navigate(LoginFragmentDirections.actionMainFragment())
    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        val appContext = context?.applicationContext ?: return
        Toast.makeText(appContext, errorString, Toast.LENGTH_LONG).show()
    }

    private val onClick = View.OnClickListener { view ->
        when(view) {
            binding.login -> {
                binding.loading.isVisible = true
                viewModel.login(
                    binding.username.text.toString(),
                    binding.password.text.toString()
                )
            }
        }
    }
}