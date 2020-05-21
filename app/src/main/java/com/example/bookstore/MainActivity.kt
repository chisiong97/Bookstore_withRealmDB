package com.example.bookstore

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private var doubleBackToExitPressed = 1
    override fun onBackPressed() {
        if (doubleBackToExitPressed == 2) {
            finishAffinity();
            exitProcess(0);
        }
        else {
            doubleBackToExitPressed++;
            Toast.makeText(this,"Please press BACK again to exit", Toast.LENGTH_SHORT).show();
        }

        Handler().postDelayed(Runnable { doubleBackToExitPressed = 1 }, 2000)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //supportActionBar()

        val intent = Intent(this, BookList::class.java)
        loginBtn.setOnClickListener{
            startActivity(intent)
            finish()
            /*if(username.text.toString().equals("SS") && password.text.toString().equals("11111")) {
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                startActivity(intent)
            }
            else
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            */
        }
    }
}
