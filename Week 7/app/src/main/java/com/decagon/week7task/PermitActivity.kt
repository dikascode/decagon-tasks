package com.decagon.week7task

import android.Manifest
import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.decagon.week7task.model.ModelContact
import kotlinx.android.synthetic.main.activity_main.*

class PermitActivity : AppCompatActivity() {
    //global variable declarations
    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permit)
        //Assign variable to view id
        button = findViewById(R.id.permit_button)

        button.setOnClickListener {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.READ_CONTACTS
                )
            ) {
                /*
                  * Show an alert dialog explain rationale for permission request
                 */
                AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("This permission is needed to view all contacts on your phone")
                    .setPositiveButton("ok", DialogInterface.OnClickListener { dialog, _ ->
                        var intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    })

                    .setNegativeButton(
                        "cancel",
                        DialogInterface.OnClickListener { dialog, _ -> dialog.dismiss() })
                    .create().show()
            }
        }
    }


}