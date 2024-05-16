package pherus.health.viewModel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import pherus.health.config.Config.UserAccount
import pherus.health.config.Config.UserAuthentication

class MainViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val storage: FirebaseStorage = Firebase.storage
    private val database: FirebaseDatabase = Firebase.database

    fun isAuthenticated(): Boolean {
        return auth.currentUser === null
    }

    fun isLogIn(email: String, password: String, onListener: () -> Unit) {
    }

    fun isCreateAccount(users: UserAuthentication) {
        auth.createUserWithEmailAndPassword(users.email!!, users.password!!).let {
            it.addOnSuccessListener { value ->
                users.onListener(
                    UserAccount(
                        name = value.user?.displayName,
                        email = value.user?.email,
                        uid = value.user?.uid,
                        verify = value.user?.isEmailVerified
                    )
                )
            }
            it.addOnFailureListener { error ->
                error.localizedMessage?.let { err -> users.onError(err) }
            }
        }
    }

    fun isVerifyEmail(onListener: () -> Unit) {

    }

    fun isResetPassword(email: String, onListener: () -> Unit) {

    }

    fun isLogOut() {
        auth.signOut()
    }

}