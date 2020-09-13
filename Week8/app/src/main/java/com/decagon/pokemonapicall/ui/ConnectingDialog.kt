package com.decagon.pokemonapicall.ui

import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import com.decagon.pokemonapicall.R

class ConnectingDialog(var activity: Activity) {
    lateinit var dialog: AlertDialog

    fun startConnectingDialog() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        val inflater: LayoutInflater = activity.layoutInflater
        builder.setView(inflater.inflate(R.layout.dialog, null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
    }


    fun dismissDialog() {
        dialog.dismiss()
    }
}