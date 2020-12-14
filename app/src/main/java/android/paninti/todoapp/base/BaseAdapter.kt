package android.paninti.todoapp.base

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseAdapter<T, B : ViewBinding>(
        private var register: Register<T, B>,
        private var diff: Diff<T>,
        private var params: Params = Params(),
        bindingClass: Class<B>,
) : RecyclerView.Adapter<BaseAdapter<T, B>.BaseViewHolder>() {

    private val bindingMethod = bindingClass.getMethod(
            INFLATE_METHOD,
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
    )

    var items: List<T>
        get() = diffUtil.currentList
        set(value) = diffUtil.submitList(value)

    private val diffCallBack = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T) =
                diff.areItemsTheSame(oldItem, newItem)

        override fun areContentsTheSame(oldItem: T, newItem: T) =
                diff.areContentsTheSame(oldItem, newItem)
    }

    private val diffUtil = AsyncListDiffer(this, diffCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {

        val width = ((params.widthPercent / 100) * parent.width).toInt()
        val height = ((params.heightPercent / 100) * parent.width).toInt()

        val baseParams = LinearLayout.LayoutParams(
                if (params.widthPercent != 0.0) width else ViewGroup.LayoutParams.WRAP_CONTENT,
                if (params.heightPercent != 0.0) height else ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(
                    params.margin.left,
                    params.margin.top,
                    params.margin.right,
                    params.margin.bottom
            )
        }

        val inflater = LayoutInflater.from(parent.context)

        @Suppress("UNCHECKED_CAST")
        val viewBinding = bindingMethod.invoke(null, inflater, parent, false) as B

        viewBinding.root.apply {
            if (params.widthPercent != 0.0 || params.heightPercent != 0.0) {
                layoutParams = baseParams
            }
        }

        return BaseViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class BaseViewHolder(private val viewBinding: B) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(item: T) {
            register.onBindHolder(adapterPosition, item, viewBinding)

            viewBinding.root.setOnClickListener {
                onItemClickCallback.invoke(viewBinding, item)
            }
        }
    }

    private var onItemClickCallback: (binding: B, item: T) -> Unit = { _, _ -> }

    fun setOnItemClickListener(callback: (view: B, item: T) -> Unit) {
        onItemClickCallback = callback
    }

    data class Register<T, B: ViewBinding>(
            var onBindHolder: (position: Int, item: T, binding: B) -> Unit,
            var asyncLayout: Boolean = false
    )

    data class Diff<T>(
            var areContentsTheSame: (old: T, new: T) -> Boolean,
            var areItemsTheSame: (old: T, new: T) -> Boolean
    )

    data class Params(
            var widthPercent: Double = 0.0,
            var heightPercent: Double = 0.0,
            var margin: Margin = Margin()
    ) {
        data class Margin(
                val top: Int = 0,
                val left: Int = 0,
                val right: Int = 0,
                val bottom: Int = 0
        )
    }

    companion object {
        const val INFLATE_METHOD = "inflate"

        inline fun <T, reified B : ViewBinding> adapterOf(
                register: Register<T, B>,
                diff: Diff<T>,
                itemList: List<T> = emptyList(),
                params: Params = Params()
        ): BaseAdapter<T, B> {
            return BaseAdapter(register, diff, params, B::class.java).apply {
                if (itemList.isNotEmpty()) items = itemList
            }
        }

        inline fun <reified B: ViewBinding> shimmerAdapter(
                register: Register<Int, B>,
                size: Int,
                params: Params = Params()
        ): BaseAdapter<Int, B> {
            val diff = Diff<Int>(
                    areContentsTheSame = { new, old -> new == old },
                    areItemsTheSame = { new, old -> new == old }
            )

            val list = MutableList(size) {it}.toList()

            return BaseAdapter(register, diff, params, B::class.java).apply {
                items = list
            }
        }
    }
}