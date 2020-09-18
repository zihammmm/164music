package com.zihany.lib_toast.toast

import android.app.Application
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.children

class BaseToast constructor(application: Application)
    : Toast(application) {
    companion object {
        fun findMessageView(view: View): TextView {
            if (view is TextView) {
                return view
            }else if (view.findViewById<TextView>(android.R.id.message) is TextView) {
                return view.findViewById(android.R.id.message)
            }else if (view is ViewGroup) {
                findTextView(view)?.let {
                    return it
                }
            }
            throw IllegalArgumentException("The layout must contain a TextView")
        }

        fun findTextView(group: ViewGroup): TextView? {
            for (view: View in group.children) {
                if (view is TextView) {
                    return view
                }else if (view is ViewGroup) {
                    findTextView(view)?.let {
                        return it
                    }
                }
            }
            return null
        }
    }

    private lateinit var messageView: TextView

    fun setMessageView(view: TextView) {
        messageView = view
    }

    override fun setText(s: CharSequence?) {
        messageView.text = s
    }

}