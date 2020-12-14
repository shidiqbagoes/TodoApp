@file:Suppress("UNCHECKED_CAST")

package android.paninti.todoapp.util

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class ViewBindingExtension<T : ViewBinding>(
    private val fragment: Fragment,
    bindingClass: Class<T>
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null
    private val bindingMethod = bindingClass.getMethod(bindMethod,View::class.java)

    private val onDestroyObserver = object : DefaultLifecycleObserver {
        override fun onDestroy(owner: LifecycleOwner) {
            binding = null
        }
    }

    private val lifeCycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                viewLifecycleOwner.lifecycle.addObserver(onDestroyObserver)
            }
        }
    }

    init {
        fragment.lifecycle.addObserver(lifeCycleObserver)
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.let { binding -> return binding }

        val lifeCycle = fragment.viewLifecycleOwner.lifecycle
        if (!lifeCycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            val lifeCycleException = "Should not attempt to get bindings when Fragment views are destroyed."
            throw IllegalAccessException(lifeCycleException)
        }

        val viewBinding = bindingMethod.invoke(null, thisRef.requireView()) as T
        return viewBinding.also { binding ->
            this.binding = binding
        }
    }
}

class ActivityViewBindingExtension <T : ViewBinding>(
    private val bindingClass: Class<T>
) : ReadOnlyProperty<Activity, T> {

    private var binding: T? = null

    override fun getValue(thisRef: Activity, property: KProperty<*>): T {
        binding?.let { binding ->
            return binding
        }

        val bindingMethod = bindingClass.getMethod(
            inflateMethod,
            LayoutInflater::class.java
        )

        val viewBinding = bindingMethod.invoke(null, thisRef.layoutInflater) as T

        thisRef.setContentView(viewBinding.root)
        return viewBinding.also { this.binding = it }
    }
}

private const val bindMethod = "bind"
private const val inflateMethod = "inflate"

inline fun <reified T : ViewBinding> Fragment.viewBinding() =
    ViewBindingExtension(this, T::class.java)

inline fun <reified T : ViewBinding> viewBinding() =
    ActivityViewBindingExtension(T::class.java)