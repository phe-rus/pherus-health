package pherus.health

import androidx.multidex.MultiDexApplication
import com.tencent.mmkv.MMKV

class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        MMKV.initialize(this)
    }
}