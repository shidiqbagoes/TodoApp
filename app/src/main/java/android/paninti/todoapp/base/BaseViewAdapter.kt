package android.paninti.todoapp.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewAdapter<T, V : View>(
        private var register: Register<T, V>,
        private var diff: Diff<T>,
        private var params: Params = Params(),
        viewClass: Class<V>,
) : RecyclerView.Adapter<BaseViewAdapter<T, V>.BaseViewHolder>() {

    private val viewConstructor = viewClass.getConstructor(Context::class.java)

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

        val baseParams = RecyclerView.LayoutParams(
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

        val itemView = viewConstructor.newInstance(parent.context)

        itemView.apply {
            if (params.widthPercent != 0.0 || params.heightPercent != 0.0) {
                layoutParams = baseParams
            }
        }

        return BaseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class BaseViewHolder(itemView: V) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: T) {
            @Suppress("UNCHECKED_CAST")
            register.onBindHolder(adapterPosition, item, itemView as V)

            itemView.setOnClickListener {
                onItemClickCallback.invoke(itemView, item)
            }
        }
    }

    private var onItemClickCallback: (view: V, item: T) -> Unit = { _, _ -> }

    fun setOnItemClickListener(callback: (view: V, item: T) -> Unit) {
        onItemClickCallback = callback
    }

    data class Register<T, V: View>(
            var onBindHolder: (position: Int, item: T, view: V) -> Unit,
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
        inline fun <T, reified V : View> adapterOf(
                register: Register<T, V>,
                diff: Diff<T>,
                itemList: List<T> = emptyList(),
                params: Params = Params()
        ): BaseViewAdapter<T, V> {
            return BaseViewAdapter(register, diff, params, V::class.java).apply {
                if (itemList.isNotEmpty()) items = itemList
            }
        }

        inline fun <reified V: View> shimmerAdapter(
                register: Register<Int, V>,
                size: Int,
                params: Params = Params()
        ): BaseViewAdapter<Int, V> {
            val diff = Diff<Int>(
                    areContentsTheSame = { new, old -> new == old },
                    areItemsTheSame = { new, old -> new == old }
            )

            val list = MutableList(size) {it}.toList()

            return BaseViewAdapter(register, diff, params, V::class.java).apply {
                items = list
            }
        }
    }
}