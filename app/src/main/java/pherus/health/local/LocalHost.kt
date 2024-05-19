package pherus.health.local

/**
@Database(entities = [PatientInformationDao::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class LocalHost : RoomDatabase() {
abstract fun paitentInfo(): PatientInformationDao

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
}
 **/