package pherus.health

import androidx.multidex.MultiDexApplication
import com.google.android.material.color.DynamicColors
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.ktx.appCheck
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tencent.mmkv.MMKV
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        MMKV.initialize(this)

        FirebaseApp.initializeApp(this)
        Firebase.database.setPersistenceEnabled(true)
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)
        Firebase.appCheck.installAppCheckProviderFactory(
            PlayIntegrityAppCheckProviderFactory.getInstance(),
        )
        DynamicColors.isDynamicColorAvailable()
    }
}