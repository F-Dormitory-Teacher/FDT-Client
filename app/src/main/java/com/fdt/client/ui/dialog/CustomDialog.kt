package com.fdt.client.ui.dialog

import android.app.AlertDialog
import android.content.Context
import android.view.View
import androidx.annotation.StringRes
import com.fdt.client.R
import kotlinx.android.synthetic.main.alert_ask_logout.view.*

class CustomDialog(private val context: Context) {
    private val builder: AlertDialog.Builder by lazy {
        AlertDialog.Builder(context).setView(view)
    }

    private val view: View by lazy {
        View.inflate(context, R.layout.alert_ask_logout, null)
    }

    private var dialog: AlertDialog? = null


    fun setMessage(@StringRes messageId: Int): CustomDialog {
        view.messageTextView.text = context.getText(messageId)
        return this
    }

    fun setMessage(message: CharSequence): CustomDialog {
        view.messageTextView.text = message
        return this
    }

    fun setPositiveButton(@StringRes textId: Int, listener: (view: View) -> (Unit)): CustomDialog {
        view.positiveButton.apply {
            text = context.getText(textId)
            setOnClickListener(listener)
        }
        return this
    }

    fun setPositiveButton(text: CharSequence, listener: (view: View) -> (Unit)): CustomDialog {
        view.positiveButton.apply {
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }

    fun setNegativeButton(@StringRes textId: Int, listener: (view: View) -> (Unit)): CustomDialog {
        view.negativeButton.apply {
            text = context.getText(textId)
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }

    fun setNegativeButton(text: CharSequence, listener: (view: View) -> (Unit)): CustomDialog {
        view.negativeButton.apply {
            this.text = text
            setOnClickListener(listener)
        }
        return this
    }

    fun create() {
        dialog = builder.create()
    }

    fun show() {
        dialog = builder.create()
        dialog?.show()
    }

    fun dismiss() {
        dialog?.dismiss()
    }
}