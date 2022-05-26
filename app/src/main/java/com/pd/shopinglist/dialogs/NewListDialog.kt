package com.pd.shopinglist.dialogs


import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.pd.shopinglist.databinding.NewListDialogBinding

object NewListDialog {
    fun showDialog(context: Context, listener: Listener) {
        var dialog: AlertDialog? = null
        val builder = AlertDialog.Builder(context)
        val binding = NewListDialogBinding.inflate(LayoutInflater.from(context))
        builder.setView(binding.root)
        binding.apply {
            bCreate.setOnClickListener {
                val listName = edNewListName.text.toString()
                if (listName.isNotEmpty()) {
                    listener.onClick(listName)
                }
                dialog?.dismiss() //закрываем диалог если пользователь ничего не ввел
            }

        }
        dialog = builder.create()
        dialog.window?.setBackgroundDrawable(null)// уберется стандартный фон
        dialog.show() //показываем диалог
    }

    interface Listener {
        fun onClick(name: String)
    }
}