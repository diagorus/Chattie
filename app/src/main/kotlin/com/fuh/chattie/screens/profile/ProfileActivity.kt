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
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.fuh.chattie.R
import com.fuh.chattie.screens.model.User
import com.fuh.chattie.util.BaseToolbarActivity
import com.fuh.chattie.util.ProgressNotificationManager
import com.fuh.chattie.util.extentions.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import io.reactivex.Observable
import kotlinx.android.synthetic.main.profile_activity.*
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Completable


/**
 * Created by lll on 11.08.2017.
 */
class ProfileActivity : BaseToolbarActivity(), ProfileContract.View {

    companion object {
        fun newIntent(context: Context): Intent =
                Intent(context, ProfileActivity::class.java)
    }

    override lateinit var presenter: ProfileContract.Presenter

    private val uriForCameraPhoto by lazy { getUriForCameraPhoto("Profile_photo.jpg")!! }

    private var newPhotoUri: Uri? = null

    override fun getLayoutId(): Int = R.layout.profile_activity

    override fun ActionBar.init() {
        title = "Profile"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        presenter = ProfilePresenter(this)
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
                val notificationManager = ProgressNotificationManager(this)

                val userName = etProfileName.textValue
                updateUserProfile(User(userName, newPhotoUri))
                        .doOnSubscribe {
                            notificationManager.startDeterminate(
                                    0,
                                    ProgressNotificationManager.Options("Uploading...", "Profile updating", R.mipmap.ic_launcher)
                            )
                        }
                        .subscribe(
                                {
                                    notificationManager.cancel(0)
                                }, {
                                    notificationManager.fail(
                                            0,
                                            ProgressNotificationManager.Options("Uploading failed", "Profile updating", R.mipmap.ic_launcher)
                                    )
                                }
                        )
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
                loadImageByUri(ivProfilePhoto, it)
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
        loadImageByUri(ivProfilePhoto, photoUri)

        val userName = user.name ?: getString(R.string.all_user_name_default)
        etProfileName.textValue = userName
    }

    private fun uploadProfilePhotoToFirebase(uri: Uri): Observable<Progress> {
        val userId = FirebaseAuth.getInstance().currentUser!!.uid

        return Observable.create<Progress> { emitter ->
            FirebaseStorage.getInstance()
                    .reference
                    .child("image/profilePhoto_$userId")
                    .putFile(uri)
                    .addOnCompleteListener { emitter.onComplete() }
                    .addOnFailureListener { emitter.onError(it) }
                    .addOnProgressListener {
                        val progress = (100.0 * it.bytesTransferred) / it.totalByteCount

                        emitter.onNext(Progress(progress))
                    }
        }


    }

    private fun updateUserProfile(user: User): Completable {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!

        val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(user.name)
                .setPhotoUri(user.photoUri)
                .build()

        return Completable.create { emitter ->
            firebaseUser.updateProfile(profileUpdates)
                    .addOnFailureListener { emitter.onError(it) }
                    .addOnCompleteListener { emitter.onComplete() }
        }
    }

    data class Progress(val percents: Double)
}