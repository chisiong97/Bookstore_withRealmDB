package com.example.bookstore

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.InputType.TYPE_CLASS_TEXT
import android.text.InputType.TYPE_NULL
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.FileProvider
import kotlinx.android.synthetic.main.activity_add_book.*
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.android.synthetic.main.activity_book_details.imageView
import kotlinx.android.synthetic.main.bookdetails_action_bar_layout.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class BookDetails : AppCompatActivity() {

    var newPhoto = false

    //Disable back
    override fun onBackPressed() {
        //super.onBackPressed()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)

        val bookArray = intent.getParcelableArrayListExtra<Book>("EXTRA_bookArray")
        var arrStatusNew : Int
        arrStatusNew = intent.getIntExtra("EXTRA_arrStatus",3)

        val adapterPos = intent.getIntExtra("EXTRA_adapterPosition", 0)

        //Appbar title changes according to book title
        val currentBook = bookArray[adapterPos]
        val tv1 : TextView = findViewById(R.id.book_title_appbar)
        tv1.text = currentBook.book_title

        //Fetch book details into each respective field
        val title : EditText = findViewById(R.id.book_title)
        val author : EditText = findViewById(R.id.author)
        val desc : EditText = findViewById(R.id.description)

        title.setText(currentBook.book_title)
        author.setText(currentBook.author)
        desc.setText(currentBook.book_desc)
        imageView.setImageURI(currentBook.book_cover)

        //Disable text field editable
        title.inputType = TYPE_NULL
        author.inputType = TYPE_NULL
        desc.inputType = TYPE_NULL
        imageView.isEnabled = false

        //Back button
        backBtn.setOnClickListener {
            val intent = Intent(this, BookList::class.java)
            arrStatusNew = 3
            intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
            intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
            startActivity(intent)
            finish()
        }

        //Edit button
        editBtn.setOnClickListener {
            //Edit button change to Done button once activated
            if (editBtn.text == "Done"){
                //Update book arraylist
                currentBook.book_title = title.text.toString()
                currentBook.author = author.text.toString()
                currentBook.book_desc = desc.text.toString()

                //check if user changed photo
                if (newPhoto){
                    currentBook.book_cover = Uri.parse(currentPhotoPath)
                }

                val intent = Intent(this, BookList::class.java)
                arrStatusNew = 2
                intent.putExtra("EXTRA_UpdatedStatus", arrStatusNew)
                intent.putParcelableArrayListExtra("EXTRA_UpdatedBookArray", bookArray)
                startActivity(intent)
                finish()

            }else{
                editBtn.text = "Done"
                //Set text field editable
                title.inputType = TYPE_CLASS_TEXT
                author.inputType = TYPE_CLASS_TEXT
                desc.inputType = TYPE_CLASS_TEXT
                imageView.isEnabled = true

            }

        }

        //Image button
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
                    newPhoto = true
                }
                IMAGE_PICK_CODE ->  {
                    println("Load library")
                    currentPhotoPath = (data!!.data).toString()
                    imageView.setImageURI(data.data)
                    println(currentPhotoPath)
                    newPhoto = true
                }

            }
        }
    }

    //create image file
    lateinit var currentPhotoPath: String

    private fun createImageFile(): File {
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
