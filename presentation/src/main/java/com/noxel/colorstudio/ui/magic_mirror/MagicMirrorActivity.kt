package com.noxel.colorstudio.ui.magic_mirror

import android.app.Activity
import android.content.Intent
import android.hardware.Camera
import android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK
import android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.FrameLayout
import com.noxel.colorstudio.R
import com.noxel.colorstudio.ui.base.BaseActivity
import com.noxel.colorstudio.utils.PICK_PHOTO_REQUEST
import com.noxel.colorstudio.utils.TAKE_PHOTO_REQUEST
import kotlinx.android.synthetic.main.activity_magic_mirror.*
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*



class MagicMirrorActivity: BaseActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null
    val MEDIA_TYPE_IMAGE = 1
    var currentCameraId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic_mirror)

        mCamera = getCameraInstance()

        currentCameraId=  Camera.CameraInfo.CAMERA_FACING_BACK
        mPreview = mCamera?.let {
            // Create our Preview view
            CameraPreview(this, it)
        }

        // Set the Preview view as the content of our activity.
        mPreview?.also {
            val preview: FrameLayout = camera_preview
            preview.addView(it)
        }


        mCamera?.setDisplayOrientation(90)
        mCamera?.startPreview()


        takePhoto.setOnClickListener {
            mCamera?.takePicture(null, null, mPicture)

        }

        switchCameraBtn.setOnClickListener {
            releaseCamera()
            switchCamera()
        }

        galleryBtn.setOnClickListener {
            pickPhotoFromGallery()
        }

    }


    fun getCameraInstance(): Camera? {
        return try {
            Camera.open() // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null // returns null if camera is unavailable
        }
    }


    private var mPicture = Camera.PictureCallback { data, _ ->
        val pictureFile: Uri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE)

        try {
//            val fos = FileOutputStream(pictureFile)
//            fos.write(data)
//            fos.close()
            photo.setImageURI(pictureFile)
        } catch (e: FileNotFoundException) {
            Log.d("ddd", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.d("ddd", "Error accessing file: ${e.message}")
        }
    }



    private fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }

    /** Create a File for saving an image or video */
    private fun getOutputMediaFile(type: Int): File? {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        val mediaStorageDir = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "ColorStudio"
        )
        // Create the storage directory if it does not exist
        mediaStorageDir.apply {
            if (!exists()) {
                if (!mkdirs()) {
                    Log.d("MyCameraApp", "failed to create directory")
                    return null
                }
            }
        }
        // Create a media file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        return when (type) {
            MEDIA_TYPE_IMAGE -> {
                File("${mediaStorageDir.path}${File.separator}IMG_$timeStamp.jpg")
            }
            else -> null
        }
    }


    private fun releaseCamera() {
        // stop and release camera
        if (mCamera != null) {
            mCamera?.stopPreview()
            mCamera?.setPreviewCallback(null)
            mCamera?.release()
            mCamera = null
        }
    }

    var cameraFront = true
    private fun findFrontFacingCamera(): Int {

        var cameraId = -1
        // Search for the front facing camera
        val numberOfCameras = Camera.getNumberOfCameras()
        for (i in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(i, info)
            if (info.facing == CAMERA_FACING_FRONT) {
                cameraId = i
                cameraFront = true
                break
            }
        }
        return cameraId

    }

    private fun findBackFacingCamera(): Int {
        var cameraId = -1
        //Search for the back facing camera
        //get the number of cameras
        val numberOfCameras = Camera.getNumberOfCameras()
        //for every camera check
        for (i in 0 until numberOfCameras) {
            val info = Camera.CameraInfo()
            Camera.getCameraInfo(i, info)
            if (info.facing == CAMERA_FACING_BACK) {
                cameraId = i
                cameraFront = false
                break
            }
        }
        return cameraId
    }


    fun switchCamera(){

        if (cameraFront) {
            val cameraId = findBackFacingCamera()
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview

                mCamera = Camera.open(cameraId)
                mCamera?.setDisplayOrientation(90)
//                mPicture = getPictureCallback()
                mPreview?.refreshCamera(mCamera!!)
            }
        } else {
            val cameraId = findFrontFacingCamera()
            if (cameraId >= 0) {
                //open the backFacingCamera
                //set a picture callback
                //refresh the preview
                mCamera = Camera.open(cameraId)
                mCamera?.setDisplayOrientation(90)
//                mPicture = getPictureCallback()
                mPreview?.refreshCamera(mCamera!!)
            }
        }
    }


    public override fun onResume() {
        super.onResume()
        if (mCamera == null) {
            mCamera = Camera.open()
            mCamera?.setDisplayOrientation(90)
//            mPicture = getPictureCallback()
            mPreview?.refreshCamera(mCamera!!)
            Log.d("nu", "null")
        } else {
            Log.d("nu", "no null")
        }
    }


    override fun onPause() {
        super.onPause()
        //when on Pause, release camera in order to be used from other applications
        releaseCamera()
    }


    //pick a photo from gallery
    private fun pickPhotoFromGallery() {
        val pickImageIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(pickImageIntent, PICK_PHOTO_REQUEST)
    }

    var fileUri: Uri? = null
    //override function that is called once the photo has been taken
    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        if (resultCode == Activity.RESULT_OK
                && requestCode == TAKE_PHOTO_REQUEST) {
            //photo from camera
            //display the photo on the imageview
            photo.setImageURI(fileUri)
        }else if(resultCode == Activity.RESULT_OK
                && requestCode == PICK_PHOTO_REQUEST){
            //photo from gallery
            fileUri = data?.data
            photo.setImageURI(fileUri)
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}