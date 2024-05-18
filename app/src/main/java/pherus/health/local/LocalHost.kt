package pherus.health.local

/**
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [], version = 1, exportSchema = false)
abstract class LocalHost : RoomDatabase() {
companion object {
@Volatile
private var INSTANCE: LocalHost? = null

fun getInstance(context: Context): LocalHost {
synchronized(this) {
var instance = INSTANCE
if (instance == null) {
instance = Room.databaseBuilder(
context.applicationContext,
LocalHost::class.java,
"localhost_database"
).fallbackToDestructiveMigration()
.build()
INSTANCE = instance
}
return instance
}
}
}
} **/