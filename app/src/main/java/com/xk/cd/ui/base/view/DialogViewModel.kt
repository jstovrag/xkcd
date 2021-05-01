package com.xk.cd.ui.base.view

import android.content.Context
import android.content.DialogInterface
import android.text.Html
import androidx.appcompat.app.AlertDialog

class DialogViewModel {
    private var alertDialog: AlertDialog? = null
    private var title: String? = null
    private var titleId: Int? = null
    private var message: String? = null
    private var messageId: Int? = null
    private var positiveButtonText: Int = android.R.string.ok
    private var positiveButtonListener: ((dialog: DialogInterface, position: Int) -> Unit) =
        { dialog: DialogInterface, _: Int -> dialog.dismiss() }
    private var negativeButtonText: Int? = null
    private var negativeButtonListener: ((dialog: DialogInterface, position: Int) -> Unit)? = null
    private var description: String? = null
    private var isCancelable: Boolean = false

    constructor(
        title: String? = null,
        message: String? = null,
        positiveButtonText: Int = android.R.string.ok,
        positiveButtonListener: ((dialog: DialogInterface, position: Int) -> Unit) = { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        },
        negativeButtonText: Int? = null,
        negativeButtonListener: ((dialog: DialogInterface, position: Int) -> Unit)? = null
    ) {
        this.title = title
        this.message = message
        this.positiveButtonText = positiveButtonText
        this.positiveButtonListener = positiveButtonListener
        this.negativeButtonText = negativeButtonText
        this.negativeButtonListener = negativeButtonListener
    }

    constructor(
        titleId: Int? = null,
        messageId: Int? = null,
        positiveButtonText: Int = android.R.string.ok,
        positiveButtonListener: ((dialog: DialogInterface, position: Int) -> Unit) = { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        },
        negativeButtonText: Int? = null,
        negativeButtonListener: ((dialog: DialogInterface, position: Int) -> Unit)? = null
    ) {
        this.titleId = titleId
        this.messageId = messageId
        this.positiveButtonText = positiveButtonText
        this.positiveButtonListener = positiveButtonListener
        this.negativeButtonText = negativeButtonText
        this.negativeButtonListener = negativeButtonListener
    }

    fun showDialog(context: Context) {
        val builder = AlertDialog.Builder(context)
        title?.let { builder.setTitle(it) }
        titleId?.let { builder.setTitle(context.resources.getString(it)) }
        if (description != null) {
            builder.setMessage(description)
        } else {
            message?.let {
                builder.setMessage(it)
            }
            messageId?.let {
                builder.setMessage(it)
            }
        }
        builder.setPositiveButton(positiveButtonText) { dialog: DialogInterface, button: Int ->
            positiveButtonListener(dialog, button)
        }

        negativeButtonText?.let {
            builder.setNegativeButton(it) { dialog: DialogInterface, button: Int ->
                negativeButtonListener?.invoke(dialog, button)
            }
        }
        builder.setCancelable(isCancelable)
        alertDialog = builder.create()
        alertDialog?.show()
    }

    fun setMessage(context: Context, message: Int, vararg args: String) {
        description = context.getString(message, args[0])
    }

    fun setCancelable(isCancelable: Boolean) {
        this.isCancelable = isCancelable
    }

    fun dismiss() {
        alertDialog?.dismiss()
    }
}
