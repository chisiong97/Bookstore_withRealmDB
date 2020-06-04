package com.example.bookstore

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_book_details.*
import kotlinx.android.synthetic.main.bookdetails_action_bar_layout.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class BookDetails : AppCompatActivity()
{

    var newPhoto = false
    private val realm = Realm.getDefaultInstance()
    private val helper = BookModel()

    //Disable back
    override fun onBackPressed()
    {
        //super.onBackPressed()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_details)
        initUI()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initUI()
    {

        //Appbar title changes according to book title
        val currentBookID = intent.getIntExtra("EXTRA_BookID", 0)
        val results = helper.getBook(realm,currentBookID)
        val currentBook: Book? = results?.get(0)

        val lblAppBar : TextView = findViewById(R.id.lblAppBarTitle)
        lblAppBar.text = currentBook?.book_title

        //Fetch book details into each respective field
        val title : EditText = findViewById(R.id.etBookTitle)
        val author : EditText = findViewById(R.id.etAuthor)
        val desc : EditText = findViewById(R.id.etDesc)

        title.setText(currentBook?.book_title)
        author.setText(currentBook?.author)
        desc.setText(currentBook?.book_desc)
        Glide.with(this).load(currentBook?.book_cover).into(btnImage)

        //Disable text field editable
        title.isEnabled = false
        author.isEnabled = false
        desc.isEnabled = false
        btnImage.isEnabled = false

        //Back button
        btnBack.setOnClickListener()
        {
            val returnIntent = Intent()
            returnIntent.putExtra("Update",true)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        //Edit button
        btnEdit.setOnClickListener()
        {
            //Edit button change to Done button once activated
            if (btnEdit.text == "Done"){

                val bookId = currentBook?.id
                val updatedAuthor = author.text.toString()
                val updatedCover : String
                //check if user changed photo
                if (newPhoto)
                {
                    updatedCover = currentPhotoPath
                }
                else
                {
                    updatedCover = currentBook?.book_cover.toString()
                }

                val updatedDesc = desc.text.toString()
                val updatedTitle = title.text.toString()

                helper.updateBook(realm,
                    bookId ,
                    updatedAuthor,
                    updatedCover,
                    updatedDesc,
                    updatedTitle)

                val returnIntent = Intent()
                returnIntent.putExtra("Update",true)
                setResult(Activity.RESULT_OK, returnIntent)
                finish()


            }
            else
            {
                btnEdit.text = "Done"
                //Set text field editable
                title.isEnabled = true
                author.isEnabled = true
                desc.isEnabled = true
                btnImage.isEnabled = true

            }

        }

        //Image button
        btnImage.setOnClickListener()
        {
            println("imgBtn clicked!")
            val popupMenu = PopupMenu(this, btnImage)
            popupMenu.menuInflater.inflate(R.menu.imageoptions,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { item ->
                //switch case
                when(item.itemId) {
                    R.id.navigation_library->
                    {
                        Toast.makeText(this, "You Clicked : " + item.title, Toast.LENGTH_SHORT).show()
                        pickImageFromGallery()
                    }
                    R.id.navigation_camera ->
                    {
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
    private fun pickImageFromGallery()
    {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    companion object
    {
        //image pick code
        private val IMAGE_PICK_CODE = 1000
        //camera code
        val REQUEST_IMAGE_CAPTURE = 1
    }


    //handle result whether is picked image or camera capture
    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        //super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK){
            println("Result OK")
            when(requestCode){
                REQUEST_IMAGE_CAPTURE -> {
                    println("Image captured")
                    Glide.with(this).load(currentPhotoPath).into(btnImage)

                    newPhoto = true
                }
                IMAGE_PICK_CODE ->  {
                    println("Load library")
                    currentPhotoPath = (data!!.data).toString()

                    Glide.with(this).load(currentPhotoPath).into(btnImage)

                    println(currentPhotoPath)
                    newPhoto = true
                }

            }
        }
    }

    //create image file
    lateinit var currentPhotoPath: String

    private fun createImageFile(): File
    {
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
    private fun dispatchTakePictureIntent()
    {
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
