package com.grace.mysecondafteassignment

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class DetailsActivity : AppCompatActivity() {
    var button_call:Button? = null
    var button_sms:Button? = null
    var button_dial:Button? = null
    var button_email:Button? = null
    var button_share:Button? = null
    var button_mpesa:Button? = null
    var button_pics:Button? = null
    var image_view:ImageView? = null
    private val PERMISSION_CODE = 1000;
    private val IMAGE_CAPTURE_CODE = 1001
    var image_uri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        button_call = findViewById(R.id.mBtnCall)
        button_sms = findViewById(R.id.mBtnSms)
        button_dial = findViewById(R.id.mBtnDial)
        button_email = findViewById(R.id.mBtnEmail)
        button_share = findViewById(R.id.mBtnShare)
        button_mpesa = findViewById(R.id.mBtnMpesa)
        button_pics = findViewById(R.id.mBtnPics)
        image_view = findViewById(R.id.mPics)


        button_call!!.setOnClickListener {
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + ""))
            if (ContextCompat.checkSelfPermission(this@DetailsActivity, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@DetailsActivity,
                    arrayOf(Manifest.permission.CALL_PHONE),
                    1
                )
            } else {
                startActivity(intent)
            }
        }
        button_sms!!.setOnClickListener {
            val uri = Uri.parse("smsto:YOUR_SMS_NUMBER")
            val intent = Intent(Intent.ACTION_SENDTO, uri)
            intent.putExtra("sms_body", "The SMS text")
            startActivity(intent)
        }
        button_dial!!.setOnClickListener {
            val phone = ""
            val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
            startActivity(intent)

        }
        button_email!!.setOnClickListener {
            val emailIntent =
                Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "abc@gmail.com", null))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))

        }
        button_share!!.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hey, download this app!")
            startActivity(shareIntent)

        }
        button_mpesa!!.setOnClickListener {
            val simToolKitLaunchIntent: Intent? =
                this@DetailsActivity.getPackageManager().getLaunchIntentForPackage("com.android.stk")
            simToolKitLaunchIntent?.let { startActivity(it) }
        }
        button_pics!!.setOnClickListener {
            private fun openCamera() {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "New Picture")
                values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera")
                image_uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                //camera intent
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri)
                startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE)
            }
            override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
                //called when user presses ALLOW or DENY from Permission Request Popup
                when(requestCode){
                    PERMISSION_CODE -> {
                        if (grantResults.size > 0 && grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED){
                            //permission from popup was granted
                            openCamera()
                        }
                        else{
                            //permission from popup was denied
                            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
            override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
                super.onActivityResult(requestCode, resultCode, data)
                //called when image was captured from camera intent
                if (resultCode == Activity.RESULT_OK){
                    //set image captured to image view
                    button_pics.setImageURI(image_uri)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.CAMERA)
                        == PackageManager.PERMISSION_DENIED ||
                        checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_DENIED){
                        //permission was not enabled
                        val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        //show popup to request permission
                        var PERMISSION_CODE = 0
                        requestPermissions(permission, PERMISSION_CODE)
                    }
                    else{
                        //permission already granted
                        openCamera()
                    }
                }
                else{
                    //system os is < marshmallow
                    openCamera()
                }
            }

        }
    }
}

private fun Button.setImageURI(imageUri: Uri?) {

}
