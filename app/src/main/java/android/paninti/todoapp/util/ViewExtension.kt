package android.paninti.todoapp.util

import android.paninti.todoapp.R
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

/**
 * Showing Snackbar with certain Options
 * @param action Execute all process inside lambda function
 */
fun View.snack(
    message: CharSequence,
    actionText: CharSequence? = null,
    length: Int = Snackbar.LENGTH_SHORT,
    isAnchored: Boolean = false,
    action: ((View) -> Unit)? = null
){
    Snackbar.make(this, message, length).apply {
        with(view){
            if (isAnchored) {
                anchorView = this
            }

            findViewById<TextView>(R.id.snackbar_text).apply {
                gravity = Gravity.CENTER
                maxLines = 2
                ellipsize = TextUtils.TruncateAt.END
            }

            if (!actionText.isNullOrEmpty()){
                findViewById<TextView>(R.id.snackbar_action)

                setAction(actionText) {
                    action?.invoke(it)
                }
            }
        }
    }.show()
}