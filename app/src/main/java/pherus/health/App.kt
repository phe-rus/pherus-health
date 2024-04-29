package pherus.health

import androidx.multidex.MultiDexApplication
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.tencent.mmkv.MMKV

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        MMKV.initialize(this)

        FirebaseApp.initializeApp(this).let {
            Firebase.database.setPersistenceEnabled(true)
        }
    }
}