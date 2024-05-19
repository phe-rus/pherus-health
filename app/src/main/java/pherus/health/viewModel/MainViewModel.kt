package pherus.health.viewModel

import android.content.Intent
import android.location.Geocoder
import android.os.Parcelable
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseException
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.tencent.mmkv.MMKV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import pherus.health.R
import pherus.health.config.Config.PatientInformation
import java.io.IOException
import java.util.Locale

@HiltViewModel
class MainViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val storage: FirebaseStorage = FirebaseStorage.getInstance()
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()

    private val _usrCollection = MutableStateFlow<PatientInformation?>(null)
    val usrCollection: StateFlow<PatientInformation?> = _usrCollection.asStateFlow()

    private var lastFetchedData: PatientInformation? = null


    private val mainStorage by lazy {
        MMKV.mmkvWithID(
            MmkvManager.ID_MAIN,
            MMKV.MULTI_PROCESS_MODE
        )
    }
    private val settingsStorage by lazy {
        MMKV.mmkvWithID(
            MmkvManager.ID_SETTING,
            MMKV.MULTI_PROCESS_MODE
        )
    }

    @Composable
    fun isProfilePictureHolder(): MutableIntState {
        return rememberSaveable { mutableIntStateOf(R.drawable.profile_mascot) }
    }

    fun isAuthenticated(): Boolean {
        return auth.currentUser === null
    }

    private fun isCurrentUserUid(): String {
        return auth.currentUser?.uid.toString()
    }

    suspend fun isUserAvailable(): Boolean {
        val userUid = isCurrentUserUid()
        return !database.getReference("_patients").child(userUid).get().await().exists()
    }

    @Composable
    fun isGoogleAuthentication(
        onAuthComplete: (AuthResult) -> Unit,
        onAuthError: (ApiException) -> Unit
    ): ManagedActivityResultLauncher<Intent, ActivityResult> {
        val scope = rememberCoroutineScope()
        return rememberLauncherForActivityResult(StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                scope.launch {
                    val authResult = Firebase.auth.signInWithCredential(credential).await()
                    onAuthComplete(authResult)
                }
            } catch (e: ApiException) {
                onAuthError(e)
            }
        }
    }

    suspend fun writeToDatabase(
        key: String? = null,
        value: Any,
        onComplete: (success: Boolean, error: DatabaseException?) -> Unit
    ) {
        val userUid = isCurrentUserUid() // Exit if UID unavailable
        val path = if (key != null) {
            "$userUid/$key"
        } else {
            userUid
        }

        database.getReference("_patients").child(path)
            .setValue(value)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onComplete(true, null)
                } else {
                    val exception = task.exception
                    if (exception is DatabaseException) {
                        onComplete(false, exception)
                    } else {
                        onComplete(false, null)
                    }
                }
            }
            .await()
    }

    fun fetchFromDatabase(
        errorHandler: (String) -> Unit
    ) {
        val userUid = isCurrentUserUid() // Exit if UID unavailable?: null
        if (userUid.isNotEmpty()) {
            val reference = database.getReference("_patients").child(userUid)
            reference.keepSynced(true)
            reference.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.getValue(PatientInformation::class.java)?.let { pat ->
                        if (pat != lastFetchedData) {
                            _usrCollection.value = pat
                            lastFetchedData = pat
                        }
                    } ?: run {
                        errorHandler("User information not found")
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    errorHandler(error.message)
                }
            })
        } else {
            errorHandler("User UID is unavailable")
        }
    }

    fun isLogOut() {
        auth.signOut()
    }

    fun isPreferenceStore(key: String, value: Any): Boolean {
        return when (value) {
            is Boolean -> mainStorage.encode(key, value)
            is String -> mainStorage.encode(key, value)
            is Int -> mainStorage.encode(key, value)
            is Long -> mainStorage.encode(key, value)
            is Float -> mainStorage.encode(key, value)
            is Double -> mainStorage.encode(key, value)
            is ByteArray -> mainStorage.encode(key, value)
            is Set<*> -> {
                if (value.all { it is String }) {
                    mainStorage.encode(key, value as Set<String>)
                } else {
                    false
                }
            }

            is List<*> -> {
                // Convert List to a Set if all elements are Strings
                if (value.all { it is String }) {
                    mainStorage.encode(key, value.toSet() as Set<String>)
                } else {
                    false
                }
            }

            is Array<*> -> {
                // Convert Array to a Set if all elements are Strings
                if (value.all { it is String }) {
                    mainStorage.encode(key, value.toSet() as Set<String>)
                } else {
                    false
                }
            }

            else -> false
        }
    }

    fun isPreferenceRetrieve(
        key: String,
        defaultValue: Any? = null
    ): Any? {
        return when {
            mainStorage.containsKey(key) -> {
                when (defaultValue) {
                    is Boolean -> mainStorage.decodeBool(key, defaultValue)
                    is String -> mainStorage.decodeString(key, defaultValue)
                    is Int -> mainStorage.decodeInt(key, defaultValue)
                    is Long -> mainStorage.decodeLong(key, defaultValue)
                    is Float -> mainStorage.decodeFloat(key, defaultValue)
                    is Double -> mainStorage.decodeDouble(key, defaultValue)
                    is ByteArray -> mainStorage.decodeBytes(key)
                    is Set<*> -> {
                        val result = mainStorage.decodeStringSet(key)
                        if (result != null) result else defaultValue
                    }

                    else -> {
                        // For Parcelable and other custom types
                        if (defaultValue is Parcelable) {
                            val clazz = defaultValue::class.java as? Class<Parcelable>
                            if (clazz != null) {
                                mainStorage.decodeParcelable(key, clazz) ?: defaultValue
                            } else {
                                null
                            }
                        } else {
                            null
                        }
                    }
                }
            }

            else -> defaultValue
        }
    }

    fun getLocation(
        fusedLocationClient: FusedLocationProviderClient,
        onLocationReceived: (Double, Double) -> Unit
    ) {
        try {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val latitude = location.latitude
                    val longitude = location.longitude
                    onLocationReceived(latitude, longitude)
                }
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun getCountryFromLocation(
        context: android.content.Context,
        latitude: Double,
        longitude: Double,
        onAddressReceived: (String?, String?) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (addresses != null && addresses.isNotEmpty()) {
                val countryName = addresses[0].countryName
                val locality = addresses[0].locality
                onAddressReceived(countryName, locality)
            } else {
                onAddressReceived(null, null)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            onAddressReceived(null, null)
        }
    }

    fun getCountryCode(countryName: String): String? {
        val locales = Locale.getAvailableLocales()
        for (locale in locales) {
            if (locale.displayCountry.equals(countryName, ignoreCase = true)) {
                return locale.country
            }
        }
        return null
    }
}