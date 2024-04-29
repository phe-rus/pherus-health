package pherus.health.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class MainViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val storage: FirebaseStorage = Firebase.storage
    private val database: FirebaseDatabase = Firebase.database

    fun isAuthenticated(): Boolean {
        return auth.currentUser === null
    }
}