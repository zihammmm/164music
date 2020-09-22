package com.zihany.cloudmusic.widget

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.zihany.cloudmusic.App
import com.zihany.cloudmusic.R
import com.zihany.cloudmusic.base.BaseDialog
import com.zihany.cloudmusic.databinding.LayoutCommomDialogBinding
import kotlinx.android.synthetic.main.layout_commom_dialog.*

class MusicDialog constructor(private val mContext: Context, themeResId: Int)
    : BaseDialog(mContext, themeResId) {

    private val binding: LayoutCommomDialogBinding = LayoutCommomDialogBinding.inflate(LayoutInflater.from(mContext))
    private var cancelListener: DialogInterface.OnClickListener? = null
    private var confirmListener: DialogInterface.OnClickListener? = null

    constructor(context: Context): this(context, R.style.BaseDialog)

    init {
        setCancelable(false)
        setCanceledOnTouchOutside(true)
        initListener()
        setContentView(binding.root)
    }

    private fun initListener() {
        binding.tvCancel.setOnClickListener {
            cancelListener?.onClick(this, DialogInterface.BUTTON_NEGATIVE)
        }

        binding.tvConfirm.setOnClickListener {
            confirmListener?.onClick(this, DialogInterface.BUTTON_POSITIVE)
        }

    }

    private fun setCancelListener(cancelListener: DialogInterface.OnClickListener) {
        this.cancelListener = cancelListener
        binding.tvCancel.visibility = View.VISIBLE
    }

    private fun setConfirmListener(confirmListener: DialogInterface.OnClickListener) {
        this.confirmListener = confirmListener
        binding.tvConfirm.visibility = View.VISIBLE
    }

    private fun setMsg(msg: String) {
        binding.tvMessage.text = msg
        binding.tvMessage.visibility = View.VISIBLE
    }

    class Builder constructor(context: Context) {
        private var dialog: MusicDialog = MusicDialog(context)
        private var tvConfirm: TextView
        private var tvCancel: TextView

        init {
            tvConfirm = dialog.tv_confirm
            tvCancel = dialog.tv_cancel
        }

        fun setMsg(resId: Int): Builder {
            val res = App.getContext().getString(resId)
            return setMsg(res)
        }

        private fun setMsg(msg: String): Builder {
            dialog.setMsg(msg)
            return this
        }

        fun setPositiveText(resId: Int): Builder {
            val res = App.getContext().getString(resId)
            return setPositiveText(res)
        }

        private fun setPositiveText(text: String): Builder {
            tvConfirm.text = text
            return this
        }

        fun setNegativeText(resId: Int): Builder {
            val res = App.getContext().getString(resId)
            return setNegativeText(res)
        }

        private fun setNegativeText(text: String): Builder {
            tvCancel.text = text
            return this
        }

        fun setNegativeClickListener(listener: DialogInterface.OnClickListener): Builder {
            dialog.setCancelListener(listener)
            return this
        }

        fun setPositiveClickListener(listener: DialogInterface.OnClickListener): Builder {
            dialog.setConfirmListener(listener)
            return this
        }

        fun setPositiveTextColor(color: Int): Builder {
            tvConfirm.setTextColor(color)
            return this
        }

        fun setNegativeTextColor(color: Int): Builder {
            tvCancel.setTextColor(color)
            return this
        }

        fun create(): MusicDialog {
            return dialog
        }

    }
}