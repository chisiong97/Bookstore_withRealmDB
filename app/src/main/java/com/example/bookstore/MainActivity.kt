package com.example.bookstore

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import kotlin.system.exitProcess
import kotlin.collections.arrayListOf

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressed = 1
    override fun onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity()
            exitProcess(0)
        }
        else {
            doubleBackToExitPressed++
            Toast.makeText(this,"Please press BACK again to exit", Toast.LENGTH_SHORT).show()
        }

        Handler().postDelayed({ doubleBackToExitPressed = 1 }, 2000)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        //Check read permission
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) -> {
                // proceed
            }
            else -> {
                //make request
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    AddBook.REQUEST_IMAGE_CAPTURE
                )
            }
        }

        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) -> {
                // proceed
            }
            else -> {
                //make request
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    AddBook.REQUEST_IMAGE_CAPTURE
                )
            }
        }



        loginBtn.setOnClickListener{

            val intent = Intent(this, BookList::class.java)
            if(username.text.toString().equals("SS") && password.text.toString().equals("11111")) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                finish()
            }
            else
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()

        }
    }
}
