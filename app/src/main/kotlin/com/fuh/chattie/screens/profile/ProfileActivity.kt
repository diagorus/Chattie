package com.fuh.chattie.screens.profile

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.Menu
import android.view.MenuItem
import com.firebase.ui.auth.AuthUI
import com.fuh.chattie.R
import com.fuh.chattie.model.User
import com.fuh.chattie.model.UserDataSource
import com.fuh.chattie.util.BaseToolbarActivity
import com.fuh.chattie.util.ProgressNotificationManager
import com.fuh.chattie.util.extentions.*
import kotlinx.android.synthetic.main.profile_activity.*

/**
 * Created by lll on 11.08.2017.
 */
class ProfileActivity : BaseToolbarActivity(), ProfileContract.View {

    companion object {
        fun newIntent(context: Context): Intent =
                Intent(context, ProfileActivity::class.java)

        private const val NOTIFICATION_PROFILE_UPDATE = 13
    }

    override lateinit var presenter: ProfileContract.Presenter

    private val uriForCameraPhoto by lazy { getUriForCameraPhoto("Profile_photo.jpg")!! }
    private val notificationManager = ProgressNotificationManager(this)

    private var newPhotoUri: Uri? = null

    override fun getLayoutId(): Int = R.layout.profile_activity

    override fun ActionBar.init() {
        title = "Profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ProfilePresenter(this, UserDataSource())
        presenter.start()

        btnProfileLogout.setOnClickListener {
            AuthUI.getInstance().signOut(this)
                    .addOnCompleteListener {
                        toast("You have been signed out.")

                        // Close activity
                        val intent = Intent(Intent.ACTION_MAIN)
                                .apply {
                                    addCategory(Intent.CATEGORY_HOME)
                                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                }
                        startActivity(intent)
                    }
        }

        ivProfilePhoto.setOnClickListener {
            showChooserPhotoFrom(uriForCameraPhoto)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.profile_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.profile_menu_save -> {


                val userName = etProfileName.textValue

                presenter.updateUser(User(userName, newPhotoUri))
            }
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when(requestCode) {
                REQUEST_CAMERA -> {
                    newPhotoUri = uriForCameraPhoto
                }
                REQUEST_GALLERY -> {
                    newPhotoUri = data?.data
                }
            }

            newPhotoUri?.let {
                ivProfilePhoto.loadImageByUri(it)
            }
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val isPermissionsGranted = grantResults.all { it == PackageManager.PERMISSION_GRANTED }

        if (isPermissionsGranted) {
            when(requestCode) {
                REQUEST_PERMISSION_CAMERA -> startCamera(uriForCameraPhoto)
                REQUEST_PERMISSION_STORAGE -> startGallery()
            }
        }
    }

    override fun showUser(user: User) {
        val photoUri = user.photoUri ?: resourceToUri(R.drawable.no_avatar)
        ivProfilePhoto.loadImageByUri(photoUri)

        val userName = user.name ?: getString(R.string.all_user_name_default)
        etProfileName.textValue = userName
    }

    override fun showUserUpdateStart() {
        notificationManager.startDeterminate(
                NOTIFICATION_PROFILE_UPDATE,
                ProgressNotificationManager.Options("Uploading...", "Profile updating", R.mipmap.ic_launcher)
        )
    }

    override fun showUserUpdateComplete() {
        notificationManager.cancel(NOTIFICATION_PROFILE_UPDATE)
    }

    override fun showUserUpdateFail() {
        notificationManager.fail(
                NOTIFICATION_PROFILE_UPDATE,
                ProgressNotificationManager.Options("Uploading failed", "Profile updating", R.mipmap.ic_launcher)
        )
    }
}