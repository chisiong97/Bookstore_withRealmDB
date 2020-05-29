package com.example.bookstore

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.activity_add_book.author
import kotlinx.android.synthetic.main.activity_add_book.book_title
import kotlinx.android.synthetic.main.activity_add_book.description
import kotlinx.android.synthetic.main.activity_add_book.imageView
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.android.synthetic.main.addbook_action_bar_layout.*
import java.io.File
import java.io.IOException
import java.security.AccessController.getContext
import java.text.SimpleDateFormat
import java.util.*

class AddBook : AppCompatActivity() {

    //Disable back
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_book)

        //Check camera permission
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) -> {
                // proceed
            }
            else -> {
                //make request
                requestPermissions(arrayOf(Manifest.permission.CAMERA),
                    AddBook.REQUEST_IMAGE_CAPTURE
                )
            }
        }

        var bookArray = intent.getParcelableArrayListExtra<Book>("EXTRA_bookArray")
        var arrStatusNew : Int
        arrStatusNew = intent.getIntExtra("EXTRA_arrStatus",1)

        //TODO: Check if textfield is empty after done is clicked
        doneBtn.setOnClickListener(){
            val bookTitle = book_title.text.toString()
            val bookAuthor = author.text.toString()
            val bookDesc = description.text.toString()

            //TODO: Get image loaded by user
            //TODO: Load image from local, Save image from local,camera

            //Check if book cover exists, if not replace with default "empty_set" png

            var newBook = Book(
                    author = bookAuthor,
                    book_title = bookTitle,
                    book_desc = bookDesc,
                    book_cover = Uri.parse(currentPhotoPath)
            )
            println("Current: " + currentPhotoPath)

            bookArray.add(newBook)

            val intent = Intent(this, BookList::class.java)
            arrStatusNew = 2
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
            startActivity(intent)
            finish()
        }



        //Show image menu options
        imageView.setOnClickListener(){
            println("imgBtn clicked!")
            val popupMenu = PopupMenu(this, imageView)
            popupMenu.menuInflater.inflate(R.menu.imageoptions,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                //switch case
                when(item.itemId) {
                    R.id.navigation_library->{
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        pickImageFromGallery()
                    }
                    R.id.navigation_camera ->{
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        dispatchTakePictureIntent()
                    }
                    R.id.navigation_cancel ->
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                }
                true
            }
            popupMenu.show()
        }

        backBtn.setOnClickListener(){
            val intent = Intent(this, BookList::class.java)
            arrStatusNew = 3
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
            startActivity(intent)
            finish()
        }


    }



    //Test camera permission
    val TAG = "Test camera"
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_IMAGE_CAPTURE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Log.i(TAG, "Permission has been denied by user")
                } else {
                    Log.i(TAG, "Permission has been granted by user")
                }
            }
        }
    }

    //function for pick image from gallery
    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //camera code
        val REQUEST_IMAGE_CAPTURE = 1
    }


    //handle result whether is picked image or camera capture
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            println("Result OK")
            when(requestCode){
                REQUEST_IMAGE_CAPTURE -> {
                    println("Image captured")
                    val imagePath = Uri.parse(currentPhotoPath)
                    imageView.setImageURI(imagePath)
                    println("Image Path: "+ imagePath)
                }
                IMAGE_PICK_CODE ->  {
                    println("Load library")
                    currentPhotoPath = (data!!.data).toString()
                    imageView.setImageURI(data.data)
                    println(currentPhotoPath)
                }

            }
        }
    }

    //create image file
    lateinit var currentPhotoPath: String

    private fun createImageFile():File {
        println("Image file created")
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
            println("Photo path: "+absolutePath)
        }
    }

    //function to take picture from camera
    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            // Ensure that there's a camera activity to handle the intent
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    println("Error while creating file!")

                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }


}
