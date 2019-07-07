package com.noxel.colorstudio.ui.magic_mirror

import android.app.Activity
import android.content.Intent
import android.hardware.Camera
import android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK
import android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.FrameLayout
import com.noxel.colorstudio.R
import com.noxel.colorstudio.ui.base.BaseActivity
import com.noxel.colorstudio.utils.MEDIA_TYPE_IMAGE
import com.noxel.colorstudio.utils.PICK_PHOTO_REQUEST
import com.noxel.colorstudio.utils.CreateDirectoryOnStorage
import kotlinx.android.synthetic.main.activity_magic_mirror.*
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


class MagicMirrorActivity: BaseActivity() {

    private var mCamera: Camera? = null
    private var mPreview: CameraPreview? = null
    var fileUri: Uri? = null
    var imageFile : File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_magic_mirror)

        mCamera = getCameraInstance()

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

    private var mPicture = Camera.PictureCallback { data, _ ->
        fileUri = CreateDirectoryOnStorage().getOutputMediaFileUri(MEDIA_TYPE_IMAGE)
        imageFile = File(fileUri!!.path)

        try {
            val fos = FileOutputStream(imageFile)
            fos.write(data)
            fos.close()
            photo.setImageURI(fileUri)
            fileUri?.let { it ->
                navigator.navigateToColorCategoryActivity(this, it)
            }
        } catch (e: FileNotFoundException) {
            Log.d("ddd", "File not found: ${e.message}")
        } catch (e: IOException) {
            Log.d("ddd", "Error accessing file: ${e.message}")
        }
    }


    fun getCameraInstance(): Camera? {
        return try {
            Camera.open() // attempt to get a Camera instance
        } catch (e: Exception) {
            // Camera is not available (in use or does not exist)
            null
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
        val numberOfCameras = Camera.getNumberOfCameras()
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
                mCamera = Camera.open(cameraId)
                mCamera?.setDisplayOrientation(90)
                mPreview?.refreshCamera(mCamera!!)
            }
        } else {
            val cameraId = findFrontFacingCamera()
            if (cameraId >= 0) {
                mCamera = Camera.open(cameraId)
                mCamera?.setDisplayOrientation(90)
                mPreview?.refreshCamera(mCamera!!)
            }
        }
    }


    public override fun onResume() {
        super.onResume()
        if (mCamera == null) {
            mCamera = Camera.open()
            mCamera?.setDisplayOrientation(90)
            mPreview?.refreshCamera(mCamera!!)
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


    //override function that is called once the photo has been taken
    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  data: Intent?) {
        if(resultCode == Activity.RESULT_OK
                && requestCode == PICK_PHOTO_REQUEST){
            //photo from gallery
            fileUri = data?.data
            photo.setImageURI(fileUri)
            fileUri?.let {
                it -> navigator.navigateToColorCategoryActivity(this, it)
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}