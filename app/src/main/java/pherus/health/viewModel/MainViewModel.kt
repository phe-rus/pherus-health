package pherus.health.viewModel

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.location.Geocoder
import android.net.Uri
import android.os.Parcelable
import android.provider.OpenableColumns
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
import androidx.lifecycle.viewModelScope
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
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.storage.FirebaseStorage
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.tencent.mmkv.MMKV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import pherus.health.R
import pherus.health.config.Config.CollectionProps
import pherus.health.config.Config.HealthModule
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

    private val _remoteCollection = MutableStateFlow<List<HealthModule>?>(null)
    val remoteCollection: StateFlow<List<HealthModule>?> = _remoteCollection.asStateFlow()

    private val _collection = MutableStateFlow<List<CollectionProps>?>(null)
    val collection: StateFlow<List<CollectionProps>?> = _collection.asStateFlow()

    private var lastFetchedData: PatientInformation? = null


    private val mainStorage by lazy {
        MMKV.mmkvWithID(
            MmkvManager.ID_MAIN, MMKV.MULTI_PROCESS_MODE
        )
    }
    private val settingsStorage by lazy {
        MMKV.mmkvWithID(
            MmkvManager.ID_SETTING, MMKV.MULTI_PROCESS_MODE
        )
    }

    fun initail() {
        fetchModules()
        fetchLocalModules()
        fetchFromDatabase {
            println("error logs: $it")
        }
    }

    @Composable
    fun isProfilePictureHolder(): MutableIntState {
        return rememberSaveable { mutableIntStateOf(R.drawable.profile_mascot) }
    }

    fun isAuthenticated(): Boolean {
        return auth.currentUser != null
    }

    private fun isCurrentUserUid(): String {
        return auth.currentUser?.uid.toString()
    }

    suspend fun isUserAvailable(): Boolean {
        val userUid = isCurrentUserUid()
        return database.getReference("_patients").child(userUid).get().await().exists()
    }

    @Composable
    fun isGoogleAuthentication(
        onAuthComplete: (AuthResult) -> Unit, onAuthError: (ApiException) -> Unit
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

        database.getReference("_patients").child(path).setValue(value)
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
            }.await()
    }

    private fun fetchFromDatabase(
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

    fun uploadImage(
        context: Context, imageUri: Uri, uploadUrl: String? = null, onUploadState: (Boolean) -> Unit
    ) {
        val result = getImageNameAndExtension(context, imageUri)
        storage.run {
            onUploadState(true)
            getReference("patients").child(isCurrentUserUid()).child("profileAvatar")
                .child("${result!!.first}.${result.second}").putFile(imageUri)
                .addOnSuccessListener { taskSnapshot ->
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { downloadUri ->
                        CoroutineScope(Dispatchers.IO).launch {
                            writeToDatabase(key = uploadUrl,
                                value = downloadUri.toString(),
                                onComplete = { success, error ->
                                    CoroutineScope(Dispatchers.Main).launch {
                                        onUploadState(!success)
                                        if (error != null) {
                                            println("Error uploading image: ${error.message}")
                                        }
                                    }
                                })
                        }
                    }?.addOnFailureListener { exception ->
                        onUploadState(false)
                        println("Error getting download URL: ${exception.message}")
                    }
                }.addOnFailureListener { exception ->
                    onUploadState(false)
                    println("Error uploading file: ${exception.message}")
                }
        }
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

    fun isPreferenceRetrieve(key: String, defaultValue: Any? = null): Any? {
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
                    is Set<*> -> mainStorage.decodeStringSet(key) ?: defaultValue
                    is Parcelable -> {
                        val clazz = defaultValue::class.java as? Class<Parcelable>
                        clazz?.let { mainStorage.decodeParcelable(key, it) } ?: defaultValue
                    }

                    else -> null
                }
            }

            else -> defaultValue
        }
    }

    private fun fetchCollection(default: Pair<String, String>) {
        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setDefaultsAsync(mapOf(default.first to default.second))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val featuresPref =
                        isPreferenceRetrieve(default.first, default.second) as? String
                    val featuresJson = remoteConfig.getString(default.first)
                    if (featuresJson.isEmpty()) {
                        logError("No value for key ${default.first}, setting to default.")
                        isPreferenceStore(default.first, default.second)
                    } else if (featuresPref != featuresJson) {
                        isPreferenceStore(default.first, featuresJson)
                    }
                } else {
                    logError("Fetch failed")
                }
            }.addOnFailureListener {
                logError("Fetch failed with exception: ${it.message}")
            }
    }

    private fun fetchModules() {
        viewModelScope.launch {
            fetchCollection("health_features" to "[]")
            delay(1000)
            fetchCollection("collection_features" to "[]")
        }
    }

    private fun logError(message: String) {
        println("Error: $message")
    }

    private fun fetchLocalModules() {
        isPreferenceRetrieve("health_features", "[]")?.let {
            runCatching {
                parseFeatures(it.toString())
            }.onSuccess {
                println("health features: $it")
                _remoteCollection.value = it
            }.onFailure {
                logError("Failed to parse features JSON: ${it.message}")
            }
        }

        isPreferenceRetrieve("collection_features", "[]")?.let {
            runCatching {
                parseCollection(it.toString())
            }.onSuccess {
                println("collection features: $it")
                _collection.value = it
            }.onFailure {
                logError("Failed to parse collection JSON: ${it.message}")
            }
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
        context: Context,
        latitude: Double,
        longitude: Double,
        onAddressReceived: (String?, String?) -> Unit
    ) {
        val geocoder = Geocoder(context, Locale.getDefault())
        try {
            val addresses = geocoder.getFromLocation(latitude, longitude, 1)
            if (!addresses.isNullOrEmpty()) {
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

    private fun getCountryCode(countryName: String): String? {
        val locales = Locale.getAvailableLocales()
        for (locale in locales) {
            if (locale.displayCountry.equals(countryName, ignoreCase = true)) {
                return locale.country
            }
        }
        return null
    }

    private fun parseFeatures(json: String): List<HealthModule> {
        val gson = Gson()
        val type = object : TypeToken<List<HealthModule>>() {}.type
        val modules: List<HealthModule> = gson.fromJson(json, type)
        return modules
    }

    private fun parseCollection(json: String): List<CollectionProps> {
        val gson = Gson()
        val type = object : TypeToken<List<CollectionProps>>() {}.type
        val modules: List<CollectionProps> = gson.fromJson(json, type)
        return modules
    }

    private fun getImageNameAndExtension(context: Context, imageUri: Uri): Pair<String, String>? {
        var name: String? = null
        var extension: String? = null

        // First, try to get the display name and extension from the ContentResolver
        val cursor: Cursor? = context.contentResolver.query(imageUri, null, null, null, null)
        cursor?.use {
            if (it.moveToFirst()) {
                val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    name = it.getString(nameIndex)
                }
            }
        }

        // If the name is found, extract the extension
        name?.let {
            val dotIndex = it.lastIndexOf('.')
            if (dotIndex != -1) {
                extension = it.substring(dotIndex + 1)
            }
        }

        // If the name is not found using ContentResolver, try to extract from the Uri path
        if (name == null) {
            val path = imageUri.path
            path?.let {
                val lastSlashIndex = it.lastIndexOf('/')
                if (lastSlashIndex != -1) {
                    name = it.substring(lastSlashIndex + 1)
                    val dotIndex = name?.lastIndexOf('.')
                    if (dotIndex != -1) {
                        if (dotIndex != null) {
                            extension = name?.substring(dotIndex + 1)
                        }
                    }
                }
            }
        }

        return if (name != null && extension != null) Pair(name!!, extension!!) else null
    }

    suspend fun generatePatientsId(
        countryName: String
    ) {
        if (isAuthenticated()) {
            if (isUserAvailable()) {
                val userRef = database.getReference("_patients").child(isCurrentUserUid())
                    .child("basicInformations").child("patientsId")
                val existingId = userRef.get().await().getValue(String::class.java)
                if (existingId.isNullOrEmpty()) {
                    // Reference to the _patients node in the database
                    val patientsRef = database.getReference("_patients")

                    // Fetch all existing patient IDs
                    val existingIds = mutableListOf<String>()
                    val snapshot = patientsRef.get().await()
                    for (snaps in snapshot.children) {
                        val patientId = snaps.child("basicInformations").child("patientsId")
                            .getValue(String::class.java)
                        if (patientId != null) {
                            existingIds.add(patientId)
                        }
                    }

                    // Generate the next unique ID
                    val patientsIdentifier = "PAT"
                    val countryCode = getCountryCode(countryName = countryName)

                    val generatedFirstFour = generateFirstFour(existingIds)
                    val generateSecondFour = generateSecondFour(existingIds)

                    when {
                        countryCode!!.isNotEmpty() -> {
                            val newUserId =
                                "$patientsIdentifier-$generatedFirstFour-$generateSecondFour$countryCode"
                            when {
                                newUserId.isNotEmpty() -> {
                                    // Save the new unique ID to the current user's profile
                                    patientsRef.child(isCurrentUserUid()).child("basicInformations")
                                        .child("patientsId").setValue(newUserId)

                                    patientsRef.child(isCurrentUserUid())
                                        .child("contactInformation").child("countryResident")
                                        .setValue(countryName)
                                }

                                else -> {
                                    return
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun generateFirstFour(existingIds: List<String>): String {
        // Generate 4 sequential digits and alphabets starting from 00AA
        val pattern = "^[0-9]{2}[A-Z]{2}$".toRegex()
        val existingFirstFour =
            existingIds.mapNotNull { it.split("-").getOrNull(1) }.filter { pattern.matches(it) }
        var newFirstFour = "00AA"
        if (existingFirstFour.isNotEmpty()) {
            val lastFirstFour = existingFirstFour.maxOrNull()
            newFirstFour = incrementFirstFour(lastFirstFour!!)
        }
        return newFirstFour
    }

    private fun incrementFirstFour(lastFirstFour: String): String {
        val digits = lastFirstFour.substring(0, 2).toInt()
        val letters = lastFirstFour.substring(2)
        var newDigits = digits
        var newLetters = letters

        if (letters == "ZZ") {
            newDigits += 1
            newLetters = "AA"
        } else {
            val charArray = newLetters.toCharArray()
            if (charArray[1] == 'Z') {
                charArray[0] = charArray[0] + 1
                charArray[1] = 'A'
            } else {
                charArray[1] = charArray[1] + 1
            }
            newLetters = String(charArray)
        }

        return "%02d%s".format(newDigits, newLetters)
    }

    private fun generateSecondFour(existingIds: List<String>): String {
        // Generate sequential IDs starting from A001
        val pattern = "^[A-Z][0-9]{3}$".toRegex()
        val existingSecondFour =
            existingIds.mapNotNull { it.split("-").getOrNull(2) }.filter { pattern.matches(it) }
        var newSecondFour = "A001"
        if (existingSecondFour.isNotEmpty()) {
            val lastSecondFour = existingSecondFour.maxOrNull()
            newSecondFour = incrementSecondFour(lastSecondFour!!)
        }
        return newSecondFour
    }

    private fun incrementSecondFour(lastSecondFour: String): String {
        val letter = lastSecondFour.substring(0, 1)
        val digits = lastSecondFour.substring(1).toInt()

        var newLetter = letter
        var newDigits = digits + 1

        if (newDigits > 999) {
            newLetter = (letter[0] + 1).toString()
            newDigits = 1
        }

        return "%s%03d".format(newLetter, newDigits)
    }
}