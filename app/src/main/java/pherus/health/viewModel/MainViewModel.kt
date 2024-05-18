package pherus.health.viewModel

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts.StartActivityForResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import pherus.health.R
import pherus.health.oauth.Authobjects.GeneralHistoryProp
import pherus.health.oauth.Authobjects.HealthHistoryProp
import pherus.health.oauth.Authobjects.ProfileProps
import pherus.health.oauth.Authobjects.ValueProps

class MainViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val storage: FirebaseStorage = Firebase.storage
    private val database: FirebaseDatabase = Firebase.database

    @Composable
    fun isProfilePictureHolder(): MutableIntState {
        return rememberSaveable { mutableIntStateOf(R.drawable.profile_mascot) }
    }

    @Composable
    fun isProfileCollections(): MutableList<ProfileProps> {
        return mutableListOf(
            ProfileProps(
                title = stringResource(id = R.string.basic_info_title),
                subtitle = stringResource(id = R.string.basic_info_subtitle),
                list = mutableListOf(
                    ValueProps(
                        value = "Preferred Name | Nickname | Acquired name",
                        onchangelistener = {}
                    ),
                    ValueProps(
                        value = "Gender Identity",
                        onchangelistener = {}
                    )
                )
            ),
            ProfileProps(
                title = stringResource(id = R.string.contact_info_title),
                subtitle = stringResource(id = R.string.contact_info_subtitle),
                list = mutableListOf(
                    ValueProps(
                        value = "Date Of Birth",
                        onchangelistener = {}
                    ),
                    ValueProps(
                        value = "Phone Number",
                        onchangelistener = {}
                    ),
                    ValueProps(
                        value = "Local Address",
                        onchangelistener = {}
                    ),
                    ValueProps(
                        value = "Country",
                        onchangelistener = {}
                    )
                )
            )
        )
    }

    @Composable
    fun isHealthHistoryCollections(): MutableList<GeneralHistoryProp> {
        return mutableListOf(
            GeneralHistoryProp(
                title = "Core Healthcare Assessment Questions",
                subTitle = "These are the questions that primarily focus on assessing the health conditions and the overall well-being of the patients.",
                list = mutableListOf(
                    HealthHistoryProp(
                        title = "Do you have any chronic diseases?",
                        list = mutableListOf("Yes", "No")
                    ),
                    HealthHistoryProp(
                        title = "Do you have any hereditary conditions/diseases?",
                        list = mutableListOf(
                            "High blood pressure",
                            "Diabetes",
                            "Hemophilia",
                            "Thalassemia",
                            "Huntington",
                            "Asthma",
                            "HIV/AIDs",
                            "Cancer",
                            "Others (Please Specify)"
                        )
                    ),
                    HealthHistoryProp(
                        title = "Are you habitual to drugs and alcohol?",
                        list = mutableListOf(
                            "Yes to both",
                            "Only to drugs",
                            "Only to alcohol",
                            "I am not habituated to either"
                        )
                    ),
                )
            ),
            GeneralHistoryProp(
                title = "Social Care Assessment Questions",
                subTitle = "These are the questions that primarily focus on your social medical aspects to health.",
                list = mutableListOf(
                    HealthHistoryProp(
                        title = "What are your sleeping patterns ?",
                        list = mutableListOf("Prefect", "Moderate", "Insomnia", "On and Off")
                    ),
                    HealthHistoryProp(
                        title = "Whats you relationship to screen time ?",
                        list = mutableListOf("High", "Medium", "Low", "Addicted")
                    ),
                    HealthHistoryProp(
                        title = "Are you social ?",
                        list = mutableListOf("High", "Medium", "Low")
                    )
                )
            ),
            GeneralHistoryProp(
                title = "Patient Consent and Donation Preferences",
                subTitle = "Your responses to the following questions will help us understand your preferences regarding donations and participation in medical research. This information is crucial for providing comprehensive care and assistance.",
                list = mutableListOf(
                    HealthHistoryProp(
                        title = "Do you consent to blood donation?",
                        list = mutableListOf("Yes", "No")
                    ),
                    HealthHistoryProp(
                        title = "Are you willing to donate organs?",
                        list = mutableListOf("Yes", "No", "Only to family")
                    ),
                    HealthHistoryProp(
                        title = "Would you like to participate in health research studies?",
                        list = mutableListOf("Yes", "No")
                    ),
                    HealthHistoryProp(
                        title = "Would You wish to have a virtual support system of friends anonymously or nonymously to talk to about anything?",
                        list = mutableListOf("Yes", "No", "Anonymously", "Nonymously")
                    )
                )
            ),
            GeneralHistoryProp(
                title = "Additional Questions",
                list = mutableListOf(
                    HealthHistoryProp(
                        title = "Do you follow any specific diet?",
                        list = mutableListOf("Yes", "No")
                    ),
                    HealthHistoryProp(
                        title = "How often do you exercise in a week?",
                        list = mutableListOf("Never", "1-2 times", "3-4 times", "5 or more times")
                    ),
                    HealthHistoryProp(
                        title = "Have you ever been diagnosed with a mental health condition?",
                        list = mutableListOf("Yes", "No")
                    ),
                    HealthHistoryProp(
                        title = "Are you up-to-date with your vaccinations?",
                        list = mutableListOf("Yes", "No")
                    ),
                    HealthHistoryProp(
                        title = "Do you have a strong support system of family and friends?",
                        list = mutableListOf("Yes", "No")
                    ),
                    HealthHistoryProp(
                        title = "Do you consent to having your medical data used for research purposes?",
                        list = mutableListOf("Yes", "No")
                    )
                )
            ),
        )
    }

    fun isAuthenticated(): Boolean {
        return auth.currentUser === null
    }

    private fun isCurrentUserUid(): String {
        return auth.currentUser?.uid.toString()
    }

    fun isUserAvailable(
        onListener: (String) -> Unit? = {}
    ): Boolean {
        val exists = mutableStateOf(false)
        if (isAuthenticated()) {
            database.getReference("_patients").child(isCurrentUserUid())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        exists.value = snapshot.exists()
                    }

                    override fun onCancelled(error: DatabaseError) {
                        onListener(error.message)
                    }
                })
        }
        return exists.value
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

    fun isDatabaseWrite(): String {
        if (isAuthenticated()) {
            return "Please sign in"
        }

        return database.getReference("").child("").let {
            it.setValue(true)
            "Success"
        }
    }


    fun isLogOut() {
        auth.signOut()
    }
}